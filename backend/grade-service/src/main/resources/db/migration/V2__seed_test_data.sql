-- Oceny bieżące (Jan Kowalczyk z matematyki i polskiego)
INSERT INTO grades (id, grade, date, description, type, weight, student_id, subject_id, semester) VALUES
                                                                                                 ('ffffffff-1111-1111-1111-111111111111', 85, '2026-03-10', 'Sprawdzian z ułamków', 'SPRAWDZIAN', 3, 'cccccccc-1111-1111-1111-111111111111', 'aaaaaaaa-1111-1111-1111-111111111111', 1),
                                                                                                 ('ffffffff-2222-2222-2222-222222222222', 92, '2026-03-15', 'Kartkówka', 'KARTKOWKA', 2, 'cccccccc-1111-1111-1111-111111111111', 'aaaaaaaa-1111-1111-1111-111111111111', 1),
                                                                                                 ('ffffffff-3333-3333-3333-333333333333', 78, '2026-03-12', 'Wypracowanie', 'ZADANIE_DOMOWE', 1, 'cccccccc-1111-1111-1111-111111111111', 'aaaaaaaa-2222-2222-2222-222222222222', 2);

-- Oceny semestralne
INSERT INTO semester_grades (id, grade, type, school_year, student_id, subject_id) VALUES
    ('99999999-1111-1111-1111-111111111111', 5, 'SEMESTRALNA', '2025/2026', 'cccccccc-1111-1111-1111-111111111111', 'aaaaaaaa-1111-1111-1111-111111111111');