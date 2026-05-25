CREATE TABLE IF NOT EXISTS matches (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id),
    registration_id_1 BIGINT NOT NULL REFERENCES registrations(id),
    registration_id_2 BIGINT NOT NULL REFERENCES registrations(id),
    winner_id BIGINT REFERENCES registrations(id),
    score VARCHAR(50),
    rating_1_before INTEGER NOT NULL DEFAULT 0,
    rating_2_before INTEGER NOT NULL DEFAULT 0,
    delta INTEGER,
    match_round VARCHAR(20),
    match_number INTEGER,
    match_date TIMESTAMP,
    processed BOOLEAN DEFAULT FALSE,
    duration_minutes INTEGER,
    match_notes TEXT,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_matches_tournament ON matches(tournament_id);
CREATE INDEX idx_matches_processed ON matches(processed);