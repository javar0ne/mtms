CREATE TABLE IF NOT EXISTS shipping_fee (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    package_length_min DOUBLE NOT NULL,
    package_length_max DOUBLE NOT NULL,
    package_width_min DOUBLE NOT NULL,
    package_width_max DOUBLE NOT NULL,
    package_height_min DOUBLE NOT NULL,
    package_height_max DOUBLE NOT NULL,
    package_weight_min DOUBLE NOT NULL,
    package_weight_max DOUBLE NOT NULL,
    fee DOUBLE NOT NULL
);