<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Управление заявками</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/"><button style="font-size: 15px"><strong>На главную</strong></button></a>
<h2>Управление заявками</h2>
<h3>Создать заявку</h3>
<form action="${pageContext.request.contextPath}/applications" method="POST">
  <label for="message">Сообщение:</label>
  <input type="text" id="message" name="message" required><br><br>
  <label for="property_id">Id недвижимости:</label>
  <input type="number" id="property_id" name="property_id" required><br><br>
  <label for="tenant_id">Id арендатора:</label>
  <input type="number" id="tenant_id" name="tenant_id" required><br><br>
  <button type="submit">Создать</button>
</form>

<h3>Список заявок</h3>
<c:if test="${not empty applications}">
  <table border="1">
    <thead>
    <tr>
      <th>Id</th>
      <th>Id недвижимости</th>
      <th>Сообщение</th>
      <th>Статус</th>
      <th>Дата создания</th>
      <th>Id арендатора</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="application" items="${applications}">
      <tr>
        <td>${application.id}</td>
        <td>${application.property.id}</td>
        <td>${application.message}</td>
        <td>${application.status}</td>
        <td>${application.createdAt}</td>
        <td>${application.tenant.id}</td>
        <td>
          <form action="${pageContext.request.contextPath}/applications" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="id" value="${application.id}">
            <input type="hidden" name="property_id" value="${application.property.id}">
            <input type="hidden" name="tenant_id" value="${application.tenant.id}">
            <input type="text" name="message" value="${application.message}" required>
            <button type="submit">Обновить</button>
          </form>
          <form action="${pageContext.request.contextPath}/applications" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="id" value="${application.id}">
            <button type="submit">Удалить</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<h3>Поиск заявки по ID</h3>
<form action="${pageContext.request.contextPath}/applications" method="GET">
  <label for="search-id">ID заявки:</label>
  <input type="text" id="search-id" name="id" required>
  <button type="submit">Искать</button>
</form>

<c:if test="${not empty application}">
  <h3>Найденная заявка</h3>
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Сообщение</th>
      <th>Статус</th>
      <th>Дата создания</th>
      <th>ID ареднатора</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>${application.id}</td>
      <td>${application.message}</td>
      <td>${application.status}</td>
      <td>${application.createdAt}</td>
      <td>${application.tenant.id}</td>
    </tr>
    </tbody>
  </table>
</c:if>
</body>
</html>