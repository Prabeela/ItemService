DROP TABLE IF EXISTS item_541455;

CREATE TABLE item_541455
(
    id varchar(36) NOT NULL,
    name varchar(200) NOT NULL,
    description varchar(200) NOT NULL,
    price varchar(200) NOT NULL,
    created date NOT NULL,
    modified date NOT NULL,
    PRIMARY KEY (id)
);