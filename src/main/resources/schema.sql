
CREATE TABLE IF NOT EXISTS rooms (
    id BIGSERIAL PRIMARY KEY,

    number VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,

    capacity INTEGER NOT NULL,
    price_per_night REAL NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'OUT_OF_SERVICE',

    CONSTRAINT chk_room_status
        CHECK (status IN ('AVAILABLE', 'MAINTENANCE', 'OUT_OF_SERVICE'))
);


CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,

    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,

    first_name VARCHAR(255),
    last_name VARCHAR(255),

    role VARCHAR(20) NOT NULL DEFAULT 'ROLE_CUSTOMER',

    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    email_verified BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_user_role
        CHECK (role IN ('ROLE_CUSTOMER', 'ROLE_ADMIN'))
);



CREATE TABLE IF NOT EXISTS booking (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,
    room_id BIGINT NOT NULL,

    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    CONSTRAINT fk_booking_user
        FOREIGN KEY (user_id)
        REFERENCES users(id),

    CONSTRAINT fk_booking_room
        FOREIGN KEY (room_id)
        REFERENCES rooms(id),

    CONSTRAINT chk_booking_dates
        CHECK (start_date <= end_date)
);

