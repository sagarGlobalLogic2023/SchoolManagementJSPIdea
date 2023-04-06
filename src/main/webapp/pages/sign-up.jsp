<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>
    School
  </title>
  <%@ include file="../components/allCss.jsp" %>
</head>

<body class="">
  <main class="main-content  mt-0">
    <section class="min-vh-100 mb-4">
      <div class="page-header align-items-start min-vh-50 pt-5 pb-11 m-3 border-radius-lg" style="background-image: url('./assets/img/curved-images/curved14.jpg');">
        <span class="mask bg-gradient-dark opacity-6"></span>
        <div class="container">
          <div class="row justify-content-center">
            <div class="col-lg-5 text-center mx-auto">
              <h1 class="text-white mb-2 mt-5">Welcome!</h1>
              <p class="text-lead text-white">Create an account to use this fantastic school service.</p>
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
                  <div class="row">
                    <div class="col-4">
                      <div class="form-check ">
                        <input class="form-check-input" type="radio" id="customRadio1" name="role" value="student">
                        <label class="custom-control-label" for="customRadio1">Student</label>
                      </div>
                    </div>
                    <div class="col-4">
                      <div class="form-check ">
                        <input class="form-check-input" type="radio" id="customRadio2" name="role" value="teacher">
                        <label class="custom-control-label" for="customRadio2">Teacher</label>
                      </div>
                    </div>
                    <div class="col-4">
                      <div class="form-check ">
                        <input class="form-check-input" type="radio"id="customRadio3" name="role" value="admin">
                        <label class="custom-control-label" for="customRadio3">Admin</label>
                      </div>
                    </div>
                  </div>
                  <div class="text-center">
                    <button type="submit" class="btn bg-gradient-dark w-100 my-4 mb-2">Sign up</button>
                  </div>
                  <p class="text-sm mt-3 mb-0">Already have an account? <a href="${pageContext.request.contextPath}/UserServlet?action=loginPage" class="text-dark font-weight-bolder">Sign in</a></p>
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