--liquibase formatted sql
--changeset lisov:1743354485

alter table maps
    add column fts tsvector
        generated always as (to_tsvector(
                'russian',
                COALESCE(name, '') || ' ' ||
                COALESCE(description, '')
                             )
            ) stored;

create index idx_maps_fts
    on maps
        using gin (fts);

alter table places
    add column fts tsvector
        generated always as (to_tsvector(
                'russian',
                COALESCE(name, '') || ' ' ||
                COALESCE(description, '')
                             )
            ) stored;

create index idx_places_fts
    on places
        using gin (fts);

alter table reviews
    add column fts tsvector
        generated always as (to_tsvector(
                'russian',
                COALESCE(text, '')
                             )
            ) stored;

create index idx_reviews_fts
    on reviews
        using gin (fts);