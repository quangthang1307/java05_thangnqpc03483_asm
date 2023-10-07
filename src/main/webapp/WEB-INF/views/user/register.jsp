<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ include file="/WEB-INF/views/common/taglib.jsp" %>
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

 <%@ include file="/WEB-INF/views/common/style1.jsp" %>

  <!-- Page Title -->
  <title>OldSkool | Bootstrap 5 HTML Template</title>

</head>
<body class=" bg-light">

    <!-- Main Section-->
    <section
        class="mt-0 overflow-hidden d-flex justify-content-center align-items-center p-4">
        <!-- Page Content Goes Here -->

        <!-- Login Form-->
        <div class="col col-md-8 col-lg-6 col-xxl-5">
            <!-- Logo-->
            <a class="navbar-brand fw-bold fs-3 flex-shrink-0 order-0 align-self-center justify-content-center d-flex mx-0 px-0" href="index">
                <div class="d-flex align-items-center">
                    <svg class="f-w-7" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 77.53 72.26"><path d="M10.43,54.2h0L0,36.13,10.43,18.06,20.86,0H41.72L10.43,54.2Zm67.1-7.83L73,54.2,68.49,62,45,48.47,31.29,72.26H20.86l-5.22-9L52.15,0H62.58l5.21,9L54.06,32.82,77.53,46.37Z" fill="currentColor" fill-rule="evenodd"/></svg>
                </div>
            </a>
            <!-- / Logo-->
            <div class="shadow-xl p-4 p-lg-5 bg-white">
                <h1 class="text-center mb-5 fs-2 fw-bold">Open Account</h1>
                <p  class="text-center fw-bold mb-5 text-danger" id="password-mismatch-error" style="min-height: 1.5rem;">${message }</p>
               
                
                <form id="register-form" action="register" method="POST">
                    <div class="form-group">
                      <label class="form-label" for="register-fname">Username</label>
                      <input type="text" class="form-control" name="username" id="register-fname" placeholder="Enter your first name">
                    </div>                    
                    <div class="form-group">
                      <label class="form-label" for="register-password">Password</label>
                      <input type="password" class="form-control" id="register-password" name="password" placeholder="Enter your password">
                    </div>
                    <div class="form-group">
                      <label class="form-label" for="register-password">Confirm Password</label>
                      <input type="password" class="form-control" id="register-confirm-password" name="cfmpassword" placeholder="Confirm your password">
                    </div>
                    <button type="submit" class="btn btn-dark d-block w-100 my-4" id="signup-button">Sign Up</button>
                  </form>
                  <p class="d-block text-center text-muted">Already registered? <a class="text-muted" href="login">Login here.</a></p>
            </div>

        </div>
        <!-- / Login Form-->

        <!-- /Page Content -->
    </section>

	
<script>
    document.addEventListener("DOMContentLoaded", function () {
        const passwordField = document.getElementById("register-password");
        const confirmPasswordField = document.getElementById("register-confirm-password");
        const errorText = document.getElementById("password-mismatch-error");
        const signUpButton = document.getElementById("signup-button"); // Thêm ID cho nút Sign Up

        confirmPasswordField.addEventListener("input", function () {
            if (passwordField.value !== confirmPasswordField.value) {
                errorText.textContent = "Mật khẩu và xác nhận mật khẩu không khớp.";
                signUpButton.disabled = true; // Tắt nút Sign Up
            } else {
                errorText.textContent = "";
                signUpButton.disabled = false; // Bật lại nút Sign Up
            }
        });
    });
</script>

	
	
	<script src="<c:url value='views/user/assets/js/vendor.bundle.js' />"></script>

	<!-- Theme JS -->
	<script src="<c:url value='views/user/assets/js/theme.bundle.js' />"></script>
</body>

</html>      