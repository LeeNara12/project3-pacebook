package com.spring.pace.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileDownloadController {

	private static String CURR_IMAGE_REPO_PATH = "D:\\spring\\image_repo";
	
	
	//6.
	@RequestMapping("/download")
	public void download(
				@RequestParam("imageFileName") String imageFileName,
				HttpServletResponse response
			)throws Exception{
		
		OutputStream out = response.getOutputStream();
		String downFile = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File file = new File(downFile);
		
		response.setContentType("text/html; charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.addHeader("Content-disposition", "attachment; fileName=" + imageFileName);
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024*8];
		while(true) {
			int count = in.read(buffer);
			if(count == -1) break;
			out.write(buffer, 0, count);//buffer(바이트 배열)[0~count]까지의 바이트를 출력 스트림으로 보냄
		}///인코딩 에러 무시하기
		in.close();
		out.close();
		
		
		
	}
	
	
}
