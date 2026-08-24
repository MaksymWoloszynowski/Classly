CREATE TABLE grades (
    id UUID PRIMARY KEY,
    grade DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL DEFAULT NOW(),
    description VARCHAR(255),
    classification_period UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    weight INTEGER NOT NULL,
    student_id UUID NOT NULL,
    subject_id UUID NOT NULL
);

CREATE TABLE semester_grades (
    id UUID PRIMARY KEY,
    grade INTEGER NOT NULL,
    type VARCHAR(50) NOT NULL,
    classification_period UUID NOT NULL,
    student_id UUID NOT NULL,
    subject_id UUID NOT NULL
);