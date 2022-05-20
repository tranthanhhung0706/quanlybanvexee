<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>

<div class="container mt-5">
	<!-- đánh dấu xe đã hoàn thành chuyến -->
	
		<table class="table table-primary table-striped mt-4">
  		<thead>
  			<tr>
  				<th scope="col">Biển số xe</th>
  				<th scope="col">Chuyến xe</th>
  				<th scope="col">Ngày chạy</th>
  				<th scope="col">Giờ chạy</th>
  				<th scope="col">Đánh dấu</th>
  			</tr>
  		</thead>
  		<tbody>
  			<c:forEach items="${ listChuyenXeChuaHoanThanh}" var="chuyenXe">
  				<tr>
  					<td>${chuyenXe.maXe.bienSoXe }</td>
  					<td style="font-family: 'FontAwesome', 'Second Font name';">${chuyenXe.maTuyen.diaDiemDi.tenDiaDiem } &#xf30b; ${chuyenXe.maTuyen.diaDiemDen.tenDiaDiem }</td>
  					<!-- chuyển date kiểu String lưu trong object sang kiểu Date mới dùng được fmt:formatDate -->
  					<fmt:parseDate value="${chuyenXe.gioChay}" pattern="yyyy-MM-dd HH:mm" var="myDate"/>
					
  					<td><fmt:formatDate value="${myDate }" pattern="dd-MM-yyyy"/></td>
  					<td><fmt:formatDate value="${myDate }" pattern="HH:mm"/></td>
  					<td>
  					<form:form action="site/danhdauxe.htm" method="POST" modelAttribute="chuyenxe">
  						<form:input  path="maChuyen" style="display:none;" value="${chuyenXe.maChuyen }"/> <!-- bỏ id chuyến xe vào đây -->
  						<button type="submit" class="btn btn-warning" >Da Hoan Thanh</button>
  					</form:form>
  					</td>
  				</tr>
  			</c:forEach>
  		</tbody>
		</table>
	<!-- x end đánh dấu xe đã hoàn thành chuyến -->


</div>
<%@include file="/WEB-INF/views/include/footer.jsp" %>