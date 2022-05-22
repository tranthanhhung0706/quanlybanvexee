<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>
	<!--- Thanh Progresss --->

	<div class="container">
		<h2 class="text-center mt-3">THANH TOÁN</h2>


		<div class="step-wizard">
			<ul class="step-wizard-list">
				<li class="step-wizard-item"><span class="progress-count">1</span>
					<span class="progress-label">CHỌN TUYẾN</span></li>
				<li class="step-wizard-item"><span class="progress-count">2</span>
					<span class="progress-label">XÁC NHẬN LỘ TRÌNH</span></li>
				<li class="step-wizard-item"><span class="progress-count ">3</span>
					<span class="progress-label">CHẤP NHẬN ĐIỀU KHOẢN</span></li>
				<li class="step-wizard-item current-item"><span
					class="progress-count">4</span> <span class="progress-label">THANH
						TOÁN</span></li>
			</ul>

		</div>
	</div>
	<!--- End Thanh Progress --->

	<!-------------------- body ------------------------->
	<!-------------------- Thông tin về vé ------------/-->
	<div class="container">
		<div class="card m-auto w-100 rounded-3 border"
			style="max-width: 900px;">
			<div class="card-header text-uppercase text-center bg-orange fw-bold">
				Thông tin mua vé</div>
			<!-- Thông tin hành khách -->
			<h6 class="bg-light p-2 border border-1 m-0">Thông tin hành
				khách</h6>
			<div class="card-body">

				<div class="row">
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Họ tên:</div>
							<div class="col">${veXe.idKhachHang.hoTen }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Số điện thoại:</div>
							<div class="col">${veXe.idKhachHang.phoneNumber }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Địa chỉ nhà:</div>
							<div class="col">${veXe.idKhachHang.diaChi }</div>
						</div>
					</div>
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Email:</div>
							<div class="col">${veXe.idKhachHang.email }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">CMND/CCCD:</div>
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
							<div class="col-4 col-md-5 text-muted">Tuyến xe:</div>
							<div class="col">
								${veXe.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem } <i
									class="fas fa-long-arrow-alt-right"></i>
								${veXe.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
							</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Thời gian:</div>
							<div class="col text-danger fw-bold">${veXe.idChuyenXe.gioChay }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Điểm lên xe:</div>
							<div class="col">${veXe.idChuyenXe.maTuyen.diaDiemLenXe }</div>
						</div>
					</div>
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Số lượng ghế:</div>
							<div class="col">${veXe.gheList.size() }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Số ghế:</div>
							<div class="col text-danger fw-bold">
								<c:forEach items="${veXe.gheList }" var="ghe">
							 	${ghe.maGhe }
							 </c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="row"></div>
			</div>

			<!--- TỔNG TIỀN --->
			<div class="bg-light p-3">
				<h5 class="text-uppercase text-end">Tổng Tiền</h5>
				<p class="m-0 text-uppercase text-end orange-text fs-3 fw-bold">
					${veXe.tongTien } <sup
						class="text-decoration-underline text-lowercase">đ</sup>
				</p>
			</div>
		</div>



	<c:if test="${veXeThu2 != null }">
		<br/><br/>
	
	<div class="card m-auto w-100 rounded-3 border"
			style="max-width: 900px;">
			<div class="card-header text-uppercase text-center bg-orange fw-bold">
				Thông tin mua vé</div>
			<!-- Thông tin hành khách -->
			<h6 class="bg-light p-2 border border-1 m-0">Thông tin hành
				khách</h6>
			<div class="card-body">

				<div class="row">
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Họ tên:</div>
							<div class="col">${veXeThu2.idKhachHang.hoTen }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Số điện thoại:</div>
							<div class="col">${veXeThu2.idKhachHang.phoneNumber }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Địa chỉ nhà:</div>
							<div class="col">${veXeThu2.idKhachHang.diaChi }</div>
						</div>
					</div>
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Email:</div>
							<div class="col">${veXeThu2.idKhachHang.email }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">CMND/CCCD:</div>
							<div class="col">${veXeThu2.idKhachHang.cmnd }</div>
						</div>
					</div>
				</div>
				<div class="row"></div>
			</div>

			<!-- thông tin chuyến-->
			<h6 class="bg-light p-2 border border-1 m-0">
				Thông tin chuyến: ${veXeThu2.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem } <i
					class="fas fa-long-arrow-alt-right"></i>
				${veXeThu2.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
			</h6>
			<div class="card-body">
				<div class="row">
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Tuyến xe:</div>
							<div class="col">
								${veXeThu2.idChuyenXe.maTuyen.diaDiemDi.tenDiaDiem } <i
									class="fas fa-long-arrow-alt-right"></i>
								${veXeThu2.idChuyenXe.maTuyen.diaDiemDen.tenDiaDiem }
							</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Thời gian:</div>
							<div class="col text-danger fw-bold">${veXeThu2.idChuyenXe.gioChay }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Điểm lên xe:</div>
							<div class="col">${veXeThu2.idChuyenXe.maTuyen.diaDiemLenXe }</div>
						</div>
					</div>
					<div class="col-12 col-md-6 d-flex flex-column">
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Số lượng ghế:</div>
							<div class="col">${veXeThu2.gheList.size() }</div>
						</div>
						<div class="row my-2">
							<div class="col-4 col-md-5 text-muted">Số ghế:</div>
							<div class="col text-danger fw-bold">
								<c:forEach items="${veXeThu2.gheList }" var="ghe">
							 	${ghe.maGhe }
							 </c:forEach>
							</div>
						</div>
					</div>
				</div>
				<div class="row"></div>
			</div>

			<!--- TỔNG TIỀN --->
			<div class="bg-light p-3">
				<h5 class="text-uppercase text-end">Tổng Tiền</h5>
				<p class="m-0 text-uppercase text-end orange-text fs-3 fw-bold">
					${veXeThu2.tongTien } <sup
						class="text-decoration-underline text-lowercase">đ</sup>
				</p>
			</div>
		</div>
	
	</c:if>

	<!--  end container -->
	</div>
	
	

	<!---------------------- Hình thức thanh toán ----------->
	<div class="container">
		<h6 class="text-center text-uppercase my-4 green-text fw-bold">Chọn
			cách thanh toán</h6>
		<c:if test="${tk_nv.hoTen !=null }">
		<form action="${pageContext.request.contextPath }/nhanvien/veChuaThanhToan" method="POST" id="phuongThucThanhToan">
		</c:if>	
		<c:if test="${tk_kh.hoTen !=null }">
		<form action="site/userBookedTickets.htm" method="POST" id="phuongThucThanhToan">
		</c:if>
		
		
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="row justify-content-around m-auto"
				style="max-width: 1000px;">
				<label
					class="col-12 col-sm-5 col-lg-4 my-2 p-3 d-flex flex-column justify-content-center border-gray-dotted position-relative bank-selected"
					for="theQuocTe" style="max-width: 280px;"> <!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/visa_card.png"
							alt=""> <img
							src="${pageContext.request.contextPath }/resources/img/master_card.png"
							alt=""> <img
							src="${pageContext.request.contextPath }/resources/img/jcb_card.png"
							alt="">
					</div> <!-- Tên thẻ -->
					<div class="text-center">Thẻ Quốc tế Visa/Master/JCB</div> <input
					class="position-absolute top-0 end-0 form-check-input m-3"
					type="radio" name="hinhThucThanhToan" id="theQuocTe"
					value="Quốc Tế" checked>
				</label> <label
					class="col-12 col-sm-5 col-lg-4 my-2 p-3 d-flex flex-column justify-content-center border-gray-dotted position-relative"
					for="theNoiDia" style="max-width: 280px;"> <!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/napas_card.png"
							alt="">
					</div> <!-- Tên thẻ -->
					<div class="text-center">Thẻ ATM Nội địa</div> <input
					class="position-absolute top-0 end-0 form-check-input m-3"
					type="radio" name="hinhThucThanhToan" id="theNoiDia"
					value="Nội Địa">
				</label> <label
					class="col-12 col-sm-5 col-lg-4 my-2 p-3 d-flex flex-column justify-content-center border-gray-dotted position-relative"
					for="momo" style="max-width: 280px;"> <!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/Momo.png"
							alt="">
					</div> <!-- Tên thẻ -->
					<div class="text-center">Ví MoMo</div> <input
					class="position-absolute top-0 end-0 form-check-input m-3"
					type="radio" name="hinhThucThanhToan" id="momo" value="Ví MoMo">
				</label> <label
					class="col-12 col-sm-5 col-lg-4 my-2 p-3 d-flex flex-column justify-content-center border-gray-dotted position-relative"
					for="zalo" style="max-width: 280px;"> <!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/ZaloPay.png"
							alt="">
					</div> <!-- Tên thẻ -->
					<div class="text-center">Ví ZaloPay</div> <input
					class="position-absolute top-0 end-0 form-check-input m-3"
					type="radio" name="hinhThucThanhToan" id="zalo" value="Ví ZaloPay">
				</label> <label
					class="col-12 col-sm-5 col-lg-4 my-2 p-3 d-flex flex-column justify-content-center border-gray-dotted position-relative"
					for="vnPay" style="max-width: 280px;"> <!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/VNPay.png"
							alt="">
					</div> <!-- Tên thẻ -->
					<div class="text-center">VNPay</div> <input
					class="position-absolute top-0 end-0 form-check-input m-3"
					type="radio" name="hinhThucThanhToan" id="vnPay" value="VNPay">
				</label> <label
					class="col-12 col-sm-5 col-lg-4 my-2 p-3 d-flex flex-column justify-content-center border-gray-dotted position-relative"
					for="traTienMat" style="max-width: 280px;"> <!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/give-money.png"
							alt="">
					</div> <!-- Tên thẻ -->
					<div class="text-center">Trả tiền mặt</div> <input
					class="position-absolute top-0 end-0 form-check-input m-3"
					type="radio" name="hinhThucThanhToan" id="traTienMat"
					value="Tiền Mặt">
				</label>

			</div>
		</form>

		<!------------------ Danh sách ngân hàng liên kết --------->
		<!------------------- Thẻ Quốc tế -------------------->
		<div class="d-block" id="cacTheQuocTe">
			<h6 class="orange-text fw-bold my-3 mx-auto"
				style="max-width: 1000px;">Danh sách ngân hàng liên kết:</h6>
			<div class="row m-auto" style="max-width: 1000px;">
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/jcb_lg.png"
							alt="">
					</div>


				</div>
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/master_card_lg.png"
							alt="">
					</div>

				</div>
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/visa_card_lg.png"
							alt="">
					</div>


				</div>


			</div>

		</div>



		<!------------------- Thẻ Trong Nước ------------------->
		<div class="d-none" id="cacTheTrongNuoc">
			<h6 class="orange-text fw-bold my-3 mx-auto"
				style="max-width: 1000px;">Danh sách ngân hàng liên kết:</h6>
			<div
				class="row m-auto justify-content-around justify-content-md-start"
				style="max-width: 1000px;">
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/abbank.png"
							alt="">
					</div>


				</div>
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/acbank.png"
							alt="">
					</div>

				</div>
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/BIDVBank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/baovietbank.png"
							alt="">
					</div>
				</div>
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/vietinbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/dongabank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/eximbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/gpbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/hdbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/kienlongbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/lienvietpostbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/mbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/maritimebank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/namabank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/bacabank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/ncbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/ocbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/oceanbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/pbvnbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/pgbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/pvcbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/tmcbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/seabank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/saigonbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/shbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/shbbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/sacombank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/techcombank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/tpbank.png"
							alt="">
					</div>
				</div>
				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/vietabank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/agribank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/vietcombank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/vibbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/vpbank.png"
							alt="">
					</div>
				</div>

				<div class="col col-sm-5 col-lg-4 bank-border rounded-3 m-2 p-0"
					style="max-width: 145px;">
					<!-- ảnh ngân hàng-->
					<div class="text-center">
						<img
							src="${pageContext.request.contextPath }/resources/img/vrbbank.png"
							alt="">
					</div>
				</div>
			</div>

		</div>
		<!-- ------------------------- 2 nút tiếp tục + quay lại ----------------------------->
		<div class="d-flex justify-content-evenly my-4">
			<div>
				<div>
					<a href="${pageContext.request.contextPath }/veXe/datVe/step3">
					<button
						class="btnQuayLai btn btn-light border border-2 rounded-pill">
						<i class="me-3 pt  -1 fas fa-chevron-left"></i>Quay lại
					</button>
					</a>
				</div>
			</div>
			<div>
				<div>
					<button type="submit" form="phuongThucThanhToan"
						class="btnTiepTuc btn btn-danger rounded-pill p-2">
						Thanh Toán<i class="ms-3 pt-1 fas fa-chevron-right"></i>
					</button>
				</div>
			</div>
		</div>
		<!-- ------------------------- End 2 nút tiếp tục + quay lại ----------------------------->
	</div>
	<!------------------- end body ----------------------->
<%@include file="/WEB-INF/views/include/footer.jsp" %>