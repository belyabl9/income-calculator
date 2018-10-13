<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>


<t:page pageTitle="Книга обліку доходів">
	<jsp:attribute name="navBar">
        <t:navBar activeMenuItem="accounting-book"></t:navBar>
    </jsp:attribute>

    <jsp:attribute name="jsIncludes">
    </jsp:attribute>

    <jsp:body>

        <script>
            var AccountingBook = (function() {
                return {
                    calculate: function (year) {
                        window.location.href = '/accounting-book/' + $('#yearInput').val();
                    }
                }
            })();

            $(document).ready(function () {

                $('#yearInput').datepicker({
                    minViewMode: 2,
                    format: 'yyyy',
                    autoclose: true,
                    todayHighlight: true,
                    toggleActive: true
                });

                <c:if test="${not empty accountingBookLines}">
                    $('.table tr td:first-child').each(function(index, val) {
                        if ($(val).text().trim().startsWith('За')) {
                            $(val).parent().css('background-color', "#d1e5f9");
                        }
                    });
                </c:if>
            });
            

        </script>
        
        <div class="acc-book-control-panel">
            <input id="yearInput" name="year" class="form-control w-100" required placeholder="Рік" autocomplete="off" pattern="20[0-9]{2}" />
            <button class="btn btn-primary btn-md acc-book-create-btn" onclick="AccountingBook.calculate()">Створити книгу обліку</button>
        </div>

        <c:if test="${not empty accountingBookLines}">
            <div class="acc-book">
                <h2 class="centerAlign">Приклад заповнення книги обліку доходів за ${year} рік</h2>
                
                <table class="table">
                    <thead>
                        <th class="acc-book-date-col">Дата запису</th>
                        <th class="acc-book-amount-col">Сума, грн, коп</th>
                        <th>Сума повернутих коштів та/або передплати, грн, коп</th>
                        <th>Скоригована сума доходу, грн, коп</th>
                        <th>Вартість безоплатно отриманих товарів (робіт, послуг), грн, коп</th>
                        <th class="acc-book-total-col">Всього, грн, коп</th>
                    </thead>
                    <tbody>
                    <c:forEach items="${accountingBookLines}" var="line">
                        <tr>
                            <td>
                                ${line.datePeriod}
                            </td>
                            <td>
                                ${line.amount}
                            </td>
                            <td>
                                
                            </td>
                            <td>
        
                            </td>
                            <td>
        
                            </td>
                            <td>
                                ${line.amount}
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        
    </jsp:body>
</t:page>