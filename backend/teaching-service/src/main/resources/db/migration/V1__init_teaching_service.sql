CREATE TABLE session (
    id UUID PRIMARY KEY,
    teaching_assignment_id UUID NOT NULL,
    description VARCHAR(255),
    date DATE NOT NULL DEFAULT NOW()
);

CREATE TABLE attendance (
    id UUID PRIMARY KEY,
    student_id UUID NOT NULL,
    session_id UUID NOT NULL REFERENCES session(id),
    type VARCHAR(50) NOT NULL
);

CREATE TABLE assessment (
    id UUID PRIMARY KEY,
    group_id UUID NOT NULL,
    teaching_assignment_id UUID NOT NULL,
    description VARCHAR(255),
    date_made DATE NOT NULL DEFAULT NOW(),
    date_due DATE NOT NULL,
    type VARCHAR(50) NOT NULL
);