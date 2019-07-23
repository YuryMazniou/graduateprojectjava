DROP TABLE user_roles IF EXISTS;
DROP TABLE dishes IF EXISTS;
DROP TABLE votes IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE history_restaurant_object IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE users
(
  id               INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  name             VARCHAR(255)            NOT NULL,
  email            VARCHAR(255)            NOT NULL,
  password         VARCHAR(255)            NOT NULL,
  enabled          BOOLEAN DEFAULT TRUE    NOT NULL,
  registered       DATE DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurants
(
    id                INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    description       VARCHAR(255) NOT NULL,
    user_id           INTEGER      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX restaurants_unique_name ON restaurants (description);

CREATE TABLE dishes
(
  id                  INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
  time_create_dish    DATE DEFAULT now()  NOT NULL,
  description         VARCHAR(255) NOT NULL,
  price               DECIMAL(19,4)      NOT NULL,
  restaurant_id       INTEGER      NOT NULL,
  user_id             INTEGER       NOT NULL ,
  FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);
CREATE INDEX dishes_idx ON dishes (restaurant_id, time_create_dish);


CREATE TABLE votes
(
     id                INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
     user_id           INTEGER      NOT NULL,
     restaurant_id     INTEGER      NOT NULL,
     time_create_vote  DATE DEFAULT now()   NOT NULL,
     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE INDEX vote_unique_idx ON votes (restaurant_id,time_create_vote);

CREATE TABLE history_restaurant_object
(
     id                INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
     counts            INTEGER  DEFAULT 0,
     description       VARCHAR(255) NOT NULL,
     votes_date         DATE DEFAULT now()  NOT NULL
)