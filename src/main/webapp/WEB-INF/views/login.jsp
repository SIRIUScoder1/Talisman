<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    </head>
    <body>
          <jsp:include page="partials/header.jsp" />
          <jsp:include page="partials/menu.jsp" />
          <div class="page-title">Login (For Employee, Manager)</div>
             <div class="login-container">
                 <h3>Enter username and password</h3>
                 <br>
                 <c:if test="${param.error == 'true'}">
                     <div style="color: red; margin: 10px 0px;">
                         Login Failed!!!<br /> Reason :
                         ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}

                     </div>
                 </c:if>
                 <form method="POST" action="${pageContext.request.contextPath}/springSecurityCheck">
                     <table>
                         <tr>
                             <td>User Name *</td>
                             <td><input type="text" name="userName" /></td>
                         </tr>

                         <tr>
                             <td>Password *</td>
                             <td><input type="password" name="password" /></td>
                         </tr>

                         <tr>
                             <td>&nbsp;</td>
                             <td><input type="submit" value="Login" />
                             <input type="reset" value="Reset" /></td>
                         </tr>
                     </table>
                 </form>
                 <span class="error-message">${error}</span>
             </div>
      <jsp:include page="partials/footer.jsp"/>
    </body>
</html>