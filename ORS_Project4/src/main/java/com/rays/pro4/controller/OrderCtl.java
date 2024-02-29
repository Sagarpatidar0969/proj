package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.RoleModel;
import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl" })
public class OrderCtl extends BaseCtl{

	
	
	  @Override 
	  protected boolean validate(HttpServletRequest request) {
	 
	  boolean pass = true;
	  
	  if (DataValidator.isNull(request.getParameter("type"))) {
	  request.setAttribute("type", PropertyReader.getValue("error.require","tpye"));
	  pass = false;
	  } 
	  else if
	  (!DataValidator.isName(request.getParameter("type"))) {
	  request.setAttribute("type","type name  contains alphabet only");
	  pass = false; 
	  }
	  
	  if (DataValidator.isNull(request.getParameter("name"))) {
	  request.setAttribute("name", PropertyReader.getValue("error.require","name ")); 
	  pass = false; 
	  }
	  else if
	  (!DataValidator.isName(request.getParameter("name"))) {
	  request.setAttribute("name"," name  contains alphabet only");
	  pass = false; 
	  }
	return pass; 
	  }
	

	protected BaseBean populateBean(HttpServletRequest request) {

		OrderBean bean = new OrderBean();

		  bean.setId(DataUtility.getLong(request.getParameter("id")));
		  bean.setType(DataUtility.getString(request.getParameter("type")));
		  bean.setName(DataUtility.getString(request.getParameter("name")));
		  
		  System.out.println("popu");
	
		return bean;

	}

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		OrderModel model = new OrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
	
		if (id > 0 || op != null) {
			OrderBean bean;
			try {
				bean = model.findByPK(id);	
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {			
				return;
			}
		}		
		ServletUtility.forward(getView(), request, response);
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(op);
		System.out.println("iddddddddddddddd" + id);
		OrderModel model = new OrderModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			OrderBean bean = (OrderBean) populateBean(request);			
			System.out.println(id);

			try {
				if (id > 0) {				
 				model.update(bean);
					ServletUtility.setBean(bean, request);			
					ServletUtility.setSuccessMessage("Order is successfully Updated", request);
				} else {	
		     	long pk = model.add(bean);		
					ServletUtility.setSuccessMessage("Order is successfully Added", request);
					bean.setId(pk);
				}
				

			} catch (ApplicationException e) {
	
				ServletUtility.handleException(e, request, response);
				return;
			
		}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			

			ServletUtility.redirect(ORSView.ORDER_LIST_CTL, request, response);
			return;
		}
	
		ServletUtility.forward(getView(), request, response);


	}

	
	@Override
	protected String getView() {
		return ORSView.ORDER_VIEW;
	}
}
	
	