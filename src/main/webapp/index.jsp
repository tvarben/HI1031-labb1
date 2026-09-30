<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jspf/header.jspf">
    <jsp:param name="title" value="Startsida"/>
</jsp:include>

<div class="card">
    <h2>Webshop</h2>
    <p>Bläddra bland produkter och lägg dem i din varukorg.</p>
    <a class="btn" href="${pageContext.request.contextPath}/cart">Visa varukorg</a>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>