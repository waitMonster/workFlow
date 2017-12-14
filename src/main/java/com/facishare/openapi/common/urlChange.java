package com.facishare.openapi.common;

import java.util.HashMap;

/**
 * Created by sunsk on 2017/8/2.
 */
public class urlChange {
    public String jsonToStr(String url){

        return url;
    }
    public String mapToStr(HashMap<String ,Object> map,String url){
        if(map.size()>0){
            int i=0;
            for (String key : map.keySet()) {
                i++;

                if(map.size()==i){
                    url=url+key+"="+map.get(key);
                }else{
                    url=url+key+"="+map.get(key)+"&";
                }

                }
            }else{
            url=url;
        }
        return url;
    }
}
