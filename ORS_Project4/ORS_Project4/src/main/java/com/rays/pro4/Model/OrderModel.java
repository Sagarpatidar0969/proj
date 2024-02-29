package com.rays.pro4.Model;

	import java.sql.Connection;
	import java.sql.Date;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.util.ArrayList;
	import java.util.List;
	import org.apache.log4j.Logger;

	import com.rays.pro4.Bean.OrderBean;
	import com.rays.pro4.Bean.OrderBean;
	import com.rays.pro4.Exception.ApplicationException;
	import com.rays.pro4.Exception.DatabaseException;
	import com.rays.pro4.Exception.DuplicateRecordException;
	import com.rays.pro4.Util.DataUtility;
	import com.rays.pro4.Util.JDBCDataSource;

	public class OrderModel {
		
		public int nextPK() throws DatabaseException {


			String sql = "SELECT MAX(ID) FROM ST_ORDER";
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

				throw new DatabaseException("Exception : Exception in getting PK");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			return pk + 1;

		}
		
		
		
		public long add(OrderBean bean) throws ApplicationException {
		
			
			String sql = "INSERT INTO ST_ORDER VALUES(?,?,?)";

			Connection conn = null;
			int pk = 0;


			try {
				conn = JDBCDataSource.getConnection();
				pk = nextPK();
		
				conn.setAutoCommit(false); 
				PreparedStatement pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, pk);
				pstmt.setString(2, bean.getType());
				pstmt.setString(3, bean.getName());
				
				int a = pstmt.executeUpdate();
				System.out.println(a);
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

		public void delete(OrderBean bean) throws ApplicationException {
			
			String sql = "DELETE FROM ST_ORDER  WHERE ID=?";
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
		public OrderBean findByPK(long pk) throws ApplicationException {
		
			String sql = "SELECT * FROM ST_ORDER WHERE ID=?";
			OrderBean bean = null;
			Connection conn = null;
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new OrderBean();
					bean.setId(rs.getLong(1));
					bean.setType(rs.getString(2));
					bean.setName(rs.getString(3));			
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				
		
			} 
		
			return bean;
		}

		public void update(OrderBean bean) throws ApplicationException{
			
			String sql = "UPDATE ST_ORDER  SET TYPE = ?,NAME=? WHERE ID=?";
			Connection conn = null;
			
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, bean.getType());
				pstmt.setString(2, bean.getName());
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

		public List search(OrderBean bean) throws ApplicationException {
			return search(bean, 0, 0);
		}

		public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {
		
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_ORDER  WHERE 1=1");
//		if (bean != null) {
//				if (bean.getC_Name() != null && bean.getC_Name().length() > 0) {
//				 	sql.append(" AND NAME like '" + bean.getC_Name() + "%'");
//				}
//		}
//				if (bean.getLogin() != null && bean.getLogin().length() > 0) {
//					sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
//				}
//				if (bean.getRoleId() > 0) {
//					sql.append(" AND ROLE_ID = " + bean.getRoleId());
//				}
//				if (bean.getLastName() != null && bean.getLastName().length() > 0) {
//					sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
//				}
//				if (bean.getId() > 0) {
//					sql.append(" AND id = " + bean.getId());
//				}
	//
//				if (bean.getPassword() != null && bean.getPassword().length() > 0) {
//					sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
//				}
//				if (bean.getDob() != null && bean.getDob().getTime() > 0) {
//					Date d = new java.sql.Date(bean.getDob().getTime());
//					sql.append(" AND DOB = " + DataUtility.getDateString(d));
//				}
//				if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
//					sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
//				}
//				if (bean.getUnSuccessfulLogin() > 0) {
//					sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfulLogin());
//				}
//				if (bean.getGender() != null && bean.getGender().length() > 0) {
//					sql.append(" AND GENDER like '" + bean.getGender() + "%'");
//				}

				/*
				 * if (bean.getDob() != null && bean.getDob().getTime() > 0) { Date d = new java.
				 * Date(bean.getDob().getTime()); sql.append("AND DOB = '" +
				 * DataUtility.getDateString(d) + "'"); }
				 */
			
		
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
					bean = new OrderBean();
					bean.setId(rs.getLong(1));
					bean.setType(rs.getString(2));
					bean.setName(rs.getString(3));
					
					list.add(bean);
				

				}
				rs.close();
			} catch (Exception e) {
				
			
			} 
			
			return list;

		}

	}

