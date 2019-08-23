package com.example.dto;

import com.example.entity.CourseEntity;
import com.example.entity.UserEntity;

public class UserCourseDTO {
    private Long id;
    private String status;
    private Long courseid;
    private Long userid;
    private UserDTO userDTO;
    private double pointHk;
    private double pointHkEnd;
    private double pointHkAnother;
    private double point;
    private String statusPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public double getPointHk() {
        return pointHk;
    }

    public void setPointHk(double pointHk) {
        this.pointHk = pointHk;
    }

    public double getPointHkEnd() {
        return pointHkEnd;
    }

    public void setPointHkEnd(double pointHkEnd) {
        this.pointHkEnd = pointHkEnd;
    }

    public double getPointHkAnother() {
        return pointHkAnother;
    }

    public void setPointHkAnother(double pointHkAnother) {
        this.pointHkAnother = pointHkAnother;
    }

    public String getStatusPoint() {
        return statusPoint;
    }

    public void setStatusPoint(String statusPoint) {
        this.statusPoint = statusPoint;
    }

    public Long getCourseid() {
        return courseid;
    }

    public void setCourseid(Long courseid) {
        this.courseid = courseid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
