CREATE TABLE IF NOT EXISTS tournaments (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) UNIQUE NOT NULL,
    organizer_id BIGINT NOT NULL REFERENCES organizers(id),
    status VARCHAR(30) NOT NULL DEFAULT 'DRAFT',
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    city VARCHAR(100) NOT NULL,
    venue VARCHAR(255),
    address TEXT,
    min_age INTEGER,
    max_age INTEGER,
    max_participants INTEGER,
    registration_deadline DATE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT chk_dates CHECK (start_date <= end_date)
);

CREATE INDEX idx_tournaments_status ON tournaments(status);
CREATE INDEX idx_tournaments_city ON tournaments(city);
CREATE INDEX idx_tournaments_organizer ON tournaments(organizer_id);
CREATE INDEX idx_tournaments_date ON tournaments(start_date);

CREATE TABLE IF NOT EXISTS tournament_categories (
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id) ON DELETE CASCADE,
    category_id INTEGER NOT NULL REFERENCES categories(id),
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (tournament_id, category_id)
);