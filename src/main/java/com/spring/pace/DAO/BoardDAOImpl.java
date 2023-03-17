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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.spring.ex01.emp.dao.EmpDAO;
import com.spring.ex01.emp.dao.EmpDAOImpl;
import com.spring.pace.VO.PaceBoardVO;

@Repository
public class BoardDAOImpl implements BoardDAOImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	public BoardDAOImpl() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env"); //JNDI 사용을 위한 설정
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public void createBoard(int user_no, PaceBoardVO pbvo) {//게시글 작성 메소드
			
		try {
			
			//DB연결
			con = dataFactory.getConnection();
		
			
			/// 데이터 베이스에 데이터를 추가
	         String query1 = " insert into board"
	                 + "   values(seq_board.nextval, current_date, 0, ?, ?, ?, ?, ?)";//SQL문 작성   // 게시글 넘버 시쿼스이름 : seq_board
	                             //1. 게시판시퀀스 2. 생성일 3.게시판 수정여부 4.게시판 이미지 url 5. 회원 시퀀스 (user_no) 6. 게시판 좋아요수  7. 게시판수정시간 8. 게시판내용  
           pstmt = con.prepareStatement(query1);
           
           pstmt.setString(1, pbvo.getBoard_url());
           pstmt.setInt(2, user_no);
           pstmt.setInt(3, pbvo.getBoard_like());
           pstmt.setDate(4, pbvo.getBoard_modify_time());
           pstmt.setString(5, pbvo.getBoard_content());
		
           pstmt.executeUpdate();
           
           pstmt.close();
           con.close();

		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
	}
	
	public void delBoard(int board_no) {
		try {
			con = dataFactory.getConnection();
			
			String query1 = " delete from board_comment"
					+ " where board_no = ?";
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, board_no);
			pstmt.executeUpdate();
			
			String query2 = " delete from board"
					+ " where board_no = ?";
			
			pstmt = con.prepareStatement(query2);
			pstmt.setInt(1, board_no);
			pstmt.executeUpdate();
			
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<PaceBoardVO> getBoard(int pageNum) {//게시물 가져오는 메소드
		List<PaceBoardVO> list = new ArrayList<PaceBoardVO>();
		try {
			con = dataFactory.getConnection();
			
			String query1 = "SELECT * FROM(\r\n"
					+ "SELECT\r\n"
					+ "	rownum AS rn,\r\n"
					+ "	board_no,\r\n"
					+ "	board_time,\r\n"
					+ "	board_modify,\r\n"
					+ "	board_content,\r\n"
					+ "	board_url,\r\n"
					+ "	user_no,\r\n"
					+ "	board_like,\r\n"
					+ "	board_modify_time\r\n"
					+ "from(\r\n"
					+ "	SELECT \r\n"
					+ "		board_no,\r\n"
					+ "		board_time,\r\n"
					+ "		board_modify,\r\n"
					+ "		board_content,\r\n"
					+ "		board_url,\r\n"
					+ "		user_no,\r\n"
					+ "		board_like,\r\n"
					+ "		board_modify_time\r\n"
					+ "	FROM board\r\n"
					+ "	ORDER BY board_time desc\r\n"
					+ "	)\r\n"
					+ ")\r\n"
					+ "WHERE rn BETWEEN (?-1)*5+1 AND ?*5";
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, pageNum);
			pstmt.setInt(2, pageNum);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PaceBoardVO pbvo = new PaceBoardVO();
				pbvo.setBoard_no(rs.getInt("board_no"));
				pbvo.setBoard_time(rs.getDate("board_time"));
				pbvo.setBoard_modify(rs.getInt("board_modify"));
				pbvo.setBoard_modify_time(rs.getDate("board_modify_time"));
				pbvo.setBoard_content(rs.getString("board_content"));
				pbvo.setUser_no(rs.getInt("user_no"));
				pbvo.setBoard_like(rs.getInt("board_like"));
				pbvo.setBoard_url(rs.getString("board_url"));
				
				list.add(pbvo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PaceBoardVO> search(String search_content) {//게시물 가져오는 메소드
		List<PaceBoardVO> list = new ArrayList<PaceBoardVO>();
		try {
			con = dataFactory.getConnection();
			
			String query1 = " select * from board"
					+ " where board_content like '%'||?||'%'"
					+ " order by board_time asc";
			
			pstmt = con.prepareStatement(query1);
			pstmt.setString(1, search_content);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PaceBoardVO pbvo = new PaceBoardVO();
				pbvo.setBoard_no(rs.getInt("board_no"));
				pbvo.setBoard_time(rs.getDate("board_time"));
				pbvo.setBoard_modify(rs.getInt("board_modify"));
				pbvo.setBoard_modify_time(rs.getDate("board_modify_time"));
				pbvo.setBoard_content(rs.getString("board_content"));
				pbvo.setUser_no(rs.getInt("user_no"));
				pbvo.setBoard_like(rs.getInt("board_like"));
				pbvo.setBoard_url(rs.getString("board_url"));
				
				list.add(pbvo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PaceBoardVO> myBoard(int user_no) {//게시물 가져오는 메소드
		List<PaceBoardVO> list = new ArrayList<PaceBoardVO>();
		try {
			con = dataFactory.getConnection();
			
			String query1 = " select * from board"
					+ " where user_no=?"
					+ " order by board_time desc";
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				PaceBoardVO pbvo = new PaceBoardVO();
				pbvo.setBoard_no(rs.getInt("board_no"));
				pbvo.setBoard_time(rs.getDate("board_time"));
				pbvo.setBoard_modify(rs.getInt("board_modify"));
				pbvo.setBoard_modify_time(rs.getDate("board_modify_time"));
				pbvo.setBoard_content(rs.getString("board_content"));
				pbvo.setUser_no(rs.getInt("user_no"));
				pbvo.setBoard_like(rs.getInt("board_like"));
				pbvo.setBoard_url(rs.getString("board_url"));
				
				list.add(pbvo);
			}
			
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}


}
