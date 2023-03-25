package com.ezen.board.controller.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.board.dao.BoardDao;
import com.ezen.board.dto.BoardDto;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class BoardUpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		ServletContext context = session.getServletContext();
		String path = context.getRealPath("upload");
		
		BoardDto bdto = new BoardDto();
		BoardDao bdao = BoardDao.getInstance();
		
		int num = 0;
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
			num = Integer.parseInt(multi.getParameter("num"));
			
			bdto.setUserid(userid);
			bdto.setPass(pass);
			bdto.setEmail(email);
			bdto.setTitle(title);
			bdto.setContent(content);
			bdto.setImgfilename(imgfilename);
			if(imgfilename == null ) imgfilename = multi.getParameter("oldfilename");
			bdto.setNum(num);
			
			
		}catch(Exception e) {
			System.out.println("파일 업로드 실패 : " + e);
		}
		
		
		bdao.updateBoard(bdto);
		RequestDispatcher dp = request.getRequestDispatcher("board.do?command=boardviewwithoutcount&num=" + 
				num);
		dp.forward(request, response);
		
		

	}

}
