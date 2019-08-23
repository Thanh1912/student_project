package com.example.data.model;

import com.example.dto.UserCourseDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserCourseTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7521833299501443347L;

    private List<UserCourseDTO> userCourseDTOS = new ArrayList<>();
    private UserCourseDTO coursesSelected;

    private final String[] columnNames = new String[]{
            "STT", "MSSV", "họ và tên", "Giới tính", "CMND", "DIEM 1", "DIEM 2", "DIEM KHAC", "TRANG THAI"
    };

    public UserCourseTableModel(List<UserCourseDTO> userCourseDTOS) {
        this.userCourseDTOS = userCourseDTOS;
    }

    @SuppressWarnings("rawtypes")
    private final Class[] columnClass = new Class[]{
            Integer.class, String.class, Integer.class, Integer.class,
            Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class
    };

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserCourseDTO row = userCourseDTOS.get(rowIndex);
        if (0 == columnIndex) {
            return row.getId();
        } else if (1 == columnIndex) {
            return "";//row.getUserDTO().getMssv()
        } else if (2 == columnIndex) {
            return row.getUserDTO().getFullname();
        } else if (3 == columnIndex) {
            return row.getUserDTO().getSex();
        } else if (4 == columnIndex) {
            return row.getUserDTO().getCardId();
        } else if (5 == columnIndex) {
            return row.getPointHk();
        } else if (6 == columnIndex) {
            return row.getPointHkEnd();
        } else if (7 == columnIndex) {
            return row.getPointHkAnother();
        } else if (8 == columnIndex) {
            return row.getStatusPoint();
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return userCourseDTOS.size();
    }

    public UserCourseDTO getUserCourseSelected() {
        return coursesSelected;
    }

    public void setUserCourseSelected(UserCourseDTO coursesSelected) {
        this.coursesSelected = coursesSelected;
    }

    public void setList(List<UserCourseDTO> courses) {
        this.userCourseDTOS = courses;
    }

    public void refresh() {
        fireTableDataChanged();
    }

    public List<UserCourseDTO> getList() {
        return this.userCourseDTOS;
    }

    public List<UserCourseDTO> getUserCourseDTOS() {
        return userCourseDTOS;
    }

    public void setUserCourseDTOS(List<UserCourseDTO> userCourseDTOS) {
        this.userCourseDTOS = userCourseDTOS;
    }
}