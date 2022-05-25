<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/WEB-INF/views/include/header.jsp" %>

	<div class="container">
	
		<div class="d-flex justify-content-between my-4">
			<h3 class="orange-text">Lịch sử mua vé</h3>
<%-- 			<a href="${pageContext.request.contextPath }/veXe/datVe/step1" class="btn bg-orange border rounded-pill px-3">Đặt vé</a> --%>
		</div>

		<!-- Thanh tìm kiếm -->
		<form class="row g-3" action="site/showlistve.htm" >
			<div class="col-md-6 col-lg-3">
				<label for="maVeInput" class="form-label">Mã vé</label> <input
					type="text" class="form-control" name="maVeInput" placeholder="Nhập mã vé" id="maVeInput">
			</div>
<!-- 			<div class="col-md-6 col-lg-3"> -->
<!-- 				<label for="dateInput" class="form-label">Thời gian</label> <input -->
<!-- 				 name="dateInput"	type="date" class="form-control" id="dateInput"> -->
<!-- 			</div> -->
			<div class="col-md-6 col-lg-3">
				<label for="tuyenDuongInput" class="form-label">Tuyến đường</label> 
				
			
				
				<select name="tuyenDuongInput" id="tuyenDuongInput" 
									class="form-select">
									<option value="default" selected>Chọn tuyến đường</option>
									<c:forEach items="${tuyenxe}" var="tx">
										<option value="${tx.diaDiemDi.idDiaDiem},${tx.diaDiemDen.idDiaDiem}">${tx.diaDiemDi.tenDiaDiem } > ${tx.diaDiemDen.tenDiaDiem } </option>
										
									</c:forEach>
								</select>
				</div>

<!-- 			<div class="col-md-6 col-lg-3"> -->
<!-- 				<label for="trangThaiInput" class="form-label">Trạng thái</label> -->
					
<!-- 					<select -->
<!-- 					class="form-select" id="trangThaiInputInput" name="trangThaiInput" > -->
<!-- 					<option value="default" selected>Chọn trạng thái</option> -->
<!-- 					<option value="Chờ thanh toán">Chờ thanh toán</option> -->
<!-- 					<option value="Đã thanh toán">Đã thanh toán</option> -->
					
<!-- 				</select> -->
<!-- 			</div> -->

			<div class="col-md-6 d-flex justify-content-start">
			
				<button name="tim" class="btn  border rounded-pill px-4 me-2">Tìm</button>
				<button name="lammoi" class="btn border rounded-pill px-4 mx-2" value="4">Làm Mới</button>
<!-- 				<input type="submit" value ="Tìm" class="btn  border rounded-pill px-4 me-2"/>  -->
<!-- 				<input type="submit" value="Làm mới" class="btn border rounded-pill px-4 mx-2 orange-text"/> -->
			</div>

		</form>


		<!-- Bảng vé -->
		<table
			class="table mt-5  table-striped m-auto shado-sm overflow-scroll border">
			<thead>
				<tr>
					<th scope="col">Mã</th>
					<th scope="col">SL</th>
					<th scope="col">Tuyến đường</th>
					<th scope="col">Ngày lập</th>
			
					<th scope="col">Tổng tiền</th>
					<th scope="col">Thanh toán</th>
					<th scope="col">Trạng thái</th>
					<th scope="col">Thao tác</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listVeXe }" var="veXe">
					<tr>
						<th scope="row">${veXe.idVe }</th>
						<td>${veXe.gheList.size() }</td>
						<td>${veXe.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem }<i
							class="fas fa-long-arrow-alt-right"></i>
							${veXe.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
						</td>
						<td>${hashMapNgayGio.get(veXe.idVe)[0] }</td>
						<td>${hashMapNgayGio.get(veXe.idVe)[1] }</td>
						<td>${veXe.tongTien }</td>
						<td>${veXe.hinhThucThanhToan }</td>
						<c:if test="${veXe.trangThai.equals(\"Chờ thanh toán\") }">
							<td>${veXe.trangThai }</td>
						</c:if>
						<c:if test="${veXe.trangThai.equals(\"Đã thanh toán\") }">
							<td class="text-success fw-bold">${veXe.trangThai }</td>
						</c:if>
						<c:if test="${veXe.trangThai.equals(\"Đã hủy\") }">
							<td class="text-danger fw-bold">${veXe.trangThai }</td>
						</c:if>
						<!--  <td><a class="btn btn-info" href="${pageContext.request.contextPath }/chiTietVe?veId=${veXe.idVe}">Chi tiết</a></td>-->
						<td><a class="btn btn-info" href="site/chitiet/${veXe.idVe}.htm">Chi tiết</a></td>
					</tr>
				</c:forEach>
                  <c:forEach var="l" items="${listve}">
                     <tr>
                       <th scope="row">${l.idVe}</th>
                       <td> ${l.gheList.size() }</td>
                       <td>${l.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem }<i
							class="fas fa-long-arrow-alt-right"></i>
							${l.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
						</td>
						<td>${l.ngayLap }</td>
						<td>${l.tongTien }</td>
						<td>${l.hinhThucThanhToan }</td>
						<c:if test="${l.trangThai.equals(\"Chờ thanh toán\") }">
							<td>${l.trangThai }</td>
						</c:if>
						<c:if test="${l.trangThai.equals(\"Đã thanh toán\") }">
							<td class="text-success fw-bold">${l.trangThai }</td>
						</c:if>
						<c:if test="${l.trangThai.equals(\"Đã hủy\") }">
							<td class="text-danger fw-bold">${l.trangThai }</td>
						</c:if>
						<td><a class="btn btn-info" href="site/chitiet/${l.idVe}.htm">Chi tiết</a></td>
                     </tr>
                  </c:forEach>
              <tr>
				    <th>${listve1.idVe }</th>
				    <td> ${listve1.gheList.size() }</td>
                       <td>${listve1.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem }<i
							class="fas fa-long-arrow-alt-right"></i>
							${listve1.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
						</td>
						<td>${listve1.ngayLap }</td>
						<td>${listve1.tongTien }</td>
						<td>${listve1.hinhThucThanhToan }</td>
						<c:if test="${listve1.trangThai.equals(\"Chờ thanh toán\") }">
							<td>${listve1.trangThai }</td>
						</c:if>
						<c:if test="${listve1.trangThai.equals(\"Đã thanh toán\") }">
							<td class="text-success fw-bold">${listve1.trangThai }</td>
						</c:if>
						<c:if test="${listve1.trangThai.equals(\"Đã hủy\") }">
							<td class="text-danger fw-bold">${listve1.trangThai }</td>
						</c:if>
						<td><a class="btn btn-info" href="site/chitiet/${listve1.idVe}.htm">Chi tiết</a></td>
				</tr>
			</tbody>
		</table>
	</div>



<%@include file="/WEB-INF/views/include/footer.jsp" %>