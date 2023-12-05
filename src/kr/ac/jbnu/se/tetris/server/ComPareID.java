package kr.ac.jbnu.se.tetris.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.ac.jbnu.se.tetris.ui.Select;

public class ComPareID{
    
    private ServerSetting serverSetting;
    
    private PreparedStatement readQuery;
    
    private ResultSet matchQuery;

    ComPareID() throws SQLException {
        super();
    }

    public boolean login(String id, String pw) throws SQLException {
        serverSetting = new ServerSetting();

        serverSetting.connectMysql();
        serverSetting.connectServer();
        String SQL = "SELECT USER_PW FROM user_info WHERE USER_ID = ?";

        readQuery = serverSetting.connection.prepareStatement(SQL);
        readQuery.setString(SqlTable.USER_ID.ordinal(), id);

        matchQuery = readQuery.executeQuery();
        while(matchQuery.next()){
            try{
                return matchQuery.getString(SqlTable.USER_ID.ordinal()).contentEquals(pw);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
