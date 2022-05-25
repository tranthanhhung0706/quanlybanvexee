<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
	<!----------------------------------------- body ------------------------------------->

	<!--- Thanh Progresss --->

	<div class="container">
		<h3 class="text-center my-3">
			CHỌN TUYẾN
			</h3>
			<div class="step-wizard">
				<ul class="step-wizard-list">
					<li class="step-wizard-item current-item"><span
						class="progress-count">1</span> <span class="progress-label"><s:message code="ve.chontuyen"/></span></li>
					<li class="step-wizard-item"><span class="progress-count">2</span>
						<span class="progress-label"><s:message code="ve.xacnhanlotrinh"/></span></li>
					<li class="step-wizard-item"><span class="progress-count">3</span>
						<span class="progress-label"><s:message code="ve.thongtinhangkhach"/></span></li>
					<li class="step-wizard-item "><span class="progress-count">4</span>
						<span class="progress-label"><s:message code="ve.thanhtoan"/></span></li>
				</ul>

			</div>
	</div>
	<!--- End Thanh Progress --->
	<div class="container p-0">
		<!-- Form chọn chuyến đi-->
		<div class="card  shadow-lg position-relative rounded-3">
			<div class="card-body">
				<form:form
					action="${pageContext.request.contextPath }/veXe/datVe/step2"
					method="GET" class="position-relative validation-loaive"
					id="formChonVe" novalidate="true" onsubmit="return validateLoaiVeForm()">

					<!-- //////////// loai ve-->
					<div>
						<div class="form-check d-inline-block ms-3">
							<input type="radio" value="motchieu" name="loaive"
								class="form-check-input" id="motchieu"> <label
								class="form-check-label fw-bold" for="motchieu"><s:message code="index.motchieu"/></label>
						</div>

						<div class="form-check d-inline-block ms-3">
							<input type="radio" value="khuhoi" name="loaive"
								class="form-check-input" id="khuhoi" checked> <label
								class="form-check-label fw-bold" for="khuhoi"><s:message code="index.khuhoi"/></label>
						</div>
					</div>

					<div class="row">
						<div class="col-lg-6">
							<div class="row text-center justify-content-center my-3">
								<div class="form-group col-5 p-2 border rounded-3">
									<h6 class="card-title text-muted text-center">
										<label for="chonDiemDi" class="fw-bold"><s:message code="index.diemdi"/></label>
										</h6>
										<select name="chonDiemDi" id="chonDiemDi"
											class="border-0 fw-bold w-100 text-center">
											<c:forEach items="${tatCaDiaDiem }" var="diaDiem">
												<option value="${diaDiem.idDiaDiem }">${diaDiem.tenDiaDiem }</option>
											</c:forEach>


										</select>
								</div>
								<div class="col-1"></div>
								<div class="form-group col-5 p-2 border rounded-3">
									<h6 class="card-title text-muted text-center">
										<label for="chonDiemDen" class="fw-bold"><s:message code="index.diemden"/></label>
									</h6>

									<select name="chonDiemDen"
										class="border-0 fw-bold w-100 text-center" id="chonDiemDen">
										<c:forEach items="${tatCaDiaDiem }" var="diaDiem">
											<option value="${diaDiem.idDiaDiem }">${diaDiem.tenDiaDiem }</option>
										</c:forEach>

									</select>
								</div>
							</div>
						</div>
						<div class="col-lg-6">
							<div class="row text-center justify-content-center my-3">
								<div class="col-5 form-group border rounded-3 p-2">
									<h6 class="card-title text-muted text-center">
										<label for="chonNgayDi" class="fw-bold"><s:message code="index.ngaydi"/></label>
									</h6>

									<input type="date"
										class="rounded-3 border-light expiredDate fw-bold w-100"
										name="chonNgayDi" id="chonNgayDi" required>
									<div class="invalid-feedback"><s:message code="index.chonngaydi"/></div>

								</div>
								<div class="col-1"></div>
								<div class="col-5 form-group border rounded-3 p-2">
									<h6 class="card-title text-muted text-center">
										<label for="chonNgayVe" class="fw-bold"><s:message code="index.ngayve"/></label>
									</h6>

									<input type="date"
										class="rounded-3 border-light expiredDate fw-bold w-100"
										name="chonNgayVe" id="chonNgayVe" required>
									<div class="invalid-feedback"><s:message code="index.chonngayve"/></div>
								</div>
							</div>
						</div>
					</div>


					<button type="submit" id="btnTimXe"
						class="btn btn-primary position-absolute top-100 start-50 start-80 translate-middle-x  border rounded-3">
						<i class="fas fa-search"></i> <s:message code="index.timchuyenxe"/>
					</button>
				</form:form>
			</div>
		</div>
		<!-- End Form chọn chuyến đi -->
	</div>






<%@include file="/WEB-INF/views/include/footer.jsp" %>