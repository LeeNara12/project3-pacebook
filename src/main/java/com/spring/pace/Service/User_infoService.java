package com.spring.pace.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.pace.DAO.User_infoDAOImpl;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUserVO;


@Service
public class User_infoService {

	
	@Autowired
	private User_infoDAOImpl user_infoDAO;
	
	public Map<String, Object> login(PaceUserVO vo) {
		return user_infoDAO.login(vo);
	}
	
	public boolean join(PaceUserVO vo) {
		return user_infoDAO.join(vo);
	}
	
	public Map<String, Object> idCheck(PaceUserVO vo) {
		return user_infoDAO.idCheck(vo);
	}
	
	public Map<String, Object> pwCheck(PaceUserVO vo) {
		return user_infoDAO.pwCheck(vo);
	}
	
	public List<PaceUserVO> getFollowList(int user_no){
		return user_infoDAO.getFollowList(user_no);
	}
	public List<PaceUserVO> getFollowerList(int user_no){
		return user_infoDAO.getFollowerList(user_no);
	}
	public PaceUserVO getUserInfo(int user_no) {
		System.out.println(" getUserInfo : "+user_infoDAO);
		return user_infoDAO.getUserInfo(user_no);
	}

	public PaceUserVO getProfile(int user_no){
		return user_infoDAO.getUserInfo(user_no);//같은 메소드 사용
	}
	
	public void follow(int user_no, int buser_no) {
		user_infoDAO.follow(user_no, buser_no);
	}
	
	public boolean isFollow(int user_no, int buser_no) {
		return user_infoDAO.isFollow(user_no, buser_no);
	}
	
	public List<PaceUserVO> notFollowUsers(int user_no, int pageNum) {
		return user_infoDAO.notFollowUsers(user_no, pageNum);
	}
	
	public List rNum()  {
		return user_infoDAO.rNum();
	}
	
	public List<PaceBoardVO> rFriend(int user_no){
		return  user_infoDAO.rFriend(user_no);
	}
	

	
}
