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
         SELECT ''Antek'', ''Rodzic'', DATE ''2019-12-04'', ''obecny w przedszkolu'', r.%I
         FROM %s r
         WHERE r."email" = ''rodzic@test.pl''
           AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Antek'' AND d."nazwisko" = ''Rodzic'')',
        dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
    );

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
         SELECT ''Lena'', ''Nowak'', DATE ''2021-01-16'', ''nieobecna - choroba'', r.%I
         FROM %s r
         WHERE r."email" = ''tomasz.nowak@test.pl''
           AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Lena'' AND d."nazwisko" = ''Nowak'')',
        dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
    );

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
         SELECT ''Filip'', ''Wisniewski'', DATE ''2020-10-30'', ''obecny w przedszkolu'', r.%I
         FROM %s r
         WHERE r."email" = ''katarzyna.wisniewska@test.pl''
           AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Filip'' AND d."nazwisko" = ''Wisniewski'')',
        dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
    );

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
         SELECT ''Maja'', ''Kowalska'', DATE ''2021-04-07'', ''odebrana przez babcie'', r.%I
         FROM %s r
         WHERE r."email" = ''marta.kowalska@test.pl''
           AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Maja'' AND d."nazwisko" = ''Kowalska'')',
        dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
    );

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
         SELECT ''Igor'', ''Nowak'', DATE ''2019-02-22'', ''obecny w przedszkolu'', r.%I
         FROM %s r
         WHERE r."email" = ''tomasz.nowak@test.pl''
           AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Igor'' AND d."nazwisko" = ''Nowak'')',
        dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
    );
END $$;
