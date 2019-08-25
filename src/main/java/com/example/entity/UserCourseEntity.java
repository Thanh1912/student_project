package com.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
//ref:  https://www.codejava.net/frameworks/hibernate/hibernate-many-to-many-association-with-extra-columns-in-join-table-example
@Entity
@Table(name = "user_course")
public class UserCourseEntity implements Serializable {
	private static final long serialVersionUID = -1969294509019205387L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_course_id")
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userid")
	private UserEntity userEntity;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "courseid")
	private CourseEntity courseEntity;

	// additional fields
	private String status;
	@Column(name = "point_hk1")
	private double pointHk;
	@Column(name = "point_hk_end")
	private double pointHkEnd;

	@Column(name = "point_hk_another")
	private double pointHkAnother;

	@Column(name = "point")
	private double point;

	@Column(name = "status_point")
	private String statusPoint;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}
}
