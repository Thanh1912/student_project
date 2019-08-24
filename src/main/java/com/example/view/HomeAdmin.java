package com.example.view;

import com.example.constant.SystemConstant;
import com.example.converter.CourseConverter;
import com.example.converter.UserConverter;
import com.example.data.model.*;
import com.example.dto.*;
import com.example.entity.ClassesEntity;
import com.example.entity.CourseEntity;
import com.example.entity.UserCourseEntity;
import com.example.entity.UserEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.IClassesRepository;
import com.example.repository.ICourseRepository;
import com.example.repository.IUserCourseRepository;
import com.example.repository.impl.ClassRepository;
import com.example.repository.impl.CourseRepository;
import com.example.repository.impl.UserCourseRepository;
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
    private JTextField Field_Txt_ClassName;
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
    private JTable table_dk_monhoc;
    private JComboBox cboxClassesSearch;
    private JButton btn_tk_dkmh;
    private JTextField txt_dk_mssv;
    private JTextField txt_dk_fullname;
    private JComboBox cb_dk;
    private JButton btn_update_dkmh;
    private JTable table_mh;
    private JTextField txt_maMon_hoc;
    private JTextField txt_name_mh;
    private JButton btn_tk_mh;
    private JTextField point_center;
    private JTextField point_end;
    private JTextField point_another;
    private JButton btn_update_point;
    private JLabel lb_class;
    private JLabel lb_mh;
    private JComboBox cboxClassesMHSearch;
    private JPanel ql_sv;
    private JPanel import_sv;
    private JTextField txt_mamh;
    private JButton btn_tk;
    private JLabel txt_sl;
    private JLabel phantram_dau;
    private JLabel phantram_rot;
    private JFrame frame;
    private List<UserDTO> users;
    private List<ClassesDTO> classes;
    private SearchUser query;
    private Long classId = new Long(0);
    private List<UserDTO> listUsers;
    private UserService userService = new UserService();
    private CourseRepository courseRepository = new CourseRepository();
    private CourseService courseService = new CourseService(courseRepository);
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
        UICommonUtils.LoadAccount(welcomeNameLabel, frame);

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
            cboxClassesSearch.addItem(item.getName());
            cboxClassesMHSearch.addItem(item.getName());
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

        import_sv.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("CLIDESDF IMOSDFS");
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
        TabDKMH();
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
        classesTableModel.setListClass(classesDTOList);
        tableClasses.setModel(classesTableModel);
        tableClasses.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = tableClasses.getSelectedRow();
                ClassesDTO classesDTO = classesDTOList.get(index);
                classesTableModel.setClassesSelected(classesDTO);
                Field_Txt_ClassName.setText(classesDTO.getName());
            }
        });

        btn_category_edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassesTableModel tableModel = (ClassesTableModel) tableClasses.getModel();
                ClassesDTO categoryDTO = tableModel.getClassesSelected();
                categoryDTO.setName(Field_Txt_ClassName.getText());
                ClassesDTO updated = classesService.save(categoryDTO);
                if (updated != null) {
                    message.setText("Cập nhật thành công");
                    tableModel.setListClass(classesService.findAll());
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
                categoryDTO.setName(Field_Txt_ClassName.getText());
                categoryDTO.setModifiedDate(getCurrentTimeStamp());
                ClassesDTO inserted = classesService.save(categoryDTO);
                if (inserted != null) {
                    message.setText("Thêm thành công");
                    tableModel.setListClass(classesService.findAll());
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
                ClassesDTO categoryDTO = tableModel.getClassesSelected();
                boolean deleted = classesService.delete(categoryDTO.getId());
                if (deleted) {
                    message.setText("Xoa thành công");
                    tableModel.refresh();
                } else {
                    message.setText("Xoa thất bại");
                }
                tableModel.setListClass(classesService.findAll());
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
                List<CourseEntity> courseEntities = new ArrayList<>();
                List<String> statusInsert = new ArrayList<>();
                courseDTOS.forEach(item -> {
                    CourseDTO courseDTO = courseService.save(item);
                    if (courseDTO != null) {
                        courseEntities.add(courseRepository.findOneByProperty("code", courseDTO.getCode()));
                        statusInsert.add("inserted: " + item.getCode());
                    } else {
                        statusInsert.add("Fail inserted: " + item.getCode());
                    }
                });
                if (classId > 0 && courseEntities.size() > 0) {
                    List<UserEntity> userEntities = userRepository.findByProperty("classId", classId);
                    for (int i = 0; i < userEntities.size(); i++) {
                        UserEntity userEntity = userEntities.get(i);
                        UserCourseEntity userCourseEntity = new UserCourseEntity();
                        courseEntities.forEach(course -> {
                            userCourseEntity.setCourseEntity(course);
                            userCourseEntity.setUserEntity(userEntity);
                            userRepository.update(userEntity);
                        });
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
        IUserCourseRepository userCourseRepository = new UserCourseRepository();

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
                        List<UserCourseEntity> userCourseEntities = new ArrayList<>();
                        String line1 = Arrays.toString(lines.get(0));
                        line1 = line1.substring(1, line1.length() - 1);
                        String[] split = line1.split("-");
                        if (split.length >= 2) {
                            lb_class.setText(split[0]);
                            lb_mh.setText(split[1]);
                            List<ClassesEntity> classesEntities = iClassesRepository.findByProperty("name", split[0]); // name lop
                            List<CourseEntity> courses = courseRepository.findByProperty("code", split[1]); // ma mon hoc

                            if (classesEntities != null && classesEntities.size() > 0) {
                                classId = classesEntities.get(0).getId();
                                Long courseId = courses.get(0).getId();
                                List<Object> userCourseEntity = userCourseRepository.findByClassIdAndCourseId(classId, courseId);
                                for (int i = 1; i < lines.size(); i++) {
                                    for (int j = 0; j < userCourseEntity.size(); j++) {
                                        Object[] objects = (Object[]) userCourseEntity.get(i);
                                        String line = Arrays.toString(lines.get(i));
                                        line = line.substring(1, line.length() - 1);
                                        String[] listColumnInLine = line.split("-");
                                        if (listColumnInLine.length >= 6) {
                                          /*  if (userCourseEntity1.getUserEntity().getMssv().equals(listColumnInLine[0])) {
                                                Double point_center1 = Double.parseDouble(listColumnInLine[2]);
                                                Double point_end1 = Double.parseDouble(listColumnInLine[3]);
                                                Double point_another1 = Double.parseDouble(listColumnInLine[4]);
                                                Double point_tb = Double.parseDouble(listColumnInLine[5]);

                                                userCourseEntity1.setPointHk(point_center1);
                                                userCourseEntity1.setPointHkEnd(point_end1);
                                                userCourseEntity1.setPointHkAnother(point_another1);
                                                userCourseEntity1.setPoint(point_tb);
                                                userCourseEntities.add(userCourseEntity1);
                                            }*/
                                        }
                                    }
                                }
                                UserCourseImportTableModel coursetableModel = new UserCourseImportTableModel(userCourseEntities);
                                tableBangDiem.setModel(coursetableModel);
                            } else {
                                JOptionPane.showMessageDialog(frame, "Khong tim thay lop " + line1);
                            }
                        }


                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        addButtonBangDiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseConverter courseConverter = new CourseConverter();
                UserConverter userConverter = new UserConverter();
                UserCourseImportTableModel userCourseImportTableModel = (UserCourseImportTableModel) tableTKB.getModel();
                List<UserCourseEntity> userCourseEntities = userCourseImportTableModel.getList();
                List<CourseEntity> courseEntities = new ArrayList<>();
                List<String> statusInsert = new ArrayList<>();
                userCourseEntities.forEach(item -> {
                    UserCourseEntity userCourseEntity = userCourseRepository.update(item);
                    if (userCourseEntity != null) {
                        statusInsert.add("inserted MSSV: " + item.getUserEntity().getMssv());
                    } else {
                        statusInsert.add("Fail inserted MSSV: " + item.getUserEntity().getMssv());
                    }
                });
               /* if (classId > 0 && courseEntities.size() > 0) {
                    List<UserEntity> userEntities = userRepository.findByProperty("classId", classId);
                    for (int i = 0; i < userEntities.size(); i++) {
                        UserEntity userEntity = userEntities.get(i);
                        UserCourseEntity userCourseEntity = new UserCourseEntity();
                        courseEntities.forEach(course -> {
                            userCourseEntity.setCourseEntity(course);
                            userCourseEntity.setUserEntity(userEntity);
                            userRepository.update(userEntity);
                        });
                    }
                }*/
                JOptionPane.showMessageDialog(frame, String.join(" \\n ", statusInsert));
            }
        });


    }

    public String checkNull(Object str) {
        return str != null ? str.toString() : "";
    }

    public void TabDKMH() {
        IUserCourseRepository userCourseRepository = new UserCourseRepository();
        UserConverter userConverter = new UserConverter();
        CourseConverter courseConverter = new CourseConverter();
        List<String> listDK = Arrays.asList(SystemConstant.STATUS_COURSE);
        listDK.stream().forEach(item -> {
            cb_dk.addItem(item);
        });
        List<UserCourseDTO> courseUserDTOS = searchUsersAll();
        UserCourseTableModel userCourseTableModel = new UserCourseTableModel(courseUserDTOS);
        table_dk_monhoc.setModel(userCourseTableModel);
        table_dk_monhoc.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = table_dk_monhoc.getSelectedRow();
                UserCourseTableModel userTableModel = (UserCourseTableModel) table_dk_monhoc.getModel();
                UserCourseDTO userDTO = userTableModel.getList().get(index);
                userTableModel.setUserCourseSelected(userDTO);
                table_dk_monhoc.setModel(userTableModel);
                txt_dk_fullname.setText(userDTO.getUserDTO().getFullname());
                txt_dk_mssv.setText(userDTO.getUserDTO().getMssv());
                CoursetableModel coursetableModel1 = (CoursetableModel) table_mh.getModel();
                if (userTableModel.getUserCourseSelected() != null && coursetableModel1.getCoursesSelected() != null) {
                    UserCourseEntity userCourseEntity = courseService.findByUserIdAndCourseId(userTableModel.getUserCourseSelected().getId(), coursetableModel1.getCoursesSelected().getId());
                    if (userCourseEntity != null) {
                        cb_dk.setSelectedItem(userCourseEntity.getStatus());
                        point_center.setText(checkNull(userCourseEntity.getPointHk()));
                        point_end.setText(checkNull(userCourseEntity.getPointHkEnd()));
                        point_another.setText(checkNull(userCourseEntity.getPointHk()));
                    } else {
                        cb_dk.setSelectedItem("");
                    }
                } else {
                    cb_dk.setSelectedItem("");
                }
            }
        });

        List<CourseDTO> courseDTOS = searchCourse();
        CoursetableModel coursetableModel = new CoursetableModel(courseDTOS);
        table_mh.setModel(coursetableModel);
        table_mh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CoursetableModel coursetableModel1 = (CoursetableModel) table_mh.getModel();
                int index = table_mh.getSelectedRow();
                CourseDTO courseDTO = coursetableModel1.getList().get(index);
                coursetableModel1.setCourseSelected(courseDTO);
                table_mh.setModel(coursetableModel1);

                UserCourseTableModel userTableModel = (UserCourseTableModel) table_dk_monhoc.getModel();
                if (userTableModel.getUserCourseSelected() != null && coursetableModel1.getCoursesSelected() != null) {
                    txt_name_mh.setText(coursetableModel1.getCoursesSelected().getName() + " - " + coursetableModel1.getCoursesSelected().getCode());
                    UserCourseEntity userCourseEntity = courseService.findByUserIdAndCourseId(userTableModel.getUserCourseSelected().getUserDTO().getId(), coursetableModel1.getCoursesSelected().getId());
                    if (userCourseEntity != null) {
                        cb_dk.setSelectedItem(userCourseEntity.getStatus());
                        point_center.setText(checkNull(userCourseEntity.getPointHk()));
                        point_end.setText(checkNull(userCourseEntity.getPointHkEnd()));
                        point_another.setText(checkNull(userCourseEntity.getPointHk()));
                    } else {
                        cb_dk.setSelectedItem("");
                    }
                } else {
                    cb_dk.setSelectedItem("");
                }
            }
        });

        btn_tk_mh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CourseDTO> courseDTOS1 = searchCourse();
                CoursetableModel tableModel = (CoursetableModel) table_dk_monhoc.getModel();
                tableModel.setCourses(courseDTOS1);
                tableModel.refresh();
            }
        });

        btn_tk_dkmh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<UserCourseDTO> userDTOS = searchUsersAll();
                UserCourseTableModel userCourseTableModel1 = (UserCourseTableModel) table_dk_monhoc.getModel();
                userCourseTableModel1.setList(userDTOS);
                userCourseTableModel1.refresh();
            }
        });

        btn_tk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThongKe();
            }
        });

        btn_update_point.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursetableModel coursetableModel1 = (CoursetableModel) table_mh.getModel();
                int index = table_mh.getSelectedRow();
                if (index < 0) {
                    JOptionPane.showMessageDialog(frame, "Vui long chon mon hoc");
                }
                CourseDTO courseDTO = coursetableModel1.getList().get(index);
                coursetableModel1.setCourseSelected(courseDTO);
                table_mh.setModel(coursetableModel1);

                UserCourseTableModel userTableModel = (UserCourseTableModel) table_dk_monhoc.getModel();
                if (userTableModel.getUserCourseSelected().getUserDTO() != null && coursetableModel1.getCoursesSelected() != null) {
                    try {
                        UserCourseEntity userCourseEntity = courseService.findByUserIdAndCourseId(userTableModel.getUserCourseSelected().getUserDTO().getId(), coursetableModel1.getCoursesSelected().getId());
                        Double point_center1 = Double.parseDouble(point_center.getText());
                        Double point_end1 = Double.parseDouble(point_end.getText());
                        Double point_another1 = Double.parseDouble(point_another.getText());
                        Double total = (point_center1 + point_end1 + point_another1) / 3;
                        userCourseEntity.setStatusPoint("ROT");
                        if (total >= 5) {
                            userCourseEntity.setStatusPoint("DAU");
                        }
                        userCourseEntity.setPointHk(point_center1);
                        userCourseEntity.setPointHkEnd(point_end1);
                        userCourseEntity.setPointHkAnother(point_another1);
                        UserCourseEntity updated = userCourseRepository.update(userCourseEntity);
                        if (updated != null) {
                            JOptionPane.showMessageDialog(frame, "Cap nhat thanh cong");
                        }
                    } catch (Exception err) {
                        JOptionPane.showMessageDialog(frame, "ERR " + err.getMessage());
                    }

                }
            }
        });

        btn_update_dkmh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CoursetableModel coursetableModel1 = (CoursetableModel) table_mh.getModel();
                int index = table_mh.getSelectedRow();
                CourseDTO courseDTO = coursetableModel1.getList().get(index);
                coursetableModel1.setCourseSelected(courseDTO);
                table_mh.setModel(coursetableModel1);
                UserCourseTableModel userTableModel = (UserCourseTableModel) table_dk_monhoc.getModel();
                if (userTableModel.getUserCourseSelected().getUserDTO() != null && coursetableModel1.getCoursesSelected() != null) {
                    try {
                        UserCourseEntity userCourseEntity = courseService.findByUserIdAndCourseId(userTableModel.getUserCourseSelected().getUserDTO().getId(), coursetableModel1.getCoursesSelected().getId());
                        if (userCourseEntity == null) {
                           /* userCourseEntity = new UserCourseEntity();
                            userCourseEntity.setUserEntity(userConverter.convertToEntity(userTableModel.getUserCourseSelected().getUserDTO()));
                            userCourseEntity.setCourseEntity(courseConverter.convertToEntity(coursetableModel1.getCoursesSelected()));
                            userCourseRepository.update(userCourseEntity);*/
                            JOptionPane.showMessageDialog(frame, "Khong the cap nhat,vi Khong tim thay trong db");
                        } else {
                            userCourseEntity.setStatus(cb_dk.getSelectedItem().toString());
                            IUserCourseRepository userCourseRepository = new UserCourseRepository();
                            UserCourseEntity updated = userCourseRepository.update(userCourseEntity);
                            if (updated != null) {
                                JOptionPane.showMessageDialog(frame, "Cap nhat thanh cong ");
                            } else {
                                JOptionPane.showMessageDialog(frame, "Khong the cap nhat");
                            }
                        }
                    } catch (Exception err) {
                        JOptionPane.showMessageDialog(frame, "ERR " + err.getMessage());
                    }
                }
            }
        });

    }

    public List<UserCourseDTO> searchUsersAll() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        query = new SearchUser();
        if (txt_maMon_hoc != null && txt_maMon_hoc.getText().length() > 0) {
            query.setCodeCourse(txt_maMon_hoc.getText());
        }
        if (cboxClassesSearch != null && cboxClassesSearch.getSelectedIndex() > 0 && cboxClassesSearch.getSelectedItem().toString().length() > 0) {
            ClassesDTO classesDTO = this.classes.get(cboxClassesSearch.getSelectedIndex());
            if (classesDTO != null) {
                query.setClassId(classesDTO.getId());
            }
        }
        SearchResult<UserCourseDTO> rsSearched = userService.searchAll(query, pageRequest);
        return rsSearched.getResults();
    }


    public List<UserDTO> searchUsers() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        query = new SearchUser();
        if (txt_maMon_hoc != null && txt_maMon_hoc.getText().length() > 0) {
            query.setCodeCourse(txt_maMon_hoc.getText());
        }
        if (cboxClassesSearch != null && cboxClassesSearch.getSelectedIndex() > 0 && cboxClassesSearch.getSelectedItem().toString().length() > 0) {
            ClassesDTO classesDTO = this.classes.get(cboxClassesSearch.getSelectedIndex());
            if (classesDTO != null) {
                query.setClassId(classesDTO.getId());
            }
        }
        SearchResult<UserDTO> rsSearched = userService.search(query, pageRequest);
        listUsers = rsSearched.getResults();
        return listUsers;
    }

    public void ThongKe() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        query = new SearchUser();
        if (txt_maMon_hoc == null || txt_maMon_hoc.getText().length() == 0) {
            JOptionPane.showMessageDialog(frame, "Vui Long nhap mon hoc ");
        } else {
            query.setCodeCourse(txt_maMon_hoc.getText());
            if (cboxClassesSearch != null && cboxClassesSearch.getSelectedIndex() > 0 && cboxClassesSearch.getSelectedItem().toString().length() > 0) {
                ClassesDTO classesDTO = this.classes.get(cboxClassesSearch.getSelectedIndex());
                if (classesDTO != null) {
                    query.setClassId(classesDTO.getId());
                }
                SearchResult<UserCourseDTO> rsSearched = userService.searchAll(query, pageRequest);
                int sl = rsSearched.getResults().size();
                Double pt_dau = new Double(0);
                Double pt_rot = new Double(0);
                int sl_dau = 0;
                int sl_rot = 0;
                for (int i = 0; i < rsSearched.getResults().size(); i++) {
                    UserCourseDTO userCourseDTO = rsSearched.getResults().get(i);
                    if (userCourseDTO.getStatusPoint() != null && userCourseDTO.getStatusPoint().equals("DAU")) {
                        sl_dau++;
                    } else {
                        sl_rot++;
                    }
                }
                txt_sl.setText(sl + "");
                phantram_dau.setText((sl_dau / sl) + "");
                phantram_rot.setText((pt_rot / sl) + "");
            } else {
                JOptionPane.showMessageDialog(frame, "Vui Long Lop");
            }
        }
    }


    public List<CourseDTO> searchCourse() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        SearchCourse query = new SearchCourse();
        if (txt_mamh != null && txt_mamh.getText().length() > 0) {
            query.setName(txt_mamh.getText());
        }
        if (cboxClassesMHSearch != null && cboxClassesMHSearch.getSelectedItem().toString().length() > 0) {
            query.setClassName(cboxClassesMHSearch.getSelectedItem().toString());
        }
        SearchResult<CourseDTO> rsSearched = courseService.search(query, pageRequest);
        return rsSearched.getResults();
    }

    private List<RoleDTO> getRoles() {
        return roleService.findAll();
    }
}
