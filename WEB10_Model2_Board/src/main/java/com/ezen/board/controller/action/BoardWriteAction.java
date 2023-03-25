package com.ezen.board.controller.action;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.dao.BoardDao;
import com.ezen.board.dto.BoardDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		// 전달된 내용들을 BoardDto 에 담아서 BoardDao 의 insertBoard메서드로
		// 전달해서 레코드를 추가합니다.
		BoardDto bdto = new BoardDto();
		BoardDao bdao = BoardDao.getInstance();
		
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String path = context.getRealPath("upload");
		
		try {
			MultipartRequest multi = new MultipartRequest(
					request, path , 5*1024*1024, "UTF-8", new DefaultFileRenamePolicy()
					);
			String userid = multi.getParameter("userid");
			String pass = multi.getParameter("pass");
			String email = multi.getParameter("email");
			String title = multi.getParameter("title");
			String content = multi.getParameter("content");
			String imgfilename = multi.getFilesystemName("imgfilename");
			bdto.setUserid(userid);
			bdto.setPass(pass);
			bdto.setEmail(email);
			bdto.setTitle(title);
			bdto.setContent(content);
			bdto.setImgfilename(imgfilename);
			bdao.insertBoard(bdto);
			
		}catch(Exception e) {
			System.out.println("파일 업로드 실패 : " + e);
		}
		
		// 최종 board.do?command=board_list 로 이동합니다. //forward를 사용하면 새로고침하면 계시물이 계속 작성됨
		response.sendRedirect("board.do?command=boardlist");
		

	}

}
