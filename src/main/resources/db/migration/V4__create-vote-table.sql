CREATE TABLE vote
(
    id BIGSERIAL PRIMARY KEY,
    vote BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    agenda_id BIGINT NOT NULL
);

ALTER TABLE vote
    ADD CONSTRAINT fk_vote_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE vote
    ADD CONSTRAINT fk_vote_agenda FOREIGN KEY (agenda_id) REFERENCES agenda (id);

ALTER TABLE vote
    ADD CONSTRAINT uc_vote_user_session UNIQUE (user_id, agenda_id);