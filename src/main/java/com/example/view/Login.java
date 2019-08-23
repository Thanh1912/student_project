package com.example.view;

import com.example.constant.Session;
import com.example.dto.UserDTO;
import com.example.service.impl.UserService;
import com.example.utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Login {
    private JButton LoginButton;
    private JRadioButton studentRole;
    private JRadioButton adminRole;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JPanel LoginPanel;
    private static JFrame frame;
    UserService userService = new UserService();

    public Login() {
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if (username.trim().length() == 0 || password.trim().length() == 0) {
                    JOptionPane.showMessageDialog(frame, "VUI LONG NHAP USER PASSWORD");
                    return;
                }
                UserDTO userDTO = userService.findOneByUsernameAndPassword(username, password);
                if (userDTO == null) {
                    JOptionPane.showMessageDialog(frame, "USER PASSWORD KHONG TIM THAY");
                    return;
                }

                if (userDTO.getUsername().length() > 0) {
                    if (userDTO.getRoles().size() > 0) {
                        if (adminRole.isSelected() && userDTO.getRoles().get(0).getCode().equalsIgnoreCase("ADMIN")) {
                            Session.userInfo = userDTO;
                            new HomeAdmin();
                            frame.dispose();
                            frame.setVisible(false);
                        } else if (studentRole.isSelected() && userDTO.getRoles().get(0).getCode().equalsIgnoreCase("USER")) {
                            Session.userInfo = userDTO;
                            new StudentForm();
                            frame.dispose();
                            frame.setVisible(false);
                        } else JOptionPane.showMessageDialog(frame, "Bạn không có quyền truy cập vào ứng dụng");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Bạn không có quyền truy cập vào ứng dụng");
                    }
                }
            }
        });
        // Load ui
        frame = new JFrame("Login");
        frame.setContentPane(LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500, 200, 647, 418);
        UICommonUtils.makeFrameToCenter(frame);
        frame.setSize(800, 400);

        frame.setVisible(true);


        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    passwordTextField.requestFocus();

                }
                super.keyPressed(e);
            }
        });
        passwordTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    LoginButton.doClick();

                }
                super.keyPressed(e);
            }
        });
    }


}
