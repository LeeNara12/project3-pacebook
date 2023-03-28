package com.spring.pace.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.pace.DAO.CommentDAO;
import com.spring.pace.VO.PaceCmCommentVO;
import com.spring.pace.VO.PaceCommentVO;
import com.spring.pace.VO.PaceUCVO;
import com.spring.pace.VO.PaceUCmCVO;

@Service
public class CommentService {

	@Autowired
	CommentDAO commentDAO;
	
	public void createComment(int user_no, int board_no, String content) {
		commentDAO.createComment(user_no, board_no, content);
	}
	
	public void delComment(int comment_no) {
		commentDAO.delComment(comment_no);
	}

	public List<PaceCommentVO> comment(int board_no){
		return commentDAO.comment(board_no);
	}
	
	public List<PaceCmCommentVO> cmComment(int comment_no){
		return commentDAO.cmComment(comment_no);
	}
	
	public void createCmComment(int user_no, int comment_no, String cmcomment_content) {
		commentDAO.createCmComment(user_no, comment_no, cmcomment_content);
	}
	
	public List<PaceUCVO> showComment(int board_no) {
		return commentDAO.showComment(board_no);
	}
	
	public List<PaceUCmCVO> showCmComment(int comment_no){
		return commentDAO.showCmComment(comment_no);
	}
}
