package com.example.view;

import com.example.constant.SystemConstant;
import com.example.converter.CourseConverter;
import com.example.converter.UserConverter;
import com.example.data.model.CoursetableModel;
import com.example.data.model.UserCourseTableModel;
import com.example.dto.*;
import com.example.entity.UserCourseEntity;
import com.example.paging.PageRequest;
import com.example.paging.SearchResult;
import com.example.repository.IClassesRepository;
import com.example.repository.IUserCourseRepository;
import com.example.repository.impl.ClassRepository;
import com.example.repository.impl.CourseRepository;
import com.example.repository.impl.UserCourseRepository;
import com.example.service.impl.ClassesService;
import com.example.service.impl.CourseService;
import com.example.service.impl.UserService;
import com.example.utils.UICommonUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;

public class StudentForm {
    private JPanel homeAdmin;
    private JButton exitButton;
    private JLabel welcomeNameLabel;
    private JTabbedPane tabbedPane1;
    private JPanel ql_sv;
    private JTable table_dk_monhoc;
    private JTextField txt_dk_fullname;
    private JComboBox cb_dk;
    private JTextField txt_dk_mssv;
    private JTextField point_center;
    private JTextField point_end;
    private JTextField txt_name_mh;
    private JTextField point_another;
    private JButton btn_tk_dkmh;
    private JComboBox cboxClassesSearch;
    private JTextField txt_maMon_hoc;
    private JButton btn_tk_mh_sv;
    private JTextField txt_text_mh;
    private JComboBox cboxClassesMHSearch;
    private JTable table_mh;
    private JButton btn_change_pass;
    private JTextField txt_code;
    private JFrame frame;
    private List<ClassesDTO> classes;
    private IClassesRepository iClassesRepository = new ClassRepository();
    private ClassesService classesService = new ClassesService(iClassesRepository);
    private CourseRepository courseRepository = new CourseRepository();
    private CourseService courseService = new CourseService(courseRepository);
    private UserService userService = new UserService();


    public StudentForm() {
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
        this.classes = classesService.findAll();
        FormHandle();
    }

    public void FormHandle() {
        IUserCourseRepository userCourseRepository = new UserCourseRepository();
        UserConverter userConverter = new UserConverter();
        CourseConverter courseConverter = new CourseConverter();
        List<String> listDK = Arrays.asList(SystemConstant.STATUS_COURSE);
        listDK.stream().forEach(item -> {
            cb_dk.addItem(item);
        });
        // init data and bean service
        this.classes = classesService.findAll();
        List<ClassesDTO> classesDTOList = this.classes;
        ClassesDTO first = new ClassesDTO();
        first.setName("");
        classesDTOList.add(0, first);
        classesDTOList.stream().forEach(item -> {
            cboxClassesSearch.addItem(item.getName());
            cboxClassesMHSearch.addItem(item.getName());
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

        btn_tk_mh_sv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<CourseDTO> userDTOS = searchCourse();
                CoursetableModel userTableModel = (CoursetableModel) table_mh.getModel();
                userTableModel.setCourses(userDTOS);
                userTableModel.refresh();
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

        btn_change_pass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ChangePassword();
                frame.dispose();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UICommonUtils.logOutSystem(frame);
            }
        });

    }

    public String checkNull(Object str) {
        return str != null ? str.toString() : "";
    }

    public List<CourseDTO> searchCourse() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        SearchCourse query = new SearchCourse();
        if (txt_text_mh != null && txt_text_mh.getText().length() > 0) {
            query.setName(txt_text_mh.getText());
        }
        if (txt_code != null && txt_code.getText().length() > 0) {
            query.setCode(txt_code.getText());
        }
       /* UserDTO userDTO = UICommonUtils.getAccount(frame);
        if (userDTO != null) {
            query.setUserId(userDTO.getId());
        }*/
        if (cboxClassesMHSearch != null && cboxClassesMHSearch.getSelectedItem() != null && cboxClassesMHSearch.getSelectedItem().toString().length() > 0) {
            query.setClassName(cboxClassesMHSearch.getSelectedItem().toString());
        }
        SearchResult<CourseDTO> rsSearched = courseService.search(query, pageRequest);
        return rsSearched.getResults();
    }

    public List<UserCourseDTO> searchUsersAll() {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setMaxPageItem(100);
        SearchUser query = new SearchUser();
        if (txt_maMon_hoc != null && txt_maMon_hoc.getText().length() > 0) {
            query.setCodeCourse(txt_maMon_hoc.getText());
        }
        UserDTO userDTO = UICommonUtils.getAccount(frame);
        query.setUserId(new Long(3));
        if (userDTO != null) {
            query.setUserId(userDTO.getId());
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


}
