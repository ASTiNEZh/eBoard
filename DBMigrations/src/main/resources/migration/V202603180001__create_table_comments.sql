CREATE TABLE Comments (
    uuid UUID PRIMARY KEY,
    text TEXT,
    deleted BOOLEAN DEFAULT FALSE,
    owner_uuid UUID REFERENCES Users(uuid) ON DELETE SET NULL,
    advert_uuid UUID REFERENCES Adverts(uuid) ON DELETE SET NULL
);