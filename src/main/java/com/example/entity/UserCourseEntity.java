package com.example.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "USERS_GROUPS")
public class UserCourseEntity implements Serializable {

	private static final long serialVersionUID = -1969294509019205387L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private UserEntity userEntity;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id")
	private CourseEntity courseEntity;

	// additional fields
	private boolean activated;
	@Column(name = "point_hk1")
	private double pointHk;
	@Column(name = "point_hk_end")
	private double pointHkEnd;

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

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
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
}
