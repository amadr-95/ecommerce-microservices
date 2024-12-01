INSERT INTO category (id, category) VALUES (nextval('category_sequence'), 'Electronics');
INSERT INTO category (id, category) VALUES (nextval('category_sequence'), 'Books');
INSERT INTO category (id, category) VALUES (nextval('category_sequence'), 'Clothing');
INSERT INTO category (id, category) VALUES (nextval('category_sequence'), 'Home');

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_sequence'), 'Smartphone', 'Latest model smartphone', 50, 699.99, 1);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_sequence'), 'Laptop', 'High performance laptop', 30, 999.99, 1);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_sequence'), 'T-shirt', 'Cotton t-shirt', 100, 19.99, 3);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_sequence'), 'Harry Potter', 'Harry Potter book', 50, 15.99, 2);

INSERT INTO product (id, name, description, available_quantity, price, category_id)
VALUES (nextval('product_sequence'), 'Motorized desktop', 'Luxury motorized desktop', 10, 499.99, 4);