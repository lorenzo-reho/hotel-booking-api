INSERT INTO users (email, password_hash, first_name, last_name, role, enabled, email_verified)
SELECT 'admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Admin', 'System', 'ROLE_ADMIN', TRUE, TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@example.com'
);