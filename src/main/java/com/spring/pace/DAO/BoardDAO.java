package com.spring.pace.DAO;

import java.util.List;

import com.spring.pace.VO.PaceBoardVO;

public interface BoardDAO {
	
	public void createBoard(int user_no, PaceBoardVO pbvo);
	public void delBoard(int board_no);
	public List<PaceBoardVO> getBoard(int pageNum);
	public List<PaceBoardVO> search(String search_content);
	public List<PaceBoardVO> myBoard(int user_no);
}
