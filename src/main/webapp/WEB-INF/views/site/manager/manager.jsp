<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
%>
<%@ taglib prefix="form"
	uri="http://www.springframework.org/tags/form"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://kit.fontawesome.com/d6bdcb3119.js"
	crossorigin="anonymous"
></script>

<link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />
<link rel="stylesheet" href="<c:url value='/resources/css/custom_bootstrap.css' />" />
<title>Manager</title>
</head>
<body class="ml-20vw px-4">
	<!-- Menu -->
	<div class="menu pt-3 px-4 d-flex flex-column gap-3">
		<a
			href="${pageContext.request.contextPath }/quanly/.htm?isShowList=true"
			class="item"
		>
			<i class="fas fa-users"></i>
			Quản Lý Nhân Viên
		</a>
		<div class="quan-ly-tuyen-xe">
			<div class="item d-flex flex-row justify-content-between"
				role="button"
			>
				<div>
					<i class="fas fa-bus"></i>
					Tuyến Xe
				</div>
				<i class="fas fa-caret-down"></i>
			</div>
			<ul class="list">
				<li>
					<a
						href="${pageContext.request.contextPath }/quanly/.htm?isQuanLyTuyenXe=true"
					>Quản Lý Tuyến Xe</a>
				</li>
				<li>
					<a href="${pageContext.request.contextPath }/quanly/.htm?isQuanLyDiaDiem=true">Quản Lý Địa Điểm</a>
				</li>
			</ul>
			<form:form id="form2"
				action="${pageContext.request.contextPath }/quanly/logout.htm" method="POST"
			>
				<button type="submit" class="dropdown-item" form="form2">
					<i class="fas fa-sign-out-alt"></i>
					Đăng xuất
				</button>
			</form:form>
		</div>
	</div>
	<!-- -------------------------------------- QUẢN LÝ NHÂN VIÊN -------------------------------------- -->
	<c:if test="${param.isShowList }">
		<div>
			<h1 class="text-center">Quản Lý Nhân Viên</h1>
			<a href="${pageContext.request.contextPath }/quanly/.htm?isThemNV=true"
				class="d-inline-block btn btn-success btn--them-nv"
			>Thêm Nhân Viên</a>
			<table
				class="table table-warning table-hover mx-auto shadow-sm border nv-table"
			>
				<thead>
					<tr>
						<th scope="col">ID</th>
						<th scope="col">Họ tên</th>
						<th scope="col">Số điện thoại</th>
						<th scope="col">Email</th>
						<th scope="col">Hành động</th>
					</tr>
				</thead>
				<tbody>
					<!-- Lặp forEach ở đây, id thì in đậm -->
					<c:forEach items="${nvList }" var="nv">
						<tr>
							<th style="padding-top: 15px">${nv.idNhanVien }</th>
							<td style="padding-top: 15px">${nv.hoTen }</td>
							<td style="padding-top: 15px">${nv.soDienThoai }</td>
							<td style="padding-top: 15px">${nv.email }</td>
							<td>
								<form:form action="xoaNV.htm" method="POST"
									onsubmit="return confirm('Bạn chắn chắc muốn xóa nhân viên có ID=${nv.idNhanVien }');"
								>
									<input type="hidden" name="idNV" value="${nv.idNhanVien }" />
									<button type="submit" class="btn btn-danger">Xóa</button>
								</form:form>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<!-- -------------------------------------- THÊM NHÂN VIÊN -------------------------------------- -->
	<c:if test="${param.isThemNV }">
		<div>
			<h3 class="text-center my-4">Thêm Nhân Viên</h3>
			<c:if test="${param.tontaiSDT }">
				<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
  <symbol id="exclamation-triangle-fill" fill="currentColor"
						viewBox="0 0 16 16"
					>
    <path
						d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"
					/>
  </symbol>
