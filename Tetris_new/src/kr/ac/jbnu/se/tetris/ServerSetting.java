package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;

// Notice, do not import com.mysql.cj.jdbc.*
// or you will have problems!

public class ServerSetting implements ServerInfo {

    Connection connection = null;
    static PreparedStatement preparedStatement = null;
    ResultSet resultset = null;
    protected static boolean visible = true;

    ServerSetting(String id, String pw) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        compareID(id,pw);
    }

    public void compareID(String id, String pw) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        connectMysql();
        connectServer();
        String SQL = "SELECT USER_PW FROM USER WHERE USER_ID = ?";

        preparedStatement = connection.prepareStatement(SQL);
        preparedStatement.setString(SqlTable.USER_ID.ordinal(), id);

        resultset = preparedStatement.executeQuery();
        while(resultset.next()){
            if (resultset.getString(SqlTable.USER_ID.ordinal()).contentEquals(pw)) {
                Select home = new Select();
                home.setVisible(true);
                visible = false;
            }
        }
    }

    public void connectMysql() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
    }

    public void connectServer() throws SQLException, IOException {
        connection = DriverManager.getConnection(
                server_url,
                management_user,
                management_password);
    }


}