CREATE TABLE grades (
    id UUID PRIMARY KEY,
    grade DOUBLE PRECISION NOT NULL,
    date DATE NOT NULL DEFAULT NOW(),
    description VARCHAR(255),
    semester INTEGER NOT NULL,
    type VARCHAR(50) NOT NULL,
    weight INTEGER NOT NULL,
    student_id UUID NOT NULL,
    subject_id UUID NOT NULL,
    subject_name VARCHAR(50)
);

CREATE TABLE semester_grades (
    id UUID PRIMARY KEY,
    grade INTEGER NOT NULL,
    description VARCHAR(255),
    type VARCHAR(50) NOT NULL,
    school_year VARCHAR(20) NOT NULL,
    student_id UUID NOT NULL,
    subject_id UUID NOT NULL,
    subject_name VARCHAR(50)
);