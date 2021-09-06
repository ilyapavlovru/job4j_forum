insert into authorities (authority)
values ('ROLE_USER');
insert into authorities (authority)
values ('ROLE_ADMIN');

insert into users (username, enabled, password, authority_id)
values ('root', true, '$2a$10$v0C5DXq7VSEgnf1iBHxujONhRJAxcPqzpSzLo6AGf3MBNd8Niy/6O',
        (select id from authorities where authority = 'ROLE_ADMIN'));

insert into posts (name, user_id)
values ('О чем этот форум?', 1);
insert into posts (name, user_id)
values ('Правила форума', 1);
