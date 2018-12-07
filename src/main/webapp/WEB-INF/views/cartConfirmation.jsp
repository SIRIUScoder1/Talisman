<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Shopping Cart Confirmation</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

</head>
<body>
  <jsp:include page="partials/header.jsp" />

  <jsp:include page="partials/menu.jsp" />

  <fmt:setLocale value="en_US" scope="session"/>

  <div class="page-title">Confirmation</div>



  <div class="customer-info-container">
      <h3>Customer Information:</h3>
      <ul>
          <li>Name: ${myCart.customerInformation.name}</li>
          <li>Email: ${myCart.customerInformation.email}</li>
          <li>Phone: ${myCart.customerInformation.phoneNumber}</li>
          <li>Address: ${myCart.customerInformation.address}</li>
      </ul>
      <h3>Cart Summary:</h3>
      <ul>
          <li>Quantity: ${myCart.totalQuantity}</li>
          <li>Total:
          <span class="total">
            <fmt:formatNumber value="${myCart.totalAmount}" type="currency"/>
          </span></li>
      </ul>
  </div>

  <form method="POST" action="${pageContext.request.contextPath}/cartConfirmation">

      <!-- Edit Cart -->
      <a class="navi-item" href="${pageContext.request.contextPath}/shoppingCart">Edit Cart</a>

      <!-- Edit Customer Info -->
      <a class="navi-item" href="${pageContext.request.contextPath}/customerInformation">Edit
          Customer Info</a>

      <!-- Send/Save -->
      <input type="submit" value="Send" class="button-send-sc" />
  </form>

  <div class="container">

      <c:forEach var="cartProductInfo" items="${myCart.cartProductInfoList}">
          <div class="product-preview-container">
              <ul>
                  <li><img class="product-image" src="${pageContext.request.contextPath}/productImage?code=${cartProductInfo.productInformation.code}" /></li>
                  <li>Code: ${cartProductInfo.productInformation.code} <input
                      type="hidden" name="code" value="${cartProductInfo.productInformation.code}" />
                  </li>
                  <li>Name: ${cartProductInfo.productInformation.name}</li>
                  <li>Price: <span class="price">
                     <fmt:formatNumber value="${cartProductInfo.productInformation.price}" type="currency"/>
                  </span>
                  </li>
                  <li>Quantity: ${cartProductInfo.productQuantity}</li>
                  <li>Subtotal:
                    <span class="subtotal">
                       <fmt:formatNumber value="${cartProductInfo.amount}" type="currency"/>
                    </span>
                  </li>
              </ul>
          </div>
      </c:forEach>

  </div>

  <jsp:include page="partials/footer.jsp" />

</body>
</html>