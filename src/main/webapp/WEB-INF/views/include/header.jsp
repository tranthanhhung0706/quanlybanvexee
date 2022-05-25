<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html >

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Vé xe Phương Trang</title>
<base href="${pageContext.servletContext.contextPath }/" />
<script src="https://kit.fontawesome.com/d6bdcb3119.js"
	crossorigin="anonymous"></script>
<!--   <link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/style.css">-->
<link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />
<!--  <link rel="stylesheet"
	href="${pageContext.request.contextPath }/resources/css/custom_bootstrap.css"> -->
	<link rel="stylesheet" href="<c:url value='/resources/css/custom_bootstrap.css' />" />
	 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
<style type="text/css">
	*[id$=errors]{
	color:red;
	font-style: italic;
</style>


</head>

<body>

<a href="?language=en">en</a>
<a href="?language=vi">vi</a>
	<!-- ////////////////////////// Main Navbar-->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid px-xl-5">
			<img
				src="<c:url value='/resources/img/tiger left.png' />"
				class="img-fluid d-none d-xl-inline" style="width: 100px;" alt="">
			<a class="pe-1" href="${pageContext.request.contextPath }"><img
				src="<c:url value='/resources/img/logo.png' />"
				alt="" style="width: 200px;"></a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#mainNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>


			<div class="collapse navbar-collapse" id="mainNavbar">
				<ul class="navbar-nav w-100 justify-content-lg-between">
					<li class="nav-item mx-2"><a
						class="nav-link px-0 fw-bolder text-uppercase" href="site/index.htm"><s:message code="index.trangchu"/></a></li>
					<li class="nav-item mx-3 "><a
						class="nav-link  px-0 fw-bolder text-uppercase" href="#"><s:message code="index.lichtrinh"/></a></li>
					<li class="nav-item mx-3"><a
						class="nav-link  px-0 fw-bolder text-uppercase" href="#"><s:message code="index.tintuc"/></a></li>

					<li class="nav-item mx-3"><a
						class="nav-link  px-0 fw-bolder text-uppercase" href="#"><s:message code="index.lienhe"/></a></li>

					<li class="nav-item mx-3 mr-5"><a
						class="nav-link  px-0 fw-bolder text-uppercase" href="#"><s:message code="index.vechungtoi"/></a></li>
					<!-- //////////////// nút đăng nhập -->

					<!-- Chưa đăng nhập thì hiện cái này -->
					
						
					  <c:if test="${tai_khoans.username ==null }">
					    <a href="site/sign_in.htm"
							class="btn btn-warning d-inline-block mr-auto fw-bold"> <i
							class="fas fa-user-circle"></i> <s:message code="login.dangnhap"/>
						</a>
					  </c:if>
					  
					   
					  


					<!-- Nếu đã đăng nhập mà là khách hàng rồi thì hiện cái này -->
					<c:if test="${tk_kh.hoTen !=null }">
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle btn btn-warning d-inline-block mr-auto p-2 fw-bold"
							data-bs-toggle="dropdown" href="#"><i
								class="fas fa-user-circle"> </i> ${tk_kh.hoTen }</a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item"
									href="site/in_for/${tk_kh.userId}.htm?btnid"><i
										class="fas fa-user"></i> <s:message code="header.thongtintk"/></a></li>
								<li><a class="dropdown-item"
									href="site/userbookedticket/${tk_kh.userId}.htm?btnid1"><i
										class="fas fa-history d-inline-block"></i> <s:message code="header.cacvedadat"/></a></li>
                                 <li><a class="dropdown-item"
									href="site/changepassword/${tk_kh.userId}.htm?btnid2"><i
										class="fas fa-history d-inline-block"></i><s:message code="header.doimk"/></a></li>
								<li><hr class="dropdown-divider"></li>

									<li>
										<button type="submit" class="dropdown-item" form="form2">
											<i class="fas fa-sign-out-alt"><a href="site/redirect.htm"><s:message code="header.dangxuat"/></a></i>
										</button>
									</li>
							</ul></li>
					</c:if>
					<!--  Nếu đã đăng nhập mà là nhân viên thanh toán thì hiện cái này -->
					<c:if test="${tk_nv.hoTen !=null }">
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle btn btn-warning d-inline-block mr-auto p-2 fw-bold"
							data-bs-toggle="dropdown" href="#"><i
								class="fas fa-user-circle"> </i> ${tk_nv.hoTen }</a>
							<ul class="dropdown-menu">
								<li><a class="dropdown-item"
									href="site/in_for2/${tk_nv.idNhanVien}.htm?btnid2"><i
										class="fas fa-user"></i> <s:message code="header.thongtintk"/></a></li>
								<li><a class="dropdown-item"
									href="site/thanh_toan/${tk_nv.idNhanVien}.htm?btnid3"><i
										class="fas fa-history d-inline-block"></i> <s:message code="header.thanhtoanve"/></a></li>
										
								<li><a class="dropdown-item" href="site/nghiepvu.htm"><i class="fas fa-bus-alt"></i> <s:message code="header.themchuyenxe"/></a></li>

								<li><hr class="dropdown-divider"></li>

								<li>
										<button type="submit" class="dropdown-item" form="form2">
											<i class="fas fa-sign-out-alt"><a href="site/redirect.htm"><s:message code="header.dangxuat"/></a></i>
										</button>
									</li>


							</ul>
							</li>
					</c:if>

				</ul>
			</div>
			<img
				src="<c:url value='/resources/img/tiger right.png' />"
				class="img-fluid d-none d-xl-inline" style="width: 100px;" alt="">
		</div>


	</nav>