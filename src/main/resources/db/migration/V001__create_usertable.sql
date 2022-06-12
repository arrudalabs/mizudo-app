create table users (
    username varchar(30) not null primary key,
    salt varchar(255),
    hash varchar(255)
);