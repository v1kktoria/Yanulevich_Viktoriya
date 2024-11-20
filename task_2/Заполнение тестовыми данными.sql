INSERT INTO Users (username, password) 
VALUES 
    ('ivanov', 'password123'),
    ('petrov', 'qwerty456'),
    ('alekseev', 'admin789');

INSERT INTO Profiles (first_name, last_name, email, phone) 
VALUES 
    ('Иван', 'Иванов', 'ivanov@example.com', '+3752910020030'),
    ('Петр', 'Петров', 'petrov@example.com', '+3752910020031'),
    ('Алексей', 'Алексеев', 'alekseev@example.com', '+3752910020032');

INSERT INTO Roles (role_name, description)
VALUES
    ('Администратор', 'Полный доступ ко всем функциям системы'),
    ('Арендодатель', 'Возможность размещать и редактировать объявления о недвижимости'),
    ('Арендатор', 'Возможность подавать заявки на аренду, оставлять отзывы, отправлять жалобы');

INSERT INTO Users_Roles (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);
	
INSERT INTO Properties (owner_id, type, area, price, rooms, description) 
VALUES 
    (1, 'квартира', 50.00, 1000.00, 2, 'Квартира с ремонтом в центре города'),
    (2, 'дом', 120.00, 900.00, 4, 'Дом с большим участком и садом'),
    (3, 'офис', 80.00, 1500.00, 3, 'Офисное помещение в бизнес-центре');
	
INSERT INTO Addresses (property_id, country, city, street, house_number)
VALUES
    (1, 'Беларусь', 'Минск', 'Независимости проспект', '12'),
    (2, 'Беларусь', 'Гомель', 'Ленина', '5'),
    (3, 'Беларусь', 'Могилёв', 'Калинина', '22');

INSERT INTO Parameters (name, description) 
VALUES 
    ('Балкон', 'Наличие балкона в квартире'),
    ('Парковка', 'Наличие парковки возле дома'),
    ('Кондиционер', 'Наличие кондиционера в помещении');
	
INSERT INTO Properties_Parameters (property_id, parameter_id, value)
VALUES
    (1, 1, 'Да'),
    (2, 2, 'Да'),
    (3, 3, 'Да');

INSERT INTO Applications (property_id, tenant_id, message, status) 
VALUES 
    (1, 2, 'Хочу арендовать эту квартиру', 'ожидает'),
    (2, 3, 'Интересуюсь домом для длительной аренды', 'ожидает'),
    (3, 1, 'Нужен офис для бизнеса', 'одобрено');
	
INSERT INTO Reviews (property_id, user_id, rating, comment) 
VALUES 
    (1, 1, 5, 'Отличная квартира'),
    (2, 2, 4, 'Дом хороший, участок маловат'),
    (3, 3, 3, 'Офис средний, но удобное местоположение');
	
INSERT INTO Analytics (property_id, views, applications_count) 
VALUES 
    (1, 100, 10),
    (2, 50, 5),
    (3, 200, 20);
	
INSERT INTO Images (property_id, filepath) 
VALUES 
    (1, 'images/kvartira1.png'),
    (2, 'images/dom1.png'),
    (3, 'images/ofis1.png');
	
INSERT INTO Favorites (user_id, property_id) 
VALUES 
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO Reports (user_id, type, content_id, message, status) 
VALUES 
    (1, 'отзыв', 1, 'Хочу оставить положительный отзыв о квартире', 'ожидает'),
    (2, 'объявление', 2, 'Пожалуйста, одобрите заявку на дом', 'ожидает'),
    (3, 'отзыв', 3, 'Офис не соответствует заявленному', 'отклонено');