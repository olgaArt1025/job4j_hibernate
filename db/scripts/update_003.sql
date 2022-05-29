create table if not exists vacancies (
     id SERIAL PRIMARY KEY,
     name TEXT
);

create table if not exists base (
      id SERIAL PRIMARY KEY,
      name TEXT,
      vacancy_id int not null references vacancy(id)
    );