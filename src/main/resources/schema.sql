CREATE TABLE IF NOT EXISTS service(
    service_id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(128) UNIQUE,
    service_type INT,
    parent_id BIGINT foreign key references service(SERVICE_ID)
);

CREATE TABLE IF NOT EXISTS usr(
    user_id BIGINT IDENTITY PRIMARY KEY,
    fio VARCHAR(256) NOT NULL,
    account VARCHAR(128) not null,
    service_id bigint FOREIGN KEY REFERENCES service(service_id)
);

ALTER TABLE usr
    ADD CONSTRAINT uq_account_service UNIQUE(account, service_id);

CREATE TABLE IF NOT EXISTS service_hierarchy(
    service_id BIGINT FOREIGN KEY REFERENCES service(service_id),
    parent_id BIGINT FOREIGN KEY REFERENCES service(service_id)
);