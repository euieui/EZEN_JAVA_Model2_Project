package com.ezenac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ezenac.dto.CartVO;
import com.ezenac.dto.OrderVO;
import com.ezenac.util.Dbman;

public class OrderDao {
	private OrderDao() {}
	private static OrderDao ist = new OrderDao();
	public static OrderDao getInstance() {return ist;}
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	public int insertOrder(ArrayList<CartVO> list, String id) {
		int oseq = 0; // orders 테이블에 추가된 주문번호를 리턴할 예정입니다.
		
		/// 1. 주문번호(시퀀스 자동입력)와 구매자 아이디로 orders 테이블에 레코드 추가
		String sql = "insert into orders(oseq, id) values(orders_seq.nextval , ?)"; // 에러 아닌가?
		con = Dbman.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			// 2. Orders 테이블에 시퀀스로 입력된 가장 마지막 (방금추가한) 주문 번호 조회
			pstmt.close();
			sql = "select max(oseq) as max_oseq from orders";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) oseq = rs.getInt("max_oseq");
			
			// 3. list 의 카트목록들을 Orders 에서 얻은 마지막 주문 번호와 함께 order_detail 에 추가
			pstmt.close();
			
			// 반복실행문을 이요 list 의 갯수만큼 반복
			for(CartVO cvo : list) {
				sql = "insert into order_detail(odseq,oseq,pseq,quantity)"
						+ "values(order_detail_seq.nextval,?,?,?)";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, oseq);
				pstmt.setInt(2, cvo.getPseq());
				pstmt.setInt(3, cvo.getQuantity());
				pstmt.executeUpdate();
				//4. order_detail 에 추가된 카트 내용은 cart 테이블에서 처리되었으므로 삭제 또는 result 를 2로 변경
				pstmt.close();

				// sql = "delete from cart where cseq = ?"  이건삭제
				sql = "update cart set result='2' where cseq =?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, cvo.getCseq());
				pstmt.executeUpdate();
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		return oseq;
	}

	public ArrayList<OrderVO> listOrderById(String id, int oseq) {
		ArrayList<OrderVO> list = new ArrayList<>();
		String sql = "select * from order_view where id=? and result ='1' and oseq=?";
		con = Dbman.getConnection();
		try {
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, oseq);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderVO ovo = new OrderVO();
				ovo.setOdseq(rs.getInt("odseq"));
				ovo.setOseq(rs.getInt("oseq"));
				ovo.setId(rs.getString("id"));
				ovo.setIndate(rs.getTimestamp("indate"));
				ovo.setMname(rs.getString("mname"));
				ovo.setZip_num(rs.getString("zip_num"));
				ovo.setAddress(rs.getString("address"));
				ovo.setPhone(rs.getString("phone"));
				ovo.setPname(rs.getString("pname"));
				ovo.setPrice2(rs.getInt("price2"));
				ovo.setPseq(rs.getInt("pseq"));
				ovo.setQuantity(rs.getInt("quantity"));
				ovo.setResult(rs.getString("result"));
				list.add(ovo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		return list;
	}

	public ArrayList<Integer> selectSeqOrderIng(String id) {
		ArrayList<Integer> list = new ArrayList<>();
		// order_view로 부터 주문자의 처리 안된 주문의 주문번호(oseq)를 조회하여 리스트를 리턴합니다
		String sql = "select distinct oseq from order_view where id=? and result='1' order by oseq desc";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {Dbman.close(con, pstmt, rs);}
		
		return list;
	}

	public ArrayList<Integer> oseqListAll(String id) {
		ArrayList<Integer> list = new ArrayList<>();
		String sql = "select distinct oseq, result from order_view where id=? order by result, oseq desc"; // wherer 에 result='1'이 없습니다.
							// 처리와 미처리로 구분되어야 해서 result 로 정렬해서 조회합니다.
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next())
				list.add(rs.getInt(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {Dbman.close(con, pstmt, rs);}
		return list;
	}

	public ArrayList<OrderVO> listOrderById2(String id, int oseq) {
		ArrayList<OrderVO> list = new ArrayList<>();
		String sql = "select * from order_view where id=? and oseq=?";
		con = Dbman.getConnection();
		try {
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, oseq);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				OrderVO ovo = new OrderVO();
				ovo.setOdseq(rs.getInt("odseq"));
				ovo.setOseq(rs.getInt("oseq"));
				ovo.setId(rs.getString("id"));
				ovo.setIndate(rs.getTimestamp("indate"));
				ovo.setMname(rs.getString("mname"));
				ovo.setZip_num(rs.getString("zip_num"));
				ovo.setAddress(rs.getString("address"));
				ovo.setPhone(rs.getString("phone"));
				ovo.setPname(rs.getString("pname"));
				ovo.setPrice2(rs.getInt("price2"));
				ovo.setPseq(rs.getInt("pseq"));
				ovo.setQuantity(rs.getInt("quantity"));
				ovo.setResult(rs.getString("result"));
				list.add(ovo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		return list;
	}
	
}
