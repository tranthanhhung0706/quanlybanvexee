<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@include file="/WEB-INF/views/include/header.jsp" %>

	<!-------------------- body ------------------------->
	<!-------------------- Thông tin về vé ------------/-->
	<div class="container">

		<c:if test="${veXe.trangThai.equals(\"Chờ thanh toán\")}">
			<div class="text-center mt-4 mb-3">
				<img
					src="${pageContext.request.contextPath }/resources/img/unpaidTicket.png"
					alt="">
			</div>
			<h5 class="text-center mt-1 mb-4 orange-text"><s:message code="ve.vechuatt"/></h5>
		</c:if>

		<c:if test="${veXe.trangThai.equals(\"Đã thanh toán\") }">
			<div class="text-center mt-4 mb-3">
				<img
					src="${pageContext.request.contextPath }/resources/img/successfulTicket.png"
					alt="">
			</div>
			<h5 class="text-center mt-1 mb-4 text-success"><s:message code="ve.vedat"/></h5>
		</c:if>

		<c:if test="${veXe.trangThai.equals(\"Đã hủy\") }">
			<div class="text-center mt-4 mb-3">
				<img
					src="${pageContext.request.contextPath }/resources/img/unpaidTicket.png"
					alt="">
			</div>
			<h5 class="text-center mt-1 mb-4 text-danger"><s:message code="ve.vedabihuy"/></h5>
		</c:if>



		<div class="card m-auto w-100 rounded-3 border"
			style="max-width: 900px;">
			<div class="card-header text-uppercase text-center bg-orange fw-bold">
				Thông tin mua vé</div>
			<!-- Thông tin hành khách -->
			<h6 class="bg-light p-2 border border-1 m-0"><s:message code="ve.tthanhkhach"/></h6>
			<div class="card-body">

				<div class="row">
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="login.hoten"/>:</div>
							<div class="col">${veXe.idKhachHang.hoTen }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="login.sdt.title"/>:</div>
							<div class="col">${veXe.idKhachHang.phoneNumber }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="login.diachi"/>:</div>
							<div class="col">${veXe.idKhachHang.diaChi }</div>
						</div>
					</div>
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Email:</div>
							<div class="col">${veXe.idKhachHang.email }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="login.cmnd"/>:</div>
							<div class="col">${veXe.idKhachHang.cmnd }</div>
						</div>
					</div>
				</div>
				<div class="row"></div>
			</div>

			<!-- thông tin chuyến-->
			<h6 class="bg-light p-2 border border-1 m-0">
				Thông tin chuyến: ${veXe.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem } <i
					class="fas fa-long-arrow-alt-right"></i>
				${veXe.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
			</h6>
			<div class="card-body">
				<div class="row">
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="ve.tuyenxe"/>:</div>
							<div class="col">
								${veXe.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem } <i
									class="fas fa-long-arrow-alt-right"></i>
								${veXe.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
							</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="ve.thoigian"/>:</div>
							<div class="col text-danger fw-bold">${veXe.idChuyenXe.gioChay }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="ve.diemlenxe"/>:</div>
							<div class="col">${veXe.idChuyenXe.maTuyen.diaDiemLenXe }</div>
						</div>
					</div>
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="ve.soluongghe"/>:</div>
							<div class="col">${veXe.gheList.size() }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted"><s:message code="ve.soghe"/>:</div>
							<div class="col text-danger fw-bold">
								<c:forEach items="${veXe.gheList }" var="ghe">
							 	${ghe.maGhe }
							 </c:forEach>
							</div>


						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-danger fw-bold"><s:message code="ve.cachthanhtoan"/>:</div>
							<div class="col text-danger fw-bold">${veXe.hinhThucThanhToan }</div>
						</div>
					</div>
				</div>
				<div class="row"></div>
			</div>

			<!--- TỔNG TIỀN --->
			<div class="bg-light p-3 position-relative">

				<c:if test="${veXe.trangThai.equals(\"Đã thanh toán\") }">
					<div class="position-absolute top-50 start-0 translate-middle-y">
						<img
							src="${pageContext.request.contextPath }/resources/img/paidstamp.jpg">
					</div>
				</c:if>


				<h5 class="text-uppercase text-end"><s:message code="ve.tongtien"/></h5>
				<p class="m-0 text-uppercase text-end orange-text fs-3 fw-bold">
					${veXe.tongTien } <sup
						class="text-decoration-underline text-lowercase">đ</sup>
				</p>
			</div>
		</div>
		<div class="d-flex justify-content-end m-auto my-4"
			style="max-width: 900px;">

			<c:if test="${tk_kh.hoTen !=null }">
				<a class="btn btn-warning  border rounded-pill px-4 me-2"
					href="site/userbookedticket/${tk_kh.userId}.htm?btnid1"><s:message code="ve.quaylai"/></a>
				<c:if test="${!veXe.trangThai.equals(\"Đã hủy\") && !veXe.trangThai.equals('Đã thanh toán') }">
					<form:form action="site/huyve/${tk_kh.userId}.htm"
						method="POST">
						<input type="hidden" name="idVe" value="${veXe.idVe }">
						<input type="submit" value="Hủy vé"
							class="btn btn-danger border rounded-pill px-4 me-2" />
					</form:form>
				</c:if>

			</c:if>
			<c:if test="${tk_nv.hoTen !=null }">
				<a class="btn btn-warning  border rounded-pill px-4 me-2"
					href="${pageContext.request.contextPath }/nhanvien/veChuaThanhToan">Quay
					lại</a>
				<c:if test="${!veXe.trangThai.equals(\"Đã hủy\") }">

					<form:form
						action="${pageContext.request.contextPath }/nhanvien/huyVe"
						method="POST">
						<input type="hidden" name="idVe" value="${veXe.idVe }">
						<input type="hidden" name="idNhanVien"
							value="${nhanVien.idNhanVien }" />
						<input type="submit" value="Hủy vé"
							class="btn btn-danger border rounded-pill px-4 me-2" />
					</form:form>
					
					<form:form
					action="${pageContext.request.contextPath }/nhanvien/thanhtoanve"
					method="POST">
					<input type="hidden" name="idVe" value="${ veXe.idVe}">
					<input type="hidden" name="idNhanVien"
						value="${nhanVien.idNhanVien }" />
					<input type="submit" value="Thanh toán"
						class="btn btn-success border rounded-pill px-4 me-2" />
				</form:form>
					
				</c:if>
				
				

			</c:if>

		</div>
	</div>


<%@include file="/WEB-INF/views/include/footer.jsp" %>