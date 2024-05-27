create table book_authors
(
    book_id   integer not null
        constraint fkbhqtkv2cndf10uhtknaqbyo0a
            references books,
    author_id integer not null
        constraint fko86065vktj3hy1m7syr9cn7va
            references authors
);

alter table book_authors
    owner to postgres;

