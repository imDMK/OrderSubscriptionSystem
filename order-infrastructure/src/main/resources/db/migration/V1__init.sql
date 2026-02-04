CREATE TABLE orders (
                        id UUID PRIMARY KEY,
                        status VARCHAR(20) NOT NULL
                            CHECK (status IN ('NEW', 'PAID', 'CANCELLED')),
                        total NUMERIC(19,2) NOT NULL
                            CHECK (total >= 0)
);

CREATE SEQUENCE order_items_seq START 1;
CREATE TABLE order_items (
                             id BIGINT PRIMARY KEY DEFAULT nextval('order_items_seq'),
                             order_id UUID NOT NULL,
                             product_id VARCHAR(255) NOT NULL,
                             quantity INT NOT NULL
                                 CHECK (quantity > 0),
                             unit_price NUMERIC(19,2) NOT NULL
                                 CHECK (unit_price >= 0),

                             CONSTRAINT fk_order_items_order
                                 FOREIGN KEY (order_id)
                                     REFERENCES orders(id)
                                     ON DELETE CASCADE
);

CREATE TABLE subscriptions (
                               id UUID PRIMARY KEY,
                               order_id UUID NOT NULL UNIQUE,
                               status VARCHAR(20) NOT NULL
                                   CHECK (status IN ('ACTIVE', 'EXPIRED', 'CANCELLED')),
                               expires_at TIMESTAMP NULL,

                               CONSTRAINT fk_subscriptions_order
                                   FOREIGN KEY (order_id)
                                       REFERENCES orders(id)
                                       ON DELETE RESTRICT
);
