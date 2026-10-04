<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<% request.setAttribute("title", "Orderbekräftelse"); %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="card">
    <h2>Tack för din order!</h2>
    <p>Ditt ordernummer är <strong><c:out value="${orderId}"/></strong>.</p>
    <a class="btn" href="${pageContext.request.contextPath}/items">Fortsätt handla</a>
</div>

<%@ include file="/WEB-INF/jspf/footer.jspf" %>