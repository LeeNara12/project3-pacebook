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

import com.spring.pace.Algorithm.TemporaryPW;
import com.spring.pace.VO.PaceUserVO;

public class User_infoDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	public User_infoDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context) ctx.lookup("java:/comp/env"); // JNDI 사용을 위한 설정
			dataFactory = (DataSource) envContext.lookup("jdbc/oracle2");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public boolean login(PaceUserVO vo) {// 로그인 메소드
		boolean result = false;
		try {
			con = dataFactory.getConnection();

			String query = " select * from user_info" + " where user_id = ? and user_pw = ?";// SQL문 작성

			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getUser_id());
			pstmt.setString(2, vo.getUser_pw());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				vo.setUser_no(rs.getInt("user_no"));
				vo.setUser_time(rs.getDate("user_time"));
				result = true;
			} else {
				result = false;
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean join(PaceUserVO vo) {// 회원가입 메소드
		boolean result = false;
		try {
			con = dataFactory.getConnection();

			String query = " select * from user_info" + " where user_id=? ";

//					+ " where value = ? ,? ,? ,? ,?, ?, ?";//SQL문 작성  // 회원넘버 시퀀스이름 : seq_user

			pstmt = con.prepareStatement(query);

			pstmt.setString(1, vo.getUser_id());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				result = false;
			} else {
				String query2 = "insert into user_info "
			


						+ " values ( seq_user.nextval, ? , ?, current_date, ?,?,?,'image/silde5.jpg',?,?,0,0)";
				/*(1. USER_NO, 2.USER_ID 3. USER_PW, 4. USER_TIME, 5. USER_NAME, 6. USER_EMAIL, 7. USER_PHONE, 
				 * 8. USER_PROFILE, 9. USER_BIRTH, 10. USER_GENDER )*/
				
				
				pstmt=con.prepareStatement(query2);
				


				// 값을 주는 애들은 jsp
				// 값을 받아오는 애들
				pstmt.setString(1, vo.getUser_id());
				System.out.println(vo.getUser_id());
				pstmt.setString(2, vo.getUser_pw());
				pstmt.setString(3, vo.getUser_name());
				pstmt.setString(4, vo.getUser_email());
				pstmt.setString(5, vo.getUser_phone());
				pstmt.setString(6, vo.getUser_birth());
				pstmt.setString(7, vo.getUser_gender());
				
				
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

	public boolean idCheck(PaceUserVO vo) {// 아이디찾기 메소드

//		Map map = new HashMap();
		boolean result = false;

		try {
			con = dataFactory.getConnection();

			StringBuffer query = new StringBuffer();
			query.append("select * from user_info ");
			query.append(" where user_name = ?");
			query.append(" and user_birth = ?");
			query.append(" and user_phone = ?");

			System.out.println(query);

			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, vo.getUser_name());
			pstmt.setString(2, vo.getUser_birth());
			pstmt.setString(3, vo.getUser_phone());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {// where 조건에 맞는 SQL문이 있다면 아이디찾기 가능

				vo.setUser_id(rs.getString("user_id"));
				result = true;

			} // 나머지는 불가능

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return result;
	}

	public boolean pwCheck(PaceUserVO vo) {// 비밀번호 일치 불일치 메소드
		boolean result = false;
		TemporaryPW tem = new TemporaryPW();
		String temPW = tem.temPW();

		try {
			con = dataFactory.getConnection();

			StringBuffer sb = new StringBuffer();
			sb.append("select * from user_info");
			sb.append(" where user_name = ?");
			sb.append(" and user_id = ?");

			System.out.println(sb);

			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, vo.getUser_name());
			pstmt.setString(2, vo.getUser_id());

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {// where 조건에 맞는 SQL문이 있다면 임시비밀번호 찾기 가능

				con = dataFactory.getConnection();

				StringBuffer sb2 = new StringBuffer();
				sb2.append("update user_info");
				sb2.append(" set user_pw = ?");
				sb2.append(" where user_name = ?");
				sb2.append(" and user_id = ?");

				System.out.println(sb2);

//				pstmt.close();

				pstmt = con.prepareStatement(sb2.toString());
				pstmt.setString(1, temPW);
				pstmt.setString(2, vo.getUser_name());
				pstmt.setString(3, vo.getUser_id());

				pstmt.executeUpdate();

				vo.setUser_pw(temPW);
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

	public PaceUserVO getUserInfo(int user_no) {// 댓글페이지 여는 메소드//댓글정보들을 가져와서 넘기는 메소드
		PaceUserVO puvo = new PaceUserVO();
		try {
			con = dataFactory.getConnection();

			String query1 = " select * from user_info" + " where user_no = ?";// SQL문 작성 // 댓글 넘버 시퀀스 이름 : seq_comment

			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				puvo.setUser_no(user_no);
				puvo.setUser_id(rs.getString("user_id"));
				puvo.setUser_time(rs.getDate("user_time"));
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

	public String profile(int user_no) {
		String profile = "";

		try {
			con = dataFactory.getConnection();
			String query1 = "Select * from user_info" + " Where user_no = ?";
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

	public PaceUserVO profile1(int user_no) {
		PaceUserVO vo = new PaceUserVO();

		try {
			con = dataFactory.getConnection();
			String query1 = "Select * from user_info" + " Where user_no = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);

			ResultSet rs = pstmt.executeQuery();

			rs.next();

			String profile = rs.getString("user_profile");
			String name = rs.getString("user_name");

			vo.setUser_profile(profile);
			vo.setUser_name(name);

//         String query2 = "Select * from user_followlist"
//               +" Where user_no = ?";
//         pstmt = con.prepareStatement(query2);
//         
//         String follow = rs.getString("user_follow");
//         String follower = rs.getString("user_follower");
//         
			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vo;
	}

	public List<PaceUserVO> getFollowList(int user_no) {
		List<PaceUserVO> puvoList = new ArrayList<PaceUserVO>();

		try {
			con = dataFactory.getConnection();
			String query1 = "Select * from follow_list" + " Where user_no = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				PaceUserVO puvo = new PaceUserVO();

				String query2 = "select * from user_info" + " where user_no = ?";
				pstmt = con.prepareStatement(query2);
				pstmt.setInt(1, rs.getInt("follow_u_no"));
				ResultSet rs1 = pstmt.executeQuery();
				rs1.next();
				puvo.setUser_no(rs1.getInt("user_no"));
				puvo.setUser_id(rs1.getString("user_id"));
				puvo.setUser_name(rs1.getString("user_name"));
				puvo.setUser_birth(rs1.getString("user_birth"));
				puvo.setUser_gender(rs1.getString("user_gender"));
				puvo.setUser_phone(rs1.getString("user_phone"));
				puvo.setUser_profile(rs1.getString("user_profile"));

				puvoList.add(puvo);
				rs1.close();
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return puvoList;
	}

	public List<PaceUserVO> getFollowerList(int user_no) {
		List<PaceUserVO> puvoList = new ArrayList<PaceUserVO>();

		try {
			con = dataFactory.getConnection();
			String query1 = "Select * from follower_list" + " Where user_no = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				PaceUserVO puvo = new PaceUserVO();

				String query2 = "select * from user_info" + " where user_no = ?";
				pstmt = con.prepareStatement(query2);
				pstmt.setInt(1, rs.getInt("follower_u_no"));
				ResultSet rs1 = pstmt.executeQuery();
				rs1.next();
				puvo.setUser_no(rs1.getInt("user_no"));
				puvo.setUser_name(rs1.getString("user_name"));
				puvo.setUser_birth(rs1.getString("user_birth"));
				puvo.setUser_gender(rs1.getString("user_gender"));
				puvo.setUser_phone(rs1.getString("user_phone"));
				puvo.setUser_profile(rs1.getString("user_profile"));

				puvoList.add(puvo);
				rs1.close();
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return puvoList;
	}
	
	public void follow(int user_no, int buser_no) {
		try {
			con = dataFactory.getConnection();
			String query1 = " select * from follow_list"
					+ " where user_no = ? and follow_u_no = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			pstmt.setInt(2, buser_no);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) { // 팔로우가 되어있으면 => 취소
				String query2 = " delete from follow_list"
						+ " where user_no = ? and follow_u_no = ?";
				pstmt = con.prepareStatement(query2);
				pstmt.setInt(1, user_no);
				pstmt.setInt(2, buser_no);
				
				pstmt.executeUpdate();
				
			} else { // 팔로우가 안되어 있으면 => 팔로우
				String query3 = " insert into follow_list"
						+ " values(?, ?)";
				pstmt = con.prepareStatement(query3);
				pstmt.setInt(1, user_no);
				pstmt.setInt(2, buser_no);
				
				pstmt.executeUpdate();
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isFollow(int user_no, int buser_no) {
		boolean result = false;
		try {
			con = dataFactory.getConnection();
			String query1 = " select * from follow_list"
					+ " where user_no = ? and follow_u_no = ?";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			pstmt.setInt(2, buser_no);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) { // 팔로우가 되어있으면 => true
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
	
	public List<PaceUserVO> notFollowUsers(int user_no, int pageNum) {
		List<PaceUserVO> nfuList = new ArrayList<PaceUserVO>();
		try {
			con = dataFactory.getConnection();
			String query1 = " SELECT \r\n"
					+ "	*\r\n"
					+ "FROM(\r\n"
					+ "	SELECT \r\n"
					+ "		rownum,\r\n"
					+ "		user_no\r\n"
					+ "	from(\r\n"
					+ "		SELECT\r\n"
					+ "			u2.user_no\r\n"
					+ "		FROM user_info u1 LEFT outer JOIN user_info u2 ON (u1.user_no != u2.user_no)\r\n"
					+ "		WHERE u1.user_no = ?\r\n"
					+ "		minus\r\n"
					+ "		SELECT\r\n"
					+ "			follow_u_no\r\n"
					+ "		FROM follow_list\r\n"
					+ "		WHERE user_no = ?\r\n"
					+ "		)\r\n"
					+ ")\r\n"
					+ "WHERE rownum BETWEEN (?-1)*20 AND ?*20";
			pstmt = con.prepareStatement(query1);
			pstmt.setInt(1, user_no);
			pstmt.setInt(2, user_no);
			pstmt.setInt(3, pageNum);
			pstmt.setInt(4, pageNum);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) { // 팔로우가 되어있으면 => true
				PaceUserVO puvo = new PaceUserVO();
				String query2 = "select * from user_info"
						+ " where user_no = ?";
				pstmt = con.prepareStatement(query2);
				pstmt.setInt(1, rs.getInt("user_no"));
				ResultSet rs1 = pstmt.executeQuery();
				rs1.next();
				puvo.setUser_no(rs1.getInt("user_no"));
				puvo.setUser_id(rs1.getString("user_id"));
				puvo.setUser_name(rs1.getString("user_name"));
				puvo.setUser_birth(rs1.getString("user_birth"));
				puvo.setUser_gender(rs1.getString("user_gender"));
				puvo.setUser_phone(rs1.getString("user_phone"));
				puvo.setUser_profile(rs1.getString("user_profile"));
				
				nfuList.add(puvo);
				rs1.close();
			}
			
			rs.close();
			pstmt.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nfuList;
	}

}
