CREATE TABLE articles (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    title VARCHAR(256),
    content TEXT,
    author VARCHAR(256),
    created_date TIMESTAMPTZ NOT NULL,
    updated_data TIMESTAMPTZ NOT NULL
);

INSERT INTO articles (
    title,
    content,
    author,
    created_date,
    updated_data
) values (
    'Hello World',
    'This is a Hello World Example',
    'Sambeth',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

INSERT INTO articles (
    title,
    content,
    author,
    created_date,
    updated_data
) values (
    'Phyllis Boatemaah',
    'This is a Phyllis Boatemaah Example',
    'Sambeth',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);