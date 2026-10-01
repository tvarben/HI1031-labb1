<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("title", "Startsida"); %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="card">
    <h2>Webshop</h2>
    <p>Bläddra bland produkter och lägg dem i din varukorg.</p>
    <a class="btn" href="${pageContext.request.contextPath}/cart">Visa varukorg</a>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>