package com.example.utils;

import com.example.constant.Session;
import com.example.dto.UserDTO;
import com.example.view.Login;

import javax.swing.*;
import java.awt.*;


public class UICommonUtils {

    public static void makeFrameToCenter(JFrame frame) {
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension thissize = frame.getSize();

        if (thissize.width > screensize.width) {
            thissize.width = screensize.width;
        }
        if (thissize.height > screensize.height) {
            thissize.height = screensize.height;
        }
        frame.setSize(1000, 700);
        frame.setLocation((screensize.width - thissize.width) / 2, (screensize.height - thissize.height) / 2);

    }

    public static UserDTO getAccount(Frame frame) {
        try {
            return (UserDTO) Session.userInfo;
        }catch (Exception e){
          frame.dispose();
          frame.setVisible(false);
          new Login();
        }
        return null;
    }
    public static void LoadAccount(JLabel welcomeNameLabel, Frame frame) {
      try {
          UserDTO user = (UserDTO) Session.userInfo;
          welcomeNameLabel.setText(user.getUsername());
      }catch (Exception e){
          frame.dispose();
          frame.setVisible(false);
          new Login();
      }
    }




    public static void logOutSystem(JFrame frame) {
        if (Session.userInfo != null) {
            Session.userInfo = null;
            new Login();
            frame.dispose();
        }
    }
}
