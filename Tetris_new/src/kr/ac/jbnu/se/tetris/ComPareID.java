package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComPareID{
    ServerSetting serverSetting;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public static boolean visible = true;
    ComPareID(String id, String pw) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        super();
        Login(id,pw);
    }

    public boolean Login(String id, String pw) throws SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        serverSetting = ServerSetting.getInstance();

        serverSetting.connectMysql();
        serverSetting.connectServer();
        String SQL = "SELECT USER_PW FROM USER WHERE USER_ID = ?";

        preparedStatement = serverSetting.connection.prepareStatement(SQL);
        preparedStatement.setString(SqlTable.USER_ID.ordinal(), id);

        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            try{
            if (resultSet.getString(SqlTable.USER_ID.ordinal()).contentEquals(pw)) {
                Select home = new Select();
                home.setVisible(true);
                visible = false;
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
