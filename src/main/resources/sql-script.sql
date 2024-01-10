drop database IF EXISTS moviedb;

CREATE DATABASE moviedb;
USE moviedb;

CREATE TABLE movie(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    movie_name VARCHAR(255) NOT NULL,
    movie_year int
);

CREATE TABLE actor(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    actor_name VARCHAR(255) NOT NULL
);

CREATE TABLE movie_actor(
    movie_id BIGINT NOT NULL,
    actor_id BIGINT NOT NULL,
    PRIMARY KEY (movie_id, actor_id),
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (actor_id) REFERENCES actor(id)
);

insert into movie (id, movie_name, movie_year)
values (1, 'The Pursuit of Happyness', 2006);
insert into movie (id, movie_name, movie_year)
values (2, 'The Matrix', 1999);
insert into movie (id, movie_name, movie_year)
values (3, 'Interstellar', 2014);
insert into movie (id, movie_name, movie_year)
values (4, 'The Dark Knight Rises', 2012);


insert into actor (id, actor_name)
values (1, 'Will Smith');
insert into actor (id, actor_name)
values (2, 'Thandiwe Newton');
insert into actor (id, actor_name)
values (3, 'Keanu Reeves');
insert into actor (id, actor_name)
values (4, 'Laurence Fishburne');
insert into actor (id, actor_name)
values (5, 'Carrie-Anne Moss');
insert into actor (id, actor_name)
values (6, 'Hugo Weaving');
insert into actor (id, actor_name)
values (7, 'Matthew McConaughey');
insert into actor (id, actor_name)
values (8, 'Anne Hathaway');
insert into actor (id, actor_name)
values (9, 'Jessica Chastain');
insert into actor (id, actor_name)
values (10, 'Christian Bale');
insert into actor (id, actor_name)
values (11, 'Michael Caine');

insert into movie_actor (actor_id, movie_id)
values (1, 1);
insert into movie_actor (actor_id, movie_id)
values (2, 1);
insert into movie_actor (actor_id, movie_id)
values (3, 2);
insert into movie_actor (actor_id, movie_id)
values (4, 2);
insert into movie_actor (actor_id, movie_id)
values (5, 2);
insert into movie_actor (actor_id, movie_id)
values (6, 2);
insert into movie_actor (actor_id, movie_id)
values (7, 3);
insert into movie_actor (actor_id, movie_id)
values (8, 3);
insert into movie_actor (actor_id, movie_id)
values (9, 3);
insert into movie_actor (actor_id, movie_id)
values (11, 3);
insert into movie_actor (actor_id, movie_id)
values (8, 4);
insert into movie_actor (actor_id, movie_id)
values (10, 4);
insert into movie_actor (actor_id, movie_id)
values (11, 4);
