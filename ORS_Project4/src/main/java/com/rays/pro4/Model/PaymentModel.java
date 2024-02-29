package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Util.JDBCDataSource;

public class PaymentModel {
	
	public int nextPK() throws DatabaseException {


		String sql = "SELECT MAX(ID) FROM ST_PAYMENT";
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;

	}
	
	
	
	public long add(PaymentBean bean) throws ApplicationException {
	
		
		String sql = "INSERT INTO ST_PAYMENT VALUES(?,?,?)";

		Connection conn = null;
		int pk = 0;


		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
	
			conn.setAutoCommit(false); 
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getPaymentType());
			pstmt.setInt(3, bean.getAmount());
			
			int a = pstmt.executeUpdate();
	
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		
		
		return pk;

	}

	public void delete(PaymentBean bean) throws ApplicationException {
		
		String sql = "DELETE FROM ST_PAYMENT  WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
			
		} catch (Exception e) {
			
			try {
				conn.rollback();
			} catch (Exception e2) {
			}
		} 			
	}
	public PaymentBean findByPK(long pk) throws ApplicationException {
	
		String sql = "SELECT * FROM ST_PAYMENT WHERE ID=?";
		PaymentBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setPaymentType(rs.getString(2));
				bean.setAmount(rs.getInt(3));			
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			
	
		} 
	
		return bean;
	}

	public void update(PaymentBean bean) throws ApplicationException{
		
		String sql = "UPDATE ST_PAYMENT  SET PAYMENTTYPE = ?,AMOUNT=? WHERE ID=?";
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getPaymentType());
			pstmt.setInt(2, bean.getAmount());
			pstmt.setLong(3, bean.getId());
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
	
			}
		} 
		
	}

	public List search(PaymentBean bean) throws ApplicationException {
		return search(bean, 0, 0);
	}

	public List search(PaymentBean bean, int pageNo, int pageSize) throws ApplicationException {
	
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PAYMENT  WHERE 1=1");

	
		if (pageSize > 0) {
		
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			
		}

		System.out.println(sql);
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new PaymentBean();
				bean.setId(rs.getLong(1));
				bean.setPaymentType(rs.getString(2));
				bean.setAmount(rs.getInt(3));
				
				list.add(bean);
			

			}
			rs.close();
		} catch (Exception e) {
			
		
		} 
		
		return list;

	}

}
