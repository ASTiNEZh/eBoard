DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS adverts;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;

CREATE TABLE users
(
    uuid       UUID NOT NULL,
    surname    VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    deleted    BOOLEAN,
    email      VARCHAR(255),
    phone      VARCHAR(255),
    password   VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (uuid)
);

CREATE TABLE categories
(
    uuid        UUID NOT NULL,
    description VARCHAR(255),
    parent_id   UUID,
    deleted     BOOLEAN,
    CONSTRAINT pk_categories PRIMARY KEY (uuid)
);

ALTER TABLE categories
    ADD CONSTRAINT uc_categories_parent UNIQUE (parent_id);

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_PARENT FOREIGN KEY (parent_id) REFERENCES categories (uuid);

CREATE TABLE adverts
(
    uuid        UUID NOT NULL,
    title       VARCHAR(255),
    cost        FLOAT,
    description VARCHAR(255),
    owner_uuid  UUID,
    deleted     BOOLEAN,
    CONSTRAINT pk_adverts PRIMARY KEY (uuid)
);

CREATE TABLE comments
(
    uuid        UUID NOT NULL,
    text        VARCHAR(255),
    owner_uuid  UUID,
    advert_uuid UUID,
    deleted     BOOLEAN,
    CONSTRAINT pk_comments PRIMARY KEY (uuid)
);

ALTER TABLE adverts
    ADD CONSTRAINT FK_ADVERTS_ON_OWNER_UUID FOREIGN KEY (owner_uuid) REFERENCES users (uuid);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_ADVERT_UUID FOREIGN KEY (advert_uuid) REFERENCES adverts (uuid);

ALTER TABLE comments
    ADD CONSTRAINT FK_COMMENTS_ON_OWNER_UUID FOREIGN KEY (owner_uuid) REFERENCES users (uuid);

CREATE TABLE advert_category (
                                 advert_uuid UUID NOT NULL,
                                 category_uuid UUID NOT NULL,
                                 PRIMARY KEY (advert_uuid, category_uuid),
                                 FOREIGN KEY (advert_uuid) REFERENCES adverts(uuid),
                                 FOREIGN KEY (category_uuid) REFERENCES categories(uuid)
);

ALTER TABLE categories ADD COLUMN title VARCHAR(255);