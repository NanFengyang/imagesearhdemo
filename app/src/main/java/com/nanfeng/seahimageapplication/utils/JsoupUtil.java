package com.nanfeng.seahimageapplication.utils;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by yangyoutao on 2016/12/22.
 */

public class JsoupUtil {
    private static String string = "varsampleValue=JSON.parse('{\"UI_YUNYING_11\":\"0\",\"UI_YUNYING_13\":\"1\",\"UI_YUNYING_21\":\"0\",\"UI_YUNYING_31\":\"2\",\"UI_WISE_RES_SPEED\":\"1\",\"UI_RSC_PERSONALIZED\":\"1\",\"UI_PC_XIANGUI_MID_IMAGE\":\"1\",\"UI_WISE_AD_DESC\":\"3\",\"UI_WISE_REWRITE\":\"1\",\"UI_WISE_PEITU_ADS\":\"0\",\"UI_WISE_IMAGEQ_GS\":\"0\",\"UI_WISE_BIG_PICTURE\":\"0\",\"UI_PC_MOUSEWHEEL\":\"0\",\"PC_WISE_ALL\":\"0\",\"UI_WISE_HOMEPAGE_FEED\":\"1\",\"UI_PC_TJAD\":\"1\",\"UI_PC_JIAZHUANG_FORWARD\":\"1\",\"WISE_THUMBURL_300\":\"1\",\"UI_PC_THUMBURL_300\":\"0\",\"UI_WISE_SET_TAG\":\"0\",\"UI_WISE_RESULT_TAG\":\"0\",\"FEED_SAMPLE\":\"0\"');";

    /**
     * 返回当前图片的URL，使用百度搜图的图片上传
     *
     * @param htmlText
     * @return
     */
    public static String getImageUrl(String htmlText) {
        Document document = Jsoup.parse(htmlText);
        String imageUrl = "";
        Elements masthead = document.head().getElementsByTag("script");
        Log.i("JsoupUtil", "masthead:" + masthead.size());
        for (int i = 0; i < masthead.size(); i++) {
            Element element = masthead.get(i);
            imageUrl = element.html().replace("window.bd = ", "");
            String[] htmlstr = imageUrl.split("bd.queryImageUrl");
            imageUrl = htmlstr[0].trim();
        }
        return imageUrl;
    }
}
