package com.ezenac.controller.admin.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezenac.controller.action.Action;
import com.ezenac.dao.AdminDao;
import com.ezenac.dto.AdminVO;

public class AdminOrderSaveAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = "shop.do?command=adminOrderList";
		
		HttpSession session = request.getSession();
		AdminVO avo = (AdminVO) session.getAttribute("loginAdmin");
		if( avo ==null) {
			url = "shop.do?command=admin";
		} else {
			// 전달된 주문상세번호들의 주문을 미처리 -> 처리로 변경해주세요.
			AdminDao adao = AdminDao.getInstance();
			String [] resultArr = request.getParameterValues("result");
			
			// 배열의 요소들(odseq들)을 하나씩 꺼내어 해당 주문의  처리여부를 처리로 업데이트합니다
			for(String odseq : resultArr)
				adao.updateOrderResult(odseq);
			
			
			
		}

		request.getRequestDispatcher(url).forward(request, response);
	}

}
