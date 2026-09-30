<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jspf/header.jspf">
    <jsp:param name="title" value="Hello"/>
</jsp:include>

<div class="card">
    <h2>${message}</h2>
    <a class="btn" href="${pageContext.request.contextPath}/index.jsp">Tillbaka</a>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>