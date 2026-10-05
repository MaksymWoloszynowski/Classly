CREATE TABLE session (
    id UUID PRIMARY KEY,
    schedule_id UUID NOT NULL,
    teaching_assignment_id UUID NOT NULL,
    description VARCHAR(255),
    date DATE NOT NULL,
    start_time TIME NOT NUll,
    end_time TIME NOT NULL
);

CREATE TABLE attendance (
    id UUID PRIMARY KEY,
    student_id UUID NOT NULL,
    session_id UUID NOT NULL REFERENCES session(id),
    type VARCHAR(50) NOT NULL,
    CONSTRAINT uq_attendance UNIQUE (student_id, session_id)
);

CREATE TABLE assessment (
    id UUID PRIMARY KEY,
    teaching_assignment_id UUID NOT NULL,
    description VARCHAR(255),
    date_made DATE NOT NULL DEFAULT NOW(),
    date_due DATE NOT NULL,
    type VARCHAR(50) NOT NULL
);