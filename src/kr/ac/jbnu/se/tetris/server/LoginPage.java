package kr.ac.jbnu.se.tetris.server;

import javax.swing.*;

import kr.ac.jbnu.se.tetris.ui.Wallpapers;
import kr.ac.jbnu.se.tetris.ui.CustomButton;

import java.awt.*;
import java.sql.SQLException;

public class LoginPage extends JFrame {

    private Wallpapers backgrounds;
    private JTextField id; // 20글자만 입력 가능
    private JPasswordField password; // 50글자만 입력 가능
    private JLabel txt_id, txt_pw;
    private CustomButton login, signup;

    public static final int x = 800, y = 453;

    LoginPage() throws SQLException {
        setText();
        setTextField();
        setButton();
        setBackgrounds();
        componentsSize();
        setFrame();
        addComponents();
    }

    private void setFrame() {
        setSize(x,y);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setTextField() {
        id = new JTextField(20);
        password = new JPasswordField(50);
    }

    public void setBackgrounds() {
        backgrounds = new Wallpapers("image/Background.jpg",x,y);
    }

    private void setText() {
        txt_id = new JLabel("ID : ");
        txt_pw = new JLabel("PW : ");

        txt_id.setFont(txt_id.getFont().deriveFont(15.0f));
        txt_pw.setFont(txt_pw.getFont().deriveFont(15.0f));

        txt_id.setForeground(Color.WHITE);
        txt_pw.setForeground(Color.WHITE);
    }

    private void setButton() {
        login = new CustomButton(new ImageIcon("image/Login.png"));
        signup = new CustomButton(new ImageIcon("image/SignUp.png"));
        buttonAction();
    }

    private void componentsSize() {
        id.setBounds(330,260,160,50);
        password.setBounds(330,330,160,50);
        txt_id.setBounds(290,280,40,20);
        txt_pw.setBounds(290,350,40,20);
        login.setBounds(530,270,80,80);
        signup.setBounds(630,270,80,80);
    }

    private void addComponents() {
        add(id); add(password); add(txt_id); add(txt_pw); add(login); add(signup);
        add(backgrounds.getPane());
    }

    private void buttonAction() {
        login.addActionListener(e->{
            String cmp_id = id.getText();
            String cmp_pw = password.getText();
            try {
                ComPareID comPareID = new ComPareID(cmp_id, cmp_pw);
                if(!ComPareID.visible) setVisible(false);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        signup.addActionListener(e-> new SignUp() );
    }
}
