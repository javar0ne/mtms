CREATE TABLE IF NOT EXISTS mail_package (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    receiver VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    package_length DOUBLE NOT NULL,
    package_width DOUBLE NOT NULL,
    package_height DOUBLE NOT NULL,
    package_weight DOUBLE NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES user(id)
);