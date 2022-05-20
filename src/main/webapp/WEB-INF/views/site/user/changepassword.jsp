
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>

   <!--  End navbar -->
<div class="container">
	<form:form action="site/changepassword.htm" cssStyle="width:300px;" cssClass="m-auto mt-5 loginValidation" method="post" modelAttribute="tai_khoan" novalidate="true">
		<div class="mb-3">
          <label for="exampleInputEmail1" class="form-label">Số điện thoại</label>
          <form:input path="username" type="text" pattern="^[0-9]{10}[0-9]?$" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" />
          
        </div>
        <div class="mb-3">
          <label for="exampleInputPassword1" class="form-label">Mật khẩu</label>
          <form:input path="password" type="password" class="form-control"  id="exampleInputPassword1" />
        	
        </div>
        <a href="site/forgotPassword.htm">Forgot your password?</a>
        <div class="d-flex justify-content-end">
            
            <button type="submit" class="btn btn-primary">Doi mat khau</button>
            <div style="color: green;">${message }</div>
        </div>
	</form:form>

</div>
<%@include file="/WEB-INF/views/include/footer.jsp" %>