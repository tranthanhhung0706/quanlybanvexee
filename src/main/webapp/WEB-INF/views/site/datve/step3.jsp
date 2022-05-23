<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<!--- Thanh Progresss --->

<div class="container">

	<h2 class="text-center mt-3">CHẤP NHẬN ĐIỀU KHOẢN</h2>


	<div class="step-wizard">
		<ul class="step-wizard-list">
			<li class="step-wizard-item"><span class="progress-count">1</span>
				<span class="progress-label">CHỌN TUYẾN</span></li>
			<li class="step-wizard-item"><span class="progress-count">2</span>
				<span class="progress-label">XÁC NHẬN LỘ TRÌNH</span></li>
			<li class="step-wizard-item current-item"><span
				class="progress-count ">3</span> <c:if test="${tk_kh.hoTen !=null }">
					<span class="progress-label"> CHẤP NHẬN ĐIỀU KHOẢN </span>
				</c:if> <c:if test="${tk_nv.hoTen !=null }">
					<span class="progress-label"> THÔNG TIN KHÁCH HÀNG </span>
				</c:if></li>
			<li class="step-wizard-item "><span class="progress-count">4</span>
				<span class="progress-label">THANH TOÁN</span></li>
		</ul>

	</div>
</div>
<!--- End Thanh Progress --->

<!-------------------- body ------------------------->
<div class="container">

	<c:if test="${param.daTonTai == true }">
		<div class="card text-dark bg-warning mb-3 m-auto"
			style="max-width: 33rem;">
			<h5 class="card-header">Số điện thoại này đã từng đăng kí. Bạn
				có muốn đổi thông tin cũ sang thông tin vừa nhập ?</h5>
			<div class="card-body">
				<div class="row">
					<div class="col-6">
						<h6 class="text-center">Thông tin cũ</h6>
						<p>
							Họ và tên: <strong>${tempUser.hoTen }</strong>
						</p>
						<p>
							Số điện thoại: <strong>${tempUser.phoneNumber }</strong>
						</p>
						<p>
							Email: <strong>${tempUser.email }</strong>
						</p>
						<p>
							CMND: <strong>${tempUser.cmnd }</strong>
						</p>
						<p>
							Địa chỉ nhà: <strong>${tempUser.diaChi }</strong>
						</p>
					</div>
					<div class="col-6 border-start border-3">
						<h6 class="text-center">Thông tin mới</h6>
						<p>
							Họ và tên: <strong>${newUser.hoTen }</strong>
						</p>
						<p>
							Số điện thoại: <strong>${newUser.phoneNumber }</strong>
						</p>
						<p>
							Email: <strong>${newUser.email }</strong>
						</p>
						<p>
							CMND: <strong>${newUser.cmnd }</strong>
						</p>
						<p>
							Địa chỉ nhà: <strong>${newUser.diaChi }</strong>
						</p>
					</div>
				</div>
				<div class="d-flex justify-content-end">
					<a href="${pageContext.request.contextPath }/veXe/datVe/step3" class="btn btn-danger m-2">Hủy</a>
					<form:form
						action="site/step4.htm"
						method="POST">
						<input type="hidden" name="doiThongTin" value="false">
						<input type="hidden" name="phoneNumber" value="${tempUser.phoneNumber }">
						<input type="submit" value="Giữ lại thông tin cũ" class="btn btn-secondary m-2">
					</form:form>
					<form:form
						action="site/step4.htm"
						method="POST">
						<input type="hidden" name="doiThongTin" value="true">
						<input type="hidden" name="phoneNumber" value="${tempUser.phoneNumber }">
						<input type="submit" value="Lưu thông tin mới" class="btn btn-primary m-2">
					</form:form>
				</div>
			</div>
		</div>
	</c:if>

	<div class="row">
		<!--  form nhập thông tin người dùng nếu đăng kí offline  -->
		<c:if test="${tk_nv.hoTen !=null }">
			<div class="col-12 col-md-6">
				<form action="site/step4.htm"
					method="POST" id="form12" class="validateKhachHang" novalidate>
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />

					<div class="mb-3">
						<label for="hoTenUser" class="form-label">Họ tên hành
							khách *</label> <input type="text" class="form-control" name="hoTen"
							id="hoTenUser" placeholder="Nhập họ và tên" required />
						<div class="invalid-feedback">Xin vui lòng nhập họ tên hợp
							lệ.</div>
					</div>

					<div class="mb-3">
						<label for="phoneNumberUser" class="form-label">Số điện
							thoại *</label> <input type="text" name="phoneNumber"
							class="form-control" id="phoneNumberUser"
							placeholder="Nhập số điện thoại" required />
						<div class="invalid-feedback">Xin vui lòng nhập số điện
							thoại hợp lệ.</div>
					</div>

					<div class="mb-3">
						<label for="emailUser" class="form-label">Email *</label> <input
							type="email" name="email" class="form-control" id="emailUser"
							placeholder="Nhập email" required />
						<div class="invalid-feedback">Xin vui lòng nhập email hợp
							lệ.</div>
					</div>

					<div class="mb-3">
						<label for="cmndUser" class="form-label">CMND/CCCD *</label> <input
							type="text" name="cmnd" class="form-control" id="cmndUser"
							placeholder="Nhập CMND/CCCD" required />
						<div class="invalid-feedback">Xin vui lòng nhập căn cước
							công dân / chứng minh nhân dân hợp lệ.</div>
					</div>

					<div class="mb-3">
						<label for="addressUser" class="form-label">Địa chỉ nhà *</label>
						<textarea rows="3" name="diaChi" cols="10" class="form-control"
							id="addressUser" required></textarea>
						<div class="invalid-feedback">Xin vui lòng nhập địa chỉ nhà
							hợp lệ.</div>
					</div>

				</form>
			</div>

		</c:if>

		<!--  Điều khoản -->
		<div class="col-12 col-md-6 m-auto">
			<div class="card m-auto" style="max-width: 600px;">
				<div class="card-body">
					<h5 class="card-title text-uppercase text-center"
						style="color: #f15a24;">Điều khoản & Lưu ý</h5>
					<p class="card-text">
						(*) Quý khách vui lòng mang email có chứa mã vé đến văn phòng để
						đổi vé lên xe trước xuất bến ít nhất <span style="color: #f15a24;">60
							phút </span> để chúng tôi trung chuyển.
					</p>
					<p class="card-text">(*) Thông tin hành khách phải chính xác,
						nếu không sẽ không thể lên xe hoặc hủy/đổi vé.</p>
					<p class="card-text">
						(*) Quý khách không được đổi/trả vé vào các ngày Lễ Tết (ngày
						thường quý khách được quyền chuyển đổi hoặc hủy vé <span
							style="color: #f15a24;">một lần</span> duy nhất trước giờ xe chạy
						24 giờ), phí hủy vé 10%.
					</p>
					<p class="card-text">
						(*) Nếu quý khách có nhu cầu trung chuyển, vui lòng liên hệ <br />
						số điện thoại <span style="color: #f15a24;">1900 6067</span> trước
						khi đặt vé. Chúng tôi không đón/trung chuyển tại những địa điểm xe
						trung chuyển không thể tới được.
					</p>
				</div>
			</div>
		</div>

	</div>

	<!-- ------------------------- 2 nút tiếp tục + quay lại ----------------------------->
	<div class="d-flex justify-content-evenly my-4">
		<div>
			<div>
				<a href="${pageContext.request.contextPath }/veXe/datVe/step1">
					<button type="submit" form="ten_form_can_submit"
						class="btnQuayLai btn btn-light border border-2 rounded-pill">
						<i class="me-3 pt  -1 fas fa-chevron-left"></i>Quay lại
					</button>
				</a>

			</div>
		</div>
		<div>
			<div>
				<!-- ///////////////////////////// test đường link qua bước 4 //////////////// -->
				<c:if test="${tk_kh.hoTen !=null }">
					<a href="site/step4.htm">
						<button type="submit" form="ten_form_can_submit"
							class="btnTiepTuc btn btn-danger rounded-pill p-2">
							Tiếp tục<i class="ms-3 pt-1 fas fa-chevron-right"></i>
						</button>
					</a>
				</c:if>
				<c:if test="${tk_nv.hoTen !=null }">
					<button type="submit" form="form12"
						class="btnTiepTuc btn btn-danger rounded-pill p-2">
						Tiếp tục<i class="ms-3 pt-1 fas fa-chevron-right"></i>
					</button>
			   </c:if>
			</div>
		</div>
	</div>
	<!-- ------------------------- End 2 nút tiếp tục + quay lại ----------------------------->
</div>


<!------------------- end body ----------------------->
<%@include file="/WEB-INF/views/include/footer.jsp" %>