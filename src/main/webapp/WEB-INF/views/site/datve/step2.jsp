<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>


	<div class="container">
		<h2 class="text-center mt-3">${tuyenXeCanTim.diaDiemDi.tenDiaDiem}
			- ${tuyenXeCanTim.diaDiemDen.tenDiaDiem}</h2>
		<small class="text-center text-muted d-block">${ngayDi }</small>

		<!--- Thanh Progresss --->
		<div class="step-wizard">
			<ul class="step-wizard-list">
				<li class="step-wizard-item"><span class="progress-count">1</span>
					<span class="progress-label">CHỌN TUYẾN</span></li>
				<li class="step-wizard-item current-item"><span
					class="progress-count">2</span> <span class="progress-label">XÁC
						NHẬN LỘ TRÌNH</span></li>
				<li class="step-wizard-item"><span class="progress-count">3</span>
					<span class="progress-label">CHẤP NHẬN ĐIỀU KHOẢN</span></li>
				<li class="step-wizard-item "><span class="progress-count">4</span>
					<span class="progress-label">THANH TOÁN</span></li>
			</ul>

		</div>
	</div>
	<!--- End Thanh Progress --->
	<div class="container">
		<p class="text-center fs-6">Chọn giờ lên xe đi
			${tuyenXeCanTim.diaDiemDen.tenDiaDiem} từ
			${tuyenXeCanTim.diaDiemDi.tenDiaDiem} phù hợp</p>

		<!--  Cài Foreach ở đây lập qua từng chuyến xe trong chuyenXeCanTim rồi -->
		<c:forEach items="${chuyenXeCanTim }" var="chuyenXe">
			<c:if test="${loaiVe.equals(\"khuhoi\") }">
				<form action = "${pageContext.request.contextPath }/veXe/datVe/step2KhuHoi" method="POST" id="form${chuyenXe.maChuyen }">
			</c:if>
			<c:if test="${loaiVe.equals(\"motchieu\") }">
				<form action = "site/step3/${tk_kh.userId}.htm" method="POST" id="form${chuyenXe.maChuyen }">
			</c:if>
			
				<input type="hidden" name="chuyenXe" value="${chuyenXe.maChuyen }">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<c:if test="${loaiVe != null }">
					<input type="hidden" name="ngayVe" value="${ngayVe }"/>
				</c:if>
				<div class="card m-auto" style="max-width: 600px;"
					id="cardChuyenXeThu${chuyenXe.maChuyen }">
					<div class="card-body p-3">
						<div class="d-flex justify-content-between">
							<h4 class="card-title">
								${hashMapDaXuLiGio.get(chuyenXe.maChuyen).get(0) } <i
									class="fas fa-long-arrow-alt-right text-muted"></i>
								${hashMapDaXuLiGio.get(chuyenXe.maChuyen).get(1)}
							</h4>
							<div>
								<i class="fas fa-tint m-1 text-muted"></i> <i
									class="fas fa-box-tissue m-1 text-muted"></i> <i
									class="fas fa-wifi m-1 text-muted"></i>
							</div>
						</div>
						<div
							class="d-inline-block p-2 border-light bg-light border rounded">
							<small><span
								id="tienVeMoiChocardChuyenXeThu${chuyenXe.maChuyen }">${chuyenXe.maXe.maLoaiXe.tienVeMoiCho}</span>đ
								<i class="fas fa-circle text-muted"></i>
								${chuyenXe.maXe.maLoaiXe.tenLoai} <i
								class="fas fa-circle text-muted"></i> Còn
								${chuyenXe.maXe.maLoaiXe.soCho - hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.get(chuyenXe.maChuyen).size() }
								chỗ </small>

						</div>
						<div class="row">
							<div class="col-9">
								<p class="my-3"">
									<i class=" fas fa-map-marker-alt text-success"></i>
									${tuyenXeCanTim.diaDiemLenXe }
								</p>
								<small class="text-muted d-block pl-3">Xe tuyến:
									${tuyenXeCanTim.soKm }Km - ${tuyenXeCanTim.thoiGianTon } tiếng</small>
								<p class="my-3">
									<i class="fas fa-map-marker-alt text-danger"></i>
									${tuyenXeCanTim.diaDiemXuongXe }
								</p>
							</div>
							<div class="col-3 d-flex align-items-center">
								<div>
									<a href="#collapse${chuyenXe.maChuyen }"
										data-bs-toggle="collapse" id="btnChonGhe${chuyenXe.maChuyen }"
										class="btn btn-outline-danger py-1 px-1 p-lg-2 btnChonGhe">Chọn
										Ghế</a>
								</div>
							</div>

						</div>
					</div>

					<div class="collapse" id="collapse${chuyenXe.maChuyen }">
						<!-- Chọn tầng -->
						<nav>
							<div class="nav nav-tabs row gx-0" id="nav-tab" role="tablist">

								<button class="nav-link active col p-1 px-0 fw-bold "
									id="nav-home-tab" data-bs-toggle="tab"
									data-bs-target="#tangduoi${chuyenXe.maChuyen }" type="button"
									role="tab" aria-controls="nav-home" aria-selected="true">
									<img class="img-fluid"
										src="${pageContext.request.contextPath }/resources/img/stair-down.png"
										alt=""> TẦNG DƯỚI
								</button>

								<button class="nav-link col  p-1 px-0 fw-bold"
									id="nav-profile-tab" data-bs-toggle="tab"
									data-bs-target="#tangtren${chuyenXe.maChuyen }" type="button"
									role="tab" aria-controls="nav-profile" aria-selected="false">
									<img class="img-fluid"
										src="${pageContext.request.contextPath }/resources/img/stair-up.png"
										alt="">TẦNG TRÊN
								</button>

							</div>
						</nav>

						<!-- chọn ghế -->
						<div class="tab-content" id="nav-tabContent">
							<div class="tab-pane fade show active"
								id="tangduoi${chuyenXe.maChuyen }" role="tabpanel"
								aria-labelledby="nav-home-tab">
								<div class="row my-3 px-2 justify-content-center text-center">
									<c:forEach items="${chuyenXe.maXe.gheList }" var="ghe">
										<c:if test="${ghe.maGhe.contains(\"A\") }">
											<div class="col-4 my-3">
												<input type="checkbox" class="btn-check" name="gheCheckBox"
													id="${Integer.toString(chuyenXe.maChuyen).concat(ghe.maGhe) }"
													value="${ghe.maGhe }" autocomplete="off"
													<c:if  test="${hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.get(chuyenXe.maChuyen).contains(ghe) }">
													<c:out value="disabled"></c:out>
												</c:if>>

												<label class="btn btn-outline-primary" style="width: 70px;"
													for="${Integer.toString(chuyenXe.maChuyen).concat(ghe.maGhe) }">${ghe.maGhe }
												</label>
											</div>
										</c:if>
									</c:forEach>

								</div>
								<div class="d-flex justify-content-around text-center my-4">
									<div class="d-flex">
										<div class="d-inline-block border border-primary rounded-2"
											style="width: 20px; height: 20px;"></div>
										<div style="height: 20px;"
											class="d-flex align-items-center ms-1">
											<span class="align-text-top">Trống</span>
										</div>

									</div>

									<div class="d-flex">
										<div class="d-inline-block bg-primary rounded-2"
											style="width: 20px; height: 20px;"></div>
										<div style="height: 20px;"
											class="d-flex align-items-center ms-1">
											<span class="align-text-top">Đang chọn</span>
										</div>

									</div>

									<div class="d-flex">
										<div class="d-inline-block bg-secondary rounded-2"
											style="width: 20px; height: 20px;"></div>
										<div style="height: 20px;"
											class="d-flex align-items-center ms-1">
											<span class="align-text-top">Đã đặt</span>
										</div>

									</div>

								</div>
							</div>
							<div class="tab-pane fade" id="tangtren${chuyenXe.maChuyen }"
								role="tabpanel" aria-labelledby="nav-profile-tab">


								<div class="row my-3 mx-3 justify-content-center text-center">


									<c:forEach items="${chuyenXe.maXe.gheList }" var="ghe">
										<c:if test="${ghe.maGhe.contains(\"B\")}">
											<div class="col-4 my-3">
												<input type="checkbox" class="btn-check" name="gheCheckBox"
													id="${Integer.toString(chuyenXe.maChuyen).concat(ghe.maGhe) }"
													value="${ghe.maGhe }" autocomplete="off"
													<c:if  test="${hashMapChuaListCacGheDaDatTuongUngVoiMoiChuyenXe.get(chuyenXe.maChuyen).contains(ghe) }">
													<c:out value="disabled"></c:out>
												</c:if>>
												<label class="btn btn-outline-primary" style="width: 70px;"
													for="${Integer.toString(chuyenXe.maChuyen).concat(ghe.maGhe) }">${ghe.maGhe }</label>
											</div>
										</c:if>
									</c:forEach>
									<!-- 								
							<!------------------------------------ Trạng thái ghế  ---------------------------------->
									<div class="d-flex justify-content-around text-center my-4">
										<div class="d-flex">
											<div class="d-inline-block border border-primary rounded-2"
												style="width: 20px; height: 20px;"></div>
											<div style="height: 20px;"
												class="d-flex align-items-center ms-1">
												<span class="align-text-top">Trống</span>
											</div>

										</div>

										<div class="d-flex">
											<div class="d-inline-block bg-primary rounded-2"
												style="width: 20px; height: 20px;"></div>
											<div style="height: 20px;"
												class="d-flex align-items-center ms-1">
												<span class="align-text-top">Đang chọn</span>
											</div>

										</div>

										<div class="d-flex">
											<div class="d-inline-block bg-secondary rounded-2"
												style="width: 20px; height: 20px;"></div>
											<div style="height: 20px;"
												class="d-flex align-items-center ms-1">
												<span class="align-text-top">Đã đặt</span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- Tong tien + tiếp tục button-->
							<div class="card-footer">
								<div class="d-flex justify-content-between">
									<div>
										<p id="soGhecardChuyenXeThu${chuyenXe.maChuyen }">
											<span class="text-muted">0 vé:</span>
										</p>
										<p id="tongTiencardChuyenXeThu${chuyenXe.maChuyen }">
											<span class="text-muted">Tổng tiền:</span>
										</p>
									</div>
									<div>
										<div class="mt-3 me-3">

										
											
												<button type="submit" form="form${chuyenXe.maChuyen }"
													class="btn btn-danger rounded-pill" style="width: 150px;">
													Tiếp tục<i class="ms-3 pt-1 fas fa-chevron-right"></i>
												</button>
											

										</div>
									</div>
								</div>
							</div>
						</div>

					</div>
				</div>
				<br /> <br />
			</form>
		</c:forEach>
	</div>

<%@include file="/WEB-INF/views/include/footer.jsp" %>