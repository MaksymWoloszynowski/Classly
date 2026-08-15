INSERT INTO schedule (id, teaching_assignment_id, day_of_week, start_time, end_time, room, valid_from, valid_to) VALUES
                                                                                                                     ('11111111-aaaa-1111-1111-111111111111', 'eeeeeeee-1111-1111-1111-111111111111', 1, '08:00', '08:45', '101', '2025-09-01', '2026-06-26'),
                                                                                                                     ('11111111-aaaa-2222-2222-222222222222', 'eeeeeeee-2222-2222-2222-222222222222', 1, '08:55', '09:40', '102', '2025-09-01', '2026-06-26'),
                                                                                                                     ('11111111-aaaa-3333-3333-333333333333', 'eeeeeeee-1111-1111-1111-111111111111', 3, '09:00', '09:45', '101', '2025-09-01', '2026-06-26'),
                                                                                                                     ('11111111-aaaa-4444-4444-444444444444', 'eeeeeeee-3333-3333-3333-333333333333', 2, '10:00', '10:45', '201', '2025-09-01', '2026-06-26');

INSERT INTO schedule_override (id, schedule_id, date, type, substitute_teacher_id, new_room) VALUES
    ('22222222-bbbb-1111-1111-111111111111', '11111111-aaaa-1111-1111-111111111111', '2026-01-05', 'SUBSTITUTION', '22222222-2222-2222-2222-222222222222', NULL);

INSERT INTO schedule_override (id, schedule_id, date, type, substitute_teacher_id, new_room) VALUES
    ('22222222-bbbb-2222-2222-222222222222', '11111111-aaaa-4444-4444-444444444444', '2026-01-07', 'CANCELLED', NULL, NULL);

INSERT INTO additional_schedule (id, teaching_assignment_id, date, start_time, end_time, room) VALUES
    ('22222222-bbbb-2222-3333-222222222222', 'eeeeeeee-1111-1111-1111-111111111111', '2026-01-07', '13:45', '14:45', 205);