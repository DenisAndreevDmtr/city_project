
#--------------------------------------------------------
--  DDL for Table city
#--------------------------------------------------------
drop table if exists cities_db.cities;
create table if not exists cities_db.cities
(
    id           int          not null auto_increment,
    name    varchar(200),
    photo   varchar(1000),
    primary key (id)
    );

# --------------------------------------------------------
# --  DDL for Table role
# --------------------------------------------------------
drop table if exists cities_db.roles;
create table if not exists cities_db.roles
(
    id   int          not null auto_increment,
    name varchar(200) not null,
    primary key (id),
    unique index idx_roles_id_unique (id asc),
    unique index idx_roles_name_unique (name asc)
    );

# --------------------------------------------------------
# --  DDL for Table users
# --------------------------------------------------------
drop table if exists cities_db.users;
create table if not exists cities_db.users
(
    id         int          not null auto_increment,
    login      varchar(45)  not null,
    password   varchar(400) not null,
    role_id    int          not null,
    primary key (id),
    unique index idx_users_user_id_unique (id asc),
    unique index idx_users_login_unique (login asc),
    constraint fk_users_role_id_roles_id
    foreign key (role_id)
    references cities_db.roles (id)
    on delete cascade
    on update cascade
    );