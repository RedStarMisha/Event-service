create table if not exists users (
    id bigint generated always as identity primary key ,
    name varchar(100),
    email varchar(100) unique ,
    created timestamp
);

create table if not exists locations(
    id bigint generated always as identity primary key ,
    lat double precision,
    lon double precision,
    description varchar(200),
    unique (lat, lon)
);

create table if not exists categories(
    id int generated always as identity primary key ,
    name varchar(100)
);

create table if not exists events (
    id bigint generated always as identity primary key ,
    title varchar(120),
    annotation text,
    description text,
    initiator bigint references users,
    category int references categories,
    eventDate timestamp,
    location bigint references locations,
    paid boolean,
    partition_limit int,
    moderation boolean,
    state int
);

create table if not exists participation_requests (
    id bigint generated always as identity primary key ,
    created timestamp,
    requestor bigint references users,
    event bigint references events,
    status int
);

create table if not exists selections (
    id bigint generated always as identity primary key ,
    pinned boolean,
    name varchar(100)
);

create table if not exists events_selections (
    selection_id bigint references selections,
    event_id bigint references events,
    unique (selection_id, event_id)
);