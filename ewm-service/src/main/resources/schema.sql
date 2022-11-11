create table if not exists users (
    id bigint generated always as identity primary key ,
    name varchar(100),
    email varchar(100) unique ,
    created timestamp without time zone
);

create table if not exists locations (
    id bigint generated always as identity primary key ,
    lat float,
    lon float,
    description text,
    title text,
    unique (lat, lon)
);

create table if not exists categories(
    id bigint generated always as identity primary key ,
    name text unique
);

create table if not exists events (
    id bigint generated always as identity primary key ,
    title varchar(120),
    annotation text,
    description text,
    initiator bigint references users,
    category bigint references categories,
    created timestamp,
    event_date timestamp without time zone,
    published timestamp without time zone,
    location bigint references locations,
    paid boolean,
    partition_limit int,
    moderation boolean,
    state int,
    number_confirmed int
);

create table if not exists participation_requests (
    id bigint generated always as identity primary key ,
    created timestamp without time zone,
    requestor bigint references users,
    event bigint references events,
    status int
);

create table if not exists compilations (
    id bigint generated always as identity primary key ,
    pinned boolean,
    title text
);

create table if not exists events_compilations (
    compilation_id bigint references compilations,
    event_id bigint references events,
    primary key (compilation_id, event_id)
);

create table if not exists subscription (
    id bigint generated always as identity primary key ,
    friendship_request boolean,
    publisher bigint references users,
    follower bigint references users,
    created timestamp without time zone,
    updated timestamp without time zone,
    status int,
    unique (publisher, follower)
);

create table if not exists groups (
    id bigint generated always as identity primary key ,
    publisher bigint references users on delete CASCADE,
    title text
);

create table if not exists followers (
    id bigint generated always as identity primary key ,
    group_level int references groups,
    added timestamp,
    publisher bigint references users,
    follower bigint references users,
    subscription bigint references subscription
);

create table if not exists request_group (
   request bigint references participation_requests,
   group_level bigint references groups,
   PRIMARY KEY (request, group_level)
);



