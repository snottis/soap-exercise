DROP TABLE IF EXISTS country;

CREATE TABLE IF NOT EXISTS country (
    id SERIAL,
    country_name VARCHAR(255),
    gdp FLOAT(32)
);

INSERT INTO country (country_name, gdp) VALUES ('Finland', 100), ('Sweden', 200), ('Estonia', 50);