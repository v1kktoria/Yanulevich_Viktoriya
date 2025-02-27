-- Последовательности
CREATE SEQUENCE users_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE profiles_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE roles_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE properties_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE addresses_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE parameters_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE applications_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reviews_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE analytics_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE images_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE favorites_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reports_id_seq START WITH 1 INCREMENT BY 1;

-- Таблицы
CREATE TABLE Users (
                       id INT PRIMARY KEY DEFAULT nextval('users_id_seq'),
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       deleted BOOLEAN DEFAULT FALSE,
                       CONSTRAINT chk_username_length CHECK (length(username) > 0),
                       CONSTRAINT chk_password_length CHECK (length(password) >= 8)
);

CREATE TABLE Profiles (
                          id INT PRIMARY KEY DEFAULT nextval('profiles_id_seq'),
                          user_id INT NOT NULL,
                          first_name VARCHAR(255) NOT NULL,
                          last_name VARCHAR(255) NOT NULL,
                          email VARCHAR(255) NOT NULL UNIQUE,
                          phone VARCHAR(20) NOT NULL,
                          registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                          CONSTRAINT chk_first_name_length CHECK (length(first_name) > 0),
                          CONSTRAINT chk_last_name_length CHECK (length(last_name) > 0),
                          CONSTRAINT chk_email_length CHECK (length(email) > 3)
);

CREATE TABLE Roles (
                       id INT PRIMARY KEY DEFAULT nextval('roles_id_seq'),
                       role_name VARCHAR(50) NOT NULL UNIQUE,
                       description TEXT,
                       CONSTRAINT chk_role_name_length CHECK (LENGTH(role_name) > 0)
);

CREATE TABLE Users_Roles (
                             user_id INT NOT NULL,
                             role_id INT NOT NULL,
                             PRIMARY KEY (user_id, role_id),
                             FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                             FOREIGN KEY (role_id) REFERENCES Roles(id) ON DELETE CASCADE
);

CREATE TABLE Properties (
                            id INT PRIMARY KEY DEFAULT nextval('properties_id_seq'),
                            owner_id INT NOT NULL,
                            type VARCHAR(20) NOT NULL,
                            area DECIMAL(10, 2) NOT NULL,
                            price DECIMAL(10, 2) NOT NULL,
                            rooms INT NOT NULL,
                            description TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            deleted BOOLEAN DEFAULT FALSE,
                            FOREIGN KEY (owner_id) REFERENCES Users(id) ON DELETE CASCADE,
                            CONSTRAINT chk_area_positive CHECK (area > 0),
                            CONSTRAINT chk_price_positive CHECK (price > 0),
                            CONSTRAINT chk_rooms_positive CHECK (rooms > 0)
);

CREATE TABLE Addresses (
                           id INT PRIMARY KEY DEFAULT nextval('addresses_id_seq'),
                           property_id INT NOT NULL,
                           country VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           street VARCHAR(255) NOT NULL,
                           house_number VARCHAR(20) NOT NULL,
                           FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE,
                           CONSTRAINT chk_country_length CHECK (length(country) > 0),
                           CONSTRAINT chk_city_length CHECK (length(city) > 0),
                           CONSTRAINT chk_street_length CHECK (length(street) > 0),
                           CONSTRAINT chk_house_number_length CHECK (length(house_number) > 0)
);

CREATE TABLE Parameters (
                            id INT PRIMARY KEY DEFAULT nextval('parameters_id_seq'),
                            name VARCHAR(255) NOT NULL UNIQUE,
                            description TEXT,
                            CONSTRAINT chk_parameter_name_length CHECK (length(name) > 0)
);

CREATE TABLE Properties_Parameters (
                                       property_id INT NOT NULL,
                                       parameter_id INT NOT NULL,
                                       property_value VARCHAR(255) NOT NULL,
                                       PRIMARY KEY (property_id, parameter_id),
                                       FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE,
                                       FOREIGN KEY (parameter_id) REFERENCES Parameters(id) ON DELETE CASCADE
);

CREATE TABLE Applications (
                              id INT PRIMARY KEY DEFAULT nextval('applications_id_seq'),
                              property_id INT NOT NULL,
                              tenant_id INT NOT NULL,
                              message TEXT,
                              status VARCHAR(20) NOT NULL,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              deleted BOOLEAN DEFAULT FALSE,
                              FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE,
                              FOREIGN KEY (tenant_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Reviews (
                         id INT PRIMARY KEY DEFAULT nextval('reviews_id_seq'),
                         property_id INT NOT NULL,
                         user_id INT NOT NULL,
                         rating INT NULL,
                         comment TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                         CONSTRAINT chk_rating CHECK (rating BETWEEN 1 AND 5)
);

CREATE TABLE Analytics (
                           id INT PRIMARY KEY DEFAULT nextval('analytics_id_seq'),
                           property_id INT NOT NULL,
                           views INT DEFAULT 0,
                           applications_count INT DEFAULT 0,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE,
                           CONSTRAINT chk_views_positive CHECK (views >= 0),
                           CONSTRAINT chk_applications_count_positive CHECK (applications_count >= 0)
);

CREATE TABLE Images (
                        id INT PRIMARY KEY DEFAULT nextval('images_id_seq'),
                        property_id INT NOT NULL,
                        filepath VARCHAR(255) NOT NULL,
                        FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE,
                        CONSTRAINT chk_filepath_length CHECK (length(filepath) > 0)
);

CREATE TABLE Favorites (
                           id INT PRIMARY KEY DEFAULT nextval('favorites_id_seq'),
                           user_id INT NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

CREATE TABLE Favorites_Properties (
                                      favorite_id INT NOT NULL,
                                      property_id INT NOT NULL,
                                      PRIMARY KEY (favorite_id, property_id),
                                      FOREIGN KEY (favorite_id) REFERENCES Favorites(id) ON DELETE CASCADE,
                                      FOREIGN KEY (property_id) REFERENCES Properties(id) ON DELETE CASCADE
);

CREATE TABLE Reports (
                         id INT PRIMARY KEY DEFAULT nextval('reports_id_seq'),
                         user_id INT NOT NULL,
                         type VARCHAR(20) NOT NULL,
                         content_id INT NOT NULL,
                         message TEXT,
                         status VARCHAR(20) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted BOOLEAN DEFAULT FALSE,
                         FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);

-- Представления
CREATE VIEW ActiveUsers AS SELECT * FROM Users WHERE deleted = FALSE;
CREATE VIEW ActiveProperties AS SELECT * FROM Properties WHERE deleted = FALSE;
CREATE VIEW ActiveApplications AS SELECT * FROM Applications WHERE deleted = FALSE;
CREATE VIEW ActiveReviews AS SELECT * FROM Reviews WHERE deleted = FALSE;
CREATE VIEW ActiveReports AS SELECT * FROM Reports WHERE deleted = FALSE;

-- Индексы
CREATE INDEX idx_properties_owner_id ON Properties (owner_id);
CREATE INDEX idx_applications_property_id ON Applications (property_id);
CREATE INDEX idx_users_username ON Users (username);
CREATE INDEX idx_favorites_user ON Favorites (user_id);