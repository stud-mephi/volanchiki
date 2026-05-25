CREATE TABLE tournaments (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    -- Основная информация
    title VARCHAR(200) NOT NULL UNIQUE,  -- уникальное название турнира
    organizer_id BIGINT NOT NULL,        -- ссылка на организатора
    status VARCHAR(20) NOT NULL,         -- DRAFT, REGISTRATION_OPEN, etc
    
    -- Даты
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    registration_deadline DATE,
    
    -- Место проведения
    city VARCHAR(100) NOT NULL,
    venue VARCHAR(200),
    address TEXT,
    
    -- Возрастные ограничения
    min_age INTEGER,  -- NULL значит нет ограничения
    max_age INTEGER,  -- NULL значит нет ограничения
    age_description TEXT,  -- например "юниоры 2008-2010 г.р."
    
    -- Дополнительно
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Внешние ключи
    FOREIGN KEY (organizer_id) REFERENCES organizers(id)
);

-- Индексы для частых запросов
CREATE INDEX idx_tournaments_dates ON tournaments(start_date, end_date);
CREATE INDEX idx_tournaments_city ON tournaments(city);
CREATE INDEX idx_tournaments_status ON tournaments(status);
CREATE INDEX idx_tournaments_organizer ON tournaments(organizer_id);


CREATE TABLE categories (
    id INTEGER PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL,  -- WS, MS, WD, MD, XD
    name VARCHAR(50) NOT NULL,         -- "Women's Singles", "Men's Singles", etc
    type VARCHAR(20) NOT NULL,         -- SINGLES, DOUBLES, MIXED
    gender VARCHAR(10)                  -- MALE, FEMALE, MIXED
);

-- Заполняем справочник
INSERT INTO categories (id, code, name, type, gender) VALUES
    (1, 'WS', 'Women''s Singles', 'SINGLES', 'FEMALE'),
    (2, 'MS', 'Men''s Singles', 'SINGLES', 'MALE'),
    (3, 'WD', 'Women''s Doubles', 'DOUBLES', 'FEMALE'),
    (4, 'MD', 'Men''s Doubles', 'DOUBLES', 'MALE'),
    (5, 'XD', 'Mixed Doubles', 'MIXED', 'MIXED');



CREATE TABLE tournament_categories (
    tournament_id BIGINT NOT NULL,
    category_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    PRIMARY KEY (tournament_id, category_id),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);

-- Индекс для быстрого поиска по категориям
CREATE INDEX idx_tournament_categories_category ON tournament_categories(category_id);


CREATE TABLE groups (
    id INTEGER PRIMARY KEY,
    code VARCHAR(1) UNIQUE NOT NULL,  -- A, B, C, D, E, F, G
    name VARCHAR(50) NOT NULL,        -- "Group A", "Group B", etc
    description TEXT
);

-- Заполняем справочник
INSERT INTO groups (id, code, name) VALUES
    (1, 'A', 'Group A'),
    (2, 'B', 'Group B'),
    (3, 'C', 'Group C'),
    (4, 'D', 'Group D'),
    (5, 'E', 'Group E'),
    (6, 'F', 'Group F'),
    (7, 'G', 'Group G');



CREATE TABLE tournament_groups (
    tournament_id BIGINT NOT NULL,
    group_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    PRIMARY KEY (tournament_id, group_id),
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE,
    FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);

CREATE INDEX idx_tournament_groups_group ON tournament_groups(group_id);


