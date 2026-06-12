DO $$
DECLARE
    rodzic_table TEXT;
BEGIN
    IF to_regclass('public."Rodzic"') IS NOT NULL THEN
        rodzic_table := '"Rodzic"';
    ELSIF to_regclass('public.rodzic') IS NOT NULL THEN
        rodzic_table := 'rodzic';
    ELSE
        RAISE NOTICE 'Tabela rodzic/Rodzic nie istnieje - Hibernate utworzy kolumne rola przy starcie aplikacji.';
        RETURN;
    END IF;

    EXECUTE format(
        'ALTER TABLE %s ADD COLUMN IF NOT EXISTS "rola" VARCHAR(30) NOT NULL DEFAULT ''RODZIC''',
        rodzic_table
    );

    EXECUTE format(
        'UPDATE %s SET "rola" = ''RODZIC'' WHERE "rola" IS NULL',
        rodzic_table
    );

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "email", "haslo", "telefon", "rola")
         VALUES
            (''Admin'', ''Systemu'', ''admin@test.pl'', ''admin123'', ''000000000'', ''ADMIN''),
            (''Anna'', ''Nauczyciel'', ''nauczyciel@test.pl'', ''nauczyciel123'', ''111111111'', ''NAUCZYCIEL''),
            (''Daria'', ''Dyrekcja'', ''dyrekcja@test.pl'', ''dyrekcja123'', ''222222222'', ''DYREKCJA''),
            (''Roman'', ''Rodzic'', ''rodzic@test.pl'', ''rodzic123'', ''333333333'', ''RODZIC'')
         ON CONFLICT ("email") DO UPDATE SET "rola" = EXCLUDED."rola"',
        rodzic_table
    );
END $$;
