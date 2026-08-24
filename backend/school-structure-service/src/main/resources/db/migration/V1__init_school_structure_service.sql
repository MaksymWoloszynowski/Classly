CREATE TABLE teachers (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL
);

CREATE TABLE subjects (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE school_groups (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    homeroom_teacher_id UUID REFERENCES teachers(id)
);

CREATE TABLE students (
      id UUID PRIMARY KEY,
      first_name VARCHAR(255) NOT NULL,
      last_name VARCHAR(255) NOT NULL,
      date_of_birth DATE NOT NULL,
      group_id UUID REFERENCES school_groups(id)
);

CREATE TABLE parents (
     id UUID PRIMARY KEY,
     first_name VARCHAR(255) NOT NULL,
     last_name VARCHAR(255) NOT NULL
);

CREATE TABLE parents_students (
    parent_id UUID NOT NULL REFERENCES parents(id),
    student_id UUID NOT NULL REFERENCES students(id),
    PRIMARY KEY (parent_id, student_id)
);

CREATE TABLE teaching_assignments (
    id UUID PRIMARY KEY,
    teacher_id UUID NOT NULL REFERENCES teachers(id),
    subject_id UUID NOT NULL REFERENCES subjects(id),
    group_id UUID NOT NULL REFERENCES school_groups(id),
    CONSTRAINT uq_subject_group UNIQUE (subject_id, group_id)
);

CREATE TABLE classification_periods (
    id UUID PRIMARY KEY,
    date_from DATE NOT NULL,
    date_to DATE NOT NULL,
    semester int NOT NULL
);