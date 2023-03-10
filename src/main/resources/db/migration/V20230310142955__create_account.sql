CREATE TABLE account
(
    id         CHAR(26)     NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    phone      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE role
(
    id         CHAR(26) NOT NULL,
    authority  VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)

);

CREATE TABLE account_role
(
    id         CHAR(26) NOT NULL ,
    account_id CHAR(26),
    role_id    CHAR(26),
    PRIMARY KEY (id),
    FOREIGN KEY (account_id) REFERENCES account (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id)
);