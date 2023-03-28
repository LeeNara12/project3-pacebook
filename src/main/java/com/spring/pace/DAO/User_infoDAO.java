package com.spring.pace.DAO;

import java.util.List;
import java.util.Map;

import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUserVO;

public interface User_infoDAO {

	public Map<String, Object> login(PaceUserVO vo);
	public boolean join(PaceUserVO vo);
	public Map<String , Object> idCheck(PaceUserVO vo);
	public Map<String , Object> pwCheck(PaceUserVO vo);
	public PaceUserVO getUserInfo(int user_no);
	public String profile(int user_no);
	public List<PaceUserVO> getFollowList(int user_no);
	public List<PaceUserVO> getFollowerList(int user_no);
	public void follow(int user_no, int buser_no);
	public boolean isFollow(int user_no, int buser_no);
	public List<PaceUserVO> notFollowUsers(int user_no, int pageNum);
	public List rNum();
	public List<PaceBoardVO> rFriend(int user_no);
	
	
}
