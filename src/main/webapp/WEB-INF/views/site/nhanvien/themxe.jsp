<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>

<div class="container mt-5">


	
		<div class="my-3" id="themXeMoiDiv">
		<!-- <c:if test="${param.themXeMoi && param.thanhCong.equals('true') }">
			<div class="alert alert-success my-3" role="alert">
  			<h5>Đã thêm xe mới thành công!</h5>
			</div>
		</c:if>
		
		<c:if test="${param.themXeMoi && param.thanhCong.equals('false') }">
			<div class="alert alert-danger my-3" role="alert">
  			<h5>Biển số xe đã tồn tại !</h5>
			</div>
		</c:if>-->
		<form:form
			action="site/themxe.htm"
			method="POST" modelAttribute="xe">
			<div class="mb-3">
				<label for="bienSoXeInput" class="form-label">Biển số xe:</label>
				<form:input path="bienSoXe" cssClass="form-control"
					id="bienSoXeInput" />
			</div>

			<div class="mb-3">


				<label for="loaiXeSelect" class="form-label">Loại xe:</label>
				<form:select path="maLoaiXe.idLoaiXe" items="${loaixesel}"
						itemLabel="tenLoai" itemValue="idLoaiXe"
						class="form-select form-select-lg mb-6"
						aria-label=".form-select-lg example">
					</form:select>
			</div>
			<input type="submit" class="btn btn-success btn-lg mt-3" value="Thêm" />
			<div style="color: green;">${message }</div>
		</form:form>
		
		</div>
	
	


</div>
<%@include file="/WEB-INF/views/include/footer.jsp" %>