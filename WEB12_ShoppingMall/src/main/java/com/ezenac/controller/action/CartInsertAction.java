package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.dao.CartDao;
import com.ezenac.dto.CartVO;
import com.ezenac.dto.MemberVO;

public class CartInsertAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 로그인 유저를 점검해서 로그인이 안된상황은 로그인 페이지로 이동
		HttpSession session = request.getSession();
		MemberVO mvo = (MemberVO)session.getAttribute("loginUser");
		String url;
		if(mvo == null) {
			url = "shop.do?command=loginForm";
		}else {
			// 로그인이 되어 있다면 로그인 정보에서 id를 추출하고 상품번호와  아이디를 CartVO에 담아서
			CartVO cvo = new CartVO(); // 이거 new 안하고 했었던것 같아 
			cvo.setId(mvo.getId());
			cvo.setPseq(Integer.parseInt(request.getParameter("pseq")));  // 상품번호 저장
			cvo.setQuantity(Integer.parseInt(request.getParameter("quantity"))); // 구매수량 저장
			CartDao cdao = CartDao.getInstance();
			// Cart 테이블에 추가합니다
			cdao.insertCart(cvo); // 레코드 추가
			url = "shop.do?command=cartList";
		}
		// Cart 내역을 조회해서 표시할 페이지로 이동합니다.
		response.sendRedirect(url); // dispatcher 쓸 때랑 sendRedirect 쓰는거 구별?

	}

}
