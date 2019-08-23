package com.example.data.model;

import com.example.entity.UserCourseEntity;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserCourseImportTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7521833299501443347L;

    private List<UserCourseEntity> courses = new ArrayList<>();
    private UserCourseEntity coursesSelected;

    private final String[] columnNames = new String[]{
            "STT", "MSSV", "họ và tên", "Giới tính", "CMND", "DIEM 1", "DIEM 2", "DIEM KHAC","DIEM TONG", "TRANG THAI"
    };

    public UserCourseImportTableModel(List<UserCourseEntity> courses) {
        this.courses = courses;
    }

    @SuppressWarnings("rawtypes")
    private final Class[] columnClass = new Class[]{
            Integer.class, String.class, Integer.class, String.class,
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
        UserCourseEntity row = courses.get(rowIndex);
        if (0 == columnIndex) {
            return row.getId();
        } else if (1 == columnIndex) {
            return row.getUserEntity().getMssv();
        } else if (2 == columnIndex) {
            return row.getUserEntity().getFullName();
        } else if (3 == columnIndex) {
            return row.getUserEntity().getSex();
        } else if (4 == columnIndex) {
            return row.getUserEntity().getCardId();
        } else if (5 == columnIndex) {
            return row.getPointHk();
        } else if (6 == columnIndex) {
            return row.getPointHkEnd();
        } else if (7 == columnIndex) {
            return row.getPointHkAnother();
        } else if (8 == columnIndex) {
            return row.getStatusPoint();
        }else if (9 == columnIndex) {
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
        return courses.size();
    }

    public UserCourseEntity getClassesSelected() {
        return coursesSelected;
    }

    public void setClassesSelected(UserCourseEntity coursesSelected) {
        this.coursesSelected = coursesSelected;
    }

    public void setCategories(List<UserCourseEntity> courses) {
        this.courses = courses;
    }

    public void refresh() {
        fireTableDataChanged();
    }

    public List<UserCourseEntity> getList() {
        return this.courses;
    }

    public List<UserCourseEntity> getCourses() {
        return courses;
    }

    public void setCourses(List<UserCourseEntity> courses) {
        this.courses = courses;
    }
}