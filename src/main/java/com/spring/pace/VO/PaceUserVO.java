package com.spring.pace.VO;

import java.sql.Date;

public class PaceUserVO {//회원정보
	private int user_no;
	private String user_id;
	private String user_pw;
	private Date user_time;//user_time 에서 바뀜
	private String user_name;
	private String user_email;
	private String user_phone;
	private String user_profile;
	private String user_birth;
	private String user_gender;
	private int user_board_oc;
	private int user_sleep;
	
	
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public Date getUser_time() {
		return user_time;
	}
	public void setUser_time(Date user_time) {
		this.user_time = user_time;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_profile() {
		return user_profile;
	}
	public void setUser_profile(String user_profile) {
		this.user_profile = user_profile;
	}
	public String getUser_birth() {
		return user_birth;
	}
	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}
	public String getUser_gender() {
		return user_gender;
	}
	public void setUser_gender(String user_gender) {
		this.user_gender = user_gender;
	}
	public int getUser_board_oc() {
		return user_board_oc;
	}
	public void setUser_board_oc(int user_board_oc) {
		this.user_board_oc = user_board_oc;
	}
	public int getUser_sleep() {
		return user_sleep;
	}
	public void setUser_sleep(int user_sleep) {
		this.user_sleep = user_sleep;
	}
	
	public void setJoindate(Date date) {
		// TODO Auto-generated method stub
		
	}
	public void setBoard_content(String board_content) {
		// TODO Auto-generated method stub
		
	}
	
}
