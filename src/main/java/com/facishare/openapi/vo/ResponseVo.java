package com.facishare.openapi.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.http.cookie.Cookie;

import java.util.List;

/**
 * Created by sunsk on 2017/8/2.
 */
@Data
public class ResponseVo {
    private JSONObject json = null;
    private String responseStr = "";
    private int httpCode = 0;

    private List<Cookie> cookies;
    private Integer fileSize = 0;
    private org.apache.http.Header[] headers;
}
