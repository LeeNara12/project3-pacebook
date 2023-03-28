package com.spring.pace.Controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



@Controller
public class FileUploadController {

	private static final String CURR_IMAGE_REPO_PATH = "D:\\spring\\image_repo";
	
	
	////3.
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(
			MultipartHttpServletRequest multipartRequest,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception{
		
		System.out.println("파일업로드 메소드 진입");
		multipartRequest.setCharacterEncoding("utf-8");
		
		List fileList = fileProcess(multipartRequest);
		
		System.out.println("fileList의 사이즈는? "+ fileList.size());
		
		request.setAttribute("fileList", fileList);
		
		
		return "forward:/pacebook/board";
	}
	
	
	////3.
	//파일 생성하는 메소드
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		
//		multipartRequest.setCharacterEncoding("utf-8");
		
		System.out.println("파일생성하는 메소드");
		List<String> fileList = new ArrayList<String>();
		
		Iterator<String> fileNames = multipartRequest.getFileNames();
		System.out.println("fileNames? " + fileNames);
		
		
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			
			List <MultipartFile> mFile = multipartRequest.getFiles("input_file");
			for(MultipartFile mf : mFile) {
				String originalFileName = mf.getOriginalFilename();//duke1.jpg
				
//				String encodeResult = URLEncoder.encode(originalFileName, "utf-8");
				
				fileList.add(originalFileName);
				System.out.println(originalFileName+" " +fileName);
				
				File file = new File(CURR_IMAGE_REPO_PATH + "\\" + fileName);
				
				if(mf.getSize()!=0) {
					
					if(!file.exists()) {
						
						if(file.getParentFile().mkdirs()) {
							file.createNewFile();
						}
					}
					
					mf.transferTo(new File(CURR_IMAGE_REPO_PATH + "\\" + originalFileName));//실제로 저장하는 부분
					//////////////////////////////////////service
				}
			}
			
			
			
			
		}
		
		return fileList;
	}
	
	
	
}
