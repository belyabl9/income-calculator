<%@ tag description="Navigation bar component" pageEncoding="UTF-8" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="t" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ attribute name="activeMenuItem" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-dark">

        <!-- Navbar brand -->
        <a class="navbar-brand" href="/">Облік доходів</a>

        <!-- Collapsible content -->
        <div class="collapse navbar-collapse" id="basicExampleNav">

            <!-- Links -->
            <ul class="navbar-nav mr-auto">
                <li class="nav-item ${activeMenuItem eq 'accounts' ? "active" : ""}">
                    <a class="nav-link" href="/accounts">Рахунки</a>
                </li>
                <li class="nav-item ${activeMenuItem eq 'incomes' ? "active" : ""}">
                    <a class="nav-link" href="/incomes">Доходи</a>
                </li>
                <li class="nav-item ${activeMenuItem eq 'accounting-book' ? "active" : ""}">
                    <a class="nav-link" href="/accounting-book">Книга обліку доходів</a>
                </li>
            </ul>
            <!-- Links -->
        </div>
    </nav>
    <!--/.Navbar-->
</header>