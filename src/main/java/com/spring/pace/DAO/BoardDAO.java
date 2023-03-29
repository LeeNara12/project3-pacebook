package com.spring.pace.DAO;

import java.util.List;
import java.util.Map;

import com.spring.pace.VO.FileListVO;
import com.spring.pace.VO.PaceBoardVO;
import com.spring.pace.VO.PaceUBVO;
import com.spring.pace.VO.PaceUserVO;

public interface BoardDAO {
	
	public void createBoard(int user_no, PaceBoardVO pbvo);
	public int selectBoard_no();
	public void uploadImage(FileListVO vo );
	public List downloadImage(FileListVO vo);
	public void delBoard(int board_no);
	public List<PaceUBVO> getBoard(int user_no, int pageNum);
	public List<PaceBoardVO> search(String search_content);
	public List<PaceBoardVO> myBoard(int user_no);
	public int followList_no(int user_no);
	public int followerList_no(int user_no);
	public List<String> myBoardList(int user_no);
	public List<PaceUserVO> myFollowList(int user_no);
	public int boardLike(int user_no, int board_no);
	
	
	//////프로필 관련 메소드///////////////
	public List<FileListVO> selectProfileImage(int user_no);
	public Map select_detail1(FileListVO vo);
	public List<FileListVO> select_detail2(int board_no);
}
