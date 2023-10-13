package kr.ac.jbnu.se.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class SignUp extends JFrame{


    JTextField txt_id, txt_pw;
    JButton accept;
    JLabel id, pw;
    String sign_id,sign_pw;
    Backgrounds backgrounds;

    SignUp() throws SQLException {
        setText();
        setTextField();
        setButton();
        setBackgrounds();
        setFrame();
        add(pw); add(id); add(txt_id); add(txt_pw); add(accept); add(backgrounds.getPane());
    }
    public void setText() {
        id = new JLabel("ID : ");
        pw = new JLabel("PW : ");

        id.setFont(id.getFont().deriveFont(15.0f));
        pw.setFont(pw.getFont().deriveFont(15.0f));

        id.setForeground(Color.WHITE);
        pw.setForeground(Color.WHITE);
    }

    public void setBackgrounds() {
        backgrounds = new Backgrounds("image\\Background.jpg", LoginPage.x, LoginPage.y);
    }

    public void setFrame() {

        setSize(LoginPage.x,LoginPage.y);
        setVisible(true);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);

    }

    public void setButton() {
        accept = new JButton("확인");
        accept.setBounds(530,270,80,80);
        buttonAction();
    }

    public void setTextField() {
        txt_id = new JTextField(20);
        txt_pw = new JTextField(50);

        id.setBounds(290,280,40,20);
        pw.setBounds(290,350,40,20);

        txt_id.setBounds(330,260,160,50);
        txt_pw.setBounds(330,330,160,50);

    }

    public void buttonAction() {
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sign_id = txt_id.getText();
                sign_pw = txt_pw.getText();
                try {
                    SignUpSQL signUpSQL = new SignUpSQL();
                    signUpSQL.SignUpUser(sign_id, sign_pw);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
    });
    }
}
