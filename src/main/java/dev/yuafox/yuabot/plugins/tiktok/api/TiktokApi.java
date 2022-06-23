package dev.yuafox.yuabot.plugins.tiktok.api;


import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

public class TiktokApi {

    private static final String COOKIE = "tt_csrf_token=TSdAWP5c-9EX01p6oj66GCIMHp07RY-X2BvE; _abck=F06DA60BE34FEFB0911FC2D349903AF2~-1~YAAQduoWAr6kRiiBAQAA0l+KjwgtPZpb6s5pf5h6Lg9MwDfm6FnXTvbinmQjJlm4nNTBwB+pKAXzcMJ1QsVYhiekF6fMFl3WFWLRIgJJRzKE24Slqb2nWZXl2bbH6hmlHn6fBQPN8m8vNtV9UictQj3j+gBfjy0GH42ftxwkix8T1hb4TI+jeeV/rxG6byRCJguNJmphBe8g95fXVS1mpW2vMAWO8R1S1b75gJehF+9jub/CL86QikgicDJuF6avLsDNJRosZwx2qdSXAp+je9/bj3ksnc0zJGnzNA2l/peDXa1n6JSxkordxOlqkMEf25rrdNcXkc8jtVQ7+Zx9R8ERNNpiF/G4XdHVULIp5E2TGqAvc8BrakLiFTk=~-1~-1~-1; ak_bmsc=F9E85F07BF98C067EFC8631DB4794297~000000000000000000000000000000~YAAQduoWAr+kRiiBAQAA0l+KjxBIeR6mL9W2D9u+ExS10GpaxrNauX0sSUcfNfY3SHgE1G4ZfaJLm23d+ExgnjK/vJlOokNrqjifWM9l9nntpDOfvBqNR8wJ4vnBn7PZ7a/wuQlq6/VBQkOJYwOk/raTx9JETlhK+/LstJ8TyB44inguhJeuLimAw1kwNfUx/9XTDMLA0yf6sV2PSdishOxj51I0KwSohd481PmfsiyXsZBTAizLhDv0zz5CS/h5jGsDGlYlrJTbGKeBgbVmacTvSwi3uEBvVQk6flxv8aLn1VYvZmZTMtPxdHSE6EQ5jXGeUD2oUsbPgDCq2q305zC0mMti6tTeU7hyEwV+0okwI9y4FkEXZA4Nol9UaSmDT5oPyNsjo6Ew; bm_sz=7FB5F2EC2C4422CB21268039EC638F9E~YAAQduoWAsGkRiiBAQAA0l+KjxDOS/7yPNx53zSV6ZjPwTGu1WVSLhauPhQmem5fsZ+ZOscBzf6TYHdwmPuI3E+L12dxPckAC1OhSu68yAodXzZlunFL2MXsQQOac9dLTaTMwd2qjo56d1qjmz0A5qktqm8Y8JNHhOB2raQ7YlY/QqMBiE1grJIzTuC/DLfI+p1fWLGaDuy9poZPmzg4EH+G7rXbzvM2xe/oA4ITgTJRPC+czFlbwWGm8dZi27rnBI6NZNJJgjGieI2j3so3TBH2cGfj9UDFs3JN2/RCPY0NgKM=~4473397~3485763; __tea_cache_tokens_1988={\"_type_\":\"default\"}; csrf_session_id=36e4eff122a47f59ce4ca82fc0636caf; bm_sv=934B6848CBA8C100A7E5994D23A22AFE~YAAQduoWAsSkRiiBAQAANmWKjxAOdsgv3a62xTmOlIJ6yznrLpVZbSeUqG8Au97P5M9UHiT5ploZXkqsEh7Fbx/XOFhRP2QMhfl1JcZ0/FyBTthNtiN129H3/8/uUCVMoJTvbLpMoBdFtiN67CgPwvZtCkJOJgdlI9RBbVqxzjDj/bRZtVgWmbKEWtg4d+Z6xF3oMoBnEBNHMWq4X+lGPfL/u+wQxzDocdPM5iBXL3oyA3bzUMd3LBEMwdtns1n/~1; ttwid=1|vuGeteC4khUraG-HeiSHHz1s591Fqq24VOoe8OhojsQ|1655970620|4f2ff91c5dcf614689534e319b9475ae7e674e2f6ae3c3449d7a3b51bdc666a5; msToken=P_pq-XcJLBLw7EJS6F8X7Z843eXuSruUQBT8lkHAn3SCKXDCacZ0rgw1dCfr66sMiMbUDclSaxdURlQornPDVzdqvtsUonCd8Mag4Tb13jRiYRuS0XVGG-LwNEIbZ2xjkeVG0cptujdzkFPc; msToken=P_pq-XcJLBLw7EJS6F8X7Z843eXuSruUQBT8lkHAn3SCKXDCacZ0rgw1dCfr66sMiMbUDclSaxdURlQornPDVzdqvtsUonCd8Mag4Tb13jRiYRuS0XVGG-LwNEIbZ2xjkeVG0cptujdzkFPc";

    public static String getVideoId(String url) throws IOException {
        if(url.contains("?")) url = url.split("\\?")[0];
        return url.replace("https://", "").split("/")[3];
    }

    public static String getVideoAuthor(String url) throws IOException {
        if(url.contains("?")) url = url.split("\\?")[0];
        return url.replace("https://", "").split("/")[1].replace("@", "");
    }

    public static String getCaptchaUrl() throws IOException {
        Document document = getDocument("https://www.tiktok.com/@yuafox", COOKIE);
        return document.html();
    }

    public static List<String> getVideos(String userName, String cookie) throws IOException {
        Document document = getDocument("https://www.tiktok.com/@" + userName, (cookie == null ? "" : cookie) +COOKIE);
        if(document.html().contains("tiktok-verify-page") &&
                document.selectFirst("script[id='SIGI_STATE']") != null
        ) return null;
        String jsonInfo = document.selectFirst("script[id='SIGI_STATE']").html();
        List<String> videoList = new LinkedList<>();
        JSONArray videoArray = new JSONObject(jsonInfo).getJSONObject("ItemList").getJSONObject("user-post").getJSONArray("list");
        for(int i = 0; i < videoArray.length(); i++){
            videoList.add("https://www.tiktok.com/@"+userName+"/video/"+videoArray.getString(i));
        }
        return videoList;
    }

    public static String getVideoUrl(String url) throws IOException {
        String videoId = getVideoId(url);
        Document document = getDocument(url, COOKIE);
        if(document.html().contains("tiktok-verify-page") &&
                document.selectFirst("script[id='SIGI_STATE']") != null
        ) return null;
        String jsonInfo = document.selectFirst("script[id='SIGI_STATE']").html();
        return new JSONObject(jsonInfo).getJSONObject("ItemModule").getJSONObject(videoId).getJSONObject("video").getString("downloadAddr");
    }

    public static Document getDocument(String url, String cookies) throws IOException {
        return Jsoup.connect(url)
                .userAgent("Naverbot")
                .cookies(buildCookiesMap(cookies))
                .timeout(100000)
                .referrer("https://www.tiktok.com/")
                .get();
    }

    private static Map<String, String> buildCookiesMap(String cookieStr) {
        Map<String, String> cookieMap = new HashMap<String, String>();
        String[] cookieArr = cookieStr.split("; ");
        Arrays.stream(cookieArr).forEach(cookie -> {
            String[] split = cookie.split("=");
            if(split.length == 2)
                cookieMap.put(split[0], split[1]);
        });
        return cookieMap;
    }
}
