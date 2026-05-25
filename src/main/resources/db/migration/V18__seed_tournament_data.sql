-- ============================================
-- ТЕСТОВЫЕ ДАННЫЕ — Первенство МИФИ (Отборочный)
-- ============================================

-- 1. Организатор МИФИ
INSERT INTO organizers (email, password_hash, name, full_name, contact_phone, city, is_verified, is_active)
VALUES ('mephi@org.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Спортклуб МИФИ', 'Спортклуб МИФИ', '+74951234567', 'Москва', true, true)
ON CONFLICT (email) DO NOTHING;

-- 2. Игроки (только реальные, пароль у всех 123456)
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city) VALUES
('Костюченко Артем', 'kostyuchenko', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'MALE', 'kost@mephi.ru', 'Москва'),
('Дубов Леонид', 'dubov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'MALE', 'dubov@mephi.ru', 'Москва'),
('Тарачев Николай', 'tarachev', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'MALE', 'tarachev@mephi.ru', 'Москва'),
('Козлов Михаил', 'kozlov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'MALE', 'kozlov@mephi.ru', 'Москва'),
('Ткачев Арсений', 'tkachev', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'MALE', 'tkachev@mephi.ru', 'Москва'),
('Силаков Леонид', 'silakov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'MALE', 'silakov@mephi.ru', 'Москва'),
('Курцын Никита', 'kurcyn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'MALE', 'kurcyn@mephi.ru', 'Москва'),
('Сеин Алексей', 'sein', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'MALE', 'sein@mephi.ru', 'Москва'),
('Рыкова Дарья', 'rykova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'FEMALE', 'rykova@mephi.ru', 'Москва'),
('Ключинская Дарья', 'kluchinskaya', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'FEMALE', 'kluch@mephi.ru', 'Москва'),
('Удальцова Екатерина', 'udalcova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'FEMALE', 'udal@mephi.ru', 'Москва'),
('Мамедова Тамила', 'mamedova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'FEMALE', 'mamedova@mephi.ru', 'Москва'),
('Искандарова Марья', 'iskandarova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'FEMALE', 'marya@mephi.ru', 'Москва'),
('Уйманова Валерия', 'uimanova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'FEMALE', 'valeria@mephi.ru', 'Москва'),
('Гришило Татьяна', 'grishilo', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'FEMALE', 'tatyana@mephi.ru', 'Москва'),
('Ян Яди', 'yanyadi', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'FEMALE', 'yanyadi@mephi.ru', 'Москва'),
('Баталова Софья', 'batalova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'FEMALE', 'sofia@mephi.ru', 'Москва')
ON CONFLICT (email) DO NOTHING;

-- 3. Турнир
INSERT INTO tournaments (title, organizer_id, status, start_date, end_date, city, venue, description)
VALUES ('Первенство МИФИ — Отборочный этап', 1, 'COMPLETED', '2026-05-10', '2026-05-15', 'Москва', 'Спорткомплекс МИФИ', 'Отборочный этап первенства МИФИ по бадминтону')
ON CONFLICT (title) DO NOTHING;

-- 4. Категории (MS и WS)
INSERT INTO tournament_categories (tournament_id, category_id) VALUES (1, 1), (1, 2)
ON CONFLICT DO NOTHING;

-- 5. Регистрации — Мужчины (MS)
INSERT INTO registrations (user_id, tournament_id, category_id, status) VALUES
(1, 1, 1, 'CONFIRMED'),  -- Костюченко
(2, 1, 1, 'CONFIRMED'),  -- Дубов
(3, 1, 1, 'CONFIRMED'),  -- Тарачев
(4, 1, 1, 'CONFIRMED'),  -- Козлов
(5, 1, 1, 'CONFIRMED'),  -- Ткачев
(6, 1, 1, 'CONFIRMED'),  -- Силаков
(7, 1, 1, 'CONFIRMED'),  -- Курцын
(8, 1, 1, 'CONFIRMED')   -- Сеин
ON CONFLICT DO NOTHING;

-- Регистрации — Женщины (WS)
INSERT INTO registrations (user_id, tournament_id, category_id, status) VALUES
(9, 1, 2, 'CONFIRMED'),   -- Рыкова
(10, 1, 2, 'CONFIRMED'),  -- Ключинская
(11, 1, 2, 'CONFIRMED'),  -- Удальцова
(12, 1, 2, 'CONFIRMED'),  -- Мамедова
(13, 1, 2, 'CONFIRMED'),  -- Искандарова
(14, 1, 2, 'CONFIRMED'),  -- Уйманова
(15, 1, 2, 'CONFIRMED'),  -- Гришило
(16, 1, 2, 'CONFIRMED'),  -- Ян Яди
(17, 1, 2, 'CONFIRMED')   -- Баталова
ON CONFLICT DO NOTHING;

-- 6. Рейтинги
INSERT INTO ratings (user_id, category_type, category_gender, rating_value, games_played, wins, losses, win_rate) VALUES
(1, 'SINGLES', 'MALE', 1500, 4, 4, 0, 100),
(2, 'SINGLES', 'MALE', 1450, 4, 3, 1, 75),
(3, 'SINGLES', 'MALE', 1400, 3, 2, 1, 66.7),
(4, 'SINGLES', 'MALE', 1350, 3, 1, 2, 33.3),
(9, 'SINGLES', 'FEMALE', 1500, 3, 3, 0, 100),
(10, 'SINGLES', 'FEMALE', 1450, 4, 3, 1, 75)
ON CONFLICT (user_id, category_type, category_gender) DO UPDATE SET rating_value = EXCLUDED.rating_value;

-- 7. Олимпийская сетка — Мужская
INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status) VALUES
(1, 1, 'QUARTERFINAL', 3, 1, 5, 3, 3, '18:21, 18:21', 'COMPLETED'),
(1, 1, 'QUARTERFINAL', 3, 2, 8, 4, 4, '21:15, 16:21, 16:21', 'COMPLETED'),
(1, 1, 'SEMIFINAL', 2, 1, 1, 3, 1, '21:18, 21:16', 'COMPLETED'),
(1, 1, 'SEMIFINAL', 2, 2, 2, 4, 2, '21:18, 21:16', 'COMPLETED'),
(1, 1, 'FINAL', 1, 1, 1, 2, 1, '21:16, 21:15', 'COMPLETED');

-- Олимпийская сетка — Женская
INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status) VALUES
(1, 2, 'QUARTERFINAL', 3, 1, 10, 13, 10, '21:18, 21:18', 'COMPLETED'),
(1, 2, 'QUARTERFINAL', 3, 2, 15, 9, 9, '18:21, 14:21', 'COMPLETED'),
(1, 2, 'SEMIFINAL', 2, 1, 11, 10, 10, '14:21, 18:21', 'COMPLETED'),
(1, 2, 'SEMIFINAL', 2, 2, 12, 9, 9, '12:21, 9:21', 'COMPLETED'),
(1, 2, 'FINAL', 1, 1, 10, 9, 9, '16:21, 14:21', 'COMPLETED');
