-- ============================================
-- Первенство МИФИ (основной этап)
-- ============================================

-- 1. Добавляем недостающих игроков
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city) VALUES
('Широков Денис', 'shirokov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'MALE', 'shirokov@mephi.ru', 'Москва'),
('Закурдаев Никита', 'zakurdaev', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'MALE', 'zakurdaev@mephi.ru', 'Москва'),
('Тихонов Артем', 'tihonov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'MALE', 'tihonov@mephi.ru', 'Москва'),
('Трофимов Владислав', 'trofimov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'MALE', 'trofimov@mephi.ru', 'Москва'),
('Гагарин Юрий', 'gagarin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'MALE', 'gagarin@mephi.ru', 'Москва'),
('Новиков Олег', 'novikov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'MALE', 'novikov@mephi.ru', 'Москва'),
('Михайлова Мария', 'mihailova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'FEMALE', 'mihailova@mephi.ru', 'Москва'),
('Лысоконь Валентина', 'lysokon', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'FEMALE', 'lysokon@mephi.ru', 'Москва'),
('Стефанова Вероника', 'stefanova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'FEMALE', 'stefanova@mephi.ru', 'Москва'),
('Кочанова Анна', 'kochanova', 'aekoch@5', '2006-10-11', 'FEMALE', 'aekochanova@gmail.com', 'Москва')
ON CONFLICT (email) DO NOTHING;

-- 2. Турнир
INSERT INTO tournaments (title, organizer_id, status, start_date, end_date, city, venue, description)
VALUES ('Первенство МИФИ', 1, 'COMPLETED', '2026-05-20', '2026-05-25', 'Москва', 'Спорткомплекс МИФИ', 'Основной этап первенства МИФИ по бадминтону')
ON CONFLICT (title) DO NOTHING;

-- 3. Категории
INSERT INTO tournament_categories (tournament_id, category_id) VALUES (2, 1), (2, 2)
ON CONFLICT DO NOTHING;

-- 4. Регистрации — Мужчины
INSERT INTO registrations (user_id, tournament_id, category_id, status) VALUES
(18, 2, 1, 'CONFIRMED'), -- Широков
(19, 2, 1, 'CONFIRMED'), -- Закурдаев
(20, 2, 1, 'CONFIRMED'), -- Тихонов
(3,  2, 1, 'CONFIRMED'), -- Тарачев
(21, 2, 1, 'CONFIRMED'), -- Трофимов
(22, 2, 1, 'CONFIRMED'), -- Гагарин
(23, 2, 1, 'CONFIRMED'), -- Новиков
(1,  2, 1, 'CONFIRMED')  -- Костюченко
ON CONFLICT DO NOTHING;

-- Регистрации — Женщины
INSERT INTO registrations (user_id, tournament_id, category_id, status) VALUES
(24, 2, 2, 'CONFIRMED'), -- Михайлова
(25, 2, 2, 'CONFIRMED'), -- Лысоконь
(12, 2, 2, 'CONFIRMED'), -- Мамедова
(26, 2, 2, 'CONFIRMED'), -- Стефанова
(27, 2, 2, 'CONFIRMED'), -- Кочанова
(10, 2, 2, 'CONFIRMED')  -- Ключинская
ON CONFLICT DO NOTHING;

-- 5. Олимпийская сетка — Мужская
INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status) VALUES
(2, 1, 'SEMIFINAL', 2, 1, 18, 22, 18, '21:18, 15:21, 21:16', 'COMPLETED'),
(2, 1, 'SEMIFINAL', 2, 2, 21, 20, 20, '16:21, 18:21', 'COMPLETED'),
(2, 1, 'FINAL', 1, 1, 18, 20, 18, '21:0, 21:0', 'COMPLETED');

-- Олимпийская сетка — Женская
INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status) VALUES
(2, 2, 'SEMIFINAL', 2, 1, 24, 26, 24, '22:20, 15:21, 21:16', 'COMPLETED'),
(2, 2, 'SEMIFINAL', 2, 2, 27, 25, 27, '21:19, 21:16', 'COMPLETED'),
(2, 2, 'FINAL', 1, 1, 24, 27, 24, '21:12, 23:21', 'COMPLETED');

-- 6. Рейтинги победителям
INSERT INTO ratings (user_id, category_type, category_gender, rating_value, games_played, wins, losses, win_rate) VALUES
(18, 'SINGLES', 'MALE', 1550, 3, 3, 0, 100),
(24, 'SINGLES', 'FEMALE', 1550, 3, 3, 0, 100)
ON CONFLICT (user_id, category_type, category_gender) DO UPDATE SET rating_value = EXCLUDED.rating_value;
