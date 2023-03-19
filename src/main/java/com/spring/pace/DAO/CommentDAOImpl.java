package com.spring.pace.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.pace.VO.PaceCmCommentVO;
import com.spring.pace.VO.PaceCommentVO;
import com.spring.pace.VO.PaceUCVO;
import com.spring.pace.VO.PaceUCmCVO;

@Repository
public class CommentDAOImpl implements CommentDAO{
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void createComment(int user_no, int board_no,String content) {//댓글 작성 메소드
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_no", user_no);
		map.put("board_no", board_no);
		map.put("content", content);
		sqlSession.insert("CommentDAO.insertBoardComment",map);
	}
	
	@Override
	public void delComment(int comment_no) {//댓글 삭제 메소드
		sqlSession.delete("CommentDAO.deleteComment", comment_no);
	}
	
	@Override
	public List<PaceCommentVO> comment(int board_no) {//댓글페이지 여는 메소드//댓글정보들을 가져와서 넘기는 메소드
		List<PaceCommentVO> list = new ArrayList<PaceCommentVO>();
		list = sqlSession.selectList("CommentDAO.selectComment", board_no);
		return list;
	}
	
	@Override
	public List<PaceCmCommentVO> cmComment(int comment_no) {
		List<PaceCmCommentVO> list = new ArrayList<PaceCmCommentVO>();
		list = sqlSession.selectList("CommentDAO.selectCmComment", comment_no);
		return list;
	}
	
	@Override
	public void createCmComment(int user_no, int comment_no,String content) {//댓글 작성 메소드
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", content);
		map.put("user_no", user_no);
		map.put("comment_no", comment_no);
		sqlSession.insert("CommentDAO.insertCmComment", map);
	}

	@Override
	public List<PaceUCVO> showComment(int board_no) {
		List<PaceUCVO> list = new ArrayList<PaceUCVO>();
		list = sqlSession.selectList("CommentDAO.selectShowComment", board_no);
		return list;
	}

	@Override
	public List<PaceUCmCVO> showCmComment(int comment_no) {
		List<PaceUCmCVO> list = new ArrayList<PaceUCmCVO>();
		list = sqlSession.selectList("CommentDAO.selectShowCmComment", comment_no);
		return list;
	}
}
