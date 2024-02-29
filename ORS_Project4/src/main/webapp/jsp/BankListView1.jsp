

<%@page import="com.rays.pro4.controller.BankListCtl1"%>
<%@page import="com.rays.pro4.controller.BankListCtl1"%>
<%@page import="com.rays.pro4.Bean.BankBean"%>
<%@page import="com.rays.pro4.Model.RoleModel"%>
<%@page import="com.rays.pro4.Model.BankModel"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.BankListCtl1"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="bean" class="com.rays.pro4.Bean.BankBean"
		scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>


	<form action="<%=ORSView.BANK_LIST_CTL1%>" method="post">

		<center>

			<div align="center">
				<h1>Bank List</h1>
				<h3>
					<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>

			<%
				List rlist = (List) request.getAttribute("RoleList");

				List ulist = (List) request.getAttribute("LoginId");

				int next = DataUtility.getInt(request.getAttribute("nextlist").toString());
			%>


			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;

				List list = ServletUtility.getList(request);
				Iterator<BankBean> it = list.iterator();

				if (list.size() != 0) {
			%>
			<table width="100%" align="center">
				<tr>
					<th></th>
					<td align="center"><label>Name</font> :
					</label> <input type="text" name="name" placeholder="Enter  Name"
						value="<%=ServletUtility.getParameter("name", request)%>">

						<%-- <label></font> </label> <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rlist) %> --%>

						<label>Account</font> :
					</label> <input type="text" name="account" placeholder="Enter account"
						value="<%=ServletUtility.getParameter("account", request)%>">
						<!-- &emsp; <label>Role</font>  -->:
				 	<%-- </label> <%=HTMLUtility.getList("roleid", String.valueOf(bean.getRoleId()), rlist) %>
						&nbsp; <%=HTMLUtility.getList("loginid", String.valueOf(bean.getRoleId()), ulist)%> --%>
                        &nbsp; <input type="submit" name="operation"
						value="<%=BankListCtl1.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=BankListCtl1.OP_RESET%>">

					</td>
				</tr>
			</table>
			<br>

			<table border="1" width="100%" align="center" cellpadding=6px
				cellspacing=".2">
				<tr style="background: skyblue">
					<th><input type="checkbox" id="select_all" name="select">Select
						All</th>
					<th>S.No.</th>
					<th>Name</th>
					<th>Account</th>
					<th>Edit</th>
					
					<!-- <th>Role</th>
					<th>LoginId</th>
					<th>Gender</th>
					<th>Date Of Birth</th>
					<th>Mobile No</th>
					<th>Edit</th> -->
				</tr>

				<%
					while (it.hasNext()) {
							bean = it.next();
							/* RoleModel model = new RoleModel();
							RoleBean rolebean = new RoleBean();
							rolebean = model.findByPK(bean.getRoleId()); */
				%>


				<tr align="center">
					<td><input type="checkbox" class="checkbox" name="ids"
						value="<%=bean.getId()%>"
						 >
						</td>
					<td><%=index++%></td>
					<td><%=bean.getC_Name()%></td>
					<td><%=bean.getAccount()%></td>
					
					
					<td><a href="BankCtl1?id=<%=bean.getId()%>">Edit</a></td>
						<%-- <%if (BankBean== bean.getId() || bean.getRoleId() == RoleBean.ADMIN) {%>
						onclick="return false;" <%}%>>Edit</a></td> --%>
				</tr>
				<%
					}
				%>
			</table>

			<table width="100%">
				<tr>
					<th></th>
					<%
						if (pageNo == 1) {
					%>
					<td><input type="submit" name="operation" disabled="disabled"
						value="<%=BankListCtl1.OP_PREVIOUS%>"></td>
					<%
						} else {
					%>
					<td><input type="submit" name="operation"
						value="<%=BankListCtl1.OP_PREVIOUS%>"></td>
					<%
						}
					%>

					<td><input type="submit" name="operation"
						value="<%=BankListCtl1.OP_DELETE%>"></td>
					<td><input type="submit" name="operation"
						value="<%=BankListCtl1.OP_NEW%>"></td>

					 <%	BankModel model = new BankModel();
                     %>
                     
                  <%--    <% if(list.size() < pageSize || model.nextPK()-1 == bean.getId() ){%>

                     		<td align="right"><input type="submit" name="operation" disabled="disabled" value="<%=UserListCtl.OP_NEXT%>"></td>
                     <% }else{%>
                     		<td align="right"><input type="submit" name="operation" value="<%=UserListCtl.OP_NEXT%>"></td>
                     <%} %> --%>
       
					<td align="right"><input type="submit" name="operation"
						value="<%=BankListCtl1.OP_NEXT%>"
						<%=(list.size() < pageSize || next == 0) ? "disabled" : ""%>></td>



				</tr>
			</table>
			<%
				}
				if (list.size() == 0) {
			%>
			<td align="center"><input type="submit" name="operation"
				value="<%=BankListCtl1.OP_BACK%>"></td>
			<%
				}
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">
	</form>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>
	</br>

	</center>

	<%@include file="Footer.jsp"%>
</body>
</html>