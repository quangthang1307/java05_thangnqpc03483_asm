<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!-- Navbar -->
<!-- Navbar -->
<nav
	class="navbar navbar-expand-lg navbar-light bg-white flex-column border-0 fixed-top">
	<div class="container-fluid">
		<div class="w-100">
			<div
				class="d-flex justify-content-between align-items-center flex-wrap">
				<!-- Logo-->
				<a class="navbar-brand fw-bold fs-3 m-0 p-0 flex-shrink-0 order-0"
					href="index">
					<div class="d-flex align-items-center">
						<svg class="f-w-7" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 77.53 72.26">
								<path
								d="M10.43,54.2h0L0,36.13,10.43,18.06,20.86,0H41.72L10.43,54.2Zm67.1-7.83L73,54.2,68.49,62,45,48.47,31.29,72.26H20.86l-5.22-9L52.15,0H62.58l5.21,9L54.06,32.82,77.53,46.37Z"
								fill="currentColor" fill-rule="evenodd" />
							</svg>
					</div>
				</a>
				<!-- / Logo-->

				<!-- Navbar Icons-->
				<ul
					class="list-unstyled mb-0 d-flex align-items-center order-1 order-lg-2 nav-sidelinks">

					<!-- Mobile Nav Toggler-->
					<li class="d-lg-none"><span
						class="nav-link text-body d-flex align-items-center cursor-pointer"
						data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown"
						aria-controls="navbarNavDropdown" aria-expanded="false"
						aria-label="Toggle navigation"><i
							class="ri-menu-line ri-lg me-1"></i> Menu</span></li>
					<!-- /Mobile Nav Toggler-->

					<!-- Navbar Search-->
					<li class="d-none d-sm-block"><span
						class="nav-link text-body search-trigger cursor-pointer">Search</span>

						<!-- Search navbar overlay-->
						<div class="navbar-search d-none">
							<div class="input-group mb-3 h-100">
								<span class="input-group-text px-4 bg-transparent border-0"><i
									class="ri-search-line ri-lg"></i></span> <input type="text"
									class="form-control text-body bg-transparent border-0"
									placeholder="Enter your search terms..."> <span
									class="input-group-text px-4 cursor-pointer disable-child-pointer close-search bg-transparent border-0"><i
									class="ri-close-circle-line ri-lg"></i></span>
							</div>
						</div>
						<div class="search-overlay"></div> <!-- / Search navbar overlay-->

					</li>
					<!-- /Navbar Search-->

				</ul>
				<!-- Navbar Icons-->

				<!-- Main Navigation-->
				<div
					class="flex-shrink-0 collapse navbar-collapse navbar-collapse-light w-auto flex-grow-1 order-2 order-lg-1"
					id="navbarNavDropdown">

					<!-- Menu-->
					<ul class="navbar-nav mx-auto mb-2 mb-lg-0">
						<li class="nav-item dropdown dropdown position-static"><a
							class="nav-link dropdown-toggle" href="#" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> Men </a></li>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> Women </a></li>
						<li class="nav-item"><a class="nav-link"
							href="./category.html" role="button"> Kids </a></li>
						<li class="nav-item"><a class="nav-link" href="/showproductsuser"
							role="button"> Sale </a></li>
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" role="button"
							data-bs-toggle="dropdown" aria-expanded="false"> Function </a>
							<ul class="dropdown-menu">
								<c:choose>
									<c:when test="${not empty sessionID}">
										<li><a class="dropdown-item" href="logout">Logout</a></li>
									</c:when>
									<c:otherwise>
										<li><a class="dropdown-item" href=login>Login</a></li>
										<li><a class="dropdown-item" href="register">Register</a></li>
									</c:otherwise>
								</c:choose>
								<li><a class="dropdown-item"
									href="forgotpass">Forgotten Password</a></li>
							</ul></li>
						<li class="nav-item"><a class="nav-link" role="button" href="editprofile">
								Hello ${sessionID.username } </a></li>
					</ul>
					<!-- / Menu-->

				</div>
				<!-- / Main Navigation-->
			</div>
		</div>
	</div>
</nav>
<!-- / Navbar-->
<!-- / Navbar-->