CREATE TABLE IF NOT EXISTS security_event (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    ip_address VARCHAR(45),
    created_at TIMESTAMP NOT NULL,
    details VARCHAR(500)
);

CREATE INDEX IF NOT EXISTS idx_security_event_created_at
    ON security_event (created_at DESC);

CREATE INDEX IF NOT EXISTS idx_security_event_email
    ON security_event (email);
