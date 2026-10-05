<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/jspf/header.jspf"/>

<div class="card">
    <h2>Användaradministration</h2>
    <c:if test="${not empty error}">
        <p style="color: red;">
            <c:out value="${error}"/>
        </p>
    </c:if>
    <c:if test="${param.updated == 'true'}">
        <p style="color: green;">Rollen har sparats</p>
    </c:if>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Användarnamn</th>
                <th>Roll</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.id}"/></td>
                    <td><c:out value="${user.username}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${user.id == sessionScope.loggedInUser.id}">
                                <c:out value="${user.role}"/>
                                (din användare)
                            </c:when>
                            <c:otherwise>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/admin/users">
                                    <input type="hidden"
                                           name="userId"
                                           value="${user.id}"/>
                                    <select name="role"
                                            aria-label="Roll">
                                        <option value="customer"
                                            ${user.role == 'customer' ? 'selected' : ''}>
                                            Kund
                                        </option>
                                        <option value="admin"
                                            ${user.role == 'admin' ? 'selected' : ''}>
                                            Admin
                                        </option>
                                        <option value="employee"
                                            ${user.role == 'employee' ? 'selected' : ''}>
                                            Lagerpersonal
                                        </option>
                                    </select>
                                    <button type="submit" class="btn">
                                        Spara
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>