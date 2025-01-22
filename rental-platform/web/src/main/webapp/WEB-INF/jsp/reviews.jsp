<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Управление отзывами</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/"><button style="font-size: 15px"><strong>На главную</strong></button></a>
<h2>Управление отзывами</h2>

<h3>Создать отзыв</h3>
<form action="${pageContext.request.contextPath}/reviews" method="POST">
  <label for="property_id">Id недвижимости:</label>
  <input type="number" id="property_id" name="property_id" required><br><br>
  <label for="user_id">Id пользователя:</label>
  <input type="number" id="user_id" name="user_id" required><br><br>
  <label for="rating">Рейтинг (1-5):</label>
  <input type="number" id="rating" name="rating" min="1" max="5" required><br><br>
  <label for="comment">Комментарий:</label>
  <textarea id="comment" name="comment" required></textarea><br><br>
  <button type="submit">Создать</button>
</form>

<h3>Список отзывов</h3>
<c:if test="${not empty reviews}">
  <table border="1">
    <thead>
    <tr>
      <th>Id</th>
      <th>Id недвижимости</th>
      <th>Id пользователя</th>
      <th>Рейтинг</th>
      <th>Комментарий</th>
      <th>Дата создания</th>
      <th>Действия</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="review" items="${reviews}">
      <tr>
        <td>${review.id}</td>
        <td>${review.property.id}</td>
        <td>${review.user.id}</td>
        <td>${review.rating}</td>
        <td>${review.comment}</td>
        <td><fmt:formatDate value="${review.createdAt}" pattern="dd.MM.yyyy HH:mm"/></td>
        <td>
          <form action="${pageContext.request.contextPath}/reviews/${review.id}" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="PUT">
            <input type="hidden" name="id" value="${review.id}">
            <input type="hidden" name="property_id" value="${review.property.id}">
            <input type="hidden" name="user_id" value="${review.user.id}">
            <input type="text" name="comment" value="${review.comment}" required>
            <input type="number" name="rating" min="1" max="5" value="${review.rating}" required><br><br>
            <button type="submit">Обновить</button>
          </form>
          <form action="${pageContext.request.contextPath}/reviews/${review.id}" method="POST" style="display:inline;">
            <input type="hidden" name="_method" value="DELETE">
            <input type="hidden" name="id" value="${review.id}">
            <button type="submit">Удалить</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>

<h3>Поиск отзыва по ID</h3>
<form action="${pageContext.request.contextPath}/reviews/review" method="GET">
  <label for="search-id">ID отзыва:</label>
  <input type="text" id="search-id" name="id" required>
  <button type="submit">Искать</button>
</form>

<c:if test="${not empty review}">
  <h3>Найденный отзыв</h3>
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Комментарий</th>
      <th>Рейтинг</th>
      <th>Дата создания</th>
      <th>ID пользователя</th>
      <th>ID недвижимости</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td>${review.id}</td>
      <td>${review.comment}</td>
      <td>${review.rating}</td>
      <td><fmt:formatDate value="${review.createdAt}" pattern="dd.MM.yyyy HH:mm"/></td>
      <td>${review.user.id}</td>
      <td>${review.property.id}</td>
    </tr>
    </tbody>
  </table>
</c:if>

</body>
</html>
