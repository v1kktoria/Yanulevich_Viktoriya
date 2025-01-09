<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Главная страница</title>
</head>
<body>
<h1>Главная страница</h1>

<table>
    <tr>
        <td><a href="${pageContext.request.contextPath}/users"><button style="font-size: 18px;"><strong>Управление пользователями</strong></button></a></td>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/properties"><button style="font-size: 18px;"><strong>Управление недвижимостью</strong></button></a></td>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/addresses"><button style="font-size: 18px;"><strong>Управление адресами</strong></button></a></td>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/applications"><button style="font-size: 18px;"><strong>Управление заявками</strong></button></a></td>
    </tr>
    <tr>
        <td><a href="${pageContext.request.contextPath}/reviews"><button style="font-size: 18px;"><strong>Управление отзывами</strong></button></a></td>
    </tr>
</table>

</body>
</html>
