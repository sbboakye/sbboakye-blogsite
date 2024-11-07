-- ARTICLES
CREATE TABLE articles (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(256) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(256) NOT NULL,
    created_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION articles_update_updated_date() RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_date = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_date
    BEFORE UPDATE
        ON articles
    FOR EACH ROW
    EXECUTE FUNCTION articles_update_updated_date();

-- INSERT INTO articles (
--     title,
--     content,
--     author
-- ) values (
--     'Hello World',
--     'This is a Hello World Example',
--     'Sambeth'
-- );
--
-- INSERT INTO articles (
--     title,
--     content,
--     author
-- ) values (
--     'Phyllis Boatemaah',
--     'This is a Phyllis Boatemaah Example',
--     'Sambeth'
-- );

-- USERS
CREATE TABLE users (
    email text PRIMARY KEY,
    hashedPassword VARCHAR(256) NOT NULL,
    firstName VARCHAR(256),
    lastName VARCHAR(256),
    role VARCHAR(256) NOT NULL,
    created_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_date TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION update_updated_date() RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_date = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_updated_date
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_updated_date();

-- INSERT INTO users (
--     email,
--     hashedPassword,
--     firstName,
--     lastName,
--     role
-- ) values (
--     'samuelbaafiboakye@gmail.com',
--     'reallLige',
--     'Samuel',
--     'Boakye',
--     'ADMIN'
--  );
--
-- INSERT INTO users (
--     email,
--     hashedPassword,
--     firstName,
--     lastName,
--     role
-- ) values (
--      'sambethslim@gmail.com',
--      '214fasdsg',
--      'Sam',
--      'Beth',
--      'READER'
--  );