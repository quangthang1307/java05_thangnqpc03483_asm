<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!doctype html>
<html lang="en">

<!-- Head -->
<head>
<!-- Page Meta Tags-->
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<meta name="keywords" content="">

<%@ include file="/WEB-INF/views/common/style1.jsp"%>

<noscript>
	<style>
/**
          * Reinstate scrolling for non-JS clients
          */
.simplebar-content-wrapper {
	overflow: auto;
}
</style>
</noscript>

<!-- Page Title -->
<title>OldSkool | Bootstrap 5 HTML Template</title>

</head>
<body class=" bg-light">

	<!-- Main Section-->
	<section
		class="mt-0 overflow-hidden  vh-100 d-flex justify-content-center align-items-center p-4">
		<!-- Page Content Goes Here -->

		<!-- Login Form-->
		<div class="col col-md-8 col-lg-6 col-xxl-5">
			<!-- Logo-->
			<a
				class="navbar-brand fw-bold fs-3 flex-shrink-0 order-0 align-self-center justify-content-center d-flex mx-0 px-0"
				href="./index.html">
				<div class="d-flex align-items-center">
					<svg class="f-w-7" xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 77.53 72.26">
						<path
							d="M10.43,54.2h0L0,36.13,10.43,18.06,20.86,0H41.72L10.43,54.2Zm67.1-7.83L73,54.2,68.49,62,45,48.47,31.29,72.26H20.86l-5.22-9L52.15,0H62.58l5.21,9L54.06,32.82,77.53,46.37Z"
							fill="currentColor" fill-rule="evenodd" /></svg>
				</div>
			</a>
			<!-- / Logo-->
			<div class="shadow-xl p-4 p-lg-5 bg-white">
				<h1 class="text-center fs-2 mb-5 fw-bold">Forgotten Password</h1>
				<c:if test="${empty message and empty resetPass}">
				<p class="text-muted">Please enter your username below and we will
					send you a secure code to reset your password.</p>
				<form action="forgotpass" method="POST">
					<div class="form-group">
						<label for="forgot-password" class="form-label">Username</label> <input
							type="text" class="form-control" id="username" name="username"
							placeholder="Username ?" >
					</div>
					<button type="submit" class="btn btn-dark d-block w-100 my-4">Send
						Reset Link</button>
				</form>
				</c:if>
				
				<c:if test="${not empty message and empty resetPass}">
				<p class="text-danger text-center fw-bold">${message }</p>
				<form action="verifyCode" method="POST">
					<div class="form-group">
						<label for="forgot-password" class="form-label">Code</label> <input
							type="text" class="form-control" id="code" name="code"
							placeholder="Code ?" >
					</div>
					<button type="submit" class="btn btn-dark d-block w-100 my-4">Send
						Reset Link</button>
				</form>
				</c:if>
				
				<c:if test="${not empty resetPass}">
				<p class="text-danger text-center fw-bold">${message }</p>
				<form action="confirmPass" method="POST">
					<div class="form-group">
						<label for="forgot-password" class="form-label">New Password</label> <input
							type="password" class="form-control" id="newpassword" name="newpassword"
							placeholder="New password ?" >
					</div>
					<button type="submit" class="btn btn-dark d-block w-100 my-4">Send
						Reset Link</button>
				</form>
				</c:if>
			</div>

		</div>
		<!-- / Login Form-->

		<!-- /Page Content -->
	</section>
	<!-- / Main Section-->


	<!-- Theme JS -->
	<!-- Vendor JS -->
	<script src="js/vendor.bundle.js"></script>

	<!-- Theme JS -->
	<script src="js/theme.bundle.js"></script>
</body>

</html>