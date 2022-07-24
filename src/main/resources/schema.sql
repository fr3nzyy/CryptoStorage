CREATE SCHEMA IF NOT EXISTS crypto_schema;

CREATE TABLE IF NOT EXISTS crypto_schema.transactions
(
    id        integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    crypto_name VARCHAR(255),
    amount DECIMAL,
    transaction_time      TIMESTAMP WITH TIME ZONE,
    created_at timestamp
);

CREATE TABLE IF NOT EXISTS crypto_schema.bitcoin_wallet
(
    id        integer NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    amount_total DECIMAL,
    transaction_id integer REFERENCES crypto_schema.transactions (id),
    created_at timestamp default current_timestamp
);
