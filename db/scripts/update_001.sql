create table if not exists candidates (
    id SERIAL PRIMARY KEY,
    name TEXT,
    experience TEXT,
    salary INT
);