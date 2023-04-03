<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>
        Online Book Shop
    </title>
    <%@ include file="./components/allCss.jsp" %>
</head>

<body class="">
<div class="container position-sticky z-index-sticky top-0">
    <div class="row">
        <div class="col-12">
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg blur blur-rounded top-0 z-index-3 shadow position-absolute my-3 py-2 start-0 end-0 mx-4">
                <div class="container-fluid pe-0">
                    <button class="navbar-toggler shadow-none ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#navigation" aria-controls="navigation" aria-expanded="false" aria-label="Toggle navigation">
              <span class="navbar-toggler-icon mt-2">
                <span class="navbar-toggler-bar bar1"></span>
                <span class="navbar-toggler-bar bar2"></span>
                <span class="navbar-toggler-bar bar3"></span>
              </span>
                    </button>
                    <div class="collapse navbar-collapse" id="navigation">
                        <ul class="navbar-nav mx-auto ms-xl-auto">
                            <li class="nav-item">
                                <a class="nav-link d-flex align-items-center me-2 active" aria-current="page" href="../index.jsp">
                                    <i class="fa fa-chart-pie opacity-6 text-dark me-1"></i>
                                    Home
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link me-2" href="pages/sign-up.jsp">
                                    <i class="fas fa-user-circle opacity-6 text-dark me-1"></i>
                                    Sign Up
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- End Navbar -->
        </div>
    </div>
</div>
<main class="main-content  mt-0">
    <section>
        <div class="page-header min-vh-75">
            <div class="container">
                <div class="row">
                    <div class="col-xl-4 col-lg-5 col-md-6 d-flex flex-column mx-auto">
                        <div class="card card-plain mt-8">
                            <div class="card-header pb-0 text-left bg-transparent">
                                <h3 class="font-weight-bolder text-info text-gradient text-center">Welcome back</h3>
                                <p class="mb-0 text-center">Enter your email and password to sign in</p>
                            </div>
                            <c:if test = "${not empty failedMessage}">
                                <p class="text-center text-danger">${failedMessage}</p>
                                <c:remove var="failedMessage" scope="session"/>
                            </c:if>
                            <div class="card-body">
                                <form role="form" method="post" action="${pageContext.request.contextPath}/UserServlet?action=login">
                                    <label>Email</label>
                                    <div class="mb-3">
                                        <input type="email" class="form-control" required placeholder="Email" aria-label="Email" name="email" aria-describedby="email-addon">
                                    </div>
                                    <label>Password</label>
                                    <div class="mb-3">
                                        <input type="password" class="form-control" required placeholder="Password" aria-label="Password" name="password" aria-describedby="password-addon">
                                    </div>
                                    <div class="text-center">
                                        <button type="submit" class="btn bg-gradient-info w-100 mt-4 mb-0">Sign in</button>
                                    </div>
                                </form>
                            </div>
                            <div class="card-footer text-center pt-0 px-lg-2 px-1">
                                <p class="mb-4 text-sm mx-auto">
                                    Don't have an account?
                                    <a href="pages/sign-up.jsp" class="text-info text-gradient font-weight-bold">Sign up</a>
                                </p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="oblique position-absolute top-0 h-100 d-md-block d-none me-n8">
                            <div class="oblique-image bg-cover position-absolute fixed-top ms-auto h-100 z-index-0 ms-n6" style="background-image:url('./assets/img/curved-images/curved6.jpg')"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</main>
</body>

</html>