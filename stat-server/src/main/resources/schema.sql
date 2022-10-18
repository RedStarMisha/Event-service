create table if not exists stats (
    id bigint generated always as identity primary key ,
    app varchar(50),
    uri text,
    ip varchar(50),
    event bigint,
    date_request timestamp
);