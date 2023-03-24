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

import com.spring.pace.VO.FileListVO;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUBVO;
import com.spring.pace.VO.PaceUserVO;

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
	public int selectBoard_no() {
		int board_no = sqlSession.selectOne("BoardDAO.selectBoard_no");
		return board_no;
	}
	
	@Override
	public void uploadImage(FileListVO vo ) {
		int count = sqlSession.insert("BoardDAO.insertFileList", vo);
		System.out.println("업로드되는 이미지 갯수는 ? "+ count);
	}
	
	
	@Override
	public List<String> downloadImage(FileListVO vo) {
		List<String> list = sqlSession.selectList("BoardDAO.selectFileList",vo);
		
		return list;
	}
	
	
	
	@Override
	public void delBoard(int board_no) {//게시물 삭제 메소드
		sqlSession.delete("BoardDAO.deleteBoardComment", board_no);
		sqlSession.delete("BoardDAO.deleteBoard", board_no);
	}
	
	@Override
	public List<PaceUBVO> getBoard(int user_no, int pageNum) {//게시물 가져오는 메소드
		Map map = new HashMap();
		map.put("user_no", user_no);
		map.put("pageNum", pageNum);
		List<PaceUBVO> list = new ArrayList<PaceUBVO>();
		list = sqlSession.selectList("BoardDAO.selectBoard", map);
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
	
	@Override
	public List<PaceBoardVO> myBoardList(int user_no) {
		List<PaceBoardVO> list = sqlSession.selectList("BoardDAO.myBoardList", user_no);
		
		return list;
	}
	
	@Override
	public List<PaceUserVO> myFollowList(int user_no){
		List<PaceUserVO> list = sqlSession.selectList("BoardDAO.myFollowList", user_no);
		
		return list;
	}

	@Override
	public int boardLike(int user_no, int board_no) {
		Map map = new HashMap();
		map.put("user_no", user_no);
		map.put("board_no", board_no);
		sqlSession.selectOne("BoardDAO.selectBLL", map);
		
		int n = sqlSession.update("BoardDAO.updateBoardLike", board_no);
		return 0;/////////////////////
	}
	
	
	
}
