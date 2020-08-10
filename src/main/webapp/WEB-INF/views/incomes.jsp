<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>


<t:page pageTitle="Доходи">
	<jsp:attribute name="navBar">
        <t:navBar activeMenuItem="incomes"></t:navBar>
    </jsp:attribute>

    <jsp:body>
        
        <script type="text/javascript">
            $(document).ready(function () {
                $('#date,#startDate,#endDate').datepicker({
                    format: "dd.mm.yyyy",
                    autoclose: true,
                    todayHighlight: true,
                    toggleActive: true
                });
            });
        </script>
        
        <div class="flex">
            <div class="new-income-form-container">
                <form class="w-100" method="post" action="/income/add">
                    <div class="form-group row date">
                        <label class="control-label col-sm-3 new-income-form-label" for="date">Дата</label>
                        <div class="col-sm-9 flex">
                            <input id="date" name="date" required pattern="[0-9]{2}.[0-9]{2}.[0-9]{4}" class="form-control" placeholder="Дата" autocomplete="off">
                            <span class="input-group-addon">
                                <img height="35px" width="35px" src="/images/datepicker-icon.png" />
                            </span>
                        </div>
                    </div>
                        
                    <div class="form-group row input-margin">
                        <label class="control-label col-sm-3 new-income-form-label" for="amount">Сума</label>
                        <div class="col-sm-9">
                            <input id="amount" type="number" required name="amount" step="0.01" class="form-control" placeholder="Сума" autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group row input-margin">
                        <label class="control-label col-sm-3 new-income-form-label" for="currency">Валюта</label>
                        <div class="col-sm-9">
                            <select id="currency" name="currency" class="mdb-select" required>
                                <option value="" disabled selected>Оберіть валюту</option>
                                <option value="UAH">UAH</option>
                                <option value="EUR">EUR</option>
                                <option value="USD">USD</option>
                            </select>
                        </div>
                    </div>
                    <div class="new-income-btn-add-container">
                        <button class="btn btn-primary btn-md w-100 no-margin">Додати</button>
                    </div>
                </form>
            </div>
    
            <div class="income-import-form-container">
                <form method="post" action="/incomes/import">
                    <div>
                        <h2>ПриватБанк - Імпорт транзакцій</h2>
                    </div>
                    <hr>
                    
                    <div class="form-group row input-margin">
                        <label class="control-label col-sm-3 new-income-form-label" for="account">Рахунок</label>
                        <div class="col-sm-9">
                            <select id="account" name="account" class="form-control" required placeholder="Рахунок">
                                <c:forEach items="${accounts}" var="account">
                                    <option value="" disabled selected>Оберіть рахунок</option>
                                    <option value="${account.account}">${account.account} (${account.currency})</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    
                    <div class="form-group row date">
                        <label class="control-label col-sm-3 new-income-form-label" for="date">Проміжок часу</label>
                        <div class="col-sm-9 flex">
                            <div class="flex">
                                <input id="startDate" name="startDate" required pattern="[0-9]{2}.[0-9]{2}.[0-9]{4}" class="form-control" placeholder="Від" autocomplete="off">
                            </div>
                            <div class="flex">
                                <input id="endDate" name="endDate" required pattern="[0-9]{2}.[0-9]{2}.[0-9]{4}" class="form-control" placeholder="До" autocomplete="off">
                            </div>
                        </div>
                    </div>

                    <div>
                        <button class="btn btn-primary btn-md w-100 no-margin">Імпортувати</button>
                    </div>
                </form>
            </div>
        </div>

        <c:if test="${not empty incomes}">
            
            <div class="flex incomes-clear-all-container">
                <form action="/incomes/clear">
                    <button class="btn btn-primary btn-md">Видалити всі</button>
                </form>
            </div>
            <table class="table incomes">
                <thead>
                    <th>Дата</th>
                    <th>Сума</th>
                    <th class="incomes-currency-column">Валюта</th>
                    <th class="incomes-actions-column">Дії</th>
                </thead>
                <tbody>
                    <c:forEach items="${incomes}" var="income">
                        <tr>
                            <td>
                                <t:localDate date="${income.date}" pattern="dd.MM.yyyy" />
                            </td>
                            <td>
                                ${income.amount}
                            </td>
                            <td class="centerAlign bold">
                                ${income.currency}
                            </td>
                            <td class="centerAlign bold">
                                <a href="/income/${income.id}/delete" class="income-del-btn">Видалити</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        
    </jsp:body>
</t:page>