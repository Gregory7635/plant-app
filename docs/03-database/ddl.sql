-- DDL-скрипт инициализации базы данных Plant App
-- PostgreSQL

CREATE TABLE IF NOT EXISTS users (
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL DEFAULT 'ROLE_USER'
);

CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

CREATE TABLE IF NOT EXISTS plants (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    description   TEXT,
    wikipedia_url VARCHAR(500),
    created_at    TIMESTAMP DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_plants_name ON plants(name);

CREATE TABLE IF NOT EXISTS identifications (
    id         BIGSERIAL PRIMARY KEY,
    image_path VARCHAR(500),
    probability DOUBLE PRECISION NOT NULL CHECK (probability >= 0 AND probability <= 1),
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    user_id    BIGINT REFERENCES users(id) ON DELETE SET NULL,
    plant_id   BIGINT REFERENCES plants(id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_identifications_user_id   ON identifications(user_id);
CREATE INDEX IF NOT EXISTS idx_identifications_created_at ON identifications(created_at DESC);
