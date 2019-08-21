package com.example.data.model;

import com.example.dto.UserDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserTableImportModel extends AbstractTableModel {
    private static final long serialVersionUID = 6230315773364576571L;

    private List<UserDTO> list = new ArrayList<>();
    private UserDTO userDTO;

    public UserTableImportModel(List<UserDTO> list) {
        this.list = list;
    }

    private final String[] columnNames = new String[]{
            "MSSV", "Họ và tên", "CMND", "Lop"
    };
    @SuppressWarnings("rawtypes")
    private final Class[] columnClass = new Class[]{
            Long.class, String.class, Integer.class, Integer.class, String.class, String.class, Integer.class
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
        UserDTO row = list.get(rowIndex);
        if (0 == columnIndex) {
            return row.getMssv();
        } else if (1 == columnIndex) {
            return row.getFullname();
        } else if (2 == columnIndex) {
            return row.getCardId();
        } else if (3 == columnIndex) {
            return row.getClassName();
        }
        return null;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    public List<UserDTO> getList() {
        return list;
    }

    public void setList(List<UserDTO> list) {
        this.list = list;
    }

    public void refresh() {
        fireTableDataChanged();
    }

    public void setUserDTOSelected(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}