drop table if exists HEROES CASCADE;
create table HEROES
(
    id   varchar(36)  not null,
    name varchar(255) not null,
    primary key (id)
)