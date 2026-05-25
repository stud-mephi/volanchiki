-- Таблица турнирного положения в группах
CREATE TABLE IF NOT EXISTS group_standings (
    id BIGSERIAL PRIMARY KEY,
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id),
    group_name VARCHAR(50) NOT NULL,
    registration_id BIGINT NOT NULL REFERENCES registrations(id),
    matches_played INTEGER DEFAULT 0,
    matches_won INTEGER DEFAULT 0,
    matches_lost INTEGER DEFAULT 0,
    sets_won INTEGER DEFAULT 0,
    sets_lost INTEGER DEFAULT 0,
    points_scored INTEGER DEFAULT 0,
    points_conceded INTEGER DEFAULT 0,
    tournament_points INTEGER DEFAULT 0,
    points INTEGER DEFAULT 0,
    sets_diff INTEGER DEFAULT 0,
    group_place INTEGER,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (tournament_id, group_name, registration_id)
);

CREATE INDEX idx_standings_group ON group_standings(tournament_id, group_name);
CREATE INDEX idx_standings_registration ON group_standings(registration_id);

-- Триггер
DROP TRIGGER IF EXISTS update_group_standings_updated_at ON group_standings;
CREATE TRIGGER update_group_standings_updated_at
    BEFORE UPDATE ON group_standings
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();