package com.spring.pace.VO;

import java.sql.Date;

public class PaceCmCommentVO {
	private int cmComment_no;
	private Date cmComment_time;
	private String cmComment_content;
	private int user_no;
	private int comment_no;
	private int cmComment_like;
	private int cmComment_modify;
	private Date cmComment_modify_time;
	
	
	public int getCmComment_no() {
		return cmComment_no;
	}
	public void setCmComment_no(int cmComment_no) {
		this.cmComment_no = cmComment_no;
	}
	public Date getCmComment_time() {
		return cmComment_time;
	}
	public void setCmComment_time(Date cmComment_time) {
		this.cmComment_time = cmComment_time;
	}
	public String getCmComment_content() {
		return cmComment_content;
	}
	public void setCmComment_content(String cmComment_content) {
		this.cmComment_content = cmComment_content;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getComment_no() {
		return comment_no;
	}
	public void setComment_no(int comment_no) {
		this.comment_no = comment_no;
	}
	public int getCmComment_like() {
		return cmComment_like;
	}
	public void setCmComment_like(int cmComment_like) {
		this.cmComment_like = cmComment_like;
	}
	public int getCmComment_modify() {
		return cmComment_modify;
	}
	public void setCmComment_modify(int cmComment_modify) {
		this.cmComment_modify = cmComment_modify;
	}
	public Date getCmComment_modify_time() {
		return cmComment_modify_time;
	}
	public void setCmComment_modify_time(Date cmComment_modify_time) {
		this.cmComment_modify_time = cmComment_modify_time;
	}
	
	
}
