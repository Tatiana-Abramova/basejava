<%@ page import="webapp.model.ContactType" %>
<%@ page import="webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" onsubmit="return verify()" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" id="fullName" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
                <c:forEach var="type" items="<%=SectionType.values()%>">
                    <dl>
                        <dt>${type.title}</dt>
                        <textarea name="${type.name()}" cols="40" rows="5">${resume.getSection(type)}</textarea>

                    </dl>
                </c:forEach>
        <hr>
        <input type="submit" value="Сохранить"></button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
<script type="text/javascript">
    function verify(){
        if(document.getElementById("fullName").value.trim().length == 0) {
                     alert("Введите имя");
                     return false;
        }
    }
</script>
</body>
</html>
