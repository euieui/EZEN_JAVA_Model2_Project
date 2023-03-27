package com.ezenac.controller.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezenac.dao.ProductDao;
import com.ezenac.dto.ProductVO;

public class ProductDetailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 전달받은 상품번호로 상품을 조회하고 ProductVO 에 담아서 productDetail.jsp 로 이동
		
		String pseq = request.getParameter("pseq"); // 파라미터 전달
		
		ProductDao pdao = ProductDao.getInstance();
		ProductVO pvo = pdao.getProduct(pseq); // 상품번호로 상품 조회 리턴
		
		request.setAttribute("productVO", pvo); // 리퀘스트에 저장
		
		request.getRequestDispatcher("product/productDetail.jsp").forward(request, response);
		
	}

}
