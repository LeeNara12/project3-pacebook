package com.spring.pace.VO;

import java.util.List;

public class PaceUBVO {
	private PaceUserVO paceUserVO;
	private PaceBoardVO paceBoardVO;
	private FileListVO fileListVO;
	private List file_image;// 파일명 VO List로 받기
	
	public PaceUserVO getPaceUserVO() {
		return paceUserVO;
	}
	public void setPaceUserVO(PaceUserVO paceUserVO) {
		this.paceUserVO = paceUserVO;
	}
	public PaceBoardVO getPaceBoardVO() {
		return paceBoardVO;
	}
	public void setPaceBoardVO(PaceBoardVO paceBoardVO) {
		this.paceBoardVO = paceBoardVO;
	}
	public FileListVO getFileListVO() {
		return fileListVO;
	}
	public void setFileListVO(FileListVO fileListVO) {
		this.fileListVO = fileListVO;
	}
	public List getFile_image() {
		return file_image;
	}
	public void setFile_image(List file_image) {
		this.file_image = file_image;
	}
	
}
