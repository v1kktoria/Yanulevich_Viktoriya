<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Управление адресами</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/"><button style="font-size: 15px"><strong>На главную</strong></button></a>
<h2>Управление адресами</h2>

<h3>Создать адрес</h3>
<form action="${pageContext.request.contextPath}/addresses" method="POST">
  <label for="country">Страна:</label>
  <input type="text" id="country" name="country" required><br><br>
  <label for="city">Город:</label>
  <input type="text" id="city" name="city" required><br><br>
  <label for="street">Улица:</label>
  <input type="text" id="street" name="street" required><br><br>
  <label for="houseNumber">Номер дома:</label>
  <input type="text" id="houseNumber" name="houseNumber" required><br><br>
  <label for="property_id">Id недвижимости:</label>
  <input type="number" id="property_id" name="property_id" required><br><br>
  <button type="submit">Создать</button>
</form>

<h3>Список адресов</h3>
<c:if test="${not empty addresses}">
  <table border="1">
    <thead>
    <tr>
      <th>Id</th>
      <th>Id недвижимости</th>
      <th>Страна</th>
      <th>Город</th>
      <th>Улица</th>
      <th>Номер дома</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="address" items="${addresses}">
      <tr>
        <td>${address.id}</td>
        <td>${address.property.id}</td>
        <td>${address.country}</td>
        <td>${address.city}</td>
        <td>${address.street}</td>
        <td>${address.houseNumber}</td>
        <td>
          <form action="${pageContext.request.contextPath}/addresses/${address.id}" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="id" value="${address.id}">
            <input type="hidden" name="property_id" value="${address.property.id}">
            <input type="text" name="country" value="${address.country}" required>
            <input type="text" name="city" value="${address.city}" required>
            <input type="text" name="street" value="${address.street}" required>
            <input type="text" name="houseNumber" value="${address.houseNumber}" required><br><br>
            <button type="submit">Обновить</button>
          </form>
          <form action="${pageContext.request.contextPath}/addresses/${address.id}" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="id" value="${address.id}">
            <button type="submit">Удалить</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<h3>Поиск адреса по ID</h3>
<form action="${pageContext.request.contextPath}/addresses/address" method="GET">
  <label for="search-id">ID адреса:</label>
  <input type="text" id="search-id" name="id" required>
  <button type="submit">Искать</button>
</form>

<c:if test="${not empty address}">
  <h3>Найденный адрес</h3>
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Страна</th>
      <th>Город</th>
      <th>Улица</th>
      <th>Номер дома</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>${address.id}</td>
      <td>${address.country}</td>
      <td>${address.city}</td>
      <td>${address.street}</td>
      <td>${address.houseNumber}</td>
    </tr>
    </tbody>
  </table>
</c:if>

</body>
</html>
