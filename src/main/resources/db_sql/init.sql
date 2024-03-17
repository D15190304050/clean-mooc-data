CREATE DATABASE IF NOT EXISTS mooc_data;
USE mooc_data;

DROP TABLE IF EXISTS `course_resource`;
CREATE TABLE `course_resource`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `resource_id` VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `chapter`     VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `course_id`   VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    KEY `idx_course_id` (`course_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin;

DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`
(
    `id`            BIGINT PRIMARY KEY AUTO_INCREMENT,
    `course_id`     VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `name`          VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    KEY `idx_course_id` (`course_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin;

DROP TABLE IF EXISTS `course_exercise_relation`;
CREATE TABLE `course_exercise_relation`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `course_id`   VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `exercise_id` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    KEY `idx_course_id` (`course_id`),
    KEY `idx_exercise_id` (`exercise_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin;

DROP TABLE IF EXISTS `student_answer`;
CREATE TABLE `student_answer`
(
    `id`          BIGINT PRIMARY KEY AUTO_INCREMENT,
    `user_id`     VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `log_id`      VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `problem_id`  VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    `is_correct`  INT,
    `attempts`    INT,
    `score`       INT,
    `submit_time` VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin,
    KEY `idx_user_id` (`user_id`),
    KEY `idx_problem_id` (`problem_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_bin;

