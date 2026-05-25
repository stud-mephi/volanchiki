-- Таблица рейтинговых позиций
CREATE TABLE IF NOT EXISTS ranking_positions (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    category_id INTEGER NOT NULL REFERENCES categories(id),
    category_type VARCHAR(20),
    category_gender VARCHAR(10),
    rank INTEGER NOT NULL,
    rating INTEGER,
    is_active BOOLEAN DEFAULT TRUE,
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_ranking_user ON ranking_positions(user_id);
CREATE INDEX idx_ranking_category ON ranking_positions(category_id);
CREATE INDEX idx_ranking_rank ON ranking_positions(rank);
CREATE INDEX idx_ranking_category_type ON ranking_positions(category_type);
CREATE INDEX idx_ranking_category_gender ON ranking_positions(category_gender);

-- Триггер
DROP TRIGGER IF EXISTS update_ranking_positions_updated_at ON ranking_positions;
CREATE TRIGGER update_ranking_positions_updated_at
    BEFORE UPDATE ON ranking_positions
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();