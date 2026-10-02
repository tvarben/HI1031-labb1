<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<% request.setAttribute("title", "Varukorg"); %>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="card">
    <h2>Din varukorg</h2>

    <c:if test="${not empty error}">
        <p style="color: red;">
            <c:out value="${error}"/>
        </p>
    </c:if>

    <c:choose>
        <c:when test="${empty cart.items}">
            <p>Korgen är tom.</p>
        </c:when>

        <c:otherwise>
            <table>
                <thead>
                    <tr>
                        <th>Vara</th>
                        <th>Antal</th>
                        <th>Styckpris</th>
                        <th>Summa</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="cartItem" items="${cart.items}">
                        <tr>
                            <td><c:out value="${cartItem.item.name}"/></td>
                            <td><c:out value="${cartItem.quantity}"/></td>
                            <td><c:out value="${cartItem.item.price}"/> kr</td>
                            <td><c:out value="${cartItem.totalPrice}"/> kr</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <p>
                <strong>
                    Totalt: <c:out value="${cart.total}"/> kr
                </strong>
            </p>
        </c:otherwise>
    </c:choose>

    <a class="btn"
       href="${pageContext.request.contextPath}/items">
        Fortsätt handla
    </a>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>