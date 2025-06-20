INSERT INTO users (username, password) VALUES
('user1', '$2a$12$lsuPldIX6TOXihrMFG0mteQ5cDjnhi3nVCZWUES3VRFxZkUld4ZKi'), -- password1
('user2', '$2a$12$lsuPldIX6TOXihrMFG0mteQ5cDjnhi3nVCZWUES3VRFxZkUld4ZKi');

INSERT INTO properties (owner_id, type, area, price, rooms, description, rating, created_at) VALUES
(1, 'OFFICE', 75.5, 150000, 3, NULL, NULL, CURRENT_TIMESTAMP);

INSERT INTO addresses (property_id, country, city, street, house_number) VALUES
(1, 'CountryName', 'CityName', 'StreetName', '123');

INSERT INTO applications (property_id, tenant_id, owner_id, message, status, created_at, deleted) VALUES
(1, 2, 1, 'Interested in this property', 'PENDING', CURRENT_TIMESTAMP, false);

INSERT INTO favorites (user_id) VALUES
(1),
(2);

INSERT INTO chats (name) VALUES
('Test Chat');

INSERT INTO images (property_id, image_url) VALUES
(1, 'http://example.com/image1.jpg');

INSERT INTO parameters (name, description) VALUES
('Parameter1', 'Description1'),
('Parameter2', 'Description2');

INSERT INTO profiles (first_name, last_name, email, phone, user_id) VALUES
('First Name1', 'Last Name1', 'User1@example.com', '123-456-7890', 1),
('First Name2', 'Last Name2', 'User2@example.com', '987-654-3210', 2);

INSERT INTO properties_parameters (property_id, parameter_id) VALUES
(1, 1),
(1, 2);

INSERT INTO messages (chat_id, sender_id, content, created_at) VALUES
(1, 1, 'Hello, this is a message', CURRENT_TIMESTAMP);

INSERT INTO reviews (property_id, user_id, rating, comment, created_at) VALUES
(1, 2, 5, 'Great property!', CURRENT_TIMESTAMP);

INSERT INTO roles (role_name, description) VALUES
('USER', NULL),
('ADMIN', NULL);

INSERT INTO users_roles (user_id, role_id) VALUES
(1, 1),
(1, 2),
(2, 1);

INSERT INTO favorites_properties (favorite_id, property_id) VALUES
(1, 1),
(2, 1);

INSERT INTO chats_users (chat_id, user_id) VALUES
(1, 1),
(1, 2);