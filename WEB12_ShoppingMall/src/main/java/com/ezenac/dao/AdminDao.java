package com.ezenac.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ezenac.dto.AdminVO;
import com.ezenac.dto.OrderVO;
import com.ezenac.dto.ProductVO;
import com.ezenac.dto.QnaVO;
import com.ezenac.util.Dbman;
import com.ezenac.util.Paging;

public class AdminDao {
	private AdminDao(){}
	private static AdminDao ist = new AdminDao();
	public static AdminDao getInstance() {return ist;}
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	public AdminVO workerCheck(String workId) {
		AdminVO avo = null;
		String sql = "select * from worker where id = ? ";
		con = Dbman.getConnection();
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, workId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				avo = new AdminVO();
				avo.setId(rs.getString("id"));
				avo.setPwd(rs.getString("pwd"));
				avo.setName(rs.getString("name"));
				avo.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		return avo;
	}

	public ArrayList<ProductVO> listProduct() {
		ArrayList<ProductVO> list = new ArrayList<>();
		String sql = "select * from product order by pseq desc";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductVO pvo = new ProductVO();
				pvo.setPseq(rs.getInt("pseq"));
				pvo.setIndate(rs.getTimestamp("indate"));
				pvo.setName(rs.getString("name"));
				pvo.setPrice1(rs.getInt("price1"));
				pvo.setPrice2(rs.getInt("price2"));
				pvo.setPrice3(rs.getInt("price3"));
				pvo.setImage(rs.getString("image"));
				pvo.setUseyn(rs.getString("useyn"));
				pvo.setBestyn(rs.getString("bestyn"));
				list.add(pvo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		return list;
	}

	public void insertProduct(ProductVO pvo) {
		String sql = "insert into product(pseq, kind, name, price1, price2, price3, content, image) values(product_seq.nextval,?,?,?,?,?,?,?)";
		con=Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pvo.getKind());;
			pstmt.setString(2, pvo.getName());
			pstmt.setInt(3, pvo.getPrice1());
			pstmt.setInt(4, pvo.getPrice2());
			pstmt.setInt(5, pvo.getPrice3());
			pstmt.setString(6, pvo.getContent());
			pstmt.setString(7, pvo.getImage());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
	}

	public void updateProduct(ProductVO pvo) {
		String sql = "update product set kind=?, useyn=?, name=?, price1=?, "
				+ " price2=?, price3=?, content=?, image=?, bestyn=? where pseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pvo.getKind());
			pstmt.setString(2, pvo.getUseyn());
			pstmt.setString(3, pvo.getName());
			pstmt.setInt(4, pvo.getPrice1());
			pstmt.setInt(5, pvo.getPrice2());
			pstmt.setInt(6, pvo.getPrice3());
			pstmt.setString(7, pvo.getContent());
			pstmt.setString(8, pvo.getImage());
			pstmt.setString(9, pvo.getBestyn());
			pstmt.setInt(10, pvo.getPseq());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
	}

	public int getAllCount(String tableName, String fieldName, String key) {
		int count = 0;
		String sql = "select count(*) as cnt from " + tableName 
				+ " where " + fieldName + " like '%'||?||'%' " ;
		// 필드명 like '%?%' 에서 ?가 빈칸이거나 널이면 해당 필드의 조건은 검색하지 않은것과 같아집니다. // 이게 중요하네
		
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, key);
			rs = pstmt.executeQuery();
			if(rs.next()) count = rs.getInt("cnt");
					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		return count;
	}

	public ArrayList<ProductVO> listProduct(Paging paging, String key) {
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		// String sql = "select * from product order by pseq desc";
		
		String sql = "select * from(" 
				+ "select * from (" 
				+ "select rownum as rn, p.* from "
				+ "((select * from product where name like '%'||?||'%' order by pseq desc)p)"
				+ " ) where rn>=?"
				+ ") where rn<=?";
		
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setInt(2, paging.getStartNum());
			pstmt.setInt(3, paging.getEndNum());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductVO pvo = new ProductVO();
				pvo.setPseq(rs.getInt("pseq"));
				pvo.setIndate(rs.getTimestamp("indate"));
				pvo.setName(rs.getString("name"));
				pvo.setBestyn(rs.getString("bestyn"));
				pvo.setContent(rs.getString("content"));
				pvo.setPrice1(rs.getInt("price1"));
				pvo.setPrice2(rs.getInt("price2"));
				pvo.setPrice3(rs.getInt("price3"));
				pvo.setUseyn(rs.getString("useyn"));
				pvo.setImage(rs.getString("image"));
				pvo.setKind(rs.getString("kind"));
				list.add(pvo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		
		return list;
	}

	public ArrayList<OrderVO> listOrder(Paging paging, String key) {
		ArrayList<OrderVO> list = new ArrayList<OrderVO>();
		
		String sql = "select * from ("
				+ "select * from ( "
				+ "select rownum as rn , o.* from "
				+ "((select * from order_view where mname like '%'||?||'%'"
				+ " order by result, odseq desc) o)"
				+ ") where rn >=?"
				+ ") where rn <=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setInt(2, paging.getStartNum());
			pstmt.setInt(3, paging.getEndNum());
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
				ovo.setPseq(rs.getInt("pseq"));
				ovo.setQuantity(rs.getInt("quantity"));
				ovo.setPname(rs.getString("pname"));
				ovo.setPrice2(rs.getInt("price2"));
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

	public void updateOrderResult(String odseq) {
		String sql = "update order_detail set result='2' where odseq=?";
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(odseq));
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
	}

	public int getAllCount(String key) {
		int count = 0;
		String sql = "select count(*) as cnt from qna "
				+ "where subject like '%'||?||'%' or content like '%'||?||'%'";
		// 필드명 like '%?%' 에서 ? 가 빈칸이거나 널이면 , 해당 필드의 조건은 검색하지 않은것과 같아집니다.
		
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setString(2, key);
			rs = pstmt.executeQuery();
			if(rs.next()) count = rs.getInt("cnt");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		return count;
	}

	public ArrayList<QnaVO> listQna(Paging paging, String key) {
		ArrayList<QnaVO> list = new ArrayList<>();
		String sql = "select * from ("
				+ "select * from ("
				+ "select rownum as rn, o.* from "
				+ "((select * from qna where subject like '%'||?||'%' or content like '%'||?||'%' "
				+ "order by qseq desc) o)"
				+ " ) where rn >=?"
				+ " ) where rn <=?";
		
		
		con = Dbman.getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setString(2, key);
			pstmt.setInt(3, paging.getStartNum());
			pstmt.setInt(4, paging.getEndNum());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				QnaVO qvo = new QnaVO();
				qvo.setQseq(rs.getInt("qseq"));
				qvo.setSubject(rs.getString("subject"));
				qvo.setContent(rs.getString("content"));
				qvo.setId(rs.getString("id"));
				qvo.setIndate(rs.getTimestamp("indate"));
				qvo.setRep(rs.getString("rep"));
				qvo.setReply(rs.getString("reply"));
				list.add(qvo);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		return list;
	}

	public void updateQna(QnaVO qvo) {
		String sql = "update qna set reply=?, rep='2' where qseq=?";
		con = Dbman.getConnection();
		try {
			pstmt =con.prepareStatement(sql);
			pstmt.setString(1, qvo.getReply());
			pstmt.setInt(2, qvo.getQseq());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Dbman.close(con, pstmt, rs);
		}
		
		
	}
}
