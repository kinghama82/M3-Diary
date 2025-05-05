package com.springboot.biz.diary;

import com.springboot.biz.m3user.M3User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "diary",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"m3user_user_seq", "year", "month", "day"}))
public class Diary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private int year;
	private int month;
	private int day;
	
	@Column(nullable = false)
	private String content;
	
	@ManyToOne
	private M3User m3user;
}
