package kr.ac.jbnu.se.tetris;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginPage extends JFrame {
    ServerSetting server;


    Backgrounds backgrounds;
    JTextField id = new JTextField(20); // 20글자만 입력 가능
    JPasswordField password = new JPasswordField(50); // 50글자만 입력 가능;;
    JLabel txt_id, txt_pw;
    JButton login,signup;

    Scanner scanner = new Scanner(System.in);

    final int x = 800, y = 453;

    LoginPage() throws SQLException {
        setTextField();
        setText();
        setTextField();
        setButton();
        setBackgrounds();
        componentsSize();
        setFrame();
        addComponents();
    }

    public void setFrame() {

        setSize(x,y);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    public void setTextField() {
    }

    public void setBackgrounds() {
        backgrounds = new Backgrounds("image\\Background.jpg",x,y);
    }

    public void setText() {
        txt_id = new JLabel("ID : ");
        txt_pw = new JLabel("PW : ");

        txt_id.setFont(txt_id.getFont().deriveFont(15.0f));
        txt_pw.setFont(txt_pw.getFont().deriveFont(15.0f));

        txt_id.setForeground(Color.WHITE);
        txt_pw.setForeground(Color.WHITE);
    }

    public void setButton() {
        login = new JButton("LOGIN");
        signup = new JButton("SIGN UP");
        buttonAction();
    }

    public void componentsSize() {
        id.setBounds(330,260,160,50);
        password.setBounds(330,330,160,50);
        txt_id.setBounds(290,280,40,20);
        txt_pw.setBounds(290,350,40,20);
        login.setBounds(530,270,80,80);
        signup.setBounds(630,270,80,80);
    }

    public void addComponents() {
        add(id); add(password); add(txt_id); add(txt_pw); add(login); add(signup);
        add(backgrounds.getPane());
    }

    public void buttonAction() {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmp_id = id.getText();
                String cmp_pw = password.getText();
                try {
                    server = new ServerSetting(cmp_id, cmp_pw);
                    if(!ServerSetting.visible) setVisible(false);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        signup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }

}
