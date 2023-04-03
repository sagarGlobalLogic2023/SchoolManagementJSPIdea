<%@ page import="com.bookstore.bookstoreservlet.entity.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	User userData = (User) session.getAttribute("userData");
%>

	<nav
		class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl"
		id="navbarBlur" navbar-scroll="true">
		<div class="container-fluid py-1 px-3">
			<%--<nav aria-label="breadcrumb">
				<ol
					class="breadcrumb bg-transparent mb-0 pb-0 pt-1 px-0 me-sm-6 me-5">
					<li class="breadcrumb-item text-sm"><a
						class="opacity-5 text-dark" href="javascript:;">Store</a></li>
					<li class="breadcrumb-item text-sm text-dark active"
						aria-current="page">Billing</li>
				</ol>
				<h6 class="font-weight-bolder mb-0">Billing</h6>
			</nav>--%>
			<div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4"
				id="navbar">

				<div class="ms-md-auto pe-md-3 d-flex align-items-center">
					<% if (!userData.isAdmin()) {%>
					<div class="input-group">
						<span class="input-group-text text-body"><i
							class="fas fa-search" aria-hidden="true"></i></span> <input type="text"
							class="form-control" placeholder="Type here...">
					</div>
					<%}%>
					<div>
						<a href="${pageContext.request.contextPath}/UserServlet?action=profile">
							Welcome, <%=userData.getFirstName() %>
						</a>
					</div>
				</div>
				<% if (userData == null) {%>
				<ul class="navbar-nav  justify-content-end">
					<li class="nav-item d-flex align-items-center"><a
						href="./sign-in.jsp"
						class="nav-link text-body font-weight-bold px-2"> <i
							class="fa fa-sign-in me-sm-1"></i> <span class="d-sm-inline d-none">Sign
								In</span>
					</a></li>
					<li class="nav-item d-flex align-items-center"><a
						href="./sign-up.jsp"
						class="nav-link text-body font-weight-bold px-2"> <i
							class="fa fa-user-plus me-sm-1"></i> <span class="d-sm-inline d-none">Sign
								Up</span>
					</a></li>
				</ul>
				<% } else {%>
				<ul class="navbar-nav  justify-content-end">
					<li class="nav-item d-flex align-items-center"><a
							href="${pageContext.request.contextPath}/UserServlet?action=signOut"
							class="nav-link text-body font-weight-bold px-2"> <i
							class="fa fa-sign-in me-sm-1"></i> <span class="d-sm-inline d-none">Sign
					Out</span>
					</a></li>
				</ul>
				<% }%>
			</div>
		</div>
	</nav>
