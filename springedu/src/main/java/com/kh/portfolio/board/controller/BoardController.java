package com.kh.portfolio.board.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.portfolio.board.svc.BoardSVC;
import com.kh.portfolio.board.vo.BoardCategoryVO;
import com.kh.portfolio.board.vo.BoardFileVO;
import com.kh.portfolio.board.vo.BoardVO;
import com.kh.portfolio.board.vo.BoardValidator;
import com.kh.portfolio.member.vo.MemberVO;

@Controller
@RequestMapping("/board")
public class BoardController {

	private static final Logger logger
		= LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	BoardSVC boardSVC;
	
//	@Inject
//	private BoardValidator boardValidator;
//	@InitBinder
//	private void initBinder(WebDataBinder binder) {
//		binder.setValidator(boardValidator);
//	}

	@ModelAttribute
	public void getBoardCategory(Model model) {
		List<BoardCategoryVO> boardCategoryVO = boardSVC.getCategory();
		model.addAttribute("boardCategoryVO", boardCategoryVO);
	}
	
	//????????? ????????????
	@GetMapping("/writeForm/{returnPage}")
	public String writeForm(
			@ModelAttribute @PathVariable String returnPage,
			Model model, HttpServletRequest request) {
		//case1)
		model.addAttribute("boardVO",new BoardVO());
		//case2)
		//BoardVO boardVO = new BoardVO();
		//MemberVO memberVO = (MemberVO)request.getSession().getAttribute("member");
		//boardVO.setBid(memberVO.getId());
		//boardVO.setBnickname(memberVO.getNickname());	
		//model.addAttribute("boardVO",boardVO);
		
		return "/board/writeForm";
	}
	
	//????????? ??????
	@PostMapping("/write/{returnPage}")
	public String write(
			@PathVariable String returnPage,
			@Valid @ModelAttribute("boardVO") BoardVO boardVO,
			BindingResult result,
			HttpServletRequest request) {
		logger.info("???????????????: " + boardVO.toString());
		
		if(result.hasErrors()) {
			return "/board/writeForm";
		}
		
		MemberVO memberVO = (MemberVO)request.getSession().getAttribute("member");
		boardVO.setBid(memberVO.getId());
		boardVO.setBnickname(memberVO.getNickname());
		logger.info("???????????????2: " + boardVO.toString());		
		boardSVC.write(boardVO);
		return "redirect:/board/view/"+returnPage+"/"+boardVO.getBnum();
	}
		
	//????????????
	@GetMapping({"/list",
							 "/list/{reqPage}",
							 "/list/{reqPage}/{searchType}/{keyword}"
	})
	public String listAll(
			@PathVariable(required=false) String reqPage,
			@PathVariable(required=false) String searchType,
			@PathVariable(required=false) String keyword,
			HttpSession session, 
			Model model) {
		
		MemberVO memberVO = (MemberVO)session.getAttribute("member");
//		if(memberVO != null ) {
//			logger.info("????????????"+memberVO.toString());
//		}else {
//			logger.info("????????????");
//		}
//		model.addAttribute("list", boardSVC.list());
		//???????????????
		model.addAttribute("list", boardSVC.list(reqPage,searchType,keyword));
		//????????? ??????
		model.addAttribute("pc", boardSVC.getPageCriteria(reqPage, searchType, keyword));	
		return "board/list";
	}
	
	//???????????????
	@GetMapping("/view/{returnPage}/{bnum}")
	public String view(
			@ModelAttribute @PathVariable String returnPage,
			@PathVariable String bnum,
			Model model) {
		
		Map<String,Object> map = boardSVC.view(bnum);
		BoardVO boardVO = (BoardVO)map.get("board");
		logger.info(boardVO.toString());
		List<BoardFileVO> files = null;
		if(map.get("files") != null) {
			files = (List<BoardFileVO>)map.get("files");
		}
		
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("files", files);
		//model.addAttribute("returnPage", returnPage);
		
		return "/board/readForm";
	}
	
	//???????????? ????????????
	@GetMapping("/file/{fid}")
	public ResponseEntity<byte[]> getFile(@PathVariable String fid) {
		BoardFileVO boardVoFileVO = boardSVC.fileView(fid);
		logger.info("getFile " + boardVoFileVO.toString());
		
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = boardVoFileVO.getFtype().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(boardVoFileVO.getFsize());
		/* ?????????????????? ??????????????? ?????? ?????? */ 
		String filename = null;
		try {
			filename = new String(boardVoFileVO.getFname().getBytes("euc-kr"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		headers.setContentDispositionFormData("attachment", filename);
		/***************************/
		return new ResponseEntity<byte[]>(boardVoFileVO.getFdata(), headers,	HttpStatus.OK);
	}	
	
	//????????? ??????
	@GetMapping("/delete/{returnPage}/{bnum}")
	public String delete(
			@PathVariable String returnPage,
			@PathVariable String bnum, 
			Model model) {
		
		//1)????????? ??? ???????????? ??????
		boardSVC.delete(bnum);
		//2)????????? ?????? ????????????
		model.addAttribute("list", boardSVC.list());
		
		return "redirect:/board/list/"+returnPage;
	}
	
	//???????????? 1??? ??????
	@DeleteMapping("/file/{fid}")
	@ResponseBody
	public ResponseEntity<String> fileDelete(
			@PathVariable String fid){
		
		int cnt = boardSVC.fileDelete(fid);
		
		if(cnt == 1) {
			return new ResponseEntity<String>("success", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("fail", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	//???????????????
	@PostMapping("/modify/{returnPage}")
	public String modify(
			@PathVariable String returnPage,
			@Valid @ModelAttribute("boardVO") BoardVO boardVO,
			BindingResult result
			) {
		if(result.hasErrors()) {
			return "/board/readForm";
		}
		logger.info("????????? ?????? ??????:" + boardVO.toString());
		boardSVC.modify(boardVO);
		return "redirect:/board/view/"+returnPage+"/"+boardVO.getBnum();
	}
	
	//????????????
	@GetMapping("/replyForm/{returnPage}/{bnum}")
	public String replyForm(
			@ModelAttribute @PathVariable String returnPage,
			@PathVariable String bnum,
			Model model) {
		
		Map<String,Object> map = boardSVC.view(bnum);
		BoardVO boardVO = (BoardVO)map.get("board");
//		List<BoardFileVO> files = null;
//		if(map.get("files") != null) {
//			files = (List<BoardFileVO>)map.get("files");
//		}
		boardVO.setBtitle("[??????] " + boardVO.getBtitle());
		boardVO.setBcontent("[??????] " + boardVO.getBcontent());
		model.addAttribute("boardVO", boardVO);
//		model.addAttribute("files", files);
			
		return "/board/replyForm";
	}
	
	//????????????
	@PostMapping("/reply/{returnPage}")
	public String reply(
			@PathVariable String returnPage,
			@Valid @ModelAttribute("boardVO") BoardVO boardVO,
			BindingResult result,
			HttpServletRequest request) {
		logger.info("???????????? :" + boardVO.toString());
		if(result.hasErrors()) {
			return "/board/replyForm";
		}
		MemberVO memberVO = (MemberVO)request.getSession().getAttribute("member");
		boardVO.setBid(memberVO.getId());
		boardVO.setBnickname(memberVO.getNickname());
		boardSVC.reply(boardVO);
		
		return "redirect:/board/list/"+returnPage;
	}
	
}







