--liquibase formatted sql
--changeset lisov:1743352638

create table user_roles
(
    user_id varchar not null,
    role    varchar not null
);

alter table user_roles
    add constraint fk_user_roles_users foreign key (user_id) references users (id);