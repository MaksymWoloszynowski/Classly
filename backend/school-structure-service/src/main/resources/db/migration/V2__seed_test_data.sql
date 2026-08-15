-- Nauczyciele
INSERT INTO teachers (id, first_name, last_name) VALUES
                                                     ('11111111-1111-1111-1111-111111111111', 'Anna', 'Kowalska'),
                                                     ('22222222-2222-2222-2222-222222222222', 'Piotr', 'Nowak'),
                                                     ('33333333-3333-3333-3333-333333333333', 'Maria', 'Wiśniewska');

-- Przedmioty
INSERT INTO subjects (id, name) VALUES
                                    ('aaaaaaaa-1111-1111-1111-111111111111', 'Matematyka'),
                                    ('aaaaaaaa-2222-2222-2222-222222222222', 'Język polski'),
                                    ('aaaaaaaa-3333-3333-3333-333333333333', 'Fizyka');

-- Grupy (z wychowawcą)
INSERT INTO school_groups (id, name, homeroom_teacher_id) VALUES
                                                              ('bbbbbbbb-1111-1111-1111-111111111111', '1A', '11111111-1111-1111-1111-111111111111'),
                                                              ('bbbbbbbb-2222-2222-2222-222222222222', '1B', '22222222-2222-2222-2222-222222222222');

-- Uczniowie
INSERT INTO students (id, first_name, last_name, date_of_birth, group_id) VALUES
                                                                              ('cccccccc-1111-1111-1111-111111111111', 'Jan', 'Kowalczyk', '2010-03-15', 'bbbbbbbb-1111-1111-1111-111111111111'),
                                                                              ('cccccccc-2222-2222-2222-222222222222', 'Ola', 'Zielińska', '2010-07-22', 'bbbbbbbb-1111-1111-1111-111111111111'),
                                                                              ('cccccccc-3333-3333-3333-333333333333', 'Kacper', 'Wójcik', '2010-01-05', 'bbbbbbbb-2222-2222-2222-222222222222');

-- Rodzice
INSERT INTO parents (id, first_name, last_name) VALUES
                                                    ('dddddddd-1111-1111-1111-111111111111', 'Ewa', 'Kowalczyk'),
                                                    ('dddddddd-2222-2222-2222-222222222222', 'Tomasz', 'Zieliński');

-- Relacje rodzic-dziecko
INSERT INTO parents_students (parent_id, student_id) VALUES
                                                         ('dddddddd-1111-1111-1111-111111111111', 'cccccccc-1111-1111-1111-111111111111'),
                                                         ('dddddddd-2222-2222-2222-222222222222', 'cccccccc-2222-2222-2222-222222222222');

-- Przypisania nauczania (kto uczy jakiego przedmiotu w jakiej grupie)
INSERT INTO teaching_assignments (id, teacher_id, subject_id, group_id) VALUES
                                                                            ('eeeeeeee-1111-1111-1111-111111111111', '11111111-1111-1111-1111-111111111111', 'aaaaaaaa-1111-1111-1111-111111111111', 'bbbbbbbb-1111-1111-1111-111111111111'),
                                                                            ('eeeeeeee-2222-2222-2222-222222222222', '22222222-2222-2222-2222-222222222222', 'aaaaaaaa-2222-2222-2222-222222222222', 'bbbbbbbb-1111-1111-1111-111111111111'),
                                                                            ('eeeeeeee-3333-3333-3333-333333333333', '33333333-3333-3333-3333-333333333333', 'aaaaaaaa-3333-3333-3333-333333333333', 'bbbbbbbb-2222-2222-2222-222222222222');