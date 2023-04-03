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
  <%@ include file="../components/allCss.jsp" %>
</head>

<body class="">
  <!-- Navbar -->
  <nav class="navbar navbar-expand-lg position-absolute top-0 z-index-3 w-100 shadow-none my-3 navbar-transparent mt-4">
    <div class="container">
      <button class="navbar-toggler shadow-none ms-2" type="button" data-bs-toggle="collapse" data-bs-target="#navigation" aria-controls="navigation" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon mt-2">
          <span class="navbar-toggler-bar bar1"></span>
          <span class="navbar-toggler-bar bar2"></span>
          <span class="navbar-toggler-bar bar3"></span>
        </span>
      </button>
      <div class="collapse navbar-collapse" id="navigation">
        <ul class="navbar-nav mx-auto ms-xl-auto ">
          <li class="nav-item">
            <a class="nav-link d-flex align-items-center me-2 active" aria-current="page" href="../index.jsp">
              <i class="fa fa-chart-pie opacity-6  me-1"></i>
              Home
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link me-2" href="sign-in.jsp">
              <i class="fas fa-key opacity-6  me-1"></i>
              Sign In
            </a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <!-- End Navbar -->
  <main class="main-content  mt-0">
    <section class="min-vh-100 mb-4">
      <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg" style="background-image: url('../assets/img/curved-images/curved14.jpg');">
        <span class="mask bg-gradient-dark opacity-6"></span>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-5 text-center mx-auto">
              <h1 class="text-white mb-2 mt-5">Welcome!</h1>
              <p class="text-lead text-white">Create an account to use this fantastic book service.</p>
            </div>
          </div>
        </div>
      </div>
      <div class="container">
        <div class="row mt-lg-n10 mt-md-n11 mt-n10">
          <div class="col-xl-4 col-lg-5 col-md-7 mx-auto">
            <div class="card z-index-0">
              <div class="card-header text-center pt-4">
                <h5>Register</h5>
                <c:if test = "${not empty successMessage}">
                  <p class="text-center text-success">${successMessage}</p>
                  <c:remove var="successMessage" scope="session"/>
                </c:if>
                <c:if test = "${not empty failedMessage}">
                  <p class="text-center text-danger">${failedMessage}</p>
                  <c:remove var="failedMessage" scope="session"/>
                </c:if>
              </div>
              <div class="card-body">
                <form role="form text-left" method="post" action="${pageContext.request.contextPath}/UserServlet?action=register">
                  <div class="mb-3 row">
                    <div class="col-6">
                      <input type="text" class="form-control" placeholder="First Name" name="firstName" aria-label="Name" aria-describedby="email-addon" required>
                    </div>
                    <div class="col-6">
                      <input type="text" class="form-control" placeholder="Last Name" name="lastName" aria-label="Name" aria-describedby="email-addon" required>
                    </div>
                  </div>
                  <div class="mb-3">
                    <input type="email" class="form-control" placeholder="Email" name="email" aria-label="Email" aria-describedby="email-addon" required>
                  </div>
                  <div class="mb-3">
                    <input type="password" class="form-control" placeholder="Password" name="password" aria-label="Password" aria-describedby="password-addon" required>
                  </div>
                  <div class="form-check form-check-info text-left">
                    <input class="form-check-input" type="checkbox" id="flexCheckDefault" name="isAdmin">
                    <label class="form-check-label" for="flexCheckDefault">
                      Are you an <a href="javascript:;" class="text-dark font-weight-bolder">Admin?</a>
                    </label>
                  </div>
                  <div class="text-center">
                    <button type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2">Sign up</button>
                  </div>
                  <p class="text-sm mt-3 mb-0">Already have an account? <a href="sign-in.jsp" class="text-dark font-weight-bolder">Sign in</a></p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
  </main>
</body>

</html>