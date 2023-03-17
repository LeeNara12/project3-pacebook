package com.spring.pace.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.pace.Algorithm.Random;
import com.spring.pace.Algorithm.TemporaryPW;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUserVO;

@Repository
public class User_infoDAO {

	private Connection con;
	private PreparedStatement pstmt;
	private DataSource dataFactory;

	@Autowired
	SqlSession sqlSession;

	public boolean login(PaceUserVO vo) {// 로그인 메소드
		boolean result = false;
		System.out.println(vo.getUser_id()+ vo.getUser_pw());//null,null
		
		List list = sqlSession.selectList("User_infoDAO.login",vo);
		System.out.println("list.size() == "+list.size());
		
		if(list.size() == 1 ) {
			result=true;
		} else {
			result=false;
		}
		
	return result;
	}

	public boolean join(PaceUserVO vo) {// 회원가입 메소드
		boolean result = false;
		
		List list = sqlSession.selectList("User_infoDAO.join1",vo);
		
		if(list.size() != 0) {
			result = false;
		}else {
			
			int count = sqlSession.insert("User_infoDAO.join2",vo);
			System.out.println("회원가입된 사람 "+count+"명");
			result = true;
		}
		
		return result;
	}
	

	public boolean idCheck(PaceUserVO vo) {// 아이디찾기 메소드
		boolean result = false;
		List list = sqlSession.selectList("User_infoDAO.idCheck",vo);
		
		if(list.size() == 1) {
			result = true;
		}
		return result;
	}
	
	

	public boolean pwCheck(PaceUserVO vo) {// 비밀번호 일치 불일치 메소드
		boolean result = false;
		Map map = new HashMap();
		
		TemporaryPW tem = new TemporaryPW();
		String temPW = tem.temPW();
		
		map.put("vo", vo);
		map.put("temPW",temPW);

		List list = sqlSession.selectList("User_infoDAO.pwCheck1",vo);
		
		if(list.size() == 1) {

			int count = sqlSession.update("User_infoDAO.pwCheck2",map);
			System.out.println("임시비밀번호가 생성되었습니다.");
			result = true;
			
		}
		
		return result;
	}

	
	
	//User_infoVO객체 가져와서 vo에 전체 저장
	public PaceUserVO getUserInfo(int user_no) {// 댓글페이지 여는 메소드//댓글정보들을 가져와서 넘기는 메소드
		PaceUserVO vo = sqlSession.selectOne("User_infoDAO.getUserInfo",user_no);
		return vo;
	}
	
	
	
	
	public String profile(int user_no) {
		String profile = "";

		profile = sqlSession.selectOne("User_infoDAO.profile", user_no);
		
		return profile;
	}
	
	
	
	
	////////////////////////////getUserInfo메소드 사용하기///////////////////////////
//	public PaceUserVO profile1(int user_no) {
//		PaceUserVO vo = sqlSession.selectOne("User_infoDAO.profile1", user_no);
//
//		try {
//			con = dataFactory.getConnection();
//			String query1 = "Select * from user_info" + " Where user_no = ?";
//			pstmt = con.prepareStatement(query1);
//			pstmt.setInt(1, user_no);
//
//			ResultSet rs = pstmt.executeQuery();
//
//			rs.next();
//
//			String profile = rs.getString("user_profile");
//			String name = rs.getString("user_name");
//
//			vo.setUser_profile(profile);
//			vo.setUser_name(name);
//
//        
//			rs.close();
//			pstmt.close();
//			con.close();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return vo;
//	}

	
	public List<PaceUserVO> getFollowList(int user_no) {
		List<PaceUserVO> puvoList = sqlSession.selectList("User_infoDAO.getFollowList", user_no);
		return puvoList;
	}
	
	

	public List<PaceUserVO> getFollowerList(int user_no) {
		List<PaceUserVO> puvoList = sqlSession.selectList("User_infoDAO.getFollowerList", user_no);
		
		return puvoList;
	}
	
	
	/////////////////////여기서 부터 하기///////////////////////////////
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
	
	
	
	// 회원가입 성공 페이지에서 랜덤으로 추천친구 보여주기 
	// 1.DB에서 총 회원수를 가져온다 COUNT 활용
	// 2. MATH.RANDOM을 사용하여 COUNT 값을 랜덤으로 출력 하게 한다.
	
//	String select = "SELET COUNT(*) FROM USER_INFO WHERE REFCOUNT='0'" ;
//	Connection dbConnection = null;
//	Statement statement = null;
//	int resultCount = 0;
//	 boolean rs = (Boolean) null;
//	try {
//		rs = statement.execute(select);
//	}
	public List<PaceUserVO> rNum() {
		System.out.println("count실행됨");
		List<PaceUserVO> list = new ArrayList<PaceUserVO>();
		int [] ka = new int[2]; // 배열의 길이 선언
		
	try {
		con = dataFactory.getConnection();
		String query="";
		query = "SELECT * FROM"
				+ " (SELECT  user_no, rownum AS rnum  FROM user_info) tmp"
				+ " ORDER BY rnum desc" ;//SQL문
		
		pstmt = con.prepareStatement(query); // DB연결 /pstmt 디비 영역 객체
		ResultSet rs = pstmt.executeQuery(); //데이터베이스 결과 값 가져오기 
		rs.next();
		int rnum = rs.getInt("rnum");
		ka[0] =(int) (Math.random()*rnum);
		Random ra = new Random();
		Map map =ra.map(rnum);
		
		String squ = "SELECT * FROM"
								+ " (SELECT  user_no, rownum AS rnum,user_profile,user_id  FROM user_info) tmp"
								+ " WHERE rnum = ? OR rnum = ?";
		pstmt = con.prepareStatement(squ);
		pstmt.setInt(1,(int)map.get("a"));
		pstmt.setInt(2,(int)map.get("b"));
		ResultSet rss  =  pstmt.executeQuery(); 
		
		while(rss.next()) {
			PaceUserVO vo = new PaceUserVO();
			
			int user_no = rss.getInt("user_no");
			String user_profile = rss.getString("user_profile");
			String user_id = rss.getString("user_id");
			
			vo.setUser_no(user_no);
			vo.setUser_profile(user_profile);
			vo.setUser_id(user_id);
			
			list.add(vo);
		}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	public List<PaceBoardVO> rFriend(int user_no){
		List<PaceBoardVO> pbvoList = new ArrayList<PaceBoardVO>();
		try {
			con = dataFactory.getConnection();
			String query = "SELECT * from(\r\n"
			         + "SELECT \r\n"
			         + "   rownum AS rnum,\r\n"
			         + "   user_no,\r\n"
			         + "   user_profile,\r\n"
			         + "   board_url\r\n"
			         + "FROM (\r\n"
			         + "   SELECT\r\n"
			         + "      user_no,\r\n"
			         + "      user_profile\r\n"
			         + "   FROM USER_info\r\n"
			         + "   )u JOIN board b using(user_no)\r\n"
			         + "WHERE user_no = ?\r\n"
			         + ")\r\n"
			         + "WHERE rnum < 3";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, user_no);
			ResultSet rs = pstmt.executeQuery();
			for(int i=0; i<2; i++) {
				if(rs.next()) {
					String board_url = rs.getString("board_url");
					PaceBoardVO pbvo = new PaceBoardVO();
					pbvo.setBoard_url(board_url);
					pbvoList.add(pbvo);
				} else {
					pbvoList.add(null);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pbvoList;
	}


}
