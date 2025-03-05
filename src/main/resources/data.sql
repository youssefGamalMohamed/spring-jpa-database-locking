-- Create the product table if it does not exist
CREATE TABLE IF NOT EXISTS product (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    version INTEGER NOT NULL
);

-- Insert a sample record into the product table
INSERT INTO product (id, name, price, version) VALUES ('9fb68f0d-6f45-495e-b3ed-6f2a7f62804f', 'product-1', 99.99, 0);