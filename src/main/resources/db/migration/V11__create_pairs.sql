-- Таблица пар для парных разрядов
CREATE TABLE IF NOT EXISTS pairs (
    id BIGSERIAL PRIMARY KEY,
    player1_id BIGINT NOT NULL REFERENCES users(id),
    player2_id BIGINT NOT NULL REFERENCES users(id),
    pair_type VARCHAR(10) NOT NULL CHECK (pair_type IN ('MD', 'WD', 'XD')),
    pair_name VARCHAR(255),
    pair_rating INTEGER DEFAULT 0,
    games_played INTEGER DEFAULT 0,
    wins INTEGER DEFAULT 0,
    losses INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    CONSTRAINT unique_pair UNIQUE (player1_id, player2_id, pair_type),
    CONSTRAINT chk_pair_order CHECK (player1_id < player2_id)
);

CREATE INDEX idx_pairs_player1 ON pairs(player1_id);
CREATE INDEX idx_pairs_player2 ON pairs(player2_id);
CREATE INDEX idx_pairs_type ON pairs(pair_type);

-- Триггер
DROP TRIGGER IF EXISTS update_pairs_updated_at ON pairs;
CREATE TRIGGER update_pairs_updated_at
    BEFORE UPDATE ON pairs
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();