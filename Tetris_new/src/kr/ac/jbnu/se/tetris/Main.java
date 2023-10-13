package kr.ac.jbnu.se.tetris;

import java.io.IOException;
import java.sql.SQLException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main { 
    public static void main(String[] args) throws SQLException, UnsupportedAudioFileException, LineUnavailableException {
/*        LoginPage login = new LoginPage();
        login.setVisible(true);*/
        Select select = new Select();
        select.setVisible(true);
    }
}
