CREATE TABLE IF NOT EXISTS mail_package (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    receiver VARCHAR(50) NOT NULL,
    address VARCHAR(50) NOT NULL,
    weight DOUBLE NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES user(id)
);