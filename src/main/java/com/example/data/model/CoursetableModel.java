package com.example.data.model;

import com.example.dto.ClassesDTO;
import com.example.dto.CourseDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CoursetableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7521833299501443347L;

    private List<CourseDTO> courses = new ArrayList<>();
    private CourseDTO coursesSelected;

    private final String[] columnNames = new String[]{
            "Id", "Mã môn", "Ten mon", "Phong", "Ngày tạo",
            "Ngày sửa"
    };

    public CoursetableModel(List<CourseDTO> courses) {
        this.courses = courses;
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
        CourseDTO row = courses.get(rowIndex);
        if (0 == columnIndex) {
            return row.getId();
        } else if (1 == columnIndex) {
            return row.getCode();
        } else if (2 == columnIndex) {
            return row.getName();
        } else if (3 == columnIndex) {
            return row.getRoom();
        } else if (4 == columnIndex) {
            return row.getCreatedDate();
        } else if (5 == columnIndex) {
            return row.getModifiedDate();
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

    public CourseDTO getCoursesSelected() {
        return coursesSelected;
    }

    public void setCourseSelected(CourseDTO coursesSelected) {
        this.coursesSelected = coursesSelected;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public void refresh() {
        fireTableDataChanged();
    }

    public List<CourseDTO> getList() {
        return this.courses;
    }
}