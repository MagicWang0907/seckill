package com.wang.seckill.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wang.seckill.entity.User;
import com.wang.seckill.vo.ResponseBean;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserUtil {

    public static void main(String[] args) throws Exception {
        generateUsers(500);
        //delete();
    }

    public static void generateUsers(int count) throws Exception{
        List<User> users = new ArrayList<>(count);
        Long mobile = 13000000000L;
        String slat = "1a2b3c";
        for (int i = 0; i < count; i++) {
            users.add(User.builder()
                    .id(mobile + i)
                    .nickname("WL"+i)
                    .slat(slat)
                    .password(MD5Utils.inputPassToDBPass("123456",slat))
                    .loginCount(0)
                    .build());
        }
        Connection connection = getConnection();
        String sql = "insert into t_user (login_count,nickname,register_date,slat,password,id) values(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (int i = 0; i < count; i++) {
            User user = users.get(i);
            preparedStatement.setInt(1,user.getLoginCount());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setDate(3,null);
            preparedStatement.setString(4,user.getSlat());
            preparedStatement.setString(5,user.getPassword());
            preparedStatement.setLong(6,user.getId());
            preparedStatement.addBatch();
            System.out.println("insertToDB:" + user);
        }
        preparedStatement.executeBatch();
        preparedStatement.clearParameters();
        connection.close();

        String urlString = "http://localhost:8080/login/doLogin";
        File file = new File("C:\\Users\\WL\\Desktop\\config.txt");
        if(file.exists()){
            file.delete();
        }
        RandomAccessFile rw = new RandomAccessFile(file, "rw");
        rw.seek(0);
        for (int i = 0; i <count; i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            String para = "mobile=" + user.getId() +"&password="+ MD5Utils.inputPassToFromPass("123456");
            outputStream.write(para.getBytes());
            outputStream.flush();
            InputStream inputStream = httpURLConnection.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) >=0){
                byteArrayOutputStream.write(bytes,0,len);
            }
            inputStream.close();
            outputStream.close();
            String response = byteArrayOutputStream.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            ResponseBean responseBean = objectMapper.readValue(response, ResponseBean.class);
            String userTicket = (String) responseBean.getObject();
            System.out.println("creat userTicket:"+user.getId());
            String row = user.getId() + "," + userTicket;
            System.out.println();
            row = row.trim();
            rw.seek(rw.length());
            rw.write(row.getBytes());
            rw.write("\r\n".getBytes());
            System.out.println("write to file:" + user.getId());
        }

        rw.close();


    }

     public static Connection getConnection() throws Exception{
        String url = "jdbc:mysql://localhost:3306/seckill";
        String user = "root";
        String password = "wllxy0907";
        String driver = "com.mysql.cj.jdbc.Driver";
         Driver instance = (Driver) Class.forName(driver).getDeclaredConstructor().newInstance();
         Properties info = new Properties();
         if (user != null) {
             info.put("user", user);
         }
         if (password != null) {
             info.put("password", password);
         }
         Connection connect = instance.connect(url, info);
         System.out.println(connect);
         return connect;
     }

     static void delete() throws Exception {
         Connection connection = getConnection();
         String sql = "DELETE FROM t_user  where id < 14000000000";
         boolean execute = connection.prepareStatement(sql).execute();
     }
}
