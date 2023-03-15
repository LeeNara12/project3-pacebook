package com.spring.pace.VO;

import java.sql.Date;

public class PaceCommentVO {
	private int comment_no; // 댓글넘버
	private Date comment_time; // 댓글 작성 시간 
	private String comment_content; // 댓글 내용
	private int user_no; // 회원 넘버
	private int board_no; // 게시글 넘버
	private int comment_like; // 좋아요
	private int comment_modify; // 
	private Date comment_modify_time;
	
	public int getComment_no() {
		return comment_no;
	}
	public void setComment_no(int comment_no) {
		this.comment_no = comment_no;
	}
	public Date getComment_time() {
		return comment_time;
	}
	public void setComment_time(Date comment_time) {
		this.comment_time = comment_time;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public int getComment_like() {
		return comment_like;
	}
	public void setComment_like(int comment_like) {
		this.comment_like = comment_like;
	}
	public int getComment_modify() {
		return comment_modify;
	}
	public void setComment_modify(int comment_modify) {
		this.comment_modify = comment_modify;
	}
	public Date getComment_modify_time() {
		return comment_modify_time;
	}
	public void setComment_modify_time(Date comment_modify_time) {
		this.comment_modify_time = comment_modify_time;
	}
	
}
