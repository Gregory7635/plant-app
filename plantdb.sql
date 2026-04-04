/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES  */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Дамп структуры для таблица public.identifications
CREATE TABLE IF NOT EXISTS "identifications" (
	"id" BIGINT NOT NULL,
	"created_at" TIMESTAMP NULL DEFAULT NULL,
	"image_path" VARCHAR(255) NULL DEFAULT NULL,
	"probability" DOUBLE PRECISION NOT NULL,
	"plant_id" BIGINT NULL DEFAULT NULL,
	"user_id" BIGINT NULL DEFAULT NULL,
	PRIMARY KEY ("id"),
	CONSTRAINT "fk4gvmmdspe3mdfr62tfb837k8r" FOREIGN KEY ("plant_id") REFERENCES "plants" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT "fkqlr7q2kpm2nckm963b140uj5r" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Дамп данных таблицы public.identifications: -1 rows
/*!40000 ALTER TABLE "identifications" DISABLE KEYS */;
INSERT INTO "identifications" ("id", "created_at", "image_path", "probability", "plant_id", "user_id") VALUES
	(13, '2026-04-14 14:06:34.039819', NULL, 0.754, 6, NULL),
	(14, '2026-04-14 14:10:32.408492', NULL, 0.754, 6, 1),
	(15, '2026-04-14 14:16:38.163479', NULL, 0.8698, 7, NULL),
	(16, '2026-04-14 14:17:14.203879', NULL, 0.754, 6, 2),
	(17, '2026-04-14 14:28:29.675401', NULL, 0.754, 6, NULL),
	(18, '2026-04-14 14:31:51.833753', NULL, 0.754, 6, NULL),
	(19, '2026-04-14 14:35:07.492291', NULL, 0.8698, 7, 1),
	(20, '2026-04-14 14:44:18.306119', NULL, 0.754, 6, 1),
	(21, '2026-04-14 14:52:50.952225', NULL, 0.754, 6, 1),
	(22, '2026-05-16 16:52:19.453233', NULL, 0.397, 8, 1),
	(23, '2026-05-16 17:39:06.823682', NULL, 0.397, 8, NULL),
	(24, '2026-05-16 17:42:02.403547', NULL, 0.397, 8, NULL),
	(25, '2026-05-16 17:42:12.173258', NULL, 0.0036, 9, NULL),
	(26, '2026-05-16 19:50:59.421721', NULL, 0.397, 8, NULL);
/*!40000 ALTER TABLE "identifications" ENABLE KEYS */;

-- Дамп структуры для таблица public.plants
CREATE TABLE IF NOT EXISTS "plants" (
	"id" BIGINT NOT NULL,
	"description" TEXT NULL DEFAULT NULL,
	"name" VARCHAR(255) NULL DEFAULT NULL,
	"wikipedia_url" VARCHAR(255) NULL DEFAULT NULL,
	"created_at" TIMESTAMP NULL DEFAULT NULL,
	PRIMARY KEY ("id")
);

-- Дамп данных таблицы public.plants: 4 rows
/*!40000 ALTER TABLE "plants" DISABLE KEYS */;
INSERT INTO "plants" ("id", "description", "name", "wikipedia_url", "created_at") VALUES
	(6, 'Ве́треница коро́нчатая — вид двудольных цветковых растений, включённый в род Ветреница (Anemone) семейства Лютиковые (Ranunculaceae). Типовой вид рода.', 'Anemone coronaria', 'https://ru.wikipedia.org/wiki/Anemone_coronaria', '2026-05-16 19:48:22'),
	(7, 'Описание не найдено', 'Echinocereus coccineus', 'https://ru.wikipedia.org/wiki/Echinocereus_coccineus', '2026-05-16 12:48:26'),
	(8, 'Каме́лия япо́нская — один из наиболее известных видов рода Камелия. Родиной камелии японской являются Япония и юго-западный Китай, она произрастает в диком виде в Шаньдуне, Тайване, южной Японии и Южной Корее на высоте 300—1100 метров. Является официальным цветочным символом штата Алабама.', 'Camellia japonica', 'https://ru.wikipedia.org/wiki/Camellia_japonica', '2026-05-16 11:48:35'),
	(9, 'Описание не найдено', 'Microstoma', 'https://ru.wikipedia.org/wiki/Microstoma', '2026-05-16 18:48:38');
/*!40000 ALTER TABLE "plants" ENABLE KEYS */;

-- Дамп структуры для таблица public.users
CREATE TABLE IF NOT EXISTS "users" (
	"id" BIGINT NOT NULL,
	"password" VARCHAR(255) NOT NULL,
	"role" VARCHAR(255) NULL DEFAULT NULL,
	"username" VARCHAR(255) NOT NULL,
	PRIMARY KEY ("id"),
	UNIQUE ("username")
);

-- Дамп данных таблицы public.users: -1 rows
/*!40000 ALTER TABLE "users" DISABLE KEYS */;
INSERT INTO "users" ("id", "password", "role", "username") VALUES
	(1, '$2a$10$iZv0jGGd1sWQB.LiKkaIl.1vL.qABeXwO77mg3ESPdt4xPJIFlKYi', 'ROLE_USER', 'test'),
	(2, '$2a$10$amGCumCM9Apa8mYMpbmceOkKAUxCb2WwyTflAobRCxcGIIcL3LGXW', 'ROLE_USER', 'test2');
/*!40000 ALTER TABLE "users" ENABLE KEYS */;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
