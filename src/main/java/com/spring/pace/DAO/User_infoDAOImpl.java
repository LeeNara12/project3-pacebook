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
public class User_infoDAOImpl implements User_infoDAO{
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public Map<String, Object> login(PaceUserVO vo) {// 로그인 메소드
		Map<String, Object> map = new HashMap<String,Object>();
		boolean result = false;
		System.out.println(vo.getUser_id()+ vo.getUser_pw());//null,null
		
		PaceUserVO puvo = sqlSession.selectOne("User_infoDAO.login",vo);
		map.put("puvo", puvo);
		System.out.println("puvo : "+puvo);
		
		if(puvo != null ) {
			result=true;
		} else {
			result=false;
		}
		map.put("result", result);
		
	return map;
	}

	@Override
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
	

	@Override
	public Map<String , Object> idCheck(PaceUserVO vo) {// 아이디찾기 메소드
		Map<String , Object> map = new HashMap();
		
		System.out.println("idCheck로 진입 + vo 는? "+vo);
		boolean result = false;
		List list = sqlSession.selectList("User_infoDAO.idCheck",vo);
		
		if(list.size() == 1) {
			result = true;
			map.put("result", result);
			map.put("vo", list.get(0));
		}
		return map;
	}
	
	

	@Override
	public Map<String, Object> pwCheck(PaceUserVO vo) {// 비밀번호 일치 불일치 메소드
		boolean result = false;
		System.out.println("pwCheck메소드 진입");
		Map<String, Object> map = new HashMap<String, Object>();
		
		TemporaryPW tem = new TemporaryPW();
		String temPW = tem.temPW();
		
		map.put("temPW", temPW);//임시비밀번호 업데이트문 parameter
		map.put("vo", vo);//vo.user_name, vo.user_id로 업데이트 where조건 입력
		
		List list = sqlSession.selectList("User_infoDAO.pwCheck1",vo);
		
		if(list.size() == 1) {

			int count = sqlSession.update("User_infoDAO.pwCheck2",map);
			System.out.println("임시비밀번호가 "+count+"개 생성되었습니다.");
			result = true;
			
		}
		
		map.put("result", result);
		
		return map;
	}

	
	//User_infoVO객체 가져와서 vo에 전체 저장
	@Override
	public PaceUserVO getUserInfo(int user_no) {// 댓글페이지 여는 메소드//댓글정보들을 가져와서 넘기는 메소드
		PaceUserVO vo = sqlSession.selectOne("User_infoDAO.getUserInfo",user_no);
		return vo;
	}
	
	
	
	@Override
	public String profile(int user_no) {
		String profile = "";

		profile = sqlSession.selectOne("User_infoDAO.profile", user_no);
		
		return profile;
	}
	

	@Override
	public List<PaceUserVO> getFollowList(int user_no) {
		List<PaceUserVO> puvoList = sqlSession.selectList("User_infoDAO.getFollowList", user_no);
		return puvoList;
	}
	
	

	@Override
	public List<PaceUserVO> getFollowerList(int user_no) {
		List<PaceUserVO> puvoList = sqlSession.selectList("User_infoDAO.getFollowerList", user_no);
		
		return puvoList;
	}
	

	@Override
	public void follow(int user_no, int buser_no) {
		
		Map map = new HashMap();
		map.put("user_no",user_no);
		map.put("buser_no",buser_no);
		
		List list = sqlSession.selectList("User_infoDAO.follow1", map);
		
		if(list.size() == 1) {
			
			int count = sqlSession.insert("User_infoDAO.follow2", map);
			System.out.println(count+"명과 팔로우를 취소합니다");
			
		}else if(list.size() == 0) {
			
			int count = sqlSession.delete("User_infoDAO.follow3",map);
			System.out.println(count+"명과 팔로우합니다");
		}

	}
	
	
	
	@Override
	public boolean isFollow(int user_no, int buser_no) {
		boolean result = false;
		
		Map map = new HashMap();
		map.put("user_no", user_no);
		map.put("buser_no", buser_no);
		
		List list = sqlSession.selectList("User_infoDAO.isFollow", map);
		
		if(list.size() == 1) {
			result = true;
		}
		
		return result;
	}
	
	
	
	@Override
	public List<PaceUserVO> notFollowUsers(int user_no, int pageNum) {
		
		Map map = new HashMap();
		map.put("user_no", user_no);
		map.put("pageNum", pageNum);
		
		List<PaceUserVO> nfuList = sqlSession.selectList("User_infoDAO.notFollowUsers", map);
		
		return nfuList;
	
	}
	
	// 회원가입 성공 페이지에서 랜덤으로 추천친구 보여주기 
	// 1.DB에서 총 회원수를 가져온다 COUNT 활용
	// 2. MATH.RANDOM을 사용하여 COUNT 값을 랜덤으로 출력 하게 한다.
	
	@Override
	public List rNum() {
		
		System.out.println("rNum 메소드 진입");
		int maxRnum = sqlSession.selectOne("User_infoDAO.rNum1");
		Random random = new Random();
		Map map =random.randomFriend(maxRnum);
		List list = (List)sqlSession.selectList("User_infoDAO.rNum2",map);

		return list;
	}
	
	
	@Override
	public List<PaceBoardVO> rFriend(int user_no){
		List<PaceBoardVO> pbvoList = new ArrayList<PaceBoardVO>();
		List list = sqlSession.selectList("User_infoDAO.rFriend", user_no);
		
		if(list.size() == 2) {
			pbvoList = list;
		}
	
		return pbvoList;
	}


}
