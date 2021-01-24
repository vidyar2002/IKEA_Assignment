DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS product_articles;


CREATE TABLE product_articles (
 ID INT PRIMARY KEY,
 name varchar(250) NOT NULL,
 status varchar(50) NOT NULL,
 active boolean not null default true
);

CREATE TABLE product_articles (
 ID INT PRIMARY KEY,
 product_id INT NOT NULL,
 art_id INT NOT NULL,
 quantity INT NOT NULL
);