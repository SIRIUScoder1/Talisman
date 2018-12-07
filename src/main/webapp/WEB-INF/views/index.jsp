<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Talisman - The Online Book Shop</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body>
    <jsp:include page="partials/header.jsp" />
    <jsp:include page="partials/menu.jsp" />

    <div class="page-title"> Shopping Cart </div>
    <div class="demo-container">
        <h3> Content </h3>
        <ul>
            <li> Buy online</li>
            <li> Admin pages</li>
            <li> Reports </li>
        </ul>
    </div>
    <jsp:include page="partials/footer.jsp" />
</body>
</html>