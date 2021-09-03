create table posts
(
    id          serial primary key,
    name        varchar(2000),
    description text,
    created     timestamp without time zone not null default now()
);

create table comments
(
    id      serial primary key,
    text    varchar(4000),
    created timestamp without time zone not null default now(),
    post_id int                         not null references posts (id)

)
