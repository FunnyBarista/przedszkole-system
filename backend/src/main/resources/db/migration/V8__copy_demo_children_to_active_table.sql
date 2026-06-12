DO $$
BEGIN
    IF to_regclass('public.dziecko') IS NULL OR to_regclass('public.rodzic') IS NULL THEN
        RETURN;
    END IF;

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Basia', 'Rodzic', DATE '2020-09-18', 'obecna w przedszkolu', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'rodzic@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Basia' AND d.nazwisko = 'Rodzic');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Antek', 'Rodzic', DATE '2019-12-04', 'obecny w przedszkolu', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'rodzic@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Antek' AND d.nazwisko = 'Rodzic');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Ola', 'Kowalska', DATE '2020-03-14', 'obecna w przedszkolu', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'marta.kowalska@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Ola' AND d.nazwisko = 'Kowalska');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Jan', 'Kowalski', DATE '2019-11-02', 'odebrane przez rodzica', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'marta.kowalska@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Jan' AND d.nazwisko = 'Kowalski');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Maja', 'Kowalska', DATE '2021-04-07', 'odebrana przez babcie', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'marta.kowalska@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Maja' AND d.nazwisko = 'Kowalska');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Zofia', 'Nowak', DATE '2020-07-21', 'obecna w przedszkolu', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'tomasz.nowak@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Zofia' AND d.nazwisko = 'Nowak');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Lena', 'Nowak', DATE '2021-01-16', 'nieobecna - choroba', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'tomasz.nowak@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Lena' AND d.nazwisko = 'Nowak');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Igor', 'Nowak', DATE '2019-02-22', 'obecny w przedszkolu', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'tomasz.nowak@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Igor' AND d.nazwisko = 'Nowak');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Mikolaj', 'Wisniewski', DATE '2019-05-09', 'odebrane przez rodzica', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'katarzyna.wisniewska@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Mikolaj' AND d.nazwisko = 'Wisniewski');

    INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
    SELECT 'Filip', 'Wisniewski', DATE '2020-10-30', 'obecny w przedszkolu', r.id_rodzica
    FROM rodzic r
    WHERE r.email = 'katarzyna.wisniewska@test.pl'
      AND NOT EXISTS (SELECT 1 FROM dziecko d WHERE d.imie = 'Filip' AND d.nazwisko = 'Wisniewski');
END $$;
