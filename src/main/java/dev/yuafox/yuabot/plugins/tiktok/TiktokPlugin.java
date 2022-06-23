package dev.yuafox.yuabot.plugins.tiktok;

import dev.yuafox.yuabot.YuaBot;
import dev.yuafox.yuabot.data.Media;
import dev.yuafox.yuabot.plugins.DataController;
import dev.yuafox.yuabot.plugins.ActionHandler;
import dev.yuafox.yuabot.plugins.Plugin;
import dev.yuafox.yuabot.plugins.tiktok.api.TiktokApi;
import dev.yuafox.yuabot.plugins.tiktok.media.TiktokSource;
import dev.yuafox.yuabot.utils.Https;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class TiktokPlugin extends Plugin implements DataController {

    @Override
    public void onLoad(){
        YuaBot.registerActionHandler("tiktok", this);
    }

    @Override
    public String getSourceName(){
        return "Tiktok";
    }

    @ActionHandler(action="install")
    public void install(){
        try {
            File folder = new File(this.getBaseFolder(), "media");
            folder.mkdirs();
            YuaBot.installMediaSource(this, TiktokSource.class);
        }catch (Exception exception){
            YuaBot.LOGGER.error("Unhandled error", exception);
        }
    }

    @ActionHandler(action="fetch")
    public void fetch() throws IOException, SQLException, IllegalAccessException {
        String username = YuaBot.params.get("username") != null ? YuaBot.params.get("username").get(0) : null;
        String url = YuaBot.params.get("url") != null ? YuaBot.params.get("url").get(0) : null;
        String cookie = YuaBot.params.get("cookie") != null ? YuaBot.params.get("cookie").get(0) : null;

        if(username != null) {
            for (String videoUrl : TiktokApi.getVideos(username, cookie)) {
                try {
                    Thread.sleep((long) (Math.random() * 5000 + 1000));
                    this.fetchVideo(videoUrl);
                    Thread.sleep((long) (Math.random() * 5000 + 1000));
                }catch (Exception e){
                    YuaBot.LOGGER.error("Unhandled error", e);
                }
            }
        }

        if(url != null) {
            this.fetchVideo(url);
        }
    }

    @ActionHandler(action="captcha")
    public void captcha() throws IOException {
        YuaBot.LOGGER.info("Captcha URL: {}",TiktokApi.getCaptchaUrl());
    }

    private void fetchVideo(String url) throws IOException, SQLException, IllegalAccessException {
        String urlLocal = new File(this.getBaseFolder(), "media").getPath() + "/" + TiktokApi.getVideoAuthor(url) + "." + TiktokApi.getVideoId(url) + ".mp4";
        String videoDownloadUrl = TiktokApi.getVideoUrl(url);

        TiktokSource tiktokSource = new TiktokSource();
        Media media = new Media();
        media.text = "";
        media.media = Https.download(videoDownloadUrl, urlLocal);
        tiktokSource.author = TiktokApi.getVideoAuthor(url);
        tiktokSource.videoId = TiktokApi.getVideoId(url);
        int sourceId = YuaBot.createSource(this, tiktokSource);
        YuaBot.createMedia(sourceId, media);
    }
}
