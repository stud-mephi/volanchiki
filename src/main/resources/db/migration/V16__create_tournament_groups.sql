-- Связь многие-ко-многим: турниры и группы
CREATE TABLE IF NOT EXISTS tournament_groups (
    tournament_id BIGINT NOT NULL REFERENCES tournaments(id) ON DELETE CASCADE,
    group_id BIGINT NOT NULL REFERENCES groups(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT NOW(),
    PRIMARY KEY (tournament_id, group_id)
);

-- Триггер
DROP TRIGGER IF EXISTS update_tournament_groups_updated_at ON tournament_groups;
CREATE TRIGGER update_tournament_groups_updated_at
    BEFORE UPDATE ON tournament_groups
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
