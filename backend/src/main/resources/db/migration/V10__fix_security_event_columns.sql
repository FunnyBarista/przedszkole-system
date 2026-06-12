ALTER TABLE security_event
    DROP COLUMN IF EXISTS "createdAt",
    DROP COLUMN IF EXISTS "ipAddress";
