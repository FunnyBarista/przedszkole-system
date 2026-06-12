INSERT INTO rodzic (imie, nazwisko, email, haslo, telefon, rola)
VALUES
    ('Marta', 'Kowalska', 'marta.kowalska@test.pl', '$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte', '501100200', 'RODZIC'),
    ('Tomasz', 'Nowak', 'tomasz.nowak@test.pl', '$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte', '501100201', 'RODZIC'),
    ('Katarzyna', 'Wisniewska', 'katarzyna.wisniewska@test.pl', '$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte', '501100202', 'RODZIC')
ON CONFLICT (email) DO NOTHING;

INSERT INTO dziecko (imie, nazwisko, data_urodzenia, status_pobytu, rodzic_id_rodzica)
SELECT dziecko.imie, dziecko.nazwisko, dziecko.data_urodzenia, dziecko.status_pobytu, rodzic.id_rodzica
FROM (
    VALUES
        ('Basia', 'Rodzic', DATE '2020-09-18', 'obecna w przedszkolu', 'rodzic@test.pl'),
        ('Antek', 'Rodzic', DATE '2019-12-04', 'obecny w przedszkolu', 'rodzic@test.pl'),
        ('Ola', 'Kowalska', DATE '2020-03-14', 'obecna w przedszkolu', 'marta.kowalska@test.pl'),
        ('Jan', 'Kowalski', DATE '2019-11-02', 'odebrane przez rodzica', 'marta.kowalska@test.pl'),
        ('Maja', 'Kowalska', DATE '2021-04-07', 'odebrana przez babcie', 'marta.kowalska@test.pl'),
        ('Zofia', 'Nowak', DATE '2020-07-21', 'obecna w przedszkolu', 'tomasz.nowak@test.pl'),
        ('Lena', 'Nowak', DATE '2021-01-16', 'nieobecna - choroba', 'tomasz.nowak@test.pl'),
        ('Igor', 'Nowak', DATE '2019-02-22', 'obecny w przedszkolu', 'tomasz.nowak@test.pl'),
        ('Mikolaj', 'Wisniewski', DATE '2019-05-09', 'odebrane przez rodzica', 'katarzyna.wisniewska@test.pl'),
        ('Filip', 'Wisniewski', DATE '2020-10-30', 'obecny w przedszkolu', 'katarzyna.wisniewska@test.pl')
) AS dziecko(imie, nazwisko, data_urodzenia, status_pobytu, email_rodzica)
JOIN rodzic ON rodzic.email = dziecko.email_rodzica
WHERE NOT EXISTS (
    SELECT 1
    FROM dziecko istniejace
    WHERE istniejace.imie = dziecko.imie
      AND istniejace.nazwisko = dziecko.nazwisko
      AND istniejace.data_urodzenia = dziecko.data_urodzenia
);
