package com.spring.pace.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.pace.DAO.BoardDAO;
import com.spring.pace.VO.FileListVO;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUBVO;
import com.spring.pace.VO.PaceUserVO;

@Service
public class BoardService {

	
	@Autowired
	BoardDAO boardDAO;
	
	public void createBoard(int user_no, PaceBoardVO pbvo) {
		boardDAO.createBoard(user_no, pbvo);
	}
	public int selectBoard_no() {
		return boardDAO.selectBoard_no();
	}
	public void uploadImage(FileListVO vo ) {
		boardDAO.uploadImage(vo);
	}
	public List<String> downloadImage(FileListVO vo) {
		return boardDAO.downloadImage(vo);
	}
	
	
	public void delBoard(int board_no) {
		boardDAO.delBoard(board_no);
	}

	public List<PaceBoardVO> search(String search_content){
		return boardDAO.search(search_content);
	}
	
	public List<PaceUBVO> getBoard(int pageNum){
		return boardDAO.getBoard(pageNum);
	}
	public List<PaceBoardVO> myBoard(int user_no){
		return boardDAO.myBoard(user_no);
	}
	
	public int followList_no(int user_no) {
		return boardDAO.followList_no(user_no);
	}
	public int followerList_no(int user_no) {
		return boardDAO.followerList_no(user_no);
	}
	
	public List<PaceBoardVO> myBoardList(int user_no) {
		return boardDAO.myBoardList(user_no);
	}
	
	public List<PaceUserVO> myFollowList(int user_no){
		return boardDAO.myFollowList(user_no);
	}
	
}
