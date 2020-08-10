<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>


<t:page pageTitle="Рахунки">
	<jsp:attribute name="navBar">
        <t:navBar activeMenuItem="accounts"></t:navBar>
    </jsp:attribute>

    <jsp:body>

        <form action="/account" method="POST">
            <div style="display: flex; flex-direction: row; align-items: center; margin: 1rem 0;">
                    <div>
                        <label>Account number</label>
                        <input name="account" required />
                    </div>
                    <div style="margin-left: 1rem;">
                        <label>Currency</label>
                        <select id="currency" name="currency" class="mdb-select" required>
                            <option value="" disabled selected>Оберіть валюту</option>
                            <option value="UAH">UAH</option>
                            <option value="EUR">EUR</option>
                            <option value="USD">USD</option>
                        </select>
                    </div>
                    <button style="margin-left: 1rem;" class="btn btn-primary">Add</button>
            </div>
        </form>
        
        <hr />
        
        
        <c:if test="${not empty accounts}">
            <table class="table">
                <thead>
                    <tr>
                        <th>Account number</th>
                        <th>Currency</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <c:forEach items="${accounts}" var="account">
                    <tr>
                        <td>${account.account}</td>
                        <td>${account.currency}</td>
                        <td style="width: 100px;">
                            <a href="/account/${account.id}/del" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        
    </jsp:body>
</t:page>