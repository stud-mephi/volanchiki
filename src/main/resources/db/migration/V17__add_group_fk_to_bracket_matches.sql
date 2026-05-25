-- Добавляем внешний ключ для связи bracket_matches с группами
ALTER TABLE bracket_matches
    ADD CONSTRAINT fk_bracket_matches_group
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE SET NULL;

-- Триггер уже есть в V13, ничего не добавляем
