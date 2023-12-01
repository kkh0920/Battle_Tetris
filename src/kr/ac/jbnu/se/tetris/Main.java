package kr.ac.jbnu.se.tetris;

import java.sql.SQLException;

import kr.ac.jbnu.se.tetris.server.LoginPage;
import kr.ac.jbnu.se.tetris.ui.Select;

public class Main {
    public static void main(String[] args) throws SQLException {
        LoginPage login = new LoginPage();
        login.setVisible(true);

/*        Select select = new Select();
        select.setVisible(true);*/
    }
}
