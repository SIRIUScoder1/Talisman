<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Shopping Cart</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

</head>
<body>
   <jsp:include page="partials/header.jsp" />

   <jsp:include page="partials/menu.jsp" />

   <fmt:setLocale value="en_US" scope="session"/>

   <div class="page-title">My Cart</div>

   <c:if test="${empty cartDetail or empty cartDetail.cartProductInfoList}">
       <h2>There is no items in Cart</h2>
       <a href="${pageContext.request.contextPath}/productList">Show
           Product List</a>
   </c:if>

   <c:if test="${not empty cartDetail and not empty cartDetail.cartProductInfoList}">
       <form:form method="POST" modelAttribute="cartDetail"
           action="${pageContext.request.contextPath}/shoppingCart">

           <c:forEach var="cartProductInfo" items="${cartDetail.cartProductInfoList}"
               varStatus="varStatus">
               <div class="product-preview-container">
                   <ul>
                       <li><img class="product-image"
                           src="${pageContext.request.contextPath}/productImage?code=${cartProductInfo.productInformation.code}" />
                       </li>
                       <li>Code: ${cartProductInfo.productInformation.code} <form:hidden
                               path="cartProductInfoList[${varStatus.index}].productInformation.code" />

                       </li>
                       <li>Name: ${cartProductInfo.productInformation.name}</li>
                       <li>Price: <span class="price">

                         <fmt:formatNumber value="${cartProductInfo.productInformation.price}" type="currency"/>

                       </span></li>
                       <li>Quantity: <form:input
                               path="cartProductInfoList[${varStatus.index}].productQuantity" /></li>
                       <li>Subtotal:
                         <span class="subtotal">

                            <fmt:formatNumber value="${cartProductInfo.amount}" type="currency"/>

                         </span>
                       </li>
                       <li><a
                           href="${pageContext.request.contextPath}/removeCartProduct?code=${cartProductInfo.productInformation.code}">
                               Delete </a></li>
                   </ul>
               </div>
           </c:forEach>
           <div style="clear: both"></div>
           <input class="button-update-sc" type="submit" value="Update Quantity" />
           <a class="navi-item"
               href="${pageContext.request.contextPath}/customerInformation">Enter
               Customer Info</a>
           <a class="navi-item"
               href="${pageContext.request.contextPath}/productList">Continue
               Buy</a>
       </form:form>


   </c:if>
   <jsp:include page="partials/footer.jsp" />

</body>
</html>