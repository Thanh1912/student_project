package com.example.data.model;

import com.example.dto.ClassesDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClassesTableModel extends AbstractTableModel {
    private static final long serialVersionUID = -7521833299501443347L;

    private List<ClassesDTO> categories = new ArrayList<>();
    private ClassesDTO categorySelected ;

    private final String[] columnNames = new String[] {
            "id", "Tên", "Ngày tạo",
            "Ngày sửa"
    };

    public ClassesTableModel(List<ClassesDTO> categories) {
        this.categories = categories;
    }

    @SuppressWarnings("rawtypes")
    private final Class[] columnClass = new Class[] {
            Integer.class, String.class, Integer.class, Integer.class,
            Integer.class, String.class, String.class, String.class, String.class, String.class, Integer.class
    };

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClassesDTO row = categories.get(rowIndex);
        if (0 == columnIndex) {
            return row.getId();
        } else if (1 == columnIndex) {
            return row.getName();
        } else if (2 == columnIndex) {
            return row.getCreatedDate();
        } else if (3 == columnIndex) {
            return row.getModifiedDate();
        }
        return null;
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return categories.size();
    }

    public ClassesDTO getClassesSelected() {
        return categorySelected;
    }

    public void setClassesSelected(ClassesDTO categorySelected) {
        this.categorySelected = categorySelected;
    }

    public void setListClass(List<ClassesDTO> categories) {
        this.categories = categories;
    }

    public void refresh(){
        fireTableDataChanged();
    }
}