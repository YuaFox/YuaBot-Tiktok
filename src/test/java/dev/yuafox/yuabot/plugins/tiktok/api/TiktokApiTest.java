package dev.yuafox.yuabot.plugins.tiktok.api;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TiktokApiTest {

    @Test
    void getVideoId() throws IOException {
        String videoIdWithParams = TiktokApi.getVideoId("https://www.tiktok.com/@zaphira.art/video/7111328087471557894?is_copy_url=1&is_from_webapp=v1&lang=es");
        String videoIdWithoutParams = TiktokApi.getVideoId("https://www.tiktok.com/@zaphira.art/video/7111328087471557894/");
        assertEquals(videoIdWithParams, "7111328087471557894");
        assertEquals(videoIdWithoutParams, "7111328087471557894");
    }

    @Test
    void getVideoAuthor() throws IOException {
        String videoIdWithParams = TiktokApi.getVideoAuthor("https://www.tiktok.com/@zaphira.art/video/7111328087471557894?is_copy_url=1&is_from_webapp=v1&lang=es");
        String videoIdWithoutParams = TiktokApi.getVideoAuthor("https://www.tiktok.com/@zaphira.art/video/7111328087471557894/");
        assertEquals(videoIdWithParams, "zaphira.art");
        assertEquals(videoIdWithoutParams, "zaphira.art");
    }

    @Test
    void getVideos() throws IOException, InterruptedException {
        Thread.sleep((long) (Math.random() * 5000 + 1000));
        List<String> videos = TiktokApi.getVideos("zaphira.art", null);
        assertNotNull(videos);
        assertTrue(videos.size() > 0);
    }

    @Test
    void getVideoUrl() throws IOException, InterruptedException {
        Thread.sleep((long) (Math.random() * 5000 + 1000));
        String videoUrl = TiktokApi.getVideoUrl("https://www.tiktok.com/@zaphira.art/video/7111328087471557894?is_copy_url=1&is_from_webapp=v1&lang=es");
        assertTrue(videoUrl != null && videoUrl.contains("https://"));
    }
}