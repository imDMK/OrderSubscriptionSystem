CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        status VARCHAR(20) NOT NULL,
                        total NUMERIC(19,2) NOT NULL
);

CREATE SEQUENCE order_items_seq START 1;

CREATE TABLE order_items (
                             id BIGINT PRIMARY KEY DEFAULT nextval('order_items_seq'),
                             order_id UUID NOT NULL REFERENCES orders(id),
                             product_id VARCHAR(255) NOT NULL,
                             quantity INT NOT NULL,
                             unit_price NUMERIC(19,2) NOT NULL
);
