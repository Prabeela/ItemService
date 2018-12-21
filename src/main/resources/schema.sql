DROP TABLE IF EXISTS item;

CREATE TABLE item
(
    id varchar(36) NOT NULL,
    name varchar(200) NOT NULL,
    description varchar(200) NOT NULL,
    price varchar(200) NOT NULL,
    created date NOT NULL,
    modified date NOT NULL,
    PRIMARY KEY (id)
);