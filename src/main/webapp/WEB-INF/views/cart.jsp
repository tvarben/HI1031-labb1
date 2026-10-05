<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
    <jsp:param name="title" value="Varukorg"/>
</jsp:include>

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
                        <th>Åtgärd</th>
                    </tr>
                </thead>

                <tbody>
                    <c:forEach var="cartItem" items="${cart.items}">
                        <tr>
                            <td><c:out value="${cartItem.item.name}"/></td>
                            <td><c:out value="${cartItem.quantity}"/></td>
                            <td><c:out value="${cartItem.item.price}"/> kr</td>
                            <td><c:out value="${cartItem.totalPrice}"/> kr</td>
                            <td>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/cart">
                                    <input type="hidden"
                                           name="action"
                                           value="remove"/>
                                    <input type="hidden"
                                           name="itemId"
                                           value="${cartItem.item.id}"/>
                                    <button type="submit" class="btn">
                                        Ta bort
                                    </button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <p>
                <strong>
                    Totalt: <c:out value="${cart.total}"/> kr
                </strong>
            </p>
            <a class="btn"
               href="${pageContext.request.contextPath}/checkout">
                Gå till kassan
            </a>
        </c:otherwise>
    </c:choose>
    <a class="btn"
       href="${pageContext.request.contextPath}/items">
        Fortsätt handla
    </a>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>