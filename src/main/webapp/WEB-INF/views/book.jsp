<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Home</title>
    <style type="text/css" title="currentStyle">
        @import "<spring:url value="/css/bootstrap.min.css"/>";
        @import "<spring:url value="/css/bootstrap-theme.min.css"/>";
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" type="text/javascript"></script>
    <script src='<spring:url value="/js/bootstrap.min.js"/>' type="text/javascript"></script>
    <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js" type="text/javascript"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">

    <script type="text/javascript">
        $(function () {
            $("div[id^='progressbar']").each ( function() {
                var ntp = $(this).attr("newTextPercent");
                $(this).progressbar({
                    value: ntp
                });
            })
        });
    </script>
</head>
<body>
<div align="center" style="padding-top: 100px;padding-bottom: 50px">
    <form action="addBook">
        <label>
            URL: <input id="bookUrl" name="bookUrl" type="text" size="100"><input type="submit">
        </label>
    </form>
</div>

<table class="table table-striped">
    <thead>

    <tr>
        <th>Author</th>
        <th>Book</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="books" scope="request" type="java.util.List<boodater.model.Book>"/>
    <c:forEach var="book" items="${books}">
        <tr>
            <td><c:out value="${book.author}"/></td>
            <td><a href="/getBook/<c:out value="${book.id}"/>"><c:out value="${book.name}"/></a></td>
            <td>
                <div id="progressbar<c:out value="${book.id}"/>"  newTextPercent="<c:out value="${book.newTextPercent}"/>">
                    <div align="center">
                        <a href="/getNewBook/<c:out value="${book.id}"/>"><c:out value="${book.status}"/></a>
                    </div>
                </div>
            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</body>
</html>
