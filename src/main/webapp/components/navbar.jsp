<%@ page import="com.studentManagement.entity.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>

<%
	User userData = (User) session.getAttribute("userData");
	User selectedUser = (User) session.getAttribute("selectedUser");
%>

	<nav
		class="navbar navbar-main navbar-expand-lg px-0 mx-4 shadow-none border-radius-xl"
		id="navbarBlur" navbar-scroll="true">
		<div class="container-fluid py-1 px-3">
			<div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4"
				id="navbar">

				<div class="ms-md-auto pe-md-3 d-flex align-items-center">
					<div>
						<% if (userData.getRole().equals("admin")) {%>
						<a href="${pageContext.request.contextPath}/UserServlet?action=profile&id=<%=userData.getUser_id()%>">
							Welcome, <%=userData.getFirstName() %>
						</a>
						<%} else {%>
						<a href="#">
							Welcome, <%=userData.getFirstName() %>
						</a>
						<%}%>
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
