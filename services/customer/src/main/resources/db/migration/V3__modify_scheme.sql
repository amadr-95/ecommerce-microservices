CREATE TABLE address
(
    id          INTEGER NOT NULL PRIMARY KEY,
    street      VARCHAR(255),
    zip_code    INTEGER NOT NULL,
    customer_id INTEGER NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS address_sequence_id INCREMENT BY 1;

ALTER TABLE address
    ADD CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id);

ALTER TABLE customer
    DROP COLUMN address;