-- Sesje (zrealizowane zajęcia) dla matematyki w 1A (teaching_assignment eeeeeeee-1111-...)
INSERT INTO session (id, teaching_assignment_id, description, date) VALUES
                                                                        ('33333333-1111-1111-1111-111111111111', 'eeeeeeee-1111-1111-1111-111111111111', 'Dodawanie i odejmowanie ułamków', '2026-07-06'),
                                                                        ('33333333-2222-2222-2222-222222222222', 'eeeeeeee-1111-1111-1111-111111111111', 'Mnożenie ułamków', '2026-07-08');

-- Sesja dla polskiego w 1A (teaching_assignment eeeeeeee-2222-...)
INSERT INTO session (id, teaching_assignment_id, description, date) VALUES
    ('33333333-3333-3333-3333-333333333333', 'eeeeeeee-2222-2222-2222-222222222222', 'Analiza wiersza Mickiewicza', '2026-07-07');

-- Frekwencja dla pierwszej sesji matematyki (uczniowie 1A: Jan Kowalczyk, Ola Zielińska)
INSERT INTO attendance (id, student_id, session_id, type) VALUES
                                                              ('44444444-1111-1111-1111-111111111111', 'cccccccc-1111-1111-1111-111111111111', '33333333-1111-1111-1111-111111111111', 'OBECNOSC'),
                                                              ('44444444-2222-2222-2222-222222222222', 'cccccccc-2222-2222-2222-222222222222', '33333333-1111-1111-1111-111111111111', 'NIEOBECNOSC_USPRAWIEDLIWIONA');

-- Frekwencja dla drugiej sesji matematyki
INSERT INTO attendance (id, student_id, session_id, type) VALUES
                                                              ('44444444-3333-3333-3333-333333333333', 'cccccccc-1111-1111-1111-111111111111', '33333333-2222-2222-2222-222222222222', 'OBECNOSC'),
                                                              ('44444444-4444-4444-4444-444444444444', 'cccccccc-2222-2222-2222-222222222222', '33333333-2222-2222-2222-222222222222', 'SPOZNIENIE_NIEUSPRAWIEDLIWIONE');

-- Sprawdziany/zadania domowe dla matematyki w 1A
INSERT INTO assessment (id, group_id, teaching_assignment_id, description, date_made, date_due, type) VALUES
                                                                                                ('55555555-1111-1111-1111-111111111111', 'bbbbbbbb-1111-1111-1111-111111111111', 'eeeeeeee-1111-1111-1111-111111111111', 'Sprawdzian z ułamków', '2026-07-01', '2026-07-10', 'SPRAWDZIAN'),
                                                                                                ('55555555-2222-2222-2222-222222222222', 'bbbbbbbb-1111-1111-1111-111111111111', 'eeeeeeee-1111-1111-1111-111111111111', 'Zadania z podręcznika str. 45', '2026-07-06', '2026-07-08', 'ZADANIE_DOMOWE');

-- Kartkówka z polskiego w 1A
INSERT INTO assessment (id, group_id, teaching_assignment_id, description, date_made, date_due, type) VALUES
    ('55555555-3333-3333-3333-333333333333', 'bbbbbbbb-1111-1111-1111-111111111111','eeeeeeee-2222-2222-2222-222222222222', 'Kartkówka ze znajomości lektury', '2026-07-07', '2026-07-07', 'KARTKOWKA');