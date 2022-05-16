create table if not exists brand (
        id SERIAL PRIMARY KEY,
        name TEXT
);

create table if not exists models (
        id SERIAL PRIMARY KEY,
        name TEXT,
        brand_id int not null references brand(id)
);
