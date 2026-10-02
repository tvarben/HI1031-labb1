<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% request.setAttribute("title", "Logga in"); %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="card">
    <h2>Logga in</h2>
    <% if (request.getAttribute("invalidCredentials") != null) { %>
        <p style="color: red;"><%= request.getAttribute("invalidCredentials") %></p>
    <% } %>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
            <label for="username">Användarnamn</label>
            <input type="text" id="username" name="username" class="form-control" required autofocus>
        </div>
        <div class="form-group">
            <label for="password">Lösenord</label>
            <input type="password" id="password" name="password" class="form-control" required>
        </div>
        <button type="submit" class="btn">Logga in</button>
    </form>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>