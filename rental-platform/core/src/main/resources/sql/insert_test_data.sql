DO $$
    DECLARE
        user_id1 INT; user_id2 INT; user_id3 INT;
        role_id1 INT; role_id2 INT; role_id3 INT;
        property_id1 INT; property_id2 INT; property_id3 INT;
        parameter_id1 INT; parameter_id2 INT; paramеter_id3 INT;
    BEGIN

        INSERT INTO Users (username, password) VALUES ('ivanov', 'password123');
        user_id1 := currval('users_id_seq');

        INSERT INTO Users (username, password) VALUES ('petrov', 'qwerty456');
        user_id2 := currval('users_id_seq');

        INSERT INTO Users (username, password) VALUES ('alekseev', 'admin789');
        user_id3 := currval('users_id_seq');

        INSERT INTO Roles (role_name, description)
        VALUES ('Администратор', 'Полный доступ ко всем функциям системы');
        role_id1 := currval('roles_id_seq');

        INSERT INTO Roles (role_name, description)
        VALUES ('Арендодатель', 'Возможность размещать и редактировать объявления о недвижимости');
        role_id2 := currval('roles_id_seq');

        INSERT INTO Roles (role_name, description)
        VALUES ('Арендатор', 'Возможность подавать заявки на аренду, оставлять отзывы, отправлять жалобы');
        role_id3 := currval('roles_id_seq');

        INSERT INTO Users_Roles (user_id, role_id)
        VALUES
            (user_id1, role_id1),
            (user_id2, role_id2),
            (user_id3, role_id3);

        INSERT INTO Properties (owner_id, type, area, price, rooms, description)
        VALUES (user_id1, 'APARTMENT', 50.00, 1000.00, 2, 'Квартира с ремонтом в центре города');
        property_id1 := currval('properties_id_seq');

        INSERT INTO Properties (owner_id, type, area, price, rooms, description)
        VALUES (user_id2, 'HOUSE', 120.00, 900.00, 4, 'Дом с большим участком и садом');
        property_id2 := currval('properties_id_seq');

        INSERT INTO Properties (owner_id, type, area, price, rooms, description)
        VALUES (user_id3, 'OFFICE', 80.00, 1500.00, 3, 'Офисное помещение в бизнес-центре');
        property_id3 := currval('properties_id_seq');

        INSERT INTO Addresses (property_id, country, city, street, house_number)
        VALUES
            (property_id1, 'Беларусь', 'Минск', 'Независимости проспект', '12'),
            (property_id2, 'Беларусь', 'Гомель', 'Ленина', '5'),
            (property_id3, 'Беларусь', 'Могилёв', 'Калинина', '22');

        INSERT INTO Parameters (name, description)
        VALUES ('Балкон', 'Наличие балкона в квартире');
        parameter_id1 := currval('parameters_id_seq');

        INSERT INTO Parameters (name, description) VALUES
            ('Парковка', 'Наличие парковки возле дома');
        parameter_id2 := currval('parameters_id_seq');

        INSERT INTO Parameters (name, description)
        VALUES ('Кондиционер', 'Наличие кондиционера в помещении');
        paramеter_id3 := currval('parameters_id_seq');

        INSERT INTO Properties_Parameters (property_id, parameter_id, value)
        VALUES
            (property_id1, parameter_id1, 'Да'),
            (property_id2, parameter_id2, 'Да'),
            (property_id3, paramеter_id3, 'Да');

        INSERT INTO Applications (property_id, tenant_id, message, status)
        VALUES
            (property_id1, user_id2, 'Хочу арендовать эту квартиру', 'PENDING'),
            (property_id2, user_id3, 'Интересуюсь домом для длительной аренды', 'PENDING'),
            (property_id3, user_id1, 'Нужен офис для бизнеса', 'APPROVED');

        INSERT INTO Reviews (property_id, user_id, rating, comment)
        VALUES
            (property_id1, user_id1, 5, 'Отличная квартира'),
            (property_id2, user_id2, 4, 'Дом хороший, участок маловат'),
            (property_id3, user_id3, 3, 'Офис средний, но удобное местоположение');

        INSERT INTO Analytics (property_id, views, applications_count)
        VALUES
            (property_id1, 100, 10),
            (property_id2, 50, 5),
            (property_id3, 200, 20);

        INSERT INTO Images (property_id, filepath)
        VALUES
            (property_id1, 'images/kvartira1.png'),
            (property_id2, 'images/dom1.png'),
            (property_id3, 'images/ofis1.png');

        INSERT INTO Reports (user_id, type, content_id, message, status)
        VALUES
            (user_id1, 'REVIEW', property_id1, 'Хочу оставить положительный отзыв о квартире', 'PENDING'),
            (user_id2, 'ADVERTISEMENT', property_id2, 'Пожалуйста, одобрите заявку на дом', 'PENDING'),
            (user_id3, 'REVIEW', property_id3, 'Офис не соответствует заявленному', 'REJECTED');

    END $$;