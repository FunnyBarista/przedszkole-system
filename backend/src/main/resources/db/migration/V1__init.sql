-- V1__init.sql (PostgreSQL)

CREATE TABLE "Rodzic" (
    "id_rodzica" BIGSERIAL PRIMARY KEY,
    "imie" VARCHAR(50) NOT NULL,
    "nazwisko" VARCHAR(50) NOT NULL,
    "email" VARCHAR(50) NOT NULL UNIQUE,
    "haslo" VARCHAR(200) NOT NULL,
    "telefon" VARCHAR(20) NOT NULL
);

CREATE TABLE "Dziecko" (
    "id_dziecka" BIGSERIAL PRIMARY KEY,
    "imie" TEXT NOT NULL,
    "nazwisko" TEXT NOT NULL,
    "data_urodzenia" DATE NOT NULL,
    "status_pobytu" TEXT NOT NULL,
    "Rodzic_id_rodzica" BIGINT NOT NULL,
    CONSTRAINT "Dziecko_Rodzic_FK"
        FOREIGN KEY ("Rodzic_id_rodzica")
        REFERENCES "Rodzic" ("id_rodzica")
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

CREATE INDEX "idx_Dziecko_Rodzic_id_rodzica"
    ON "Dziecko" ("Rodzic_id_rodzica");

CREATE TABLE "Powiadomienie" (
    "powiadomienie_id" BIGSERIAL PRIMARY KEY,
    "typ" TEXT NOT NULL,
    "tresc" TEXT NOT NULL,
    "data_wyslania" DATE NOT NULL,
    "status" TEXT NOT NULL,
    "Dziecko_id_dziecka" BIGINT NOT NULL,
    CONSTRAINT "Powiadomienie_Dziecko_FK"
        FOREIGN KEY ("Dziecko_id_dziecka")
        REFERENCES "Dziecko" ("id_dziecka")
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

CREATE INDEX "idx_Powiadomienie_Dziecko_id_dziecka"
    ON "Powiadomienie" ("Dziecko_id_dziecka");

CREATE TABLE "Zapis_stolowka" (
    "zapis_stolowka_id" BIGSERIAL PRIMARY KEY,
    "data_zapisu" DATE NOT NULL,
    "Dziecko_id_dziecka" BIGINT NOT NULL,
    CONSTRAINT "Zapis_stolowka_Dziecko_FK"
        FOREIGN KEY ("Dziecko_id_dziecka")
        REFERENCES "Dziecko" ("id_dziecka")
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

CREATE INDEX "idx_Zapis_stolowka_Dziecko_id_dziecka"
    ON "Zapis_stolowka" ("Dziecko_id_dziecka");

CREATE TABLE "Zajecia_dodatkowe" (
    "id_zajecie" BIGSERIAL PRIMARY KEY,
    "nazwa" TEXT NOT NULL,
    "opis" TEXT NOT NULL,
    "prowadzacy" TEXT NOT NULL,
    "dzien_tygodnia" TEXT NOT NULL,
    "godzina" TIME NOT NULL
);

CREATE TABLE "Zapis_zajecia" (
    "zapis_id" BIGSERIAL PRIMARY KEY,
    "data_zapisu" DATE NOT NULL,
    "Dziecko_id_dziecka" BIGINT NOT NULL,
    "Zajecia_dodatkowe_id_zajecie" BIGINT NOT NULL,
    CONSTRAINT "Zapis_zajecia_Dziecko_FK"
        FOREIGN KEY ("Dziecko_id_dziecka")
        REFERENCES "Dziecko" ("id_dziecka")
        ON UPDATE RESTRICT
        ON DELETE RESTRICT,
    CONSTRAINT "Zapis_zajecia_Zajecia_dodatkowe_FK"
        FOREIGN KEY ("Zajecia_dodatkowe_id_zajecie")
        REFERENCES "Zajecia_dodatkowe" ("id_zajecie")
        ON UPDATE RESTRICT
        ON DELETE RESTRICT
);

CREATE INDEX "idx_Zapis_zajecia_Dziecko_id_dziecka"
    ON "Zapis_zajecia" ("Dziecko_id_dziecka");

CREATE INDEX "idx_Zapis_zajecia_Zajecia_dodatkowe_id_zajecie"
    ON "Zapis_zajecia" ("Zajecia_dodatkowe_id_zajecie");

CREATE TABLE "Jadlospis" (
    "jadlospis_id" BIGSERIAL PRIMARY KEY,
    "data" DATE NOT NULL,
    "sniadanie" TEXT NOT NULL,
    "obiad" TEXT NOT NULL
);

CREATE TABLE "Aktualnosc" (
    "aktualnosc_id" BIGSERIAL PRIMARY KEY,
    "tytul" TEXT NOT NULL,
    "tresc" TEXT NOT NULL,
    "data_dodania" DATE NOT NULL,
    "typ" TEXT NOT NULL
);
