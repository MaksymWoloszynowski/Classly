CREATE TABLE schedule (
    id UUID PRIMARY KEY,
    teaching_assignment_id UUID NOT NULL,
    day_of_week INT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(255) NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE NOT NULL
);

CREATE TABLE schedule_override (
    id UUID PRIMARY KEY,
    schedule_id UUID NOT NULL REFERENCES schedule(id),
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    substitute_teacher_id UUID,
    substitute_subject_id UUID,
    new_room VARCHAR(255)
);

CREATE TABLE additional_schedule (
    id UUID PRIMARY KEY,
    teaching_assignment_id UUID NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(255) NOT NULL
);