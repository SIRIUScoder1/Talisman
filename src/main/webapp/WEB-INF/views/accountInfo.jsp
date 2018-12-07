<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Account Information</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

</head>
<body>
   <jsp:include page="partials/header.jsp" />
   <jsp:include page="partials/menu.jsp" />

   <div class="page-title">Account Information</div>
   <div class="account-container">
       <ul>
           <li>User Name: ${pageContext.request.userPrincipal.name}</li>
           <li>Role:
               <ul>
                   <c:forEach var="auth" items="${userDetails.authorities}">
                       <li>${auth.authority }</li>
                   </c:forEach>
               </ul>
           </li>
       </ul>
   </div>
   <jsp:include page="partials/footer.jsp" />

</body>
</html>