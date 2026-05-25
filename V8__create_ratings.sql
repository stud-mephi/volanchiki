CREATE TABLE IF NOT EXISTS ratings (
    user_id BIGINT NOT NULL REFERENCES users(id),
    category_type VARCHAR(20) NOT NULL CHECK (category_type IN ('SINGLES', 'DOUBLES')),
    category_gender VARCHAR(10) NOT NULL CHECK (category_gender IN ('MALE', 'FEMALE', 'MIXED')),
    rating_value INTEGER NOT NULL DEFAULT 0,
    games_played INTEGER NOT NULL DEFAULT 0,
    wins INTEGER NOT NULL DEFAULT 0,
    losses INTEGER NOT NULL DEFAULT 0,
    win_rate DECIMAL(5,2),
    last_updated TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (user_id, category_type, category_gender)
);

CREATE TABLE IF NOT EXISTS rating_history (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    category_type VARCHAR(20) NOT NULL,
    category_gender VARCHAR(10) NOT NULL,
    old_rating INTEGER NOT NULL,
    new_rating INTEGER NOT NULL,
    change_value INTEGER NOT NULL,
    change_date TIMESTAMP DEFAULT NOW(),
    tournament_id BIGINT REFERENCES tournaments(id),
    organizer_id BIGINT REFERENCES organizers(id),
    reason TEXT
);

CREATE INDEX idx_ratings_user ON ratings(user_id);
CREATE INDEX idx_rating_history_user ON rating_history(user_id);