CREATE TABLE IF NOT EXISTS customer
(
    id       INTEGER     NOT NULL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    email VARCHAR(25) NOT NULL,
    address VARCHAR(50) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS customer_sequence INCREMENT BY 1;