package com.spring.pace.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.spring.pace.VO.PaceCmCommentVO;
import com.spring.pace.VO.PaceCommentVO;

public class CommentDAO {
	
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public CommentDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env"); //JNDI 사용을 위한 설정
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void createComment(int user_no, int board_no,String content) {//댓글 작성 메소드
		try {
			con = dataFactory.getConnection();
			
			String query1 = " insert into board_comment(comment_no, comment_time, comment_content,"
					+ " user_no, board_no, comment_like, comment_modify, comment_modify_time)"
					+ " values(seq_comment.nextval, current_date, ?, ?, ?, 0, 0, null)";//SQL문 작성  // 댓글 넘버 시퀀스 이름 : seq_comment
			
			pstmt = con.prepareStatement(query1);
			pstmt.setString(1, content);
			pstmt.setInt(2, user_no);
			pstmt.setInt(3, board_no);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delComment(int comment_no) {//댓글 작성 메소드
		try {
			con = dataFactory.getConnection();
			
			String query1 = " delete from board_comment"
					+ " where comment_no = ?";
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, comment_no);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<PaceCommentVO> comment(int board_no) {//댓글페이지 여는 메소드//댓글정보들을 가져와서 넘기는 메소드
		List<PaceCommentVO> list = new ArrayList<PaceCommentVO>();
		try {
			con = dataFactory.getConnection();
			
			String query1 = " select * from board_comment"
					+ " where board_no = ?"
					+ " order by comment_time asc";//SQL문 작성  // 댓글 넘버 시퀀스 이름 : seq_comment
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, board_no);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PaceCommentVO pcvo = new PaceCommentVO();
				pcvo.setComment_no(rs.getInt("comment_no"));
				pcvo.setComment_time(rs.getDate("comment_time"));
				pcvo.setComment_content(rs.getString("comment_content"));
				pcvo.setUser_no(rs.getInt("user_no"));
				pcvo.setBoard_no(board_no);
				pcvo.setComment_like(rs.getInt("comment_like"));
				pcvo.setComment_modify(rs.getInt("comment_modify"));
				pcvo.setComment_modify_time(rs.getDate("comment_modify_time"));
				
				list.add(pcvo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PaceCmCommentVO> cmComment(int comment_no) {
		List<PaceCmCommentVO> list = new ArrayList<PaceCmCommentVO>();
		try {
			con = dataFactory.getConnection();
			
			String query1 = " select * from cmcomment"
					+ " where comment_no = ?"
					+ " order by cmcomment_time asc";
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, comment_no);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PaceCmCommentVO pccvo = new PaceCmCommentVO();
				pccvo.setCmComment_no(rs.getInt("cmcomment_no"));
				pccvo.setCmComment_time(rs.getDate("cmcomment_time"));
				pccvo.setCmComment_content(rs.getString("cmcomment_content"));
				pccvo.setUser_no(rs.getInt("user_no"));
				pccvo.setComment_no(comment_no);
				pccvo.setCmComment_like(rs.getInt("cmcomment_like"));
				pccvo.setCmComment_modify(rs.getInt("cmcomment_modify"));
				pccvo.setCmComment_modify_time(rs.getDate("cmcomment_modify_time"));
				
				list.add(pccvo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public void createCmComment(int user_no, int comment_no,String content) {//댓글 작성 메소드
		try {
			con = dataFactory.getConnection();
			
			String query1 = " insert into cmcomment(cmComment_no, cmComment_time, cmComment_content,"
					+ " user_no, comment_no, cmComment_like, cmComment_modify, cmComment_modify_time)"
					+ " values(seq_cmcomment.nextval, current_date, ?, ?, ?, 0, 0, null)";//SQL문 작성  // 댓글 넘버 시퀀스 이름 : seq_comment
			
			pstmt = con.prepareStatement(query1);
			pstmt.setString(1, content);
			pstmt.setInt(2, user_no);
			pstmt.setInt(3, comment_no);
			
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
