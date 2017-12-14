package com.facishare.openapi.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaoUtil {

	Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    public HashMap<String, String> select(String url,String userName,String password, String sql) {
        HashMap<String, String> map = new HashMap<String, String>();
        String selectStr = null;
        try {
            con = JdbcUtil.getConnection(url,userName,password);
            st = con.prepareStatement(sql);
            rs = st.executeQuery();
            
            selectStr = JsonUtil.resultSetToJson(rs);
            String convertStr = convert(selectStr);
            map.put("body", convertStr);
            map.put("exception", "");
            map.put("duration", "None");
            
        } catch (SQLException e) {
            // TODO: handle exception
            e.printStackTrace();
            map.put("body", "");
            map.put("exception", e.toString());
            map.put("duration", "None");
        } finally {
            JdbcUtil.free(rs, st, con);
        }
        return map;
    }
    
    

    public static String convert(String utfString){
        //StringBuffer b=new StringBuffer();
        String newStr = utfString;
        Matcher m = Pattern.compile("\\\\u([0-9a-fA-F]{4})").matcher(utfString);
        while(m.find())
            newStr = newStr.replace("\\u"+m.group(1), String.valueOf(((char)Integer.parseInt(m.group(1),16))));
            //b.append((char)Integer.parseInt(m.group(1),16));
        return newStr;
    }
}
