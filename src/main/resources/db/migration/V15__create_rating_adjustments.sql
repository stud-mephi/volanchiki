-- Таблица корректировок рейтинга
CREATE TABLE IF NOT EXISTS rating_adjustments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    old_rating INTEGER NOT NULL,
    new_rating INTEGER NOT NULL,
    change_value INTEGER NOT NULL,
    reason VARCHAR(500),
    organizer_id BIGINT REFERENCES organizers(id),
    is_approved BOOLEAN DEFAULT FALSE,
    approved_by BIGINT REFERENCES users(id),
    approved_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE INDEX idx_rating_adjustments_user ON rating_adjustments(user_id);
CREATE INDEX idx_rating_adjustments_approved ON rating_adjustments(is_approved);

-- Триггер
DROP TRIGGER IF EXISTS update_rating_adjustments_updated_at ON rating_adjustments;
CREATE TRIGGER update_rating_adjustments_updated_at
    BEFORE UPDATE ON rating_adjustments
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
