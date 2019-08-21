package com.example.view;

import com.example.constant.SystemConstant;
import com.example.converter.CourseConverter;
import com.example.converter.UserConverter;
import com.example.data.model.ClassesTableModel;
import com.example.data.model.CoursetableModel;
import com.example.data.model.UserTableImportModel;
import com.example.data.model.UserTableModel;
import com.example.dto.*;
import com.example.entity.ClassesEntity;
import com.example.entity.CourseEntity;
import com.example.entity.UserEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.IClassesRepository;
import com.example.repository.ICourseRepository;
import com.example.repository.impl.ClassRepository;
import com.example.repository.impl.CourseRepository;
import com.example.repository.impl.UserRepository;
import com.example.service.impl.ClassesService;
import com.example.service.impl.CourseService;
import com.example.service.impl.RoleService;
import com.example.service.impl.UserService;
import com.example.utils.UICommonUtils;
import com.opencsv.CSVReader;
import org.apache.commons.lang3.RandomStringUtils;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeAdmin {
    private JPanel homeAdmin;
    private JButton exitButton;
    private JLabel welcomeNameLabel;
    private JTabbedPane tabbedPane1;
    private JTable tableTKB;
    private JTable tableBangDiem;
    private JTabbedPane tabbedPane2;
    private JTable tableUserTab0;
    private JTable tableClasses;
    private JButton updateButton;
    private JButton btn_change_pass;
    private JButton btn_category_add;
    private JTextField FieldClassesName;
    private JTextField txt_fullname;
    private JButton btn_category_edit;
    private JLabel message;

    private JButton btn_category_delete;
    private JTextField textFieldFullName;
    private JComboBox comboBoxRoles;
    private JButton btn_add_user;
    private JTextField textFieldPassword;
    private JTextField textFieldPhone;
    private JTextField textFieldEmail;
    private JTextField textFieldAddress;
    private JButton bnt_cancel;
    private JScrollPane scrollPane1;
    private JTable tableUserTab1;
    private JTextField FieldName;
    private JTextField FieldMSSV;
    private JTextField FieldCMND;
    private JTextField FieldCategoryName;
    private JComboBox comboBoxClassEdit;
    private JButton btn_product_edit;
    private JPanel searchPanel;
    private JTextField textFieldName;
    private JButton buttonSearch;
    private JComboBox comboBoxClassesSearch;
    private JPanel contentPanel;
    private JButton btn_refesh;
    private JComboBox FieldGT;
    private JButton btnSave;
    private JTextField IMSSV;
    private JComboBox ISEX;
    private JComboBox IClass;
    private JTextField ICMND;
    private JButton buttonImportTKB;
    private JButton addTKBButton;
    private JButton chooseFileBtnBangDiem;
    private JButton addButtonBangDiem;
    private JButton btn_load_tkb;
    private JFrame frame;
    private List<UserDTO> users;
    private List<ClassesDTO> classes;
    private SearchUser query;
    private Long classId = new Long(0);
    private List<UserDTO> listUsers;
    private UserService userService = new UserService();
    private IClassesRepository iClassesRepository = new ClassRepository();
    private UserRepository userRepository = new UserRepository();
    private ClassesService classesService = new ClassesService(iClassesRepository);
    RoleService roleService = new RoleService();

    public HomeAdmin() {
        // init frame admin
        frame = new JFrame("Quản trị hệ thống");
        frame.setContentPane(homeAdmin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 800);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        UICommonUtils.LoadAccount(welcomeNameLabel);

        // init data and bean service
        this.classes = classesService.findAll();
        List<ClassesDTO> classesDTOList = this.classes;
        ClassesDTO first = new ClassesDTO();
        first.setName("");
        classesDTOList.add(0, first);
        classesDTOList.stream().forEach(item -> {
            comboBoxClassEdit.addItem(item.getName());
            comboBoxClassesSearch.addItem(item.getName());
            IClass.addItem(item.getName());
        });

        List<String> listSex = Arrays.asList(SystemConstant.STATUS_SEX);
        listSex.stream().forEach(item -> {
            FieldGT.addItem(item);
            ISEX.addItem(item);
        });

        List<UserDTO> users = getUsers();
        UserTableModel userTableModel = new UserTableModel(users);
        tableUserTab0.setModel(userTableModel);

        tableUserTab0.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tableUserTab0.getSelectedRow();
                UserDTO userDTO = listUsers.get(index);
                UserTableModel productTableModel = (UserTableModel) tableUserTab0.getModel();
                productTableModel.setUserDTOSelected(userDTO);
                FieldName.setText(userDTO.getFullname());
                FieldMSSV.setText(userDTO.getMssv());
                FieldCMND.setText(userDTO.getCardId());
                comboBoxClassEdit.setSelectedItem("");
                FieldGT.setSelectedItem("");
                listSex.forEach(item -> {
                    if (item.equals(userDTO.getSex())) {
                        FieldGT.setSelectedItem(item);
                    }
                });
                classesService.findAll().forEach(item -> {
                    if (item.getId().equals(userDTO.getClassId())) {
                        comboBoxClassEdit.setSelectedItem(item.getName());
                    }
                });
            }
        });

        // table user
        this.users = getUsers();
        UserTableModel userTableModelTab2 = new UserTableModel(this.users);
        tableUserTab0.setModel(userTableModelTab2);

        List<RoleDTO> rolesUser = new ArrayList<>();
        // Load combobox roles
        List<RoleDTO> roles = roleService.findAll();
        roles.stream().forEach(item -> {
            comboBoxRoles.addItem(item.getName());
            if (item.getCode().equals("USER")) {
                rolesUser.add(item);
            }
        });

        buttonSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<UserDTO> products = getUsers();
                UserTableModel tableModel = (UserTableModel) tableUserTab0.getModel();
                tableModel.setList(products);
                tableModel.refresh();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTableImportModel tableModel = (UserTableImportModel) tableUserTab1.getModel();
                List<UserDTO> users = tableModel.getList();
                List<String> statusInsert = new ArrayList<>();
                users.forEach(item -> {
                    UserDTO userDTO = userService.insert(item);
                    if (userDTO != null)
                        statusInsert.add("inserted: " + item.getMssv());
                    else
                        statusInsert.add("Fail inserted: " + item.getMssv());
                });
                JOptionPane.showMessageDialog(frame, String.join(" \\n ", statusInsert));
            }
        });

        btn_change_pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassword();
                frame.dispose();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    CSVReader reader = null;
                    try {
                        reader = new CSVReader(new FileReader(selectedFile.getAbsolutePath()));
                        List<String[]> lines = reader.readAll();
                        List<UserDTO> users1 = new ArrayList<>();
                        String line1 = Arrays.toString(lines.get(0));
                        line1 = line1.substring(1, line1.length() - 1);
                        List<ClassesEntity> classesEntities = iClassesRepository.findByProperty("name", line1);
                        if (classesEntities != null && classesEntities.size() > 0) {
                            for (int i = 1; i < lines.size(); i++) {
                                UserDTO userDTO = new UserDTO();
                                String line = Arrays.toString(lines.get(i));
                                line = line.substring(1, line.length() - 1);
                                String[] listColumnInLine = line.split("-");
                                if (listColumnInLine.length >= 4) {
                                    userDTO.setMssv(listColumnInLine[0]);
                                    userDTO.setFullname(listColumnInLine[1]);
                                    userDTO.setSex(listColumnInLine[2]);
                                    userDTO.setCardId(listColumnInLine[3]);
                                    userDTO.setUsername(userDTO.getMssv());
                                    if (classesEntities.size() >= 1) {
                                        userDTO.setClassId(classesEntities.get(0).getId());
                                        userDTO.setClassName(classesEntities.get(0).getName());
                                    }
                                    userDTO.setStatus("1");
                                    userDTO.setRoles(rolesUser);
                                    userDTO.setPassword(RandomStringUtils.randomAlphanumeric(10));
                                    users1.add(userDTO);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Format file sai" + line1);
                                }
                            }
                            UserTableImportModel userTableModel1 = new UserTableImportModel(users1);
                            tableUserTab1.setModel(userTableModel1);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Khong tim thay lop " + line1);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

   /*      btn_refesh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTableModel tableModel = (UserTableModel) table_user.getModel();
                tableModel.setList(getUsers());
                tableModel.refresh();
            }
        });
*/

        btn_add_user.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserDTO userModel = new UserDTO();
                userModel.setUsername(textFieldFullName.getText());
                userModel.setFullname(txt_fullname.getText());
                userModel.setCardId(ICMND.getText());
                listSex.forEach(item -> {
                    if (item.equals(ISEX.getSelectedItem())) {
                        IClass.setSelectedItem(item);
                        userModel.setSex(item);
                    }
                });
                userModel.setPassword(textFieldPassword.getText());
                classesDTOList.forEach(item -> {
                    if (item.getName().equals(IClass.getSelectedItem())) {
                        IClass.setSelectedItem(item.getName());
                        userModel.setClassId(item.getId());
                    }
                });
                userModel.setStatus("1");
                List<RoleDTO> roles = new ArrayList<>();
                RoleDTO roleModel = getRoles().get(comboBoxRoles.getSelectedIndex());
                roles.add(roleModel);
                userModel.setRoles(roles);
                UserDTO userUpdated = userService.insert(userModel);
                if (userUpdated != null) {
                    JOptionPane.showMessageDialog(frame, "Thêm user thành công");
                } else {
                    JOptionPane.showMessageDialog(frame, "Thêm user thất bại");
                }
            }
        });


        btn_product_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTableModel tableModel = (UserTableModel) tableUserTab0.getModel();
                UserDTO userDTO = tableModel.getUserDTO();
                ClassesDTO categoryDTO = classes.get(comboBoxClassEdit.getSelectedIndex());
                userDTO.setClassId(categoryDTO.getId());
                userDTO.setUsername(FieldName.getText());
                userDTO.setSex(FieldGT.getSelectedItem().toString());
                classesDTOList.forEach(item -> {
                    if (item.getName().equals(IClass.getSelectedItem())) {
                        userDTO.setClassId(item.getId());
                    }
                });
                userDTO.setMssv(FieldMSSV.getText());
                userDTO.setCardId(FieldCMND.getText());
                userDTO.setModifiedDate(getCurrentTimeStamp());
                UserDTO updated = userService.insert(userDTO);
                if (updated != null) {
                    JOptionPane.showMessageDialog(frame, "Cập nhật thành công");
                    tableModel.setList(getUsers());
                    tableModel.refresh();
                } else {
                    JOptionPane.showMessageDialog(frame, "Cập nhật thất bại");
                }
            }
        });

        TabClass();
        TabImport();
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UICommonUtils.logOutSystem(frame);
            }
        });

    }

    private static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    public List<UserDTO> getUsers() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        query = new SearchUser();
        if (textFieldName != null) {
            query.setFullname(textFieldName.getText());
        }
        if (comboBoxClassesSearch != null && comboBoxClassesSearch.getSelectedIndex() > 0 && comboBoxClassesSearch.getSelectedItem().toString().length() > 0) {
            ClassesDTO classesDTO = this.classes.get(comboBoxClassesSearch.getSelectedIndex());
            if (classesDTO != null) {
                query.setClassId(classesDTO.getId());
            }
        }
        SearchResult<UserDTO> rsSearched = userService.search(query, pageRequest);
        listUsers = rsSearched.getResults();
        return listUsers;
    }


    public void TabClass() {
        List<ClassesDTO> classesDTOList = classesService.findAll();
        ClassesTableModel classesTableModel = new ClassesTableModel(classesDTOList);
        tableClasses.setModel(classesTableModel);
        tableClasses.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tableClasses.getSelectedRow();
                ClassesDTO category = classesDTOList.get(index);
                classesTableModel.setClassesSelected(category);
                FieldClassesName.setText(category.getName());
            }
        });

        btn_category_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassesTableModel tableModel = (ClassesTableModel) tableClasses.getModel();
                ClassesDTO categoryDTO = tableModel.getClassesSelected();
                categoryDTO.setName(FieldClassesName.getText());
                ClassesDTO updated = classesService.save(categoryDTO);
                if (updated != null) {
                    message.setText("Cập nhật thành công");
                    tableModel.setCategories(classesService.findAll());
                    tableModel.refresh();
                } else {
                    message.setText("Cập nhật thất bại");
                }
            }
        });

        btn_category_add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassesTableModel tableModel = (ClassesTableModel) tableClasses.getModel();
                ClassesDTO categoryDTO = new ClassesDTO();
                categoryDTO.setName(FieldClassesName.getText());
                categoryDTO.setModifiedDate(getCurrentTimeStamp());
                ClassesDTO inserted = classesService.save(categoryDTO);
                if (inserted != null) {
                    message.setText("Thêm thành công");
                    tableModel.setCategories(classesService.findAll());
                    tableModel.refresh();
                } else {
                    message.setText("Thêm thất bại");
                }
            }
        });

        btn_category_delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassesTableModel tableModel = (ClassesTableModel) tableClasses.getModel();
                ClassesDTO categoryDTO = new ClassesDTO();
                categoryDTO.setName(FieldClassesName.getText());
                categoryDTO.setModifiedDate(getCurrentTimeStamp());
                boolean deleted = classesService.delete(categoryDTO.getId());
                if (deleted) {
                    message.setText("Xoa thành công");
                    tableModel.refresh();
                } else {
                    message.setText("Xoa thất bại");
                }
                tableModel.setCategories(classesService.findAll());
                tableModel.refresh();
            }
        });

    }


    public void TabImport() {
        ICourseRepository courseRepository = new CourseRepository();
        CourseService courseService = new CourseService(courseRepository);
        btn_load_tkb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursetableModel coursetableModel = new CoursetableModel(courseService.findAll());
                tableTKB.setModel(coursetableModel);
            }
        });
        addTKBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseConverter courseConverter = new CourseConverter();
                UserConverter userConverter = new UserConverter();
                CoursetableModel tableModel = (CoursetableModel) tableTKB.getModel();
                List<CourseDTO> courseDTOS = tableModel.getList();
                List<CourseEntity> courseE = new ArrayList<>();
                List<String> statusInsert = new ArrayList<>();
                courseDTOS.forEach(item -> {
                    CourseDTO courseDTO = courseService.save(item);
                    if (courseDTO != null) {
                        courseE.add(courseRepository.findOneByProperty("code", courseDTO.getCode()));
                        statusInsert.add("inserted: " + item.getCode());
                    } else {
                        statusInsert.add("Fail inserted: " + item.getCode());
                    }
                });
                if (classId > 0 && courseE.size() > 0) {
                    List<UserEntity> userEntities = userRepository.findByProperty("classId", classId);
                    for (int i = 0; i < userEntities.size(); i++) {
                        UserEntity userEntity = userEntities.get(i);
                        List<CourseEntity> courses = userEntity.getCourses();
                        courses.addAll(courseE);
                        userEntity.setCourses(courses);
                        userRepository.update(userEntity);
                    }
                }
                JOptionPane.showMessageDialog(frame, String.join(" \\n ", statusInsert));
            }
        });


        buttonImportTKB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    CSVReader reader = null;
                    try {
                        reader = new CSVReader(new FileReader(selectedFile.getAbsolutePath()));
                        List<String[]> lines = reader.readAll();
                        List<CourseDTO> courseDTOS = new ArrayList<>();
                        String line1 = Arrays.toString(lines.get(0));
                        line1 = line1.substring(1, line1.length() - 1);
                        List<ClassesEntity> classesEntities = iClassesRepository.findByProperty("name", line1);

                        if (classesEntities != null && classesEntities.size() > 0) {
                            classId = classesEntities.get(0).getId();
                            for (int i = 1; i < lines.size(); i++) {
                                CourseDTO courseDTO = new CourseDTO();
                                String line = Arrays.toString(lines.get(i));
                                line = line.substring(1, line.length() - 1);
                                String[] listColumnInLine = line.split("-");
                                if (listColumnInLine.length >= 3) {
                                    courseDTO.setCode(listColumnInLine[0]);
                                    courseDTO.setName(listColumnInLine[1]);
                                    courseDTO.setRoom(listColumnInLine[2]);
                                    courseDTOS.add(courseDTO);
                                }
                            }
                            CoursetableModel coursetableModel = new CoursetableModel(courseDTOS);
                            tableTKB.setModel(coursetableModel);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Khong tim thay lop " + line1);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        chooseFileBtnBangDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    CSVReader reader = null;
                    try {
                        reader = new CSVReader(new FileReader(selectedFile.getAbsolutePath()));
                        List<String[]> lines = reader.readAll();
                        List<CourseDTO> courseDTOS = new ArrayList<>();
                        String line1 = Arrays.toString(lines.get(0));
                        line1 = line1.substring(1, line1.length() - 1);
                        List<ClassesEntity> classesEntities = iClassesRepository.findByProperty("name", line1);

                        if (classesEntities != null && classesEntities.size() > 0) {
                            classId = classesEntities.get(0).getId();
                            for (int i = 1; i < lines.size(); i++) {
                                CourseDTO courseDTO = new CourseDTO();
                                String line = Arrays.toString(lines.get(i));
                                line = line.substring(1, line.length() - 1);
                                String[] listColumnInLine = line.split("-");
                                if (listColumnInLine.length >= 3) {
                                    courseDTO.setCode(listColumnInLine[0]);
                                    courseDTO.setName(listColumnInLine[1]);
                                    courseDTO.setRoom(listColumnInLine[2]);
                                    courseDTOS.add(courseDTO);
                                }
                            }
                            CoursetableModel coursetableModel = new CoursetableModel(courseDTOS);
                            tableTKB.setModel(coursetableModel);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Khong tim thay lop " + line1);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    public void TabImport2() {
        ICourseRepository courseRepository = new CourseRepository();
        CourseService courseService = new CourseService(courseRepository);
        btn_load_tkb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursetableModel coursetableModel = new CoursetableModel(courseService.findAll());
                tableTKB.setModel(coursetableModel);
            }
        });
        chooseFileBtnBangDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursetableModel tableModel = (CoursetableModel) tableTKB.getModel();
                List<CourseDTO> users = tableModel.getList();
                List<String> statusInsert = new ArrayList<>();
                users.forEach(item -> {
                    CourseDTO courseDTO = courseService.save(item);
                    if (courseDTO != null)
                        statusInsert.add("inserted: " + item.getCode());
                    else
                        statusInsert.add("Fail inserted: " + item.getCode());
                });
                JOptionPane.showMessageDialog(frame, String.join(" \\n ", statusInsert));
            }
        });


        buttonImportTKB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = jfc.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    CSVReader reader = null;
                    try {
                        reader = new CSVReader(new FileReader(selectedFile.getAbsolutePath()));
                        List<String[]> lines = reader.readAll();
                        List<CourseDTO> courseDTOS = new ArrayList<>();
                        String line1 = Arrays.toString(lines.get(0));
                        line1 = line1.substring(1, line1.length() - 1);
                        List<ClassesEntity> classesEntities = iClassesRepository.findByProperty("name", line1);

                        if (classesEntities != null && classesEntities.size() > 0) {
                            List<UserEntity> userEntities = userRepository.findByProperty("classId", classesEntities.get(0).getId());
                            for (int i = 1; i < lines.size(); i++) {
                                // insert mon hoc vao database sau do dang ky cho tat ca sinh vien
                                CourseDTO courseDTO = new CourseDTO();
                                String line = Arrays.toString(lines.get(i));
                                line = line.substring(1, line.length() - 1);
                                String[] listColumnInLine = line.split("-");
                                if (listColumnInLine.length >= 4) {
                                    courseDTO.setCode(listColumnInLine[0]);
                                    courseDTO.setName(listColumnInLine[1]);
                                    courseDTO.setRoom(listColumnInLine[2]);
                                    courseDTOS.add(courseDTO);
                                } else {
                                    JOptionPane.showMessageDialog(frame, "Format file sai" + line1);
                                }
                            }
                            CoursetableModel coursetableModel = new CoursetableModel(courseDTOS);
                            tableTKB.setModel(coursetableModel);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Khong tim thay lop " + line1);
                        }
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    private List<RoleDTO> getRoles() {
        return roleService.findAll();
    }
}