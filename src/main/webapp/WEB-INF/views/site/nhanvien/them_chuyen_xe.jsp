<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>

<div class="container mt-5">

	
	
	
	<!-- Thêm chuyến xe mới tab -->
	
	
		<form:form
			action="site/themchuyenxe.htm" modelAttribute="chuyenxe"
			method="POST">
			  <div class="mb-3">
				<label for="bienSoXeInput" class="form-label">Nhập Biển Số Xe:</label>
				<form:select path="maXe.idXe" items="${bienxesel}"
						itemLabel="bienSoXe" itemValue="idXe"
						class="form-select form-select-lg mb-6"
						aria-label=".form-select-lg example">
					</form:select>
			</div>
			
			<div class="mb-3">
			
				<label for="chonTuyenXe" class="form-label">Chọn Tuyến Xe:</label>
				<!--  <select class="form-select" id="chonTuyenXe" name="tuyenXe" style="font-family: 'FontAwesome', 'Second Font name';">
  					
  					<c:forEach items="${tuyenXeList }" var="tuyenXe">
  						<option value="${tuyenXe.diaDiemDi.idDiaDiem },${tuyenXe.diaDiemDen.idDiaDiem}">
  							${tuyenXe.diaDiemDi.tenDiaDiem } &#xf30b; ${tuyenXe.diaDiemDen.tenDiaDiem }
  						</option>
  					
  					</c:forEach>
				</select>-->
				<form:select path="maTuyen.idTuyen" items="${tuyenxesel}"
						itemLabel="idTuyen" itemValue="idTuyen"
						class="form-select form-select-lg mb-6"
						aria-label=".form-select-lg example">
			</form:select>
		
	
			</div>
			
			<div class="mb-3">
				<label for="ngayChayInput" class="form-label">Chọn Ngày Khởi Hành:</label>
				<form:input type="date" onkeydown="return false" class="form-control" id="ngayChayInput" path="gioChay"/>
			</div>
		
			<!--  
			 <div class="my-4">
				<div class="form-label">Giờ chạy:</div>

					<div class="row">
						<input type="text"  name="hh" id="" placeholder="Giờ"
						class="form-control mx-3" style="width: 100px;" value="" required> 
										
						<input type="text" name="mm" id="" placeholder="Phút"
						class="form-control" style="width: 100px;" value="" required> 
						<div class="invalid-feedback">Xin vui lòng nhập giờ, phút hợp lệ.</div>
				</div>-->
			<div>
			<input type="submit" class="btn btn-success btn-lg mt-3" value="Thêm" />
			   </div>
			<div style="color: green;">${message }</div>
		</form:form>
		</div>
	
	


</div>
<%@include file="/WEB-INF/views/include/footer.jsp" %>