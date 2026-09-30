<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jspf/header.jspf">
    <jsp:param name="title" value="Varukorg"/>
</jsp:include>

<div class="card">
    <h2>Din varukorg</h2>

    <c:if test="${cart.itemCount == 0}">
        <p>Korgen är tom.</p>
    </c:if>

    <c:if test="${cart.itemCount > 0}">
        <table>
            <tr><th>Vara</th><th>Antal</th><th>Pris</th></tr>
            <c:forEach var="orderedItem" items="${cart.items}">
                <tr>
                    <td>${orderedItem.item.name}</td>
                    <td>${orderedItem.quantity}</td>
                    <td>${orderedItem.calculateTotalPrice()} kr</td>
                </tr>
            </c:forEach>
        </table>
        <p><strong>Totalt: ${cart.total} kr</strong></p>
    </c:if>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>