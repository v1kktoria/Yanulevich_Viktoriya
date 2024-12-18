<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Управление недвижимостью</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/"><button style="font-size: 15px"><strong>На главную</strong></button></a>
<h2>Управление недвижимостью</h2>

<h3>Создать недвижимость</h3>
<form action="${pageContext.request.contextPath}/properties" method="POST">
  <label for="type">Тип недвижимости:</label>
  <select id="type" name="type">
    <option value="HOUSE">Дом</option>
    <option value="APARTMENT">Квартира</option>
    <option value="OFFICE">Офис</option>
  </select>
  <label for="area">Площадь:</label>
  <input type="number" id="area" name="area" required><br><br>
  <label for="price">Цена:</label>
  <input type="number" id="price" name="price" required><br><br>
  <label for="rooms">Количество комнат:</label>
  <input type="number" id="rooms" name="rooms" required><br><br>
  <label for="description">Описание:</label>
  <textarea id="description" name="description" required></textarea><br><br>
  <label for="ownerId">ID владельца:</label>
  <input type="number" id="ownerId" name="ownerId" required><br><br>
  <button type="submit">Создать</button>
</form>

<h3>Список недвижимости</h3>
<c:if test="${not empty properties}">
  <table border="1">
    <thead>
    <tr>
      <th>Id</th>
      <th>Id владельца</th>
      <th>Тип</th>
      <th>Площадь</th>
      <th>Цена</th>
      <th>Количество комнат</th>
      <th>Описание</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="property" items="${properties}">
      <tr>
        <td>${property.id}</td>
        <td>${property.owner.id}</td>
        <td>${property.type}</td>
        <td>${property.area}</td>
        <td>${property.price}</td>
        <td>${property.rooms}</td>
        <td>${property.description}</td>
        <td>
          <form action="${pageContext.request.contextPath}/properties" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="id" value="${property.id}">
            <input type="hidden" name="ownerId" value="${property.owner.id}">
            <select name="type" required>
              <option value="HOUSE" ${property.type == 'HOUSE' ? 'selected' : ''}>Дом</option>
              <option value="APARTMENT" ${property.type == 'APARTMENT' ? 'selected' : ''}>Квартира</option>
              <option value="OFFICE" ${property.type == 'OFFICE' ? 'selected' : ''}>Офис</option>
            </select>
            <input type="number" name="area" value="${property.area}" required>
            <input type="number" name="price" value="${property.price}" required>
            <input type="number" name="rooms" value="${property.rooms}" required>
            <textarea name="description">${property.description}</textarea><br><br>
            <button type="submit">Обновить</button>
          </form>
          <form action="${pageContext.request.contextPath}/properties" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="id" value="${property.id}">
            <button type="submit">Удалить</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<h3>Поиск недвижимости по ID</h3>
<form action="${pageContext.request.contextPath}/properties" method="GET">
  <label for="search-id">ID недвижимости:</label>
  <input type="text" id="search-id" name="id" required>
  <button type="submit">Искать</button>
</form>

<c:if test="${not empty property}">
  <h3>Найденная недвижимость</h3>
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Тип</th>
      <th>Площадь</th>
      <th>Цена</th>
      <th>Количество комнат</th>
      <th>Описание</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>${property.id}</td>
      <td>${property.type}</td>
      <td>${property.area}</td>
      <td>${property.price}</td>
      <td>${property.rooms}</td>
      <td>${property.description}</td>
    </tr>
    </tbody>
  </table>
</c:if>

</body>
</html>
