CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    nickname VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('MALE', 'FEMALE')),
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    city VARCHAR(100),
    avatar_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    tournaments_played INTEGER DEFAULT 0,
    first_tournament_date DATE,
    last_active_date DATE,
    verification_token VARCHAR(255),
    reset_token VARCHAR(255),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_nickname ON users(nickname);
CREATE INDEX idx_users_city ON users(city);
