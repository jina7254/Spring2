package com.myspring.pro28.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.coobird.thumbnailator.Thumbnails;

@Controller
public class FileDownloadController {

	private static String CURR_IMAGE_REPO_PATH = "C:\\spring\\image_repo";
	
	@RequestMapping("/download")
	protected void download(@RequestParam("imageFileName") String imageFileName, HttpServletResponse response) throws Exception{
		OutputStream out = response.getOutputStream();
		String filePath = CURR_IMAGE_REPO_PATH + "\\" + imageFileName;
		File image = new File(filePath);
		
		int lastIndex = imageFileName.lastIndexOf(".");				
		String fileName = imageFileName.substring(0, lastIndex);	//확장자를 제외한 원본 이미지 파일의 이름 가져오기
		
		File thumbnail = new File(CURR_IMAGE_REPO_PATH + "\\" + "thumbnail" + "\\" + fileName + ".png");	//원본 이미지 파일 이름과 같은 이름의 썸네일 파이렝 대한 File객체 생성
		
		if(image.exists()) {
			thumbnail.getParentFile().mkdirs();
			Thumbnails.of(image).size(50,50).outputFormat("png").toFile(thumbnail);	//원본 이미지 파일을 가로세로가 50픽셀인 png형식의 썸네일 이미지 파일로 생성
		}
		
		FileInputStream in = new FileInputStream(thumbnail);
		byte[] buffer = new byte[1024 * 8];
		while(true) {
			int count = in.read(buffer);
			if(count == -1)
				break;
			out.write(buffer, 0, count);	//생성된 썸네일 파일을 브라우저로 전송
		}
		in.close();
		out.close();
	}
}
