<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jspf/header.jspf">
    <jsp:param name="title" value="Logga in"/>
</jsp:include>

<div class="card">
    <h2>Logga in</h2>

    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/login">
        <label>Användarnamn:<br/>
            <input type="text" name="username" required/>
        </label><br/><br/>
        <label>Lösenord:<br/>
            <input type="password" name="password" required/>
        </label><br/><br/>
        <button type="submit" class="btn">Logga in</button>
    </form>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>