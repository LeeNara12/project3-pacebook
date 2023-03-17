package com.spring.pace.DAO;

import java.util.List;

import com.spring.pace.VO.PaceCmCommentVO;
import com.spring.pace.VO.PaceCommentVO;

public interface CommentDAO {
	public void createComment(int user_no, int board_no,String content);
	public void delComment(int comment_no);
	public List<PaceCommentVO> comment(int board_no);
	public List<PaceCmCommentVO> cmComment(int comment_no);
	public void createCmComment(int user_no, int comment_no,String content);
}
