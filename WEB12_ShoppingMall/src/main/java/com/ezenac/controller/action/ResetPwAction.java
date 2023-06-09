package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.MemberDao;
import com.ezenac.dto.MemberVO;

public class ResetPwAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 아이디와 패스워드 전달받아서
		MemberVO mvo = new MemberVO();
		mvo.setId(request.getParameter("id"));
		mvo.setPwd(request.getParameter("pwd"));
		
		// 패스워드를 수정합니다
		MemberDao mdao = MemberDao.getInstance();
		mdao.resetPw(mvo);
		
		// 패스워드 리셋 완료 페이지로 이동합니다
		String url ="member/resetPwcomplete.jsp";
		request.getRequestDispatcher(url).forward(request, response);

	}

}
