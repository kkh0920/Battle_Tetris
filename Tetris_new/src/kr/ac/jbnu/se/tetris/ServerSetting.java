package kr.ac.jbnu.se.tetris;
import java.sql.*;

public class ServerSetting {

    Connection connection = null;

    private ServerSetting() throws SQLException{
        connectMysql();
        connectServer();
    }

    private static class ServerSettingHolder {
        private static final ServerSetting INSTANCE;

        static {
            try {
                INSTANCE = new ServerSetting();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static ServerSetting getInstance() {
        return ServerSettingHolder.INSTANCE;
    }


    public void connectMysql() {
        try {
            Class.forName(ServerInfo.DRIVER).newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    public void connectServer() throws SQLException {
        connection = DriverManager.getConnection(
                ServerInfo.SERVER_URL,
                ServerInfo.MANAGEMENT_USER,
                ServerInfo.MANAGEMENT_PASSWORD);
    }
}