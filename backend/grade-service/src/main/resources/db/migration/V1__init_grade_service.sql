CREATE TABLE grade_categories (
    id UUID PRIMARY KEY,
    description VARCHAR(255),
    classification_period UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    weight INTEGER NOT NULL CHECK (weight > 0),
    teaching_assignment_id UUID NOT NULL
);

CREATE TABLE grades (
    id UUID PRIMARY KEY,
    grade DOUBLE PRECISION NOT NULL CHECK (grade >= 0.0),
    date DATE NOT NULL DEFAULT NOW(),
    student_id UUID NOT NULL,
    grade_category_id UUID REFERENCES grade_categories(id)
);

CREATE TABLE semester_grades (
    id UUID PRIMARY KEY,
    grade INTEGER NOT NULL CHECK (grade > 0),
    type VARCHAR(50) NOT NULL,
    classification_period UUID NOT NULL,
    student_id UUID NOT NULL,
    teaching_assignment_id UUID NOT NULL,
    CONSTRAINT uq_semester_grade UNIQUE (student_id, teaching_assignment_id, classification_period, type)
);