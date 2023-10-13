package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SignUpSQL{

    ServerSetting serverSetting;
    PreparedStatement preparedStatement;

    SignUpSQL() throws SQLException, IOException {

    }

    public boolean SignUpUser(String id, String pw) throws SQLException, IOException {
        serverSetting = ServerSetting.getInstance();

        String SQL = "INSERT INTO USER(USER_ID, USER_PW) values (?, ?)";

        preparedStatement = serverSetting.connection.prepareStatement(SQL);

        try {
            preparedStatement.setString(SqlTable.USER_ID.ordinal(), id);
            preparedStatement.setString(SqlTable.USER_PW.ordinal(), pw);

            int updatecount = preparedStatement.executeUpdate();

            if(updatecount >= 1) {
                JOptionPane.showMessageDialog(null, "회원가입 성공 ! 종료하시려면 아무 키나 눌러주세요.");
                return true;
            }

        } catch (SQLException e) {
            if(e.getMessage().contains("PRIMARY")){
                JOptionPane.showMessageDialog(null, "중복되는 아이디입니다.");
            }
            else {
                JOptionPane.showMessageDialog(null, "제대로 된 정보를 입력해주세요.");
            }
        }finally {
            if(preparedStatement != null)
                try{
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if(serverSetting.connection != null)
                try{
                serverSetting.connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }



}
