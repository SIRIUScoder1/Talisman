<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Product</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">

</head>
<body>

   <jsp:include page="partials/header.jsp" />
   <jsp:include page="partials/menu.jsp" />

   <div class="page-title">Product</div>

   <c:if test="${not empty errorMessage }">
     <div class="error-message">
         ${errorMessage}
     </div>
   </c:if>

   <form:form modelAttribute="productInfo" method="POST" enctype="multipart/form-data"
            action="${pageContext.request.contextPath}/product">
       <table style="text-align:left;">
           <tr>
               <td>Code *</td>
               <td style="color:red;">
                  <c:if test="${not empty productInfo.code}">
                       <form:hidden path="code"/>
                       ${productInformation.code}
                  </c:if>
                  <c:if test="${empty productInfo.code}">
                       <form:input path="code" />
                       <form:hidden path="newProduct" />
                  </c:if>
               </td>
               <td><form:errors path="code" class="error-message" /></td>
           </tr>

           <tr>
               <td>Name *</td>
               <td><form:input path="name" /></td>
               <td><form:errors path="name" class="error-message" /></td>
           </tr>

           <tr>
               <td>Price *</td>
               <td><form:input path="price" /></td>
               <td><form:errors path="price" class="error-message" /></td>
           </tr>
           <tr>
               <td>Image</td>
               <td>
               <img src="${pageContext.request.contextPath}/productImage?code=${productInformation.code}" width="100"/></td>
               <td> </td>
           </tr>
           <tr>
               <td>Upload Image</td>
               <td><input type="file" name="imageData"/></td>
               <td> </td>
           </tr>


           <tr>
               <td>&nbsp;</td>
               <td><input type="submit" value="Submit" /> <input type="reset"
                   value="Reset" /></td>
           </tr>
       </table>
   </form:form>

   <jsp:include page="partials/footer.jsp" />
</body>
</html>