-- =============================================
-- Добавление колонки role в таблицу users
-- =============================================

-- Добавляем колонку role с значением по умолчанию 'PLAYER'
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS role VARCHAR(20) NOT NULL DEFAULT 'PLAYER';

-- Добавляем CHECK constraint для ограничения значений
ALTER TABLE users 
ADD CONSTRAINT chk_user_role 
CHECK (role IN ('PLAYER', 'ORGANIZER'));

-- Создаём индекс для быстрого поиска по роли
CREATE INDEX IF NOT EXISTS idx_users_role ON users(role);

-- Комментарий к колонке
COMMENT ON COLUMN users.role IS 'Роль пользователя: PLAYER - игрок, ORGANIZER - организатор';
