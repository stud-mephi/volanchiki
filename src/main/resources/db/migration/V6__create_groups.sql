CREATE TABLE IF NOT EXISTS groups (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id) ON DELETE CASCADE,
    category_id INTEGER NOT NULL REFERENCES categories(id),
    code VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    max_teams INTEGER DEFAULT 4,
    teams_count INTEGER DEFAULT 0,
    is_completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE(tournament_id, category_id, code)
);