CREATE TABLE t_order_1 (
     order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
     user_id BIGINT NOT NULL,
     product_id BIGINT NOT NULL,
     quantity INT NOT NULL,
     total_price DECIMAL(10, 2) NOT NULL,
     create_time DATETIME NOT NULL
);
CREATE TABLE t_order_2 (
       order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
       user_id BIGINT NOT NULL,
       product_id BIGINT NOT NULL,
       quantity INT NOT NULL,
       total_price DECIMAL(10, 2) NOT NULL,
       create_time DATETIME NOT NULL
);

