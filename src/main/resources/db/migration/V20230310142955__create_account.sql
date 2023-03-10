CREATE TABLE account
(
    uuid       CHAR(26)     NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    name       VARCHAR(255),
    phone      VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (uuid)
);

CREATE TABLE role
(
    uuid       CHAR(26) NOT NULL,
    authority  VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (uuid)

);

CREATE TABLE account_role
(
    account_uuid CHAR(26),
    role_uuid    CHAR(26),
    FOREIGN KEY (account_uuid) REFERENCES account (uuid) ON DELETE CASCADE,
    FOREIGN KEY (role_uuid) REFERENCES role (uuid)
);