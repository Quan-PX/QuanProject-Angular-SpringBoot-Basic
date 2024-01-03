DROP SCHEMA IF EXISTS `quanpro`;

create
database quanpro;
USE
quanpro;

CREATE TABLE roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    login         VARCHAR(50) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    first_name    VARCHAR(50)          DEFAULT '',
    last_name     VARCHAR(50)          DEFAULT '',
    email         VARCHAR(254) UNIQUE  DEFAULT '',
    activated     BOOLEAN     NOT NULL DEFAULT true,
    image_url     VARCHAR(256)         DEFAULT '',
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    role_id       INT,

    CONSTRAINT `role_key` FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE categories
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name varchar(50) NOT NULL DEFAULT '' COMMENT 'Tên danh mục, vd: đồ điện tử'
);

CREATE TABLE products
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    category_id INT,
    name       VARCHAR(50) NOT NULL DEFAULT '',
    thumnail   VARCHAR(254)         DEFAULT '',
    decription LONGTEXT,
    price      FLOAT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE tokens
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    token           varchar(255) UNIQUE NOT NULL,
--     revoked         tinyint(1) NOT NULL,
    expired_at      TIMESTAMP,
    created_at      TIMESTAMP,
    user_id         int,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

// thêm ro-le
SELECT * FROM quanpro.roles;
INSERT INTO roles (id, name) VALUES (1, "ADMIN");
INSERT INTO roles (id, name) VALUES (2, "USER");