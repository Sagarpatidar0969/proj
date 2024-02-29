package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.PaymentBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.PaymentModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "PaymentCtl", urlPatterns = { "/ctl/PaymentCtl" })
public class PaymentCtl extends BaseCtl{

	
	
	  @Override 
	  protected boolean validate(HttpServletRequest request) {
	 
	  boolean pass = true;
	  
	  if (DataValidator.isNull(request.getParameter("payment"))) {
	  request.setAttribute("payment", PropertyReader.getValue("error.require","payment"));
	  pass = false;
	  } 
	  else if
	  (!DataValidator.isName(request.getParameter("payment"))) {
	  request.setAttribute("payment","payment type  contains alphabet only");
	  pass = false; 
	  }
	  
	  if (DataValidator.isNull(request.getParameter("amount"))) {
	  request.setAttribute("amount", PropertyReader.getValue("error.require","amount")); 
	  pass = false; 
	  }
	  else if
	  (!DataValidator.isInteger(request.getParameter("amount"))) {
	  request.setAttribute("amount"," amount must in integer ");
	  pass = false; 
	  }
	return pass; 
	  }
	

	protected BaseBean populateBean(HttpServletRequest request) {

		PaymentBean bean = new PaymentBean();

		  bean.setId(DataUtility.getLong(request.getParameter("id")));
		  bean.setPaymentType(DataUtility.getString(request.getParameter("payment")));
		  bean.setAmount(DataUtility.getInt(request.getParameter("amount")));
		  
		  System.out.println("popu");
	
		return bean;

	}

	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		PaymentModel model = new PaymentModel();
		long id = DataUtility.getLong(request.getParameter("id"));
	
		if (id > 0 || op != null) {
			PaymentBean bean;
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
		PaymentModel model = new PaymentModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			PaymentBean bean = (PaymentBean) populateBean(request);			
			System.out.println(id);

			try {
				if (id > 0) {				
 				model.update(bean);
					ServletUtility.setBean(bean, request);			
					ServletUtility.setSuccessMessage("Payment is  Updated", request);
				} else {	
		     	long pk = model.add(bean);		
					ServletUtility.setSuccessMessage("Payment is Added", request);
					bean.setId(pk);
				}
				

			} catch (ApplicationException e) {
	
				ServletUtility.handleException(e, request, response);
				return;
			
		}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			

			ServletUtility.redirect(ORSView.PAYMENT_LIST_CTL, request, response);
			return;
		}
	
		ServletUtility.forward(getView(), request, response);


	}

	
	@Override
	protected String getView() {
		return ORSView.PAYMENT_VIEW;
	}
}
	
	