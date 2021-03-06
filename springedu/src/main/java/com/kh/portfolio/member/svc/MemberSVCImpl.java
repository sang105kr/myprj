package com.kh.portfolio.member.svc;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kh.portfolio.member.dao.MemberDAO;
import com.kh.portfolio.member.dao.MemberDAOImplJDBC;
import com.kh.portfolio.member.vo.MemberVO;

@Service
public class MemberSVCImpl implements MemberSVC{
	
	private static final Logger logger 
	= LoggerFactory.getLogger(MemberDAOImplJDBC.class);
	
	@Inject
	@Qualifier("memberDAOImplXML")
	MemberDAO memberDAO;
	
	//회원 등록
	@Override
	public int joinMember(MemberVO memberVO) {
		logger.info("MemberSVCImpl.joinMember(MemberVO memberVO) 호출됨");
		return memberDAO.joinMember(memberVO);
	}
	//회원 수정
	@Override
	public int modifyMember(MemberVO memberVO) {
		logger.info("MemberSVCImpl.modifyMember(MemberVO memberVO) 호출됨");
		try {
			if(memberVO.getFile().getSize() > 0 ) {
				//첨부파일
				memberVO.setPic(memberVO.getFile().getBytes());
				//첨부파일 크기
				memberVO.setFsize(memberVO.getFile().getSize());
				//첨부파일 타입
				memberVO.setFtype(memberVO.getFile().getContentType());		
				//첨부파일명
				memberVO.setFname(memberVO.getFile().getName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return memberDAO.modifyMember(memberVO);
	}
	//회원 전체조회
	@Override
	public List<MemberVO> selectAllMember() {
		logger.info("MemberSVCImpl.selectAllMember() 호출됨");
		return memberDAO.selectAllMember();
	}
	//회원 개별조회
	@Override
	public MemberVO selectMember(String id) {
		logger.info("MemberSVCImpl.selectMember(String id) 호출됨");
		return memberDAO.selectMember(id);
	}
	//회원 탈퇴
	@Override
	public int outMember(String id, String pw) {
		logger.info("MemberSVCImpl.outMember(String id, String pw) 호출됨");
		return memberDAO.outMember(id, pw);
	}
	//로그인
	@Override
	public MemberVO loginMember(String id, String pw) {
		logger.info("MemberSVCImpl.loginMember(String id, String pw) 호출됨");
		return memberDAO.loginMember(id, pw);
	}
	//아이디 찾기
	@Override
	public String findID(String tel, Date birth) {
		logger.info("MemberSVCImpl.findID(String tel, Date birth) 호출됨!"+birth);
		return memberDAO.findID(tel, birth);
	}
	//비밀번호 변경
	@Override
	public int changePW(String id, String pw) {
		logger.info("MemberSVCImpl.changePW(String id, String pw) 호출됨");
		return memberDAO.changePW(id, pw);
	}
	//비밀번호 변경 대상 찾기
	@Override
	public int findPW(MemberVO memberVO) {
		logger.info("MemberSVCImpl.findPW(MemberVO memberVO) 호출됨");
		return memberDAO.findPW(memberVO);
	}
	//프로파일 이미지 조회
	@Override
	public byte[] fileView(String id) {
		logger.info("MemberSVCImpl.fileView(String id) 호출됨");
		return memberDAO.fileView(id);
	}

}
