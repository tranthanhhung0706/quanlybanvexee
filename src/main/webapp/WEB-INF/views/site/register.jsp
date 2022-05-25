<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/views/include/header.jsp" %>



	<div class="container">
<<<<<<< Updated upstream
		<h3 class="text-center mt-4">THÔNG TIN TÀI KHOẢN</h3>
=======
		<h3 class="text-center mt-4"><s:message code="login.taotk"/></h3>

>>>>>>> Stashed changes
		
		<input id="thanhcong" type="hidden" value="${thanhcong }" onloadeddata="alSignUp()"/>
		<form:form action="site/sign_up.htm" modelAttribute="tks"
			method="POST" class="m-auto mt-5" style="width: 400px;" var="a"> 
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
			<div style="color: red;font-size: large;" id="issuccess">${message }</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="nameInput" class="form-label">Họ tên *</label>
				<input type="text" class="form-control" name="name" oninput="inputHandler('hte')" value="${hoten }"
=======
				<label for="nameInput" class="form-label"><s:message code="login.hoten"/> *</label>

			

				<input type="text" class="form-control" name="name"
>>>>>>> Stashed changes
						/>
					<br />
					<div style="color: red; margin-top: -20px" id="hte">${hotenerror }</div>
			</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="nameInput" class="form-label">Số điện thoại *</label>
				<input type="text" class="form-control" name="sdt"  oninput="inputHandler('sdte')" value="${sdt }"/>
=======
				<label for="nameInput" class="form-label"><s:message code="login.sdt.title"/> *</label>
				<input type="text" class="form-control" name="sdt"/>
>>>>>>> Stashed changes
					<br />
					<div style="color: red; margin-top: -20px" id="sdte" >${sdterror }</div>
			</div>
			<div class="mb-3">
				<label for="nameInput" class="form-label">Email *</label>
				<input type="text" class="form-control" name="email" oninput="inputHandler('merr')"  value="${email }"
						/>
					<br />
					<div style="color: red; margin-top: -20px" id="merr" >${mailerror }</div>
			</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="nameInput" class="form-label">CMND</label>
				<input type="text" class="form-control" name="cmnd" oninput="inputHandler('cmerr')" value="${cmnd }"
=======
				<label for="nameInput" class="form-label"><s:message code="login.cmnd"/></label>
				<input type="text" class="form-control" name="cmnd"
>>>>>>> Stashed changes
						/>
					<br />
					<div style="color: red; margin-top: -20px" id="cmerr" >${cmnderror }</div>
			</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="nameInput" class="form-label">Địa chỉ</label>
				<input type="text" class="form-control" name="diachi" value="${diachi }"
=======
				<label for="nameInput" class="form-label"><s:message code="login.diachi"/></label>
				<input type="text" class="form-control" name="diachi"
>>>>>>> Stashed changes
						/>
					<br />
			</div>
			<div class="mb-3">
				<label for="nameInput" class="form-label"><s:message code="login.gioitinh"/></label>
				<div style="display:flex; align-items: center;">
					<input type="radio" id="nam" name="gioitinh" value="Nam" checked>
					<label for="name" style="margin:0 30px 0 10px" ><s:message code="login.gtnam"/></label><br>
					<input type="radio" id="nu" name="gioitinh" value="Nữ">
					<label style="margin:0 30px 0 10px" for="nu"><s:message code="login.gtnu"/></label><br>
				</div>
				
			</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="nameInput" class="form-label">Ngày sinh</label>
				<input type="date" class="form-control" name="ngaysinh" value="${ngaysinh }"
=======
				<label for="nameInput" class="form-label"><s:message code="login.ngaysinh"/></label>
				<input type="text" class="form-control" name="ngaysinh"
>>>>>>> Stashed changes
						/>
					<br />
			</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="nameInput" class="form-label">Nghề nghiệp</label>
				<input type="text" class="form-control" name="nghenghiep" value="${nghenghiep }"
=======
				<label for="nameInput" class="form-label"><s:message code="login.nghenghiep"/></label>
				<input type="text" class="form-control" name="nghenghiep"
>>>>>>> Stashed changes
						/>

					<br />
			</div>
			<div class="mb-3">
<<<<<<< Updated upstream
				<label for="passwordInput" class="form-label">Mật khẩu *</label>
					<form:input type="password" class="form-control" id="passwordInput" path="password"  oninput="inputHandler('passe')"
=======
				<label for="passwordInput" class="form-label"><s:message code="login.matkhau.title"/> *</label>
					<form:input type="password" class="form-control" id="passwordInput" path="password"
>>>>>>> Stashed changes
						/>
					<br />
					<div style="color: red; margin-top: -20px"  id="passe">${passerror}</div>
			</div>
			<button type="submit" class="btn btn-outline-success float-end"><s:message code="login.dangki"/></button>
               
		</form:form>
	</div>
	<div class="clearfix"></div>

<%@include file="/WEB-INF/views/include/footer.jsp" %>