CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    ref_id UUID
);

CREATE INDEX idx_users_email ON users(email);

CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    token_hash VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_refresh_tokens_user_id ON refresh_tokens(user_id);

CREATE TABLE access_codes (
    id UUID PRIMARY KEY,
    code VARCHAR(64) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL,
    ref_id UUID NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    expires_at timestamptz NOT NULL
);

CREATE INDEX idx_access_codes_code ON access_codes(code);