CREATE TABLE users (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    full_name VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    gender CHAR(1) NOT NULL,  -- 'M' или 'F'
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Добавляем поля в существующую таблицу users
ALTER TABLE users ADD COLUMN IF NOT EXISTS city VARCHAR(100);
ALTER TABLE users ADD COLUMN IF NOT EXISTS avatar_url TEXT;
ALTER TABLE users ADD COLUMN IF NOT EXISTS is_active BOOLEAN DEFAULT TRUE;

-- Индекс для поиска
CREATE INDEX idx_users_nickname ON users(nickname);
CREATE INDEX idx_users_city ON users(city);
CREATE INDEX idx_users_full_name ON users(full_name);

CREATE TABLE ratings (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    category_type VARCHAR(20) NOT NULL, -- 'SINGLES' или 'DOUBLES'
    category_gender VARCHAR(10) NOT NULL, -- 'MALE', 'FEMALE', 'MIXED'
    rating_value INTEGER NOT NULL DEFAULT 0,
    games_played INTEGER NOT NULL DEFAULT 0, -- количество сыгранных матчей
    wins INTEGER NOT NULL DEFAULT 0, -- побед
    losses INTEGER NOT NULL DEFAULT 0, -- поражений
    win_rate DECIMAL(5,2), -- процент побед (вычисляемое поле)
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_tournament_id BIGINT, -- последний турнир, повлиявший на рейтинг
    
    -- Уникальность: у пользователя может быть только одна запись для каждой категории
    UNIQUE(user_id, category_type, category_gender),
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (last_tournament_id) REFERENCES tournaments(id) ON DELETE SET NULL
);

-- Индексы для быстрой сортировки и фильтрации
CREATE INDEX idx_ratings_value ON ratings(rating_value DESC);
CREATE INDEX idx_ratings_category ON ratings(category_type, category_gender);
CREATE INDEX idx_ratings_user ON ratings(user_id);
CREATE INDEX idx_ratings_win_rate ON ratings(win_rate DESC);

-- Составной индекс для частых запросов
CREATE INDEX idx_ratings_lookup ON ratings(category_type, category_gender, rating_value DESC);

CREATE TABLE rating_history (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    category_type VARCHAR(20) NOT NULL,
    category_gender VARCHAR(10) NOT NULL,
    old_rating INTEGER NOT NULL,
    new_rating INTEGER NOT NULL,
    change_value INTEGER NOT NULL, -- new - old
    change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tournament_id BIGINT, -- если изменение связано с турниром
    organizer_id BIGINT, -- если ручная правка
    reason TEXT, -- причина изменения
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE SET NULL,
    FOREIGN KEY (organizer_id) REFERENCES organizers(id) ON DELETE SET NULL
);

-- Индексы для истории
CREATE INDEX idx_history_user ON rating_history(user_id, change_date DESC);
CREATE INDEX idx_history_date ON rating_history(change_date);
CREATE INDEX idx_history_tournament ON rating_history(tournament_id);


CREATE TABLE ranking_positions (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,
    category_type VARCHAR(20) NOT NULL,
    category_gender VARCHAR(10) NOT NULL,
    position INTEGER NOT NULL, -- 1, 2, 3, ...
    rating_value INTEGER NOT NULL,
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    UNIQUE(user_id, category_type, category_gender),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Индекс для быстрого получения топа
CREATE INDEX idx_positions_top ON ranking_positions(category_type, category_gender, position);

CREATE TABLE ratings (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,                    -- <- ССЫЛАЕТСЯ на users.id
    category_type VARCHAR(20) NOT NULL,         -- 'SINGLES' или 'DOUBLES'
    category_gender VARCHAR(10) NOT NULL,       -- 'MALE', 'FEMALE', 'MIXED'
    rating_value INTEGER NOT NULL DEFAULT 0,
    games_played INTEGER NOT NULL DEFAULT 0,
    wins INTEGER NOT NULL DEFAULT 0,
    losses INTEGER NOT NULL DEFAULT 0,
    win_rate DECIMAL(5,2),
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_tournament_id BIGINT,
    
    -- Внешний ключ, связывающий с users
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    
    -- Уникальность: у пользователя только одна запись на категорию
    UNIQUE(user_id, category_type, category_gender)
);

CREATE TABLE rating_history (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    user_id BIGINT NOT NULL,                    -- <- ССЫЛАЕТСЯ на users.id
    category_type VARCHAR(20) NOT NULL,
    category_gender VARCHAR(10) NOT NULL,
    old_rating INTEGER NOT NULL,
    new_rating INTEGER NOT NULL,
    change_value INTEGER NOT NULL,
    change_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    tournament_id BIGINT,
    organizer_id BIGINT,
    reason TEXT,
    
    -- Внешний ключ к users
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE organizers (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    
    -- Учетные данные для входа
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    
    -- Основная информация
    name VARCHAR(200) NOT NULL,              -- Название организации или ФИО
    contact_phone VARCHAR(30) NOT NULL,       -- Контактный номер
    city VARCHAR(100) NOT NULL,               -- Город
    
    -- Дополнительная информация
    description TEXT,                          -- Описание организации
    website VARCHAR(255),                      -- Сайт
    logo_url TEXT,                             -- Логотип
    inn VARCHAR(20),                            -- ИНН (для официальных турниров)
    
    -- Статус и настройки
    is_verified BOOLEAN DEFAULT FALSE,         -- Подтвержден ли организатор админом
    is_active BOOLEAN DEFAULT TRUE,            -- Активен ли аккаунт
    
    -- Даты
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,                       -- Последний вход
    
    -- Для восстановления пароля
    reset_token VARCHAR(255),
    reset_token_expiry TIMESTAMP
);

-- Индексы для быстрого поиска
CREATE INDEX idx_organizers_email ON organizers(email);
CREATE INDEX idx_organizers_city ON organizers(city);
CREATE INDEX idx_organizers_name ON organizers(name);
CREATE INDEX idx_organizers_verified ON organizers(is_verified) WHERE is_verified = TRUE;

-- Добавляем внешний ключ в таблицу tournaments
ALTER TABLE tournaments 
ADD COLUMN organizer_id BIGINT NOT NULL,
ADD FOREIGN KEY (organizer_id) REFERENCES organizers(id) ON DELETE RESTRICT;

-- Индекс для связи
CREATE INDEX idx_tournaments_organizer ON tournaments(organizer_id);

CREATE TABLE registrations (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    
    -- Связи (кто, куда, в какой категории)
    user_id BIGINT NOT NULL,
    tournament_id BIGINT NOT NULL,
    category_id INTEGER NOT NULL,  -- ссылка на categories (WS, MS, WD, MD, XD)
    
    -- Для парных категорий
    partner_id BIGINT,              -- ID партнера (если парная категория)
    team_name VARCHAR(100),          -- Название команды (опционально)
    
    -- Статус регистрации
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',  -- PENDING, CONFIRMED, CANCELLED, REJECTED, WAITLISTED
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    confirmed_at TIMESTAMP,          -- когда подтвердили (если нужно)
    cancelled_at TIMESTAMP,          -- когда отменили
    
    -- Дополнительная информация при регистрации
    comment TEXT,                    -- комментарий от участника
    needs_accommodation BOOLEAN DEFAULT FALSE,  -- нуждается в проживании
    needs_transport BOOLEAN DEFAULT FALSE,      -- нуждается в трансфере
    
    -- Результаты (заполняются после турнира)
    place INTEGER,                    -- место (1, 2, 3, и т.д.)
    points_earned INTEGER,             -- очки, начисленные за турнир
    games_played INTEGER,              -- количество сыгранных матчей
    wins INTEGER,                      -- побед
    losses INTEGER,                    -- поражений
    
    -- Для сетки турнира (если нужно)
    seed INTEGER,                      -- посев (номер в сетке)
    group_name VARCHAR(10),             -- группа (A, B, C, ...)
    
    -- Аудит
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Внешние ключи
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE RESTRICT,
    FOREIGN KEY (partner_id) REFERENCES users(id) ON DELETE SET NULL,
    
    -- Уникальность: один пользователь может зарегистрироваться в категорию только один раз
    UNIQUE(user_id, tournament_id, category_id)
);

-- Индексы для быстрых запросов
CREATE INDEX idx_registrations_user ON registrations(user_id);
CREATE INDEX idx_registrations_tournament ON registrations(tournament_id);
CREATE INDEX idx_registrations_category ON registrations(category_id);
CREATE INDEX idx_registrations_status ON registrations(status);
CREATE INDEX idx_registrations_partner ON registrations(partner_id) WHERE partner_id IS NOT NULL;

-- Составные индексы для частых запросов
CREATE INDEX idx_registrations_tournament_status ON registrations(tournament_id, status);
CREATE INDEX idx_registrations_user_status ON registrations(user_id, status);
CREATE INDEX idx_registrations_tournament_category ON registrations(tournament_id, category_id);


-- Добавляем CHECK constraint для статусов
ALTER TABLE registrations 
ADD CONSTRAINT check_registration_status 
CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELLED', 'REJECTED', 'WAITLISTED'));

-- Комментарий к полю
COMMENT ON COLUMN registrations.status IS 'PENDING - ожидает, CONFIRMED - подтвержден, CANCELLED - отменен, REJECTED - отклонен, WAITLISTED - лист ожидания';

-- Добавляем поля для отслеживания статуса игрока
ALTER TABLE users 
ADD COLUMN first_tournament_date DATE,           -- дата первого турнира
ADD COLUMN tournaments_played INTEGER DEFAULT 0,  -- количество сыгранных турниров
ADD COLUMN is_newbie BOOLEAN GENERATED ALWAYS AS (tournaments_played <= 5) STORED, -- новичок ли
ADD COLUMN initial_rating INTEGER,                -- стартовый рейтинг, выставленный организатором
ADD COLUMN last_active_date DATE;                  -- дата последнего турнира

-- Индекс для поиска новичков
CREATE INDEX idx_users_newbie ON users(is_newbie) WHERE is_newbie = TRUE;

CREATE TABLE matches (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    
    -- Связи
    tournament_id BIGINT NOT NULL,
    registration_id_1 BIGINT NOT NULL,  -- первый игрок/пара
    registration_id_2 BIGINT NOT NULL,  -- второй игрок/пара
    
    -- Результат
    winner_id BIGINT NOT NULL,           -- кто победил (ссылка на registration_id)
    score VARCHAR(50) NOT NULL,           -- счет (например, "21-15, 21-18")
    
    -- Рейтинги до матча
    rating_1_before INTEGER NOT NULL,
    rating_2_before INTEGER NOT NULL,
    
    -- Дельта (изменение) - положительная для победителя, отрицательная для проигравшего
    delta INTEGER,                         -- будет вычислено по формуле
    
    -- Статус
    match_round VARCHAR(20),                -- 1/16, 1/8, 1/4, 1/2, FINAL
    match_number INTEGER,                    -- номер матча в турнире
    match_date TIMESTAMP,
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (tournament_id) REFERENCES tournaments(id) ON DELETE CASCADE,
    FOREIGN KEY (registration_id_1) REFERENCES registrations(id) ON DELETE CASCADE,
    FOREIGN KEY (registration_id_2) REFERENCES registrations(id) ON DELETE CASCADE,
    FOREIGN KEY (winner_id) REFERENCES registrations(id) ON DELETE CASCADE,
    
    -- Уникальность: не может быть двух одинаковых матчей в турнире
    UNIQUE(tournament_id, match_number)
);

-- Индексы
CREATE INDEX idx_matches_tournament ON matches(tournament_id);
CREATE INDEX idx_matches_registration1 ON matches(registration_id_1);
CREATE INDEX idx_matches_registration2 ON matches(registration_id_2);
CREATE INDEX idx_matches_winner ON matches(winner_id);

-- Добавляем поля для рейтинга на момент регистрации
ALTER TABLE registrations
ADD COLUMN rating_before_tournament INTEGER,  -- рейтинг до начала турнира
ADD COLUMN rating_after_tournament INTEGER,   -- рейтинг после турнира
ADD COLUMN delta_total INTEGER;                -- общее изменение за турнир

CREATE TABLE rating_adjustments (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    
    user_id BIGINT NOT NULL,
    organizer_id BIGINT NOT NULL,
    
    category_type VARCHAR(20) NOT NULL,      -- SINGLES или DOUBLES
    category_gender VARCHAR(10) NOT NULL,    -- MALE, FEMALE, MIXED
    
    old_rating INTEGER NOT NULL,
    new_rating INTEGER NOT NULL,
    adjustment INTEGER NOT NULL,              -- на сколько увеличили
    
    reason TEXT NOT NULL,                      -- причина (например, "прогресс в спортшколе")
    last_tournament_date DATE,                  -- дата последнего турнира
    adjustment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    -- Подтверждение
    is_approved BOOLEAN DEFAULT FALSE,
    approved_by BIGINT,                         -- кто утвердил (админ)
    approved_date TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (organizer_id) REFERENCES organizers(id) ON DELETE CASCADE,
    FOREIGN KEY (approved_by) REFERENCES organizers(id) ON DELETE SET NULL
);

CREATE INDEX idx_adjustments_user ON rating_adjustments(user_id);
CREATE INDEX idx_adjustments_pending ON rating_adjustments(is_approved) WHERE is_approved = FALSE;

CREATE OR REPLACE FUNCTION calculate_delta(
    winner_rating INTEGER,
    loser_rating INTEGER
) RETURNS INTEGER AS $$
DECLARE
    rating_diff INTEGER;
    delta NUMERIC;
BEGIN
    -- Разница рейтингов
    rating_diff := winner_rating - loser_rating;
    
    -- Если победитель сильнее на 100+, дельта = 0
    IF rating_diff >= 100 THEN
        RETURN 0;
    END IF;
    
    -- Формула: [100 - (РВ - РП)] / 10
    delta := (100.0 - rating_diff) / 10.0;
    
    -- Округляем до целого
    RETURN ROUND(delta);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION process_match_result(
    p_match_id BIGINT
) RETURNS VOID AS $$
DECLARE
    v_match RECORD;
    v_winner_reg RECORD;
    v_loser_reg RECORD;
    v_winner_user_id BIGINT;
    v_loser_user_id BIGINT;
    v_delta INTEGER;
    v_new_winner_rating INTEGER;
    v_new_loser_rating INTEGER;
    v_tournament_date DATE;
BEGIN
    -- Получаем данные матча
    SELECT * INTO v_match FROM matches WHERE id = p_match_id;
    
    -- Получаем регистрации
    SELECT * INTO v_winner_reg FROM registrations WHERE id = v_match.winner_id;
    SELECT * INTO v_loser_reg FROM registrations 
    WHERE id = CASE 
        WHEN v_match.winner_id = v_match.registration_id_1 
        THEN v_match.registration_id_2 
        ELSE v_match.registration_id_1 
    END;
   -- Получаем user_id
    v_winner_user_id := v_winner_reg.user_id;
    v_loser_user_id := v_loser_reg.user_id;
    
    -- Получаем дату турнира
    SELECT start_date INTO v_tournament_date FROM tournaments WHERE id = v_match.tournament_id;
    
    -- Вычисляем дельту
    v_delta := calculate_delta(v_match.rating_1_before, v_match.rating_2_before);
    
    -- Обновляем рейтинг победителя (увеличиваем)
    UPDATE ratings 
    SET rating_value = rating_value + v_delta,
        games_played = games_played + 1,
        wins = wins + 1,
        last_updated = NOW()
    WHERE user_id = v_winner_user_id 
      AND category_type = v_winner_reg.category_type 
      AND category_gender = v_winner_reg.category_gender
    RETURNING rating_value INTO v_new_winner_rating;
    
    -- Обновляем рейтинг проигравшего (уменьшаем, но не ниже 1)
    UPDATE ratings 
    SET rating_value = GREATEST(1, rating_value - v_delta),
        games_played = games_played + 1,
        losses = losses + 1,
        last_updated = NOW()
    WHERE user_id = v_loser_user_id 
      AND category_type = v_loser_reg.category_type 
      AND category_gender = v_loser_reg.category_gender
    RETURNING rating_value INTO v_new_loser_rating;
    
    -- Записываем дельту в матч
    UPDATE matches SET delta = v_delta WHERE id = p_match_id;
    
    -- Записываем историю для победителя
    INSERT INTO rating_history (
        user_id, category_type, category_gender, 
        old_rating, new_rating, change_value, 
        tournament_id, reason
    ) VALUES (
        v_winner_user_id, v_winner_reg.category_type, v_winner_reg.category_gender,
        v_match.rating_1_before, v_new_winner_rating, v_delta,
        v_match.tournament_id, 'Победа в матче'
    );
    
    -- Записываем историю для проигравшего
    INSERT INTO rating_history (
        user_id, category_type, category_gender, 
        old_rating, new_rating, change_value, 
        tournament_id, reason
    ) VALUES (
        v_loser_user_id, v_loser_reg.category_type, v_loser_reg.category_gender,
        v_match.rating_2_before, v_new_loser_rating, -v_delta,
        v_match.tournament_id, 'Поражение в матче'
    );
    
    -- Обновляем количество турниров у игроков (если это их первый матч в турнире)
    UPDATE users 
    SET tournaments_played = tournaments_played + 1,
        first_tournament_date = COALESCE(first_tournament_date, v_tournament_date),
        last_active_date = v_tournament_date
    WHERE id IN (v_winner_user_id, v_loser_user_id)
      AND NOT EXISTS (
          SELECT 1 FROM matches m2 
          WHERE m2.tournament_id = v_match.tournament_id 
            AND (m2.registration_id_1 IN (v_match.registration_id_1, v_match.registration_id_2)
                 OR m2.registration_id_2 IN (v_match.registration_id_1, v_match.registration_id_2))
            AND m2.id < v_match.id
      );
    
END;
$$ LANGUAGE plpgsql;



