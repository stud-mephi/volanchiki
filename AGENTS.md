# AGENTS.md - Volanchiki Tournament System

## Сборка и запуск
```bash
./mvnw clean compile      # компиляция
./mvnw spring-boot:run    # запуск
./mvnw test               # тесты
psql -U postgres -c "CREATE DATABASE volanchiki;"  # создать БД (один раз)
```

## Стек с версиями
- Java 17
- Spring Boot 3.2.0
- PostgreSQL 15
- Flyway (миграции БД)
- jjwt 0.12.3
- MapStruct 1.5.5.Final
- springdoc-openapi 2.3.0

## Структура веток
- `feature/setup` — pom.xml и application.properties
- `feature/security` — JWT и Spring Security
- `feature/service` — бизнес-логика
- `feature/backend-controllers` — REST контроллеры
- `feature/dto` — DTO классы
- `feature/exception` — обработка ошибок
- `feature/application` — главный класс запуска
- `backend` — Entity-модели
- `backend-repositories` — репозитории
- `db-migration` — SQL миграции
- `html` — фронтенд
- `master` — финальная версия

## Соглашение о языках
- Бэкенд (Java) — код и комментарии на **английском**
- Фронтенд (HTML/CSS/JS) — названия файлов и текст на **русском**: `Glavnay.html`, `Kalendar.html`, `Ychastnikam.html`, `Organizatoram.html`
- БД (SQL) — названия таблиц и колонок на **английском**

## Don't touch zones
- Не редактировать существующие файлы миграций V1–V15 — только добавлять новые `V16__`, `V17__` и т.д.
- Не менять структуру таблиц БД напрямую — только через новый файл миграции
- Не коммитить `jwt.secret` в репозиторий — только через `application.properties`
- Не менять формулу рейтинга без согласования: `дельта = [100 - (РВ - РП)] / 10`
- Не коммитить напрямую в `master`
