CREATE TABLE IF NOT EXISTS registrations (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id),
    category_id INTEGER NOT NULL REFERENCES categories(id),
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    partner_id BIGINT REFERENCES registrations(id),
    team_name VARCHAR(255),
    comment TEXT,
    needs_accommodation BOOLEAN DEFAULT FALSE,
    needs_transport BOOLEAN DEFAULT FALSE,
    rating_before INTEGER,
    rating_after INTEGER,
    games_played INTEGER DEFAULT 0,
    group_name VARCHAR(50),
    registered_at TIMESTAMP DEFAULT NOW(),
    confirmed_at TIMESTAMP,
    cancelled_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW(),
    UNIQUE (user_id, tournament_id, category_id)
);

CREATE INDEX idx_registrations_user ON registrations(user_id);
CREATE INDEX idx_registrations_tournament ON registrations(tournament_id);
CREATE INDEX idx_registrations_category ON registrations(category_id);
CREATE INDEX idx_registrations_status ON registrations(status);
CREATE INDEX idx_registrations_group ON registrations(group_name);