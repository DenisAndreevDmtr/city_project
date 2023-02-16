delete from cities_db.roles where id is not null;
delete from cities_db.users where id is not null;
#--------------------------------------------------------
#-- --  DML for Table cities_db.roles
#-- --------------------------------------------------------
insert into cities_db.roles (name)
values ('ROLE_ALLOW_EDIT');
insert into cities_db.roles (name)
values ('ROLE_ALLOW_VIEW');

#--------------------------------------------------------
#-- --  DML for Table cities_db.users
#-- --------------------------------------------------------
-- ADMIN
-- password = admin
-- login = admin
-- USER
-- password = user
-- login = user

insert into cities_db.users (login, password, role_id)
values ('admin', '$2a$10$/FQax/HRPNqcqwIJCWo2b.D4JSd6FKWq2e8FKpoNNCa9udRBpfWIy', 1);
insert into cities_db.users (login, password, role_id)
values ('user', '$2a$10$B8YqZ0yJ42VFufmOKeLLG.Tg6je3LTZRs6NEMcEt.ePUctacc7oQa', 2);