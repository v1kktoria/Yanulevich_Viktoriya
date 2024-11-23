-- Примеры soft-delete
UPDATE Users 
SET deleted = TRUE  WHERE username = 'ivanov';

UPDATE Properties 
SET deleted = TRUE WHERE type = 'HOUSE' AND price < 1000;

UPDATE Applications 
SET deleted = TRUE WHERE property_id = 1 AND tenant_id = 2;

UPDATE Reviews 
SET deleted = TRUE WHERE property_id = 3 AND user_id = 1;

UPDATE Reports 
SET deleted = TRUE WHERE content_id = 2 AND type = 'ADVERTISEMENT';

-- Примеры strong-delete
DELETE FROM Favorites 
WHERE user_id = 1 AND property_id = 1;

DELETE FROM Analytics 
WHERE property_id = 2 AND views < 100;

DELETE FROM Images 
WHERE property_id = 3 AND filepath = 'images/ofis1.png';

DELETE FROM Applications 
WHERE tenant_id = 2 AND property_id = 1 AND status = 'PENDING';

DELETE FROM Addresses 
WHERE property_id = 2 AND city = 'Гомель' AND street = 'Ленина';

DELETE FROM Properties_Parameters 
WHERE property_id = 1 AND value = 'Да';

DELETE FROM Properties 
WHERE owner_id = 1 AND type = 'APARTMENT' AND area < 60;

DELETE FROM Reports 
WHERE user_id = 1 AND type = 'REVIEW' AND status = 'PENDING';

DELETE FROM Users_Roles 
WHERE user_id = 3 AND role_id = 3;

DELETE FROM Users 
WHERE username = 'alekseev';