</svg>
				<div class="alert alert-danger d-flex align-items-center mx-auto"
					style="max-width: 500px" role="alert"
				>
					<svg class="bi flex-shrink-0 me-2" width="24" height="24"
						role="img" aria-label="Danger:"
					>
					<use xlink:href="#exclamation-triangle-fill" /></svg>
					<div>Số điện thoại đã có tài khoản.</div>
				</div>
			</c:if>
			<form:form action="themNV.htm" modelAttribute="nhanVien" method="POST"
				cssClass="infoValidation" novalidate="true"
			>
				<div class="row my-5">
					<div class="col">
						<div class="card m-auto " style="max-width: 800px;">
							<div class="card-header text-muted">THÔNG TIN CÁ NHÂN</div>
							<div class="card-body">
								<div class="row mb-3">
									<label for="inputName" class="col-sm-3 col-form-label">Họ
										và tên:</label>
									<div class="col-sm-9">
										<form:input path="hoTen" cssClass="form-control"
											id="inputName" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập họ
											tên.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputEmail" class="col-sm-3 col-form-label">Email:</label>
									<div class="col-sm-9">
										<form:input path="email" type="email"
											cssClass="form-control" id="inputEmail" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập email.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputPhone" class="col-sm-3 col-form-label">Số
										điện thoại:</label>
									<div class="col-sm-9">
										<form:input path="soDienThoai" cssClass="form-control"
											id="inputPhone" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập số
											điện thoại hợp lệ.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputIdno" class="col-sm-3 col-form-label">CMND/CCCD:</label>
									<div class="col-sm-9">
										<form:input path="cmnd" cssClass="form-control"
											id="inputIdno" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập số
											chứng minh nhân dân / căn cước công dân.</div>
									</div>
								</div>
								<!-- Giới tính -->
								<div class="row my-4">
									<form:label path="gioiTinh">Giới tính:</form:label>
									<div class="col-sm-9 mt-2">
										<div class="form-check form-check-inline">
											<form:radiobutton path="gioiTinh" value="Nam" label="Nam"
												cssClass="form-check-input" required="true"
											/>
										</div>
										<div class="form-check form-check-inline">
											<form:radiobutton path="gioiTinh" value="Nữ" label="Nữ"
												cssClass="form-check-input" required="true"
											/>
										</div>
									</div>
								</div>
								<!-- Mật khẩu -->
								<div class="row mb-3">
									<label for="password" class="col-sm-3 col-form-label">Password:</label>
									<div class="col-sm-9">
										<input type="password" name="password" class="form-control"
											id="inputEmail" required
										/>
										<div class="invalid-feedback">Xin vui lòng nhập mật
											khẩu.</div>
									</div>
								</div>
								<!--   ngày sinh giữ nguyên không làm form:input  -->
								<div class="row my-4">
									<div class="col-sm-3 col-form-label">Ngày sinh:</div>
									<div class="col-sm-9 row justify-content-around">
										<input type="text" name="dd" id="" placeholder="Ngày"
											class="form-control" style="width: 100px;"
											value="${nhanVien.ngaySinh.substring(8) }" required
										>
										<input type="text" name="mm" id="" placeholder="Tháng"
											class="form-control" style="width: 100px;"
											value="${nhanVien.ngaySinh.substring(5,7)}" required
										>
										<input type="text" name="yyyy" id="" placeholder="Năm"
											class="form-control" style="width: 100px;"
											value="${nhanVien.ngaySinh.substring(0,4)}" required
										>
										<div class="invalid-feedback">Xin vui lòng nhập ngày,
											tháng, năm sinh hợp lệ.</div>
									</div>
								</div>
								<!--   ngày bắt đầu làm việc giữ nguyên không làm form:input  -->
								<div class="row my-4">
									<div class="col-sm-3 col-form-label">Ngày bắt đầu làm
										việc:</div>
									<div class="col-sm-9 row justify-content-around">
										<input type="text" name="bdlv-dd" id="" placeholder="Ngày"
											class="form-control" style="width: 100px;" required
										>
										<input type="text" name="bdlv-mm" id="" placeholder="Tháng"
											class="form-control" style="width: 100px;" required
										>
										<input type="text" name="bdlv-yyyy" id="" placeholder="Năm"
											class="form-control" style="width: 100px;" required
										>
										<div class="invalid-feedback">Xin vui lòng nhập ngày,
											tháng, năm bắt đầu làm việc hợp lệ.</div>
									</div>
								</div>
								<div class="row mt-5">
									<div class="col-8"></div>
									<div class="col-4">
										<input type="submit"
											class="my-4 mt-md-0 btn btn-success d-block"
											style="width: 200px;" value="Lưu Thông Tin"
										/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</c:if>
	<!-- -------------------------------------- QUẢN LÝ TUYẾN XE -------------------------------------- -->
	<c:if test="${param.isQuanLyTuyenXe }">
		<div>
			<h1 class="text-center">Quản Lý Tuyến Xe</h1>
			<c:if test="${param.tonTaiChuyenXe }">
				<div class="d-flex justify-content-center my-3">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert"
					>
						<strong>Tuyến xe này đã tồn tại chuyến xe đi nên không
							thể xóa.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			<c:if test="${param.xoaTuyenXeThanhCong }">
				<div class="d-flex justify-content-center my-3">
					<div class="alert alert-success alert-dismissible fade show"
						role="alert"
					>
						<strong>Xóa tuyến xe thành công.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			<c:if test="${param.daBiThanhToan }">
				<div class="d-flex justify-content-center my-3">
					<div class="alert alert-warning alert-dismissible fade show"
						role="alert"
					>
						<strong>Tuyến xe này đã có chuyến xe đã bị đặt vé nên
							không thể sửa.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			<div class="text-end">
				<a
					href="${pageContext.request.contextPath }/quanly/.htm?isThemTuyenXe=true"
					class="d-inline-block btn btn-success"
				>Thêm Tuyến Xe</a>
			</div>
			<div class="container mt-3">
				<div class="row head fw-bold ">
					<div class="col-1 p-0  text-center border py-3">ID</div>
					<div class="col p-0  text-center border py-3">Tuyến đi</div>
					<div class="col-2 p-0  text-center border py-3 ">Địa điểm
						đi</div>
					<div class="col-2 p-0  text-center border py-3">Địa điểm
						đến</div>
					<div class="col-1 p-0  text-center border py-3">Km</div>
					<div class="col-1 p-0  text-center border py-3">Thời gian</div>
					<div class="col p-0  text-center border py-3">Action</div>
				</div>
				<div class="grid-scroll">
					<c:forEach items="${tuyenXeList }" var="tx">
						<div class="row">
							<div class="col-1 p-0  text-center border py-3">${tx.idTuyen }</div>
							<div class="col p-0  text-center border py-3">${tx.diaDiemDi.tenDiaDiem }
								→ ${tx.diaDiemDen.tenDiaDiem }</div>
							<div class="col-2 p-0  text-center border py-3 ">${tx.diaDiemLenXe }</div>
							<div class="col-2 p-0  text-center border py-3">${tx.diaDiemXuongXe }</div>
							<div class="col-1 p-0  text-center border py-3">${tx.soKm }</div>
							<div class="col-1 p-0  text-center border py-3">${tx.thoiGianTon }</div>
							<div class="col p-0  text-center border py-3">
								<form:form cssClass="d-inline-block" action="xuLyTuyenXe.htm"
									method="POST"
									onsubmit="return confirm('Bạn chắn chắc muốn chỉnh sửa tuyến xe có ID=${tx.idTuyen }');"
								>
									<input type="hidden" name="idTuyenXe" value="${tx.idTuyen }" />
									<button type="submit" name="updateBtn"
										class="btn btn-info fw-bold"
									>Update</button>
								</form:form>
								<form:form cssClass="d-inline-block" action="xuLyTuyenXe.htm"
									method="POST"
									onsubmit="return confirm('Bạn chắn chắc muốn xóa tuyến xe có ID=${tx.idTuyen }');"
								>
									<input type="hidden" name="idTuyenXe" value="${tx.idTuyen }" />
									<button name="deleteBtn" class="btn btn-danger fw-bold">Delete</button>
								</form:form>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</c:if>
	<!-- -------------------------------------- THÊM VÀ CHỈNH SỬA TUYẾN XE -------------------------------------- -->
	<c:if test="${param.isThemTuyenXe || isUpdateTuyenXe }">
		<div>
			<c:if test="${param.isThemTuyenXe }">
				<h3 class="text-center my-4">Thêm Tuyến Xe</h3>
			</c:if>
			<c:if test="${isUpdateTuyenXe }">
				<h3 class="text-center my-4">Chỉnh sửa Tuyến Xe</h3>
			</c:if>
			
			<c:if test="${param.tonTaiTuyenXe || tonTaiTuyenXe }">
				<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
			<symbol id="exclamation-triangle-fill" fill="currentColor"
						viewBox="0 0 16 16"
					>
    <path
						d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"
					/>
  </symbol>
  </svg>
				<div class="alert alert-danger d-flex align-items-center mx-auto"
					style="max-width: 500px" role="alert"
				>
					<svg class="bi flex-shrink-0 me-2" width="24" height="24"
						role="img" aria-label="Danger:"
					>
					<use xlink:href="#exclamation-triangle-fill" /></svg>
					<div>Tuyến xe tương ứng với điểm đi, điểm đến này đã tồn
						tại.</div>
				</div>
			</c:if>
			<c:if test="${param.diaDiemKhongTonTai}">
				<svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
  
  <symbol id="exclamation-triangle-fill" fill="currentColor"
						viewBox="0 0 16 16"
					>
    <path
						d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"
					/>
  </symbol>
</svg>
				<div class="alert alert-danger d-flex align-items-center mx-auto"
					style="max-width: 500px" role="alert"
				>
					<svg class="bi flex-shrink-0 me-2" width="24" height="24"
						role="img" aria-label="Danger:"
					>
					<use xlink:href="#exclamation-triangle-fill" /></svg>
					<div>Điểm đi hoặc điểm đến không có trong dữ liệu.</div>
				</div>
			</c:if>
			<form:form action="xuLyTuyenXe.htm" modelAttribute="tuyenXe"
				method="POST" cssClass="infoValidation" novalidate="true"
				onsubmit="return validateThemTuyenXeForm()"
			>
				<div class="row my-5">
					<div class="col">
						<div class="card m-auto " style="max-width: 800px;">
							<div class="card-header text-muted">THÔNG TIN TUYẾN XE</div>
							<div class="card-body">
								<div class="row mb-3">
								<c:if test="${isUpdateTuyenXe }">
									<input type="hidden" name="idTuyenXe" value="${tuyenXe.idTuyen }" />
									<input type="hidden" name="diaDiemDiCu" value="${tuyenXe.diaDiemDi.tenDiaDiem }">
									<input type="hidden" name="diaDiemDenCu" value="${tuyenXe.diaDiemDen.tenDiaDiem }">
								</c:if>
								
									<label for="diaDiemDi" class="col-sm-3 col-form-label">Địa
										điểm đi:</label>
									<div class="col-sm-9">
										<input class="form-control" required list="diPlaces"
											name="diaDiemDi" id="diaDiemDi"
										>
										<datalist id="diPlaces">
											<c:forEach items="${places }" var="diaDiem">
												<option value="${diaDiem.tenDiaDiem }">
											</c:forEach>
										</datalist>
										<div class="invalid-feedback">Xin vui lòng chọn địa
											điểm đi.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="diaDiemDen" class="col-sm-3 col-form-label">Địa
										điểm đến:</label>
									<div class="col-sm-9">
										<input class="form-control" required list="denPlaces"
											name="diaDiemDen" id="diaDiemDen"
										>
										<datalist id="denPlaces">
											<c:forEach items="${places }" var="diaDiem">
												<option value="${diaDiem.tenDiaDiem }">
											</c:forEach>
										</datalist>
										<div class="invalid-feedback">Xin vui lòng chọn địa
											điểm đến.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputDiaDiemLenXe"
										class="col-sm-3 col-form-label"
									>Địa điểm lên xe:</label>
									<div class="col-sm-9">
										<form:input path="diaDiemLenXe" cssClass="form-control"
											id="inputDiaDiemLenXe" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập địa
											điểm lên xe hợp lệ.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputDiaDiemXuongXe"
										class="col-sm-3 col-form-label"
									>Địa điểm xuống xe:</label>
									<div class="col-sm-9">
										<form:input path="diaDiemXuongXe" cssClass="form-control"
											id="inputDiaDiemXuongXe" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập địa
											điểm xuống xe hợp lệ.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputSoKM" class="col-sm-3 col-form-label">Số
										KM:</label>
									<div class="col-sm-9">
										<form:input path="soKm" cssClass="form-control"
											id="inputSoKM" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập khoảng
											Km hợp lệ.</div>
									</div>
								</div>
								<div class="row mb-3">
									<label for="inputThoiGianTon" class="col-sm-3 col-form-label">Thời
										gian tốn:</label>
									<div class="col-sm-9">
										<form:input path="thoiGianTon" cssClass="form-control"
											id="inputThoiGianTon" required="true"
										/>
										<div class="invalid-feedback">Xin vui lòng nhập khoảng
											thời gian tốn cho chuyến đi.</div>
									</div>
								</div>
								<div class="row mt-5">
									<div class="col-8"></div>
									<c:if test="${param.isThemTuyenXe }">
										<div class="col-4">
										<input type="submit" name="add"
											class="my-4 mt-md-0 btn btn-success d-block"
											style="width: 200px;" value="Lưu Thông Tin"
										/>
										</div>
									</c:if>
									<c:if test="${isUpdateTuyenXe }">
										<div class="col-4">
										<input type="submit" name="update"
											class="my-4 mt-md-0 btn btn-success d-block"
											style="width: 200px;" value="Lưu Thông Tin"
										/>
										</div>
									</c:if>
									
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</c:if>
	<!-- -------------------------------------- QUẢN LÝ ĐỊA ĐIỂM ----------------------------- -->
	<c:if test="${param.isQuanLyDiaDiem }">
		<div>
			<h1 class="text-center">Quản Lý Địa Điểm</h1>
			
			<c:if test="${param.diaDiemTonTaiTuyen }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert"
					>
						<strong>Địa điểm này đã có tuyến xe chạy nên không thể xóa 😢.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<c:if test="${param.xoaDiaDiem }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-success alert-dismissible fade show"
						role="alert"
					>
						<strong>Xóa địa điểm thành công 😊.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<c:if test="${param.tonTaiVe }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert"
					>
						<strong>Chuyến xe mà đi, đến địa điểm này đã bị khách hàng đặt vé nên không thể đổi 😢.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<div class="text-end">
				<a
					href="${pageContext.request.contextPath }/quanly/.htm?isThemDiaDiem=true";
					class="d-inline-block btn btn-success"
				>Thêm Địa Điểm</a>
			</div>
			<div class="container mt-3" style="max-width: 500px">
				<div class="row head fw-bold ">
					<div class="col-2 p-0  text-center border py-3">ID</div>
					<div class="col-5 p-0  text-center border py-3">Tên địa điểm</div>
					<div class="col p-0  text-center border py-3">Action</div>
				</div>
				<div class="grid-scroll">
					<c:forEach items="${places }" var="place">
						<div class="row">
							<div class="col-2 p-0  text-center border py-3">${place.idDiaDiem }</div>
							<div class="col-5 p-0  text-center border py-3">
							${place.tenDiaDiem }
							</div>
							
							<div class="col p-0  text-center border py-3">
								<form:form cssClass="d-inline-block" action="xuLyDiaDiem.htm"
									method="POST"
									onsubmit="return confirm('Bạn chắn chắc muốn chỉnh sửa địa điểm có ID=${place.idDiaDiem }');"
								>
									<input type="hidden" name="idDiaDiem" value="${place.idDiaDiem }" />
									<button type="submit" name="updateDiaDiemBtn"
										class="btn btn-info fw-bold"
									>Update</button>
								</form:form>
								<form:form cssClass="d-inline-block" action="xuLyDiaDiem.htm"
									method="POST"
									onsubmit="return confirm('Bạn chắn chắc muốn xóa địa điểm có ID=${place.idDiaDiem }');"
								>
									<input type="hidden" name="idDiaDiem" value="${place.idDiaDiem }" />
									<button name="deleteDiaDiemBtn" class="btn btn-danger fw-bold">Delete</button>
								</form:form>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</c:if>
	<!-- -------------------------------------- THÊM ĐỊA ĐIỂM ----------------------------- -->
	
	<c:if test="${param.isThemDiaDiem }">
		<div class="container">
			<h1 class="text-center">Thêm Địa Điểm</h1>
			
			<c:if test="${param.tonTaiDiaDiem }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert"
					>
						<strong>Tên địa điểm đã tồn tại 😢.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<c:if test="${param.themDiaDiemThanhCong }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-success alert-dismissible fade show"
						role="alert"
					>
						<strong>Thêm địa điểm thành công 😊.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<form:form action="themDiaDiem.htm" modelAttribute="diaDiem"
				method="POST" cssClass="infoValidation" novalidate="true"
				onsubmit="return validateThemDiaDiemForm()"
			>
				<div class="row my-5">
					<div class="col">
						<div class="card m-auto " style="max-width: 800px;">
							<div class="card-header text-muted text-center">THÔNG TIN ĐỊA ĐIỂM</div>
							<div class="card-body">
								<div class="row mb-3">
									<label for="inputTenDiaDiem" class="col-sm-3 col-form-label">Tên địa điểm:</label>
									<div class="col-sm-9">
										<form:input path="tenDiaDiem" cssClass="form-control"
											id="inputTenDiaDiem" required="true"
										/>
										<div class="invalid-feedback">Tên địa điểm không được để trống.</div>
									</div>
								
								</div>
								<div class="row mt-2">
									<div class="col-8"></div>
									<div class="col-4 text-end">
										<input type="submit"
											class="my-2 mt-md-0 btn btn-success"
											style="width: 200px;" value="Lưu Thông Tin"
										/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</c:if>
	
	<!-- -------------------------------------- UPDATE ĐỊA ĐIỂM ----------------------------- -->
	
	<c:if test="${DuDKChinhSuaDD }">
		<div class="container">
			<h1 class="text-center">Sửa Địa Điểm</h1>
			
			<c:if test="${param.tonTaiDiaDiem }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert"
					>
						<strong>Tên địa điểm đã tồn tại 😢.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<c:if test="${param.thayDoiDiaDiemThanhCong }">
				<div class="d-flex justify-content-center mt-4">
					<div class="alert alert-success alert-dismissible fade show"
						role="alert"
					>
						<strong>Thay đổi địa điểm thành công 😊.</strong>
						<button type="button" class="btn-close" data-bs-dismiss="alert"
							aria-label="Close"
						></button>
					</div>
				</div>
			</c:if>
			
			<form:form action="updateDiaDiem.htm" modelAttribute="diaDiem"
				method="POST" cssClass="infoValidation" novalidate="true"
				onsubmit="return validateThemDiaDiemForm()"
			>
				<div class="row my-5">
					<div class="col">
						<div class="card m-auto " style="max-width: 800px;">
							<div class="card-header text-muted text-center">THÔNG TIN ĐỊA ĐIỂM</div>
							<div class="card-body">
								<div class="row mb-3">
									<label for="inputTenDiaDiem" class="col-sm-3 col-form-label">Tên địa điểm:</label>
									<div class="col-sm-9">
										<form:hidden path="idDiaDiem" />
										<form:input path="tenDiaDiem" cssClass="form-control"
											id="inputTenDiaDiem" required="true"
										/>
										<div class="invalid-feedback">Tên địa điểm không được để trống.</div>
									</div>
								
								</div>
								<div class="row mt-2">
									<div class="col-8"></div>
									<div class="col-4 text-end">
										<input type="submit"
											class="my-2 mt-md-0 btn btn-success"
											style="width: 200px;" value="Lưu Thông Tin"
										/>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>
		</div>
	</c:if>
	<script type="text/javascript"
	src="<c:url value='/resources/js/my_script.js' />"></script>
    <script type="text/javascript"
	src="<c:url value='/resources/js/validator.js' />"></script>
     <script type="text/javascript"
	src="<c:url value='/resources/js/jquery-3.6.0.slim.min.js' />"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.5/dist/umd/popper.min.js" integrity="sha384-Xe+8cL9oJa6tN/veChSP7q+mnSPaj5Bcu9mPX5F5xIGE0DVittaqT5lorf0EI7Vk" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.min.js" integrity="sha384-kjU+l4N0Yf4ZOJErLsIcvOU2qSb74wXpOhqTvwVx3OElZRweTnQ6d31fXEoRD1Jy" crossorigin="anonymous"></script>

</body>
</html>