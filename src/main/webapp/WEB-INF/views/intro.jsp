<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>


<t:page pageTitle="Головна">
	<jsp:attribute name="navBar">
        <t:navBar></t:navBar>
    </jsp:attribute>

    <jsp:attribute name="jsIncludes">
    </jsp:attribute>

    <jsp:body>
        <div class="intro-container">
            <h1 class="centerAlign">Інтсрукція з використання</h1>
            <hr>
            <p>
                Даний додаток спрощує ведення <b>обліку доходів</b>.<br>
                Зазвичай IT-фахівці отримують дохід на валютний рахунок, 50% з якого продається автоматично та переводиться на рахунок у грн.<br>
                Для ведення обліку доходів треба знати <b>дату отримання</b>, <b>суму</b>, <b>валюту</b> та <b>курс НБУ на цю дату</b> (для 50% валюти, що продається за власним бажанням)
            </p>
            <p>
                Цей додаток дозволяє Вам вводити лише <b>дату</b>, <b>суму</b> та <b>валюту</b>.<br>
                <b>Знаходження курсу НБУ</b> та <b>перерахунок в гривню</b> виконується <b>автоматично</b>.<br>
                Так як підприємцям необхідно вести облік доходів у <b>"Книзі обліку доходів"</b>,<br>
                то дуже зручною є можливість отримати її в <b>готовому електронному вигляді</b> і лише переписувати вручну.<br>
            </p>
            <p class="intro-p24-info">
                <b class="underlined">Для клієнтів ПриватБанк</b>:<br>
                <br>
                    Електронний кабінет "Приват24 для бізнесу" дозволяє створити додаток "Автоклієнт",
                    який дозволяє отримувати транзакції по Вашим рахнукам за допомогою API.
                    В налаштуваннях додатку "Автоклієнт" Ви зможете отримати <b>id</b> та <b>token</b>, які є обов'язковою умовою для використання API.
                    <br>
                    <a href="https://docs.google.com/document/d/e/2PACX-1vS8rx2WKg69o6JvG5L4AhSXcU6vxXcJph6WK84qJcAYDBvsNYEob57jDMQhbosjc9gRS5bOTqTXf0vb/pub">Деталі</a>
                    <br><br>
                    В даному додатку реалізовано імпорт вхідних транзакцій, використовуючи вище згаданий "Автоклієнт" та API запити.<br>
                    Це є дуже зручним, так як для імпортування всіх доходів, проведених через рахунки ПриватБанку,<br>
                    достатньо лише вказати <b>номер рахунку</b> та <b>період часу</b>.
                    <br>
                </span>
            </p>
            
            <div class="intro-footer-warning">
                <h3 class="centerAlign">Увага!</h3>
                <ul>
                    <li>Даний додаток робить лише read-only API запити.</li>
                    <li>Даний додаток знаходиться на етапі тестування. Використання тільки на <b>власний ризик</b>!</li>
                </ul>
            </div>
        </div>
    </jsp:body>
</t:page>