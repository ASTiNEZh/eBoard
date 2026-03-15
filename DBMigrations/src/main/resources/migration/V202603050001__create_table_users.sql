CREATE TABLE Users (
                       uuid UUID PRIMARY KEY,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                       surname VARCHAR(100),
                       deleted BOOLEAN DEFAULT FALSE
);