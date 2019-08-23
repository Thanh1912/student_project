package com.example.view;

import com.example.constant.Session;
import com.example.dto.UserDTO;
import com.example.service.impl.UserService;
import com.example.utils.StringUtils;
import com.example.utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangePassword {
    private JPanel LoginPanel;
    private JTextField passwordOldTextField;
    private JButton btnUpdate;
    private JPasswordField passwordTextField;
    private JPanel changePassword;
    private JButton btnBack;
    private JButton exitButton;
    private JLabel welcomeNameLabel;
    private JPasswordField repasswordTextField;
    private JFrame frame;

    public ChangePassword() {
        UserService userService = new UserService();
        frame = new JFrame("changePassword");
        frame.setContentPane(changePassword);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        UICommonUtils.LoadAccount(welcomeNameLabel, frame);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UICommonUtils.logOutSystem(frame);
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Session.userInfo != null) {
                    UserDTO user = (UserDTO) Session.userInfo;
                    if (user.getRoles().size() > 0) {
                        if (user.getRoles().get(0).getCode().equalsIgnoreCase("ADMIN")) {
                            new HomeAdmin();
                        }
                    }
                } else {
                    new Login();
                }
                frame.dispose();
            }
        });


        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // check valid input
                if (StringUtils.isEmpty(repasswordTextField.getText()) || StringUtils.isEmpty(passwordTextField.getText()) || StringUtils.isEmpty(passwordOldTextField.getText())) {
                    JOptionPane.showMessageDialog(frame, "Vui lòng nhập đủ thông tin");
                    return;
                }
                // check valid password input
                if (!repasswordTextField.getText().equals(passwordTextField.getText())) {
                    JOptionPane.showMessageDialog(frame, "Nhập password mới không khớp....");
                    return;
                }
                if (Session.userInfo != null) {
                    UserDTO user = (UserDTO) Session.userInfo;
                    UserDTO userUpdated = userService.changePassword(user.getUsername(), passwordOldTextField.getText(), passwordTextField.getText());
                    if (userUpdated != null) {
                        JOptionPane.showMessageDialog(frame, "Cập nhật thành công...");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Nhập pass cũ sai");
                    }
                } else {
                    // se chuyen save form login khi ko the lay data tu session
                    new Login();
                    frame.dispose();
                }
            }
        });
    }

}
