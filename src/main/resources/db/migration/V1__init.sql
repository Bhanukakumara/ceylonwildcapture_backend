-- ============================
-- USERS & AUTHENTICATION
-- ============================

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(120) NOT NULL,
    role VARCHAR(30) NOT NULL CHECK (role IN ('ADMIN', 'PHOTOGRAPHER', 'BUYER')),
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);


-- Photographer profile (optional extended info)
CREATE TABLE photographer_profiles (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    display_name VARCHAR(150),
    portfolio_description TEXT,
    verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_photographer_user
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


-- ============================
-- TAGS & CATEGORIES
-- ============================

CREATE TABLE tags (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(120) NOT NULL UNIQUE
);

CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(120) NOT NULL UNIQUE,
    slug VARCHAR(130) NOT NULL UNIQUE
);


-- ============================
-- PHOTOS
-- ============================

CREATE TABLE photos (
    id BIGSERIAL PRIMARY KEY,
    photographer_id BIGINT NOT NULL,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    species VARCHAR(150),
    location_text VARCHAR(200),
    geo_lat DECIMAL(10, 7),
    geo_lng DECIMAL(10, 7),
    price NUMERIC(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    license_type VARCHAR(50) NOT NULL CHECK (license_type IN ('PERSONAL', 'COMMERCIAL', 'EDITORIAL')),
    
    file_key VARCHAR(300) NOT NULL,
    thumbnail_key VARCHAR(300),
    watermarked_key VARCHAR(300),

    camera_model VARCHAR(150),
    focal_length VARCHAR(50),
    iso VARCHAR(50),
    capture_date TIMESTAMP NULL,

    status VARCHAR(30) NOT NULL CHECK (status IN ('DRAFT', 'PUBLISHED', 'REMOVED')),
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_photo_photographer
        FOREIGN KEY (photographer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_photos_photographer ON photos(photographer_id);
CREATE INDEX idx_photos_species ON photos(species);
CREATE INDEX idx_photos_price ON photos(price);


-- Photo ↔ Tag (Many-to-Many)
CREATE TABLE photo_tags (
    photo_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,

    PRIMARY KEY (photo_id, tag_id),

    FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
);


-- Photo ↔ Category (Many-to-Many)
CREATE TABLE photo_categories (
    photo_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    PRIMARY KEY (photo_id, category_id),

    FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);


-- ============================
-- ORDERS & ORDER ITEMS
-- ============================

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    buyer_id BIGINT NOT NULL,
    total_amount NUMERIC(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    status VARCHAR(30) NOT NULL CHECK (status IN ('PENDING', 'PAID', 'FAILED', 'REFUNDED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_buyer
        FOREIGN KEY (buyer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_orders_buyer ON orders(buyer_id);


CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    photo_id BIGINT NOT NULL,
    price_at_purchase NUMERIC(12,2) NOT NULL,
    license_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_order_item_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

    CONSTRAINT fk_order_item_photo
        FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE
);

CREATE INDEX idx_order_items_order ON order_items(order_id);


-- ============================
-- LICENSE TABLE
-- ============================

CREATE TABLE licenses (
    id BIGSERIAL PRIMARY KEY,
    order_item_id BIGINT NOT NULL UNIQUE,
    license_key VARCHAR(200) NOT NULL UNIQUE,
    license_text TEXT,
    expires_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_license_order_item
        FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE
);


-- ============================
-- PAYMENTS
-- ============================

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    payment_provider VARCHAR(50) NOT NULL,
    provider_payment_id VARCHAR(200),
    status VARCHAR(30) NOT NULL CHECK (status IN ('PENDING', 'SUCCESS', 'FAILED')),
    amount NUMERIC(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'USD',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);

CREATE INDEX idx_payments_order ON payments(order_id);


-- ============================
-- PAYOUTS (PHOTOGRAPHER EARNINGS)
-- ============================

CREATE TABLE payouts (
    id BIGSERIAL PRIMARY KEY,
    photographer_id BIGINT NOT NULL,
    amount NUMERIC(12,2) NOT NULL,
    status VARCHAR(30) NOT NULL CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),
    payout_method VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_payout_photographer
        FOREIGN KEY (photographer_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_payouts_photographer ON payouts(photographer_id);


-- ============================
-- DOWNLOAD AUDIT LOGS
-- ============================

CREATE TABLE download_audit (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    photo_id BIGINT NOT NULL,
    license_id BIGINT NOT NULL,
    ip_address VARCHAR(100),
    user_agent VARCHAR(300),
    downloaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE,
    FOREIGN KEY (license_id) REFERENCES licenses(id) ON DELETE CASCADE
);


-- ============================
-- MODERATION RECORDS
-- ============================

CREATE TABLE moderation_records (
    id BIGSERIAL PRIMARY KEY,
    photo_id BIGINT NOT NULL,
    admin_id BIGINT NOT NULL,
    action VARCHAR(50) NOT NULL CHECK (action IN ('APPROVED', 'REJECTED', 'FLAGGED')),
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE,
    FOREIGN KEY (admin_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_moderation_photo ON moderation_records(photo_id);
