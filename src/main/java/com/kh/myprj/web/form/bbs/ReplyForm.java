package com.kh.myprj.web.form.bbs;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class ReplyForm {
	private Long 	 pbnum;				//부모글 번호

	@NotBlank
	private String bcategory;
	@NotBlank
	private String btitle;
	@NotNull
	private Long bid;		
	@NotBlank
	private String bemail;		
	@NotBlank
	private String bnickname;
	@NotBlank
	private String bcontent;
	
	private List<MultipartFile> files; //파일첨부	
}
