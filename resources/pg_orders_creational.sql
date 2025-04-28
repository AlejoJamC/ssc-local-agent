CREATE TABLE coffee_products (
    product_id_sku VARCHAR(20) PRIMARY KEY,
    product_name VARCHAR(100),
    category VARCHAR(50),
    description TEXT,
    quantity_in_stock INTEGER,
    unit_price NUMERIC(10, 2),
    cost_price NUMERIC(10, 2),
    supplier_vendor VARCHAR(150),
    status VARCHAR(30),
    date_added DATE,
    weight VARCHAR(20),
    images_url TEXT
);
