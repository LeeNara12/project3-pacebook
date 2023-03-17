package com.spring.pace.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.pace.Algorithm.Random;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceCommentVO;
import com.spring.pace.VO.PaceUserVO;


@Repository
public class PaceDAO {
	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;
	
	/////////////////////////////이쪽영역에서 작업금지//////////////////////////////////////
	@Autowired
	SqlSession sqlSession;
	

//	@Override
	public boolean login(PaceUserVO vo) {// 로그인 메소드
		boolean result = false;
	
			List list = sqlSession.selectList("User_infoDAO.login");
			
			if(list.size() == 1 ) {
				result=true;
			} else {
				result=false;
			}
			
		return result;
	}
	
	
	public boolean join(PaceUserVO vo) {//회원가입 메소드
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			
			String query = " select * from user_info"
					+ " where user_id=? ";
		
//					+ " where value = ? ,? ,? ,? ,?, ?, ?";//SQL문 작성  // 회원넘버 시퀀스이름 : seq_user
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, vo.getUser_id());
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result= false;
			}else {
				String query2 = "insert into user_info "
						+ " values ( seq_user.nextval, ? , ?, current_date, ?,?,?,?,?,?)";
				/*(1. USER_NO, 2.USER_ID 3. USER_PW, 4. USER_TIME, 5. USER_NAME, 6. USER_EMAIL, 7. USER_PHONE, 
				 * 8. USER_PROFILE, 9. USER_BIRTH, 10. USER_GENDER)*/
				
				
				pstmt=con.prepareStatement(query2);
				
				// 값을 주는 애들은 jsp
				// 값을 받아오는 애들 
				pstmt.setString(1, vo.getUser_id());
				pstmt.setString(2,vo.getUser_pw());
//				pstmt.setDate(3, vo.getJoindate());
				pstmt.setString(3, vo.getUser_name());
				pstmt.setString(4, vo.getUser_email());
				pstmt.setString(5, vo.getUser_phone());
				pstmt.setString(6, vo.getUser_profile());
				pstmt.setString(7, vo.getUser_birth());
				pstmt.setString(8, vo.getUser_gender());
				
				pstmt.executeUpdate();
				
				result = true;
				
				
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
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

	public void createComment(int user_no, int board_no,PaceCommentVO pcvo) {//댓글 작성 메소드
		try {
			con = dataFactory.getConnection();
			
			String query1 = " insert into board_comment(comment_no, comment_time, comment_content,"
					+ " user_no, board_no, comment_like, comment_modify, comment_modify_time)"
					+ " values(seq_comment.nextval, current_date, ?, ?, ?, 0, 0, null)";//SQL문 작성  // 댓글 넘버 시퀀스 이름 : seq_comment
			
			pstmt = con.prepareStatement(query1);
			pstmt.setString(1, pcvo.getComment_content());
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
	
	public PaceUserVO getUserInfo(int user_no) {//댓글페이지 여는 메소드//댓글정보들을 가져와서 넘기는 메소드
		PaceUserVO puvo = new PaceUserVO();
		try {
			con = dataFactory.getConnection();
			
			String query1 = " select * from user_info"
					+ " where user_no = ?";//SQL문 작성  // 댓글 넘버 시퀀스 이름 : seq_comment
			
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				puvo.setUser_no(user_no);
				puvo.setUser_id(rs.getString("user_id"));
				puvo.setJoindate(rs.getDate("user_time"));
				puvo.setUser_name(rs.getString("user_name"));
				puvo.setUser_email(rs.getString("user_email"));
				puvo.setUser_birth(rs.getString("user_birth"));
				puvo.setUser_phone(rs.getString("user_phone"));
				puvo.setUser_profile(rs.getString("user_profile"));
				puvo.setUser_birth(rs.getString("user_birth"));
				puvo.setUser_gender(rs.getString("user_gender"));
			}
			
			rs.close();
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return puvo;
	}
	
	public List<PaceBoardVO> getBoard() {//게시물 가져오는 메소드
		List<PaceBoardVO> list = new ArrayList<PaceBoardVO>();
		try {
			con = dataFactory.getConnection();
			
			String query1 = " select * from board"
					+ " order by board_time desc";
			
			pstmt = con.prepareStatement(query1);
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
	
	//프로필 url 불러오는 메소드
	public String profile(int user_no) {
		String profile="";
		
		try {
			con = dataFactory.getConnection();
			String query1 = "Select * from user_info"
							+" Where user_no = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			
			ResultSet rs = pstmt.executeQuery();
			
			rs.next();
			
			profile = rs.getString("user_profile");
			
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return profile;
	}


}
	

 
	

	
