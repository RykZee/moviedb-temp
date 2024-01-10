CREATE TABLE `movie`(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `movie_name` VARCHAR(255) NOT NULL,
    `movie_year` integer
);

CREATE TABLE `actor`(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `actor_name` VARCHAR(255) NOT NULL
);

CREATE TABLE `movie_actor`(
    `movie_id` BIGINT NOT NULL,
    `actor_id` BIGINT NOT NULL,
    PRIMARY KEY (`movie_id`, `actor_id`),
    FOREIGN KEY (`movie_id`) REFERENCES movie(`id`),
    FOREIGN KEY (`actor_id`) REFERENCES actor(`id`)
);