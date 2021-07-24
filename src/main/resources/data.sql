DROP TABLE IF EXISTS currency;
CREATE TABLE currency (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  iso_code VARCHAR(250) NOT NULL UNIQUE
);

INSERT INTO currency VALUES (1, 'USD');
INSERT INTO currency VALUES (2, 'EUR');
INSERT INTO currency VALUES (3, 'CHF');
INSERT INTO currency VALUES (4, 'CAD');
INSERT INTO currency VALUES (5, 'GBP');

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  amount DOUBLE NOT NULL,
  created_at TIMESTAMP NOT NULL,
  currency_source_id INT NOT NULL,
  currency_target_id INT NOT NULL,
  foreign key (currency_source_id) references currency,
  foreign key (currency_target_id) references currency
);