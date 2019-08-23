package com.example.dto;

import com.example.entity.CourseEntity;
import com.example.entity.UserEntity;

public class UserCourseEntities {
    private Long id;
    private UserEntity userEntity;
    private CourseEntity courseEntity;
    private String status;
    private double pointHk;
    private double pointHkEnd;
    private double pointHkAnother;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String statusPoint;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public CourseEntity getCourseEntity() {
        return courseEntity;
    }

    public void setCourseEntity(CourseEntity courseEntity) {
        this.courseEntity = courseEntity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}
