package com.nanfeng.seahimageapplication.utils;

import android.util.Log;

import com.nanfeng.seahimageapplication.bean.ImageBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangyoutao on 2016/12/22.
 */

public class JsonUtil {
    /**
     * 解析网页数据
     *
     * @param json
     * @return
     */
    public static List<ImageBean> getImageList(String json) {
        json = json.substring(0, json.length() - 1);
        List<ImageBean> list = null;
        try {
            JSONArray baseArray = new JSONArray(json);
            if (null != baseArray && baseArray.length() > 0) {
                list = new ArrayList<>();
                for (int i = 0; i < baseArray.length(); i++) {
                    JSONObject item = baseArray.optJSONObject(i);
                    ImageBean imageBean = new ImageBean();
                    imageBean.image_url = item.optString("objURL");
                    imageBean.from_url = item.optString("fromURL");
                    list.add(imageBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }
}
