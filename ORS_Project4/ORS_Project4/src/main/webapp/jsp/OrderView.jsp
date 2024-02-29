<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="com.rays.pro4.Util.HTMLUtility"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@page import="com.rays.pro4.controller.OrderCtl"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean" scope="request"></jsp:useBean>
   <%@ include file="Header.jsp"%>
   
   <center>

    <form action="<%=ORSView.ORDER_CTL%>" method="post">
    <div align="center">    
            <h1>
 				
           		<% if(bean != null && bean.getId() > 0) { %>
            <tr><th><font size="5px"> Update Order </font>  </th></tr>
            	<%}else{%>
			<tr><th><font size="5px"> Add Order </font>  </th></tr>   
            	<%}%>
            </h1>
   
            <h3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
	       
</div>
           <input type="hidden" name="id" value="<%=bean.getId()%>">
           
            <table>
                <tr>
                    <th align="left">Type <span style="color: red">*</span> :</th>
                    <td><input type="text" name="type" placeholder="Enter type" size="25"  value="<%=DataUtility.getStringData(bean.getType())%>"></td>
                    <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("type", request)%></font></td> 
                    
                </tr>
    
    <tr><th style="padding: 3px"></th></tr>          
              
              <tr>
                    <th align="left">Name<span style="color: red">*</span> :</th>
                    <td><input type="text" name="name" placeholder="Enter name" size="25" value="<%=DataUtility.getStringData(bean.getName())%>"></td>
                     <td style="position: fixed"><font  color="red"> <%=ServletUtility.getErrorMessage("name", request)%></font></td>
                </tr>
   
    <tr><th style="padding: 3px"></th></tr>          

              
                <tr ><th></th>
                <%
                if(bean.getId()>0){
                %>
                <td colspan="2" >
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_UPDATE%>">
                      &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_CANCEL%>"></td>
                
                <% }else{%>
                
                <td colspan="2" > 
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_SAVE%>">
                    &nbsp;  &nbsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_RESET%>"></td>
                
                <% } %>
                </tr>
            </table>
    </form>
    </center>

    <%@ include file="Footer.jsp"%>
	
</body>
</html>