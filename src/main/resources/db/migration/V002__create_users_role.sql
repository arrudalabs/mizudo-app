create table users_roles (
    username varchar(30) not null,
    role varchar(30) not null
);

alter table users_roles
    add constraint user_role_fk
    foreign key (username)
    REFERENCES users;
