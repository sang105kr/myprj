package com.kh.myprj.domain.board.dao;

import java.util.List;

import com.kh.myprj.domain.board.dto.BoardDTO;

public interface BoardDAO {
	
	/**
	 * 원글 작성
	 * @param boardDTO
	 * @return
	 */
	Long write(BoardDTO boardDTO);
	
	/**
	 * 답글작성
	 * @param boardDTO
	 * @return
	 */
	Long reply(BoardDTO boardDTO);
		
	/**
	 * 게시글 목록
	 * @return
	 */
	List<BoardDTO> list();
	List<BoardDTO> list(int startRec, int endRec);
	
	/**
	 * 게시글 상세
	 * @param bnum
	 * @return
	 */
	BoardDTO itemDetail(Long bnum);
	
	/**
	 * 게시글 수정
	 * @param bnum
	 * @param boardDTO
	 * @return
	 */
	Long modifyItem(Long bnum, BoardDTO boardDTO);	
	
	/**
	 * 게시글 삭제
	 * @param bnum
	 */
	void delItem(Long bnum);
	
	/**
	 * 조회수 증가
	 * @param bnum
	 */
	void updateBhit(Long bnum);
	
	/**
	 * 게시판 전체 레코드 수 
	 * @return
	 */
	long totoalRecordCount();
}
