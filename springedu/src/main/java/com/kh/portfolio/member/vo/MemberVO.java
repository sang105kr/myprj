package com.kh.portfolio.member.vo;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Entity
@Data
public class MemberVO {
	@NotNull
	@Pattern(regexp = "^\\w+@\\w+\\.\\w+(\\.\\w+)?$", message = "s:ex)aaa.@bbb.com" )
	private String id;				// ID	아이디(이메일)	X			VARCHAR2
	@NotNull
	@Size(min = 6, max = 10, message = "s:비밀번호는 6~10자리로 입력바랍니다.")
	private String pw;				//PW	비밀번호				VARCHAR2
	@NotNull
	@Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "s:ex)010-1234-5678")
	private String tel; 			//TEL	전화번호				VARCHAR2
	@NotNull
	@Size(min = 3, max = 10, message = "s:닉네임은 3~10자리로 입력바랍니다.")
	private String nickname;	//NICKNAME	별칭				VARCHAR2
	
	private String gender;		//GENDER	성별('남','여')				CHAR
	@NotNull
	@Pattern(regexp = "^[^0]+$", message = "선택하세요!")
	private String region;		//REGION	지역				VARCHAR2
	@NotNull
//	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date birth;				//BIRTH	생년월일('yyyymmdd')				DATE
	private Timestamp cdate;	//CDATE	생성일시		systimestamp		timestamp
	private Timestamp udate;	//UDATE	변경일시		systimestamp		timestamp
	
	//회원 이미지 첨부용
	private MultipartFile file;
	private String fname;					//파일명
	private long fsize;						//파일크기
	private String ftype;					//파일타입
	private byte[] pic;						//첨부파일
	
}
