package com.facishare.openapi.util;

/**
 * 数据库操作工具类
 * Created by liushu on 2016/6/14.
 */

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.*;
import java.util.*;

public class DBUtil {

    // 数据库连接地址
    public  String Connection_URL;
    // 用户名
    public  String USERNAME;
    // 密码
    public  String PASSWORD;
    // mysql的驱动类
    public  String DRIVER;

    QueryRunner qr = new QueryRunner();

    //	private static ResourceBundle rb = ResourceBundle.getBundle("com.util.db.db-config");
    private  ResourceBundle rb = getRb();

    private  ResourceBundle getRb(){
        File file = new File("config");
        URL[]urls = new URL[0];
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        ClassLoader loader = new URLClassLoader(urls);
        ResourceBundle rb = ResourceBundle.getBundle("mysqlconnection", Locale.getDefault(), loader);
        return  rb;
    }

    public DBUtil(String url,String username,String password ) {
        Connection_URL = url;
        USERNAME = username;
        PASSWORD = password;
        DRIVER = "com.mysql.jdbc.Driver";
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

//    // 使用静态块加载驱动程序
//    static {
//        Connection_URL = rb.getString("jdbc.url");
//        USERNAME = rb.getString("jdbc.username");
//        PASSWORD = rb.getString("jdbc.password");
//        DRIVER = rb.getString("jdbc.driver");
//        try {
//            Class.forName(DRIVER);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    // 定义一个获取数据库连接的方法
    public  Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(Connection_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取连接失败");
        }
        return conn;
    }

    // 关闭数据库连接
    public  void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null)
                rs.close();
            if (stat != null)
                stat.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(String sql, Object... objs)
    {
        try {
            qr.update(getConnection(), sql, objs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//		String sql = "insert into user(name,password,email,birthday) values(?,?,?,?)";
//		Object[] params = {"kevin", "12345", "xj@163.com", new Date()};

    }

    public void delete(String sql, int id)
    {
//		String sql = "delete from user where id=?";
        try {
            qr.update(getConnection(), sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(String sql, String id)
    {
//		String sql = "delete from user where id=?";
        try {
            qr.update(getConnection(), sql, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String sql, Object... objs)
    {
        try {
            qr.update(getConnection(), sql, objs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//		String sql = "update user set name=? where id=?";
//		Object[] params = {"xiangjie", 2};

    }

    public List getAll(String sql, Class clasName)
    {
        List<?> list = new ArrayList();
        try {
            list = (List)qr.query(getConnection(), sql, new BeanListHandler(clasName));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
//		String sql = "select * from user";
//		List list = (List) qr.query(sql, new BeanListHandler(clasName));
//		System.out.println(list.size());
    }

    public List<?> findBy(String sql, Class clasName, Object... objs)
    {
        List<?> list = new ArrayList();
        try {
            list = (List)qr.query(getConnection(), sql, new BeanHandler(clasName), objs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

//        String sql = "select * from user where id=?";
//        Object[] params = {2};
//        User user = (User) qr.query(sql, new BeanHandler(User.class), params);
//        System.out.println(user.getName());
    }

    public List<Map<String, Object>> queryMapListHandler(String sql, Object[] params)
    {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            mapList = (List<Map<String, Object>>) qr.query(getConnection(), sql, new MapListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("mapList.size(): "+mapList.size());
        return mapList;
//        for(int i=0; i<mapList.size();i++){
//            for(Map.Entry<String , Object> entry : mapList.get(i).entrySet())
//            {
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }
//        }

    }

    public List<Map<String, Object>> queryMapListHandler(String sql)
    {
        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        try {
            mapList = (List<Map<String, Object>>) qr.query(getConnection(), sql, new MapListHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("mapList.size(): "+mapList.size());
        return mapList;

    }

    public Map<String, Object> queryMapHandler(String sql)
    {
       Map<String, Object> map = new HashMap<String, Object>();

        try {
            map = (Map<String, Object>) qr.query(getConnection(), sql, new MapHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        for(Map.Entry<String , Object> entry : map.entrySet())
//        {
//            System.out.println(entry.getKey() + "=" + entry.getValue());
//        }
        return map;
    }


    public List<?> queryArrayHandler(String sql)
    {
        List<?> list = new ArrayList();
        Object[] result = null;
        try {
            result =  (Object[]) qr.query(getConnection(), sql, new ArrayHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list = Arrays.asList(result);
        return  list;
    }

    public List<?> queryArrayHandler(String sql, Object[] params)
    {
        List<?> list = new ArrayList();
        Object[] result = null;
        try {
            result =  (Object[]) qr.query(getConnection(), sql, new ArrayHandler(), params);
            System.out.println("sql "+sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("queryArrayHandler: result.length== "+result.length);
        list = Arrays.asList(result);
        return  list;
    }

    public List<Object[]> queryArrayListHandler(String sql)
    {
        List<Object[]> list  = new ArrayList<Object[]>();
        try {
            list  =  (List<Object[]>) qr.query(getConnection(), sql, new ArrayListHandler());
            for(Object[] obj : list)
                System.out.println(Arrays.asList(obj));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

}