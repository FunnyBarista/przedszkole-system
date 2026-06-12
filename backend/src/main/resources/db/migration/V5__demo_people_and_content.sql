DO $$
DECLARE
    rodzic_table TEXT;
    dziecko_table TEXT;
    jadlospis_table TEXT;
    post_table TEXT;
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
    END IF;

    IF to_regclass('public."Jadlospis"') IS NOT NULL THEN
        jadlospis_table := '"Jadlospis"';
    ELSIF to_regclass('public.jadlospis') IS NOT NULL THEN
        jadlospis_table := 'jadlospis';
    END IF;

    IF to_regclass('public."Post"') IS NOT NULL THEN
        post_table := '"Post"';
    ELSIF to_regclass('public.post') IS NOT NULL THEN
        post_table := 'post';
    END IF;

    SELECT column_name INTO rodzic_id_col
    FROM information_schema.columns
    WHERE table_schema = 'public'
      AND lower(table_name) = replace(lower(rodzic_table), '"', '')
      AND lower(column_name) = 'id_rodzica'
    LIMIT 1;

    EXECUTE format(
        'INSERT INTO %s ("imie", "nazwisko", "email", "haslo", "telefon", "rola")
         VALUES
            (''Marta'', ''Kowalska'', ''marta.kowalska@test.pl'', ''$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte'', ''501100200'', ''RODZIC''),
            (''Tomasz'', ''Nowak'', ''tomasz.nowak@test.pl'', ''$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte'', ''501100201'', ''RODZIC''),
            (''Katarzyna'', ''Wisniewska'', ''katarzyna.wisniewska@test.pl'', ''$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte'', ''501100202'', ''RODZIC''),
            (''Ewa'', ''Zielinska'', ''ewa.zielinska@test.pl'', ''$2a$10$3clUJpImNNlAw/z0PtphHubZ7PwUyrtDDDRgURUrMLk1nUciG5i66'', ''501200300'', ''NAUCZYCIEL''),
            (''Piotr'', ''Lewandowski'', ''piotr.lewandowski@test.pl'', ''$2a$10$3clUJpImNNlAw/z0PtphHubZ7PwUyrtDDDRgURUrMLk1nUciG5i66'', ''501200301'', ''NAUCZYCIEL'')
         ON CONFLICT ("email") DO NOTHING',
        rodzic_table
    );

    IF dziecko_table IS NOT NULL THEN
        EXECUTE format(
            'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
             SELECT ''Ola'', ''Kowalska'', DATE ''2020-03-14'', ''aktywny'', r.%I FROM %s r WHERE r."email" = ''marta.kowalska@test.pl''
             AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Ola'' AND d."nazwisko" = ''Kowalska'')',
            dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
        );

        EXECUTE format(
            'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
             SELECT ''Jan'', ''Kowalski'', DATE ''2019-11-02'', ''aktywny'', r.%I FROM %s r WHERE r."email" = ''marta.kowalska@test.pl''
             AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Jan'' AND d."nazwisko" = ''Kowalski'')',
            dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
        );

        EXECUTE format(
            'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
             SELECT ''Zofia'', ''Nowak'', DATE ''2020-07-21'', ''aktywny'', r.%I FROM %s r WHERE r."email" = ''tomasz.nowak@test.pl''
             AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Zofia'' AND d."nazwisko" = ''Nowak'')',
            dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
        );

        EXECUTE format(
            'INSERT INTO %s ("imie", "nazwisko", "data_urodzenia", "status_pobytu", %I)
             SELECT ''Mikolaj'', ''Wisniewski'', DATE ''2019-05-09'', ''aktywny'', r.%I FROM %s r WHERE r."email" = ''katarzyna.wisniewska@test.pl''
             AND NOT EXISTS (SELECT 1 FROM %s d WHERE d."imie" = ''Mikolaj'' AND d."nazwisko" = ''Wisniewski'')',
            dziecko_table, dziecko_rodzic_col, rodzic_id_col, rodzic_table, dziecko_table
        );
    END IF;

    IF jadlospis_table IS NOT NULL THEN
        EXECUTE format(
            'INSERT INTO %s ("data", "sniadanie", "obiad")
             SELECT DATE ''2026-06-12'', ''Owsianka z owocami, herbata'', ''Zupa pomidorowa, ryz z indykiem i warzywami''
             WHERE NOT EXISTS (SELECT 1 FROM %s WHERE "data" = DATE ''2026-06-12'')',
            jadlospis_table, jadlospis_table
        );

        EXECUTE format(
            'INSERT INTO %s ("data", "sniadanie", "obiad")
             SELECT DATE ''2026-06-13'', ''Kanapki z twarozkiem, kakao'', ''Krupnik, makaron z sosem warzywnym''
             WHERE NOT EXISTS (SELECT 1 FROM %s WHERE "data" = DATE ''2026-06-13'')',
            jadlospis_table, jadlospis_table
        );
    END IF;

    IF post_table IS NOT NULL THEN
        EXECUTE format(
            'INSERT INTO %s ("tytul", "tresc", "opublikowany")
             SELECT ''Piknik rodzinny'', ''W piatek zapraszamy dzieci i rodzicow na piknik w ogrodzie przedszkolnym.'', TRUE
             WHERE NOT EXISTS (SELECT 1 FROM %s WHERE "tytul" = ''Piknik rodzinny'')',
            post_table, post_table
        );

        EXECUTE format(
            'INSERT INTO %s ("tytul", "tresc", "opublikowany")
             SELECT ''Zajecia plastyczne'', ''W przyszlym tygodniu dzieci przygotuja prace na wystawe koncowa.'', TRUE
             WHERE NOT EXISTS (SELECT 1 FROM %s WHERE "tytul" = ''Zajecia plastyczne'')',
            post_table, post_table
        );
    END IF;
END $$;
