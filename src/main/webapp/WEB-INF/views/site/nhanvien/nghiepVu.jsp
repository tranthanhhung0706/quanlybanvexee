<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>

<div class="container mt-5">

	<!-- Danh sách các button nghiệp vụ  -->
	<div class="d-flex justify-content-around">
		<a href="site/themxe.htm" class="btn btn-primary"> Thêm Xe Mới</a>
		 <a href="site/themchuyenxe.htm" class="btn btn-primary"> Thêm Chuyến Xe Mới</a> 
		 <a href="site/chuyen_xe_hoan_thanh.htm" class="btn btn-primary"> Đánh Dấu Xe Đã Hoàn Thành Chuyến Xe</a>
	</div>
	
	
	<!-- Thêm xe mới tab -->
	<!--  
	<c:if test="${param.themXeMoi }">
		<div class="my-3" id="themXeMoiDiv">
		<c:if test="${param.themXeMoi && param.thanhCong.equals('true') }">
			<div class="alert alert-success my-3" role="alert">
  			<h5>Đã thêm xe mới thành công!</h5>
			</div>
		</c:if>
		
		<c:if test="${param.themXeMoi && param.thanhCong.equals('false') }">
			<div class="alert alert-danger my-3" role="alert">
  			<h5>Biển số xe đã tồn tại !</h5>
			</div>
		</c:if>
		<form:form
			action="${pageContext.request.contextPath }/nhanvien/themXeMoi"
			method="POST" modelAttribute="xeMoi">
			<div class="mb-3">
				<label for="bienSoXeInput" class="form-label">Biển số xe:</label>
				<form:input path="bienSoXe" cssClass="form-control"
					id="bienSoXeInput" />
			</div>

			<div class="mb-3">


				<label for="loaiXeSelect" class="form-label">Loại xe:</label> <select
					class="form-select" id="loaiXeSelect" name="loaiXe">
					<c:forEach items="${listLoaiXe }" var="item">
						<option value="${item.idLoaiXe }">${item.tenLoai }</option>
					</c:forEach>
				</select>
			</div>
			<input type="submit" class="btn btn-success btn-lg mt-3" value="Thêm" />
		</form:form>
		</div>
	</c:if>-->
	<!-- x END thêm xe mới tab x -->
	
	<!-- Thêm chuyến xe mới tab -->
	<!--  
	<c:if test="${param.themChuyenXeMoi }">
		<div class="my-3" id="themXeMoiDiv">
		<c:if test="${param.thanhCong}">
			<div class="alert alert-success my-3" role="alert">
  			<h5>Đã thêm chuyến xe mới thành công!</h5>
			</div>
		</c:if>
		
		<c:if test="${param.tonTaiXe.equals('false') }">
			<div class="alert alert-danger my-3" role="alert">
  			<h5>Biển số xe đã không tồn tại!</h5>
			</div>
		</c:if>
		<c:if test="${param.tonTaiChuyenXe.equals('true') }">
			<div class="alert alert-danger my-3" role="alert">
  			<h5>Biển số xe này đang chạy cho một chuyến xe nào đó và vẫn chưa xong !</h5>
			</div>
		</c:if>
		<form:form
			action="${pageContext.request.contextPath }/nhanvien/themChuyenXeMoi"
			method="POST">
			
			<div class="mb-3">
				<label for="bienSoXeInput" class="form-label">Nhập Biển Số Xe:</label>
				<input type="text" class="form-control" id="bienSoXeInput" name="bienSoXe"/>
			</div>
			
			<div class="mb-3">
			
				<label for="chonTuyenXe" class="form-label">Chọn Tuyến Xe:</label>
				<select class="form-select" id="chonTuyenXe" name="tuyenXe" style="font-family: 'FontAwesome', 'Second Font name';">
  					
  					<c:forEach items="${tuyenXeList }" var="tuyenXe">
  						<option value="${tuyenXe.diaDiemDi.idDiaDiem },${tuyenXe.diaDiemDen.idDiaDiem}">
  							${tuyenXe.diaDiemDi.tenDiaDiem } &#xf30b; ${tuyenXe.diaDiemDen.tenDiaDiem }
  						</option>
  					
  					</c:forEach>
				</select>
			</div>
			
			<div class="mb-3">
				<label for="ngayChayInput" class="form-label">Chọn Ngày Khởi Hành:</label>
				<input type="date" onkeydown="return false" class="form-control" id="ngayChayInput" name="ngayChay"/>
			</div>
		-->	
			<!-- Thời gian chạy -->
			<!--  <div class="my-4">
				<div class="form-label">Giờ chạy:</div>

					<div class="row">
						<input type="text"  name="hh" id="" placeholder="Giờ"
						class="form-control mx-3" style="width: 100px;" value="" required> 
										
						<input type="text" name="mm" id="" placeholder="Phút"
						class="form-control" style="width: 100px;" value="" required> 
						<div class="invalid-feedback">Xin vui lòng nhập giờ, phút hợp lệ.</div>
				</div>
			</div>
			<input type="submit" class="btn btn-success btn-lg mt-3" value="Thêm" />
		</form:form>
		</div>
	</c:if>-->
	<!-- x END thêm chuyến xe mới tab x -->
	
	<!-- đánh dấu xe đã hoàn thành chuyến -->
	
	<c:if test="${param.danhDauXe }">
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
  					<form:form action="${pageContext.request.contextPath }/nhanvien/danhDauChuyenXe" method="POST">
  						<input type="hidden" name="idChuyenXe" value="${chuyenXe.maChuyen }"/> <!-- bỏ id chuyến xe vào đây -->
  						<input type="submit" value="Đã hoàn thành" class="btn btn-warning" />
  					</form:form>
  					</td>
  				</tr>
  			
  			</c:forEach>
  			
  		
  		</tbody>
		</table>
	</c:if>	
	
	
	<!-- x end đánh dấu xe đã hoàn thành chuyến -->


</div>
<%@include file="/WEB-INF/views/include/footer.jsp" %>