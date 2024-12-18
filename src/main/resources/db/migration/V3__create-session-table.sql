CREATE TABLE session
(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR( 255 ) NOT NULL,
    description TEXT,
    duration INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    finished_at TIMESTAMP,
    agenda_id BIGINT NOT NULL
);

ALTER TABLE session
    ADD CONSTRAINT fk_session_agenda FOREIGN KEY (agenda_id) REFERENCES agenda (id);