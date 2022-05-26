
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<%@include file="/WEB-INF/views/include/header.jsp"%>

<!--  End navbar -->
<div class="container">

	<form:form action="site/sign_in.htm" cssStyle="width:300px;"
		cssClass="m-auto mt-5 loginValidation" method="post" novalidate="true"
		modelAttribute="Account">
		<div class="mb-3">

			<label for="exampleInputEmail1" class="form-label"><!-- Số điện
				thoại -->
				<s:message code="login.sdt.title"/>
				</label> <input name="username" type="text" pattern="^[0-9]{10}[0-9]?$"
				class="form-control" id="exampleInputEmail1"
				aria-describedby="emailHelp" required>
			<div class="invalid-feedback"><!-- Xin vui lòng nhập số điện thoại
				hợp lệ -->
				<s:message code="login.sdt.placeholder"/>
				</div>
			<form:errors path="username" />
		</div>
		
		<div class="mb-3">
			<label for="exampleInputPassword1" class="form-label"><s:message code="login.matkhau.title"/></label> <input name="password" type="password" class="form-control"
				id="exampleInputPassword1" required>

			<div class="invalid-feedback"><s:message code="login.matkhau.placeholder"/></div>
			<form:errors path="password" />
			
			<div class="row  px-7 m-4">
			<s:message code="login.captcha" var="placeholderCaptcha"/>
			<img src="${pageContext.request.contextPath }/captcha/"> <input
				name="captcha" type="text" placeholder="${placeholderCaptcha }" /> <label
				class="mb-1">
				<h6 class="mb-0 text-sm errors">${reCaptra}</h6>
			</label>
		</div>

		</div>
		<a href="site/forgotPassword.htm"><s:message code="login.quenmk"/></a>
		<div class="d-flex justify-content-end">
			<a class="btn btn-success me-2" href="site/register.htm"><s:message code="login.dangki"/></a>
			<button type="submit" class="btn btn-primary"><s:message code="login.dangnhap"/></button>
			
		</div>
<div style="color: green;">${message }</div>
		

	</form:form>

</div>
<%@include file="/WEB-INF/views/include/footer.jsp"%>