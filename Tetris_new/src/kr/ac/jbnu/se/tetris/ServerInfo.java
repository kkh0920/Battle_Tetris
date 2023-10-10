package kr.ac.jbnu.se.tetris;

import java.sql.SQLException;

public interface ServerInfo {
    static final String server_url = "jdbc:mysql://tetris.cjrrqj3pg3pd.ap-northeast-2.rds.amazonaws.com:3306/USER_INFO";
    static final String management_user = "admin";
    static final String management_password = "xpxmfltm";

    public default String getUrl(){
        return server_url;
    }
    public default String getUser(){
        return management_user;
    }
    public default String getPassword(){
        return management_password;
    }

}
