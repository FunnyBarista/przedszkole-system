INSERT INTO rodzic (imie, nazwisko, email, haslo, telefon, rola)
VALUES
    ('Admin', 'Systemu', 'admin@test.pl', '$2a$10$zZPtkIQZEqBy7anx4xlMM.Gf9NKJnWxg2QQqTcChnn8BYuVzwA6DS', '000000000', 'ADMIN'),
    ('Anna', 'Nauczyciel', 'nauczyciel@test.pl', '$2a$10$3clUJpImNNlAw/z0PtphHubZ7PwUyrtDDDRgURUrMLk1nUciG5i66', '111111111', 'NAUCZYCIEL'),
    ('Daria', 'Dyrekcja', 'dyrekcja@test.pl', '$2a$10$X8g3nkBHALfu6q54RYJ2V.67VnuXT2.tG1z8Z1sHSWtg0Ra1LeBB.', '222222222', 'DYREKCJA'),
    ('Roman', 'Rodzic', 'rodzic@test.pl', '$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte', '333333333', 'RODZIC')
ON CONFLICT (email) DO UPDATE
SET
    imie = EXCLUDED.imie,
    nazwisko = EXCLUDED.nazwisko,
    haslo = EXCLUDED.haslo,
    telefon = EXCLUDED.telefon,
    rola = EXCLUDED.rola;
