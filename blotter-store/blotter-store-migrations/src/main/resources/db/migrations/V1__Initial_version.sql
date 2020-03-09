DROP TABLE IF EXISTS normalized_orders CASCADE;

CREATE TABLE normalized_orders (
  id                  VARCHAR(255)  NOT NULL,
  external_identifier VARCHAR(255)  NOT NULL,
  portfolio           VARCHAR(50)   NOT NULL,
  meta_type           VARCHAR(50)   NOT NULL,
  status              VARCHAR(50)   NOT NULL,
  author              VARCHAR(255)  NOT NULL,
  intent              VARCHAR(50)   NOT NULL,
  price              VARCHAR(255)  NOT NULL,
  instrument          VARCHAR(50)   NOT NULL,
  details             VARCHAR(4000) NOT NULL,
  `timestamp`         VARCHAR(255)  NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE normalized_orders
  ADD CONSTRAINT idx_normalized_orders_uniq UNIQUE (portfolio, instrument, meta_type, intent, status);
CREATE INDEX idx_normalized_orders_ext_id
  ON normalized_orders (external_identifier);
CREATE INDEX idx_normalized_orders_meta_type
  ON normalized_orders (meta_type);
CREATE INDEX idx_normalized_orders_status
  ON normalized_orders (status);
CREATE INDEX idx_normalized_orders_author
  ON normalized_orders (author);
CREATE INDEX idx_normalized_orders_intent
  ON normalized_orders (intent);
CREATE INDEX idx_normalized_orders_instrument
  ON normalized_orders (instrument);
CREATE INDEX idx_normalized_orders_portfolio
  ON normalized_orders (portfolio);
CREATE INDEX idx_normalized_orders_timestamp
  ON normalized_orders (`timestamp`);

