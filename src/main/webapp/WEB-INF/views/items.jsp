<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<jsp:include page="/WEB-INF/jspf/header.jspf">
    <jsp:param name="title" value="Produkter"/>
</jsp:include>

<div class="card">
    <h2>Produkter</h2>
    <c:if test="${empty items}">
        <p>Det finns inga produkter ännu.</p>
    </c:if>
    <c:forEach var="item" items="${items}">
        <div class="card">
            <h3><c:out value="${item.name}"/></h3>
            <p>Pris: <c:out value="${item.price}"/> kr</p>
            <c:choose>
                <c:when test="${item.quantity > 0}">
                    <p>
                        I lager: <c:out value="${item.quantity}"/> st
                    </p>
                    <form method="post"
                          action="${pageContext.request.contextPath}/cart">
                        <input type="hidden"
                               name="action"
                               value="add"/>
                        <input type="hidden"
                               name="itemId"
                               value="${item.id}"/>
                        <label>
                            Antal:
                            <input type="number"
                                   name="quantity"
                                   value="1"
                                   min="1"
                                   max="${item.quantity}"
                                   required/>
                        </label>
                        <button type="submit" class="btn">
                            Lägg i varukorg
                        </button>
                    </form>
                </c:when>
                <c:otherwise>
                    <p>Slut i lager</p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>

<jsp:include page="/WEB-INF/jspf/footer.jspf"/>