CREATE TABLE Adverts (
    uuid UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    cost REAL,
    description TEXT,
    owner_uuid UUID REFERENCES Users(uuid) ON DELETE SET NULL
);