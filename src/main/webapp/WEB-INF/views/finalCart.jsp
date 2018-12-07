<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Shopping Cart Finalize</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

</head>
<body>
   <jsp:include page="partials/header.jsp" />

   <jsp:include page="partials/menu.jsp" />

   <div class="page-title">Finalize</div>

   <div class="container">
       <h3>Thank you for Order</h3>
       Your order number is: ${lastOrderedCart.orderNumber}
   </div>

   <jsp:include page="partials/footer.jsp" />

</body>
</html>