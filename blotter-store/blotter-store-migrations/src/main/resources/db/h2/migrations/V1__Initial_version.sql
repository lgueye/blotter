DROP TABLE IF EXISTS normalized_orders CASCADE;

DROP TABLE IF EXISTS stex_orders CASCADE;

DROP TABLE IF EXISTS fx_orders CASCADE;

CREATE TABLE normalized_orders (
  id                  VARCHAR(255) NOT NULL,
  external_identifier VARCHAR(255) NOT NULL,
  portfolio           VARCHAR(255) NOT NULL,
  meta_type           VARCHAR(255) NOT NULL,
  author              VARCHAR(255) NOT NULL,
  intent              VARCHAR(255) NOT NULL,
  amount              VARCHAR(255) NOT NULL,
  instrument          VARCHAR(255) NOT NULL,
  `timestamp`         VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE stex_orders (
  id                  VARCHAR(255) NOT NULL,
  external_identifier VARCHAR(255) NOT NULL,
  portfolio           VARCHAR(255) NOT NULL,
  meta_type           VARCHAR(255) NOT NULL,
  author              VARCHAR(255) NOT NULL,
  intent              VARCHAR(255) NOT NULL,
  amount              VARCHAR(255) NOT NULL,
  instrument          VARCHAR(255) NOT NULL,
  `timestamp`         VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE fx_orders (
  id                  VARCHAR(255) NOT NULL,
  external_identifier VARCHAR(255) NOT NULL,
  portfolio           VARCHAR(255) NOT NULL,
  meta_type           VARCHAR(255) NOT NULL,
  author              VARCHAR(255) NOT NULL,
  intent              VARCHAR(255) NOT NULL,
  amount              VARCHAR(255) NOT NULL,
  instrument          VARCHAR(255) NOT NULL,
  `timestamp`         VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE normalized_orders
  ADD CONSTRAINT idx_normalized_orders_uniq UNIQUE (portfolio, instrument, meta_type, intent);
CREATE INDEX idx_normalized_orders_ext_id
  ON normalized_orders (external_identifier);
CREATE INDEX idx_normalized_orders_meta_type
  ON normalized_orders (meta_type);
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

ALTER TABLE stex_orders
  ADD CONSTRAINT idx_stex_orders_uniq UNIQUE (portfolio, instrument, meta_type, intent);
CREATE INDEX idx_stex_orders_ext_id
  ON stex_orders (external_identifier);
CREATE INDEX idx_stex_orders_meta_type
  ON stex_orders (meta_type);
CREATE INDEX idx_stex_orders_author
  ON stex_orders (author);
CREATE INDEX idx_stex_orders_intent
  ON stex_orders (intent);
CREATE INDEX idx_stex_orders_instrument
  ON stex_orders (instrument);
CREATE INDEX idx_stex_orders_portfolio
  ON stex_orders (portfolio);
CREATE INDEX idx_stex_orders_timestamp
  ON stex_orders (`timestamp`);

ALTER TABLE fx_orders
  ADD CONSTRAINT idx_fx_orders_uniq UNIQUE (portfolio, instrument, meta_type, intent);
CREATE INDEX idx_fx_orders_ext_id
  ON fx_orders (external_identifier);
CREATE INDEX idx_fx_orders_meta_type
  ON fx_orders (meta_type);
CREATE INDEX idx_fx_orders_author
  ON fx_orders (author);
CREATE INDEX idx_fx_orders_intent
  ON fx_orders (intent);
CREATE INDEX idx_fx_orders_instrument
  ON fx_orders (instrument);
CREATE INDEX idx_fx_orders_portfolio
  ON fx_orders (portfolio);
CREATE INDEX idx_fx_orders_timestamp
  ON fx_orders (`timestamp`);
