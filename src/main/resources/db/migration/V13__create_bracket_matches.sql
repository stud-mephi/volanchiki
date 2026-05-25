-- Матчи плей-офф (турнирная сетка)
CREATE TABLE IF NOT EXISTS bracket_matches (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id),
    category_id INTEGER NOT NULL REFERENCES categories(id),
    group_id BIGINT,
    round_name VARCHAR(20) NOT NULL,
    round_number INTEGER NOT NULL,
    match_order INTEGER NOT NULL,
    player1_registration_id BIGINT REFERENCES registrations(id),
    player2_registration_id BIGINT REFERENCES registrations(id),
    winner_registration_id BIGINT REFERENCES registrations(id),
    score VARCHAR(50),
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    match_date TIMESTAMP,
    court_number INTEGER,
    next_match_id BIGINT REFERENCES bracket_matches(id),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (tournament_id, category_id, round_number, match_order)
);

CREATE INDEX idx_bracket_tournament ON bracket_matches(tournament_id, category_id);
CREATE INDEX idx_bracket_round ON bracket_matches(tournament_id, category_id, round_number);
CREATE INDEX idx_bracket_next ON bracket_matches(next_match_id);

-- Триггер
DROP TRIGGER IF EXISTS update_bracket_matches_updated_at ON bracket_matches;
CREATE TRIGGER update_bracket_matches_updated_at
    BEFORE UPDATE ON bracket_matches
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();