-- ============================================
-- ТЕСТОВЫЕ ДАННЫЕ — Первенство МИФИ (Отборочный)
-- ============================================

-- 1. Организатор МИФИ
INSERT INTO organizers (email, password_hash, name, full_name, contact_phone, city, is_verified, is_active)
SELECT 'mephi@org.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'Спортклуб МИФИ', 'Спортклуб МИФИ', '+74951234567', 'Москва', true, true
WHERE NOT EXISTS (SELECT 1 FROM organizers WHERE email = 'mephi@org.com');

-- 2. Игроки (пароль у всех 123456)
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Костюченко Артем', 'kostyuchenko', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'MALE', 'kost@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'kost@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Дубов Леонид', 'dubov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'MALE', 'dubov@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'dubov@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Тарачев Николай', 'tarachev', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'MALE', 'tarachev@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'tarachev@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Козлов Михаил', 'kozlov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'MALE', 'kozlov@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'kozlov@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Ткачев Арсений', 'tkachev', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'MALE', 'tkachev@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'tkachev@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Силаков Леонид', 'silakov', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'MALE', 'silakov@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'silakov@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Курцын Никита', 'kurcyn', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'MALE', 'kurcyn@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'kurcyn@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Сеин Алексей', 'sein', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'MALE', 'sein@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'sein@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Рыкова Дарья', 'rykova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2000-01-01', 'FEMALE', 'rykova@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'rykova@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Ключинская Дарья', 'kluchinskaya', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2001-01-01', 'FEMALE', 'kluch@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'kluch@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Удальцова Екатерина', 'udalcova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2002-01-01', 'FEMALE', 'udal@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'udal@mephi.ru');
INSERT INTO users (full_name, nickname, password_hash, birth_date, gender, email, city)
SELECT 'Мамедова Тамила', 'mamedova', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '2003-01-01', 'FEMALE', 'mamedova@mephi.ru', 'Москва' WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'mamedova@mephi.ru');

-- 3. Турнир
INSERT INTO tournaments (title, organizer_id, status, start_date, end_date, city, venue, description)
SELECT 'Первенство МИФИ — Отборочный этап', o.id, 'COMPLETED', '2026-05-10', '2026-05-15', 'Москва', 'Спорткомплекс МИФИ', 'Отборочный этап первенства МИФИ по бадминтону'
FROM organizers o WHERE o.email = 'mephi@org.com'
AND NOT EXISTS (SELECT 1 FROM tournaments WHERE title = 'Первенство МИФИ — Отборочный этап');

-- 4. Категории
INSERT INTO tournament_categories (tournament_id, category_id)
SELECT t.id, 1 FROM tournaments t WHERE t.title = 'Первенство МИФИ — Отборочный этап'
AND NOT EXISTS (SELECT 1 FROM tournament_categories tc WHERE tc.tournament_id = t.id AND tc.category_id = 1);

INSERT INTO tournament_categories (tournament_id, category_id)
SELECT t.id, 2 FROM tournaments t WHERE t.title = 'Первенство МИФИ — Отборочный этап'
AND NOT EXISTS (SELECT 1 FROM tournament_categories tc WHERE tc.tournament_id = t.id AND tc.category_id = 2);

-- 5. Регистрации — Мужчины
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'kost@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'dubov@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'tarachev@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'kozlov@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'tkachev@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'silakov@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'kurcyn@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 1, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'sein@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 1);

-- Женщины
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 2, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'rykova@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 2);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 2, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'kluch@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 2);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 2, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'udal@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 2);
INSERT INTO registrations (user_id, tournament_id, category_id, status)
SELECT u.id, t.id, 2, 'CONFIRMED' FROM users u, tournaments t WHERE u.email = 'mamedova@mephi.ru' AND t.title = 'Первенство МИФИ — Отборочный этап' AND NOT EXISTS (SELECT 1 FROM registrations r WHERE r.user_id = u.id AND r.tournament_id = t.id AND r.category_id = 2);

-- 6. Сетка — Мужская
INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 1, 'QUARTERFINAL', 3, 1, r1.id, r2.id, r2.id, '18:21, 18:21', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'tkachev@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'tarachev@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 1, 'QUARTERFINAL', 3, 2, r1.id, r2.id, r2.id, '21:15, 16:21, 16:21', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'sein@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'kozlov@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 1, 'SEMIFINAL', 2, 1, r1.id, r2.id, r1.id, '21:18, 21:16', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'kost@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'tarachev@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 1, 'SEMIFINAL', 2, 2, r1.id, r2.id, r1.id, '21:18, 21:16', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'dubov@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'kozlov@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 1, 'FINAL', 1, 1, r1.id, r2.id, r1.id, '21:16, 21:15', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'kost@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'dubov@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

-- Сетка — Женская
INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 2, 'QUARTERFINAL', 3, 1, r1.id, r2.id, r1.id, '21:18, 21:18', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'kluch@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'udal@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 2, 'QUARTERFINAL', 3, 2, r1.id, r2.id, r2.id, '18:21, 14:21', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'mamedova@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'rykova@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 2, 'SEMIFINAL', 2, 1, r1.id, r2.id, r2.id, '14:21, 18:21', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'udal@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'kluch@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 2, 'SEMIFINAL', 2, 2, r1.id, r2.id, r2.id, '12:21, 9:21', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'mamedova@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'rykova@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;

INSERT INTO bracket_matches (tournament_id, category_id, round_name, round_number, match_order, player1_registration_id, player2_registration_id, winner_registration_id, score, status)
SELECT t.id, 2, 'FINAL', 1, 1, r1.id, r2.id, r2.id, '16:21, 14:21', 'COMPLETED'
FROM tournaments t, registrations r1, registrations r2
WHERE t.title = 'Первенство МИФИ — Отборочный этап' AND r1.user_id = (SELECT id FROM users WHERE email = 'kluch@mephi.ru') AND r2.user_id = (SELECT id FROM users WHERE email = 'rykova@mephi.ru') AND r1.tournament_id = t.id AND r2.tournament_id = t.id;
