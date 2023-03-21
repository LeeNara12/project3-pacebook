package com.spring.pace.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUBVO;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void createBoard(int user_no, PaceBoardVO pbvo) {//게시글 작성 메소드
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_no", user_no);
		map.put("pbvo", pbvo);
		int count = sqlSession.insert("BoardDAO.insertBoard", map);
		System.out.println("추가된 게시물 갯수 : "+count);
	}
	
	@Override
	public void delBoard(int board_no) {//게시물 삭제 메소드
		sqlSession.delete("BoardDAO.deleteBoardComment", board_no);
		sqlSession.delete("BoardDAO.deleteBoard", board_no);
	}
	
	@Override
	public List<PaceUBVO> getBoard(int pageNum) {//게시물 가져오는 메소드
		List<PaceUBVO> list = new ArrayList<PaceUBVO>();
		list = sqlSession.selectList("BoardDAO.selectBoard", pageNum);
		return list;
	}
	
	@Override
	public List<PaceBoardVO> search(String search_content) {//검색한 게시물 가져오는 메소드
		List<PaceBoardVO> list = new ArrayList<PaceBoardVO>();
		list = sqlSession.selectList("BoardDAO.selectSearch", search_content);
		return list;
	}
	
	@Override
	public List<PaceBoardVO> myBoard(int user_no) {//본인 게시물 가져오는 메소드
		List<PaceBoardVO> list = new ArrayList<PaceBoardVO>();
		list = sqlSession.selectList("BoardDAO.selectMyBoard", user_no);
		return list;
	}
	
	@Override
	public int followList_no(int user_no) {
		int count = sqlSession.selectOne("BoardDAO.followList_no",user_no);
		return count;
	}
	
	@Override
	public int followerList_no(int user_no) {
		int count = sqlSession.selectOne("BoardDAO.followerList_no",user_no);
		return count;
	}
	
	
	
	
}
