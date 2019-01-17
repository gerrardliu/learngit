<%--
  Created by IntelliJ IDEA.
  User: gerrardliu
  Date: 2019-01-17
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <c:set var="path" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>字典维护</title>
</head>
<body>
<form action="${path}/dicts/add" method="post">
    <input type="hidden" name="id" value="${model.id}">
    <table>
        <c:if test="${msg != null}">
            <tr>
                <th colspan="2" style="color:red;max-width:400px;">${msg}</th>
            </tr>
        </c:if>
        <tr>
            <th colspan="2">字典维护</th>
        </tr>
        <tr>
            <th>类别名</th>
            <td><input type="text" name="code" value="${model.code}"></td>
        </tr>
        <tr>
            <th>字典名</th>
            <td><input type="text" name="name" value="${model.name}"></td>
        </tr>
        <tr>
            <th>字典值</th>
            <td><input type="text" name="value" value="${model.value}"></td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="submit" value="保存">
                <input type="button" onclick="backToList()" value="取消">
            </th>
        </tr>
    </table>
</form>
<script>
    function backToList() {
        location.href = '${path}/dicts';
    }
</script>
</body>
</html>
