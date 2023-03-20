package com.spring.pace.VO;

import java.sql.Date;

public class PaceBoardVO {
	private int board_no; //게시판 번호
	private Date board_time;//게시판 생성 날짜;
	private int board_modify;//수정여부
	private String board_content;//게시판 내용
	private String board_url;
	private int user_no; // 회원넘버
	private int board_like; // 좋아요 
	private Date board_modify_time;// 게시글 수정 시간 
	private int board_comment_cnt;// 댓글 갯수
	private String board_time_s;
	
	
	public String getBoard_time_s() {
		return board_time_s;
	}
	public void setBoard_time_s(String board_time_s) {
		this.board_time_s = board_time_s;
	}
	public String getBoard_url() {
		return board_url;
	}
	public void setBoard_url(String board_url) {
		this.board_url = board_url;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public String getBoard_content() {
		return board_content;
	}
	public void setBoard_content(String board_content) {
		this.board_content = board_content;
	}
	public int getBoard_modify() {
		return board_modify;
	}
	public void setBoard_modify(int board_modify) {
		this.board_modify = board_modify;
	}
	public Date getBoard_time() {
		return board_time;
	}
	public void setBoard_time(Date board_time) {
		this.board_time = board_time;
	}
	// 회원넘버
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	// 좋아요 
	public int getBoard_like() {
		return board_like;
	}
	public void setBoard_like(int board_like) {
		this.board_like = board_like;
	}
	//게시글 수정 시간 
	public  Date getBoard_modify_time() {
		return board_modify_time;
	}
	public void setBoard_modify_time(Date board_modify_time) {
		this.board_modify_time = board_modify_time;
	}
	public int getBoard_comment_cnt() {
		return board_comment_cnt;
	}
	public void setBoard_comment_cnt(int board_comment_cnt) {
		this.board_comment_cnt = board_comment_cnt;
	}
	
}
