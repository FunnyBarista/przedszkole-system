DO $$
DECLARE
    rodzic_table TEXT;
    dziecko_table TEXT;
    rodzic_id_col TEXT;
    dziecko_rodzic_col TEXT;
BEGIN
    IF to_regclass('public."Rodzic"') IS NOT NULL THEN
        rodzic_table := '"Rodzic"';
    ELSIF to_regclass('public.rodzic') IS NOT NULL THEN
        rodzic_table := 'rodzic';
    ELSE
        RETURN;
    END IF;

    IF to_regclass('public."Dziecko"') IS NOT NULL THEN
        dziecko_table := '"Dziecko"';
        dziecko_rodzic_col := 'Rodzic_id_rodzica';
    ELSIF to_regclass('public.dziecko') IS NOT NULL THEN
        dziecko_table := 'dziecko';
        dziecko_rodzic_col := 'rodzic_id_rodzica';
    ELSE
        RETURN;
    END IF;

    SELECT column_name INTO rodzic_id_col
    FROM information_schema.columns
    WHERE table_schema = 'public'
      AND lower(table_name) = replace(lower(rodzic_table), '"', '')
      AND lower(column_name) = 'id_rodzica'
    LIMIT 1;

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
         SELECT ''Basia'', ''Rodzic'', DATE ''2020-09-18'', ''obecna w przedszkolu'', r.%I
         FROM %s r
         WHERE r."email" = ''rodzic@test.pl''
           AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Basia'' AND d."nazwisko" = ''Rodzic'')',
        dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
    );

    EXECUTE format(
        'UPDATE %s SET "status_pobytu" = ''obecna w przedszkolu''
         WHERE "imie" IN (''Ola'', ''Zofia'')',
        dziecko_table
    );

    EXECUTE format(
        'UPDATE %s SET "status_pobytu" = ''odebrane przez rodzica''
         WHERE "imie" IN (''Jan'', ''Mikolaj'')',
        dziecko_table
    );
END $$;
