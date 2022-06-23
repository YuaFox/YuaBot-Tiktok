package dev.yuafox.yuabot.plugins.tiktok.media;

import dev.yuafox.yuabot.data.Data;
import dev.yuafox.yuabot.data.Source;

public class TiktokSource extends Source {

    @Data(id="videoId", unique = true)
    public String videoId;

}
