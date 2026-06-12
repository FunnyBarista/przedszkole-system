DO $$
DECLARE
    rodzic_table TEXT;
BEGIN
    IF to_regclass('public."Rodzic"') IS NOT NULL THEN
        rodzic_table := '"Rodzic"';
    ELSIF to_regclass('public.rodzic') IS NOT NULL THEN
        rodzic_table := 'rodzic';
    ELSE
        RETURN;
    END IF;

    EXECUTE format(
        'UPDATE %s SET "haslo" = CASE "email"
            WHEN ''admin@test.pl'' THEN ''$2a$10$zZPtkIQZEqBy7anx4xlMM.Gf9NKJnWxg2QQqTcChnn8BYuVzwA6DS''
            WHEN ''nauczyciel@test.pl'' THEN ''$2a$10$3clUJpImNNlAw/z0PtphHubZ7PwUyrtDDDRgURUrMLk1nUciG5i66''
            WHEN ''dyrekcja@test.pl'' THEN ''$2a$10$X8g3nkBHALfu6q54RYJ2V.67VnuXT2.tG1z8Z1sHSWtg0Ra1LeBB.''
            WHEN ''rodzic@test.pl'' THEN ''$2a$10$1SjN64ZngOuKcvMkveqRquKa3yhb/0QAQSldGNoAlzpf4ttZZPwte''
            ELSE "haslo"
         END
         WHERE "email" IN (''admin@test.pl'', ''nauczyciel@test.pl'', ''dyrekcja@test.pl'', ''rodzic@test.pl'')',
        rodzic_table
    );
END $$;
