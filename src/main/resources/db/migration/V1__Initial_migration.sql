DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS posts_seq;

CREATE TABLE users (
    id BIGINT NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    registered_on TIMESTAMP(6),
    partition_key VARCHAR(255),
    PRIMARY KEY (id, partition_key)
)
PARTITION BY LIST (partition_key);

CREATE TABLE users_asia
PARTITION OF users FOR VALUES IN ('Asia');

CREATE TABLE users_africa
PARTITION OF users FOR VALUES IN ('Africa');

CREATE TABLE users_north_america
PARTITION OF users FOR VALUES IN ('North America');

CREATE TABLE users_south_america
PARTITION OF users FOR VALUES IN ('South America');

CREATE TABLE users_europe
PARTITION OF users FOR VALUES IN ('Europe');

CREATE TABLE users_australia
PARTITION OF users FOR VALUES IN ('Australia');

CREATE TABLE posts (
    id BIGINT NOT NULL,
    title VARCHAR(255),
    created_on TIMESTAMP(6),
    user_id BIGINT,
    partition_key VARCHAR(255),
    PRIMARY KEY (id, partition_key)
)
PARTITION BY LIST (partition_key);

CREATE TABLE posts_asia
PARTITION OF posts FOR VALUES IN ('Asia');

CREATE TABLE posts_africa
PARTITION OF posts FOR VALUES IN ('Africa');

CREATE TABLE posts_north_america
PARTITION OF posts FOR VALUES IN ('North America');

CREATE TABLE posts_south_america
PARTITION OF posts FOR VALUES IN ('South America');

CREATE TABLE posts_europe
PARTITION OF posts FOR VALUES IN ('Europe');

CREATE TABLE posts_australia
PARTITION OF posts FOR VALUES IN ('Australia');

ALTER TABLE IF EXISTS posts
ADD CONSTRAINT fk_posts_user_id
FOREIGN KEY (user_id, partition_key)
REFERENCES users (id, partition_key);

CREATE SEQUENCE users_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
CREATE SEQUENCE posts_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
