<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Управление пользователями</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/"><button style="font-size: 15px"><strong>На главную</strong></button></a>
<h2>Управление пользователями</h2>

<h3>Создать пользователя</h3>
<form action="${pageContext.request.contextPath}/users" method="POST">
  <label for="username">Имя пользователя:</label>
  <input type="text" id="username" name="username" required><br><br>
  <label for="password">Пароль:</label>
  <input type="password" id="password" name="password" required><br><br>
  <button type="submit">Создать</button>
</form>

<h3>Список пользователей</h3>
<c:if test="${not empty users}">
  <table border="1">
    <thead>
    <tr>
      <th>Id</th>
      <th>Имя пользователя</th>
      <th>Пароль</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
      <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.password}</td>
        <td>
          <form action="${pageContext.request.contextPath}/users/${user.id}" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="PUT">
            <input type="text" name="username" value="${user.username}" required>
            <input type="password" name="password" value="${user.password}" required>
            <button type="submit">Обновить</button>
          </form>
          <form action="${pageContext.request.contextPath}/users/${user.id}" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <button type="submit">Удалить</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<h3>Поиск пользователя по ID</h3>
<form action="${pageContext.request.contextPath}/users/user" method="GET">
  <label for="search-id">ID пользователя:</label>
  <input type="text" id="search-id" name="id" required>
  <button type="submit">Искать</button>
</form>

<c:if test="${not empty user}">
  <h3>Найденный пользователь</h3>
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Имя пользователя</th>
      <th>Пароль</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>${user.id}</td>
      <td>${user.username}</td>
      <td>${user.password}</td>
    </tr>
    </tbody>
  </table>
</c:if>

</body>
</html>
