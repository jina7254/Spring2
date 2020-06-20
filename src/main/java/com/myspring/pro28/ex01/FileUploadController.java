package com.myspring.pro28.ex01;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {

	private static final String CURR_IMAGE_REPO_PATH = "c:\\spring\\image_repo"; //파일 저장할 위치
	
	@RequestMapping(value="/form")
	public String form() {	//업로드창인 uploadForm.jsp를 반환
		return "uploadForm";
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public ModelAndView upload(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception{
		
		multipartRequest.setCharacterEncoding("utf-8");
		Map map = new HashMap();	//매개변수 정보와 파일 정보를 저장할 Map을 생성
		Enumeration enu = multipartRequest.getParameterNames();
		while(enu.hasMoreElements()) {		
			String name = (String)enu.nextElement();
			String value = multipartRequest.getParameter(name);
			map.put(name, value);	//전송된 매개변수 값을 key/value로 map에 저장
		}
		
		List fileList = fileProcess(multipartRequest);
		map.put("fileList", fileList);		//파일을 업로드한 후, 반환된 파일 이름이 저장된 fileList를 다시 map에 저장
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("map", map);
		mav.setViewName("result");
		return mav;
		
	}
	
	private List<String> fileProcess(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList = new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();	//첨부된 파일 이름을 가져온다.
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);	//파일 이름에 대한 MultipartFile객채를 가져온다
			String originalFileName = mFile.getOriginalFilename();	//실제 파일이름 가져오기
			System.out.println("파일이름:"+originalFileName);
			fileList.add(originalFileName);	//파일 이름을 하나씩 fileList에 저장
			File file = new File(CURR_IMAGE_REPO_PATH + "\\" + originalFileName);
			
			if(mFile.getSize() != 0) {	//첨부된 파일이 있는지 체크
				if(! file.exists()) {	//경로에 파일이 없으면 
					if(file.getParentFile().mkdirs()) {	//그 경로에 해당하는 디렉터리를 만든 후 파일 생성하기
						file.createNewFile();
					}
				}
				mFile.transferTo(new File(CURR_IMAGE_REPO_PATH + "\\" + originalFileName));	//임시로 저장된 multipartFile을 실제 파일로 전송
			}
		}
		return fileList;	//첨부한 파일 이름이 저장된 fileList반환
		
	}
}
