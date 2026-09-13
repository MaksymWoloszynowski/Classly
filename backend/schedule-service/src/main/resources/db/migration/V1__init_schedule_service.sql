CREATE TABLE schedule (
    id UUID PRIMARY KEY,
    teaching_assignment_id UUID NOT NULL,
    day_of_week INT NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(255) NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE NOT NULL,
    CONSTRAINT chk_schedule_time CHECK (start_time < end_time),
    CONSTRAINT chk_valid_date CHECK (valid_from <= valid_to),
    CONSTRAINT chk_day_of_week CHECK (day_of_week BETWEEN 1 AND 7)
);

CREATE TABLE schedule_override (
    id UUID PRIMARY KEY,
    schedule_id UUID NOT NULL REFERENCES schedule(id),
    date DATE NOT NULL,
    type VARCHAR(50) NOT NULL,
    substitute_teaching_assignment_id UUID,
    new_room VARCHAR(255),
    CONSTRAINT uq_schedule_override UNIQUE (schedule_id, date)
);

CREATE TABLE additional_schedule (
    id UUID PRIMARY KEY,
    teaching_assignment_id UUID NOT NULL,
    date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    room VARCHAR(255) NOT NULL,
    CONSTRAINT chk_schedule_time CHECK (start_time < end_time)
);