--liquibase formatted sql
--changeset lisov:1743349731

create table users
(
    id        varchar primary key,
    status    varchar   not null,
    created   timestamp not null default current_timestamp,
    updated   timestamp not null default current_timestamp,
    name      varchar   not null,
    username  varchar   not null unique,
    password  varchar   not null,
    last_seen timestamp not null default current_timestamp
);

create table maps
(
    id          varchar primary key,
    status      varchar   not null,
    created     timestamp not null default current_timestamp,
    updated     timestamp not null default current_timestamp,
    name        varchar   not null,
    description varchar   not null,
    is_public   boolean   not null,
    author_id   varchar   not null
);

alter table maps
    add constraint fk_maps_users foreign key (author_id) references users (id);

create table places
(
    id          varchar primary key,
    status      varchar   not null,
    created     timestamp not null default current_timestamp,
    updated     timestamp not null default current_timestamp,
    name        varchar   not null,
    description varchar,
    coordinates varchar   not null,
    url         varchar   not null,
    rating      float     not null default 0,
    category    varchar,
    is_visited  boolean   not null default false,
    map_id      varchar   not null,
    author_id   varchar   not null
);

alter table places
    add constraint fk_places_maps foreign key (map_id) references maps (id);

alter table places
    add constraint fk_places_users foreign key (author_id) references users (id);

create table reviews
(
    id        varchar primary key,
    status    varchar   not null,
    created   timestamp not null default current_timestamp,
    updated   timestamp not null default current_timestamp,
    text      varchar,
    mark      integer   not null,
    place_id  varchar   not null,
    author_id varchar   not null
);

alter table reviews
    add constraint fk_reviews_places foreign key (place_id) references places (id);

alter table reviews
    add constraint fk_reviews_users foreign key (author_id) references users (id);