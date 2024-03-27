CREATE TABLE IF NOT EXISTS cities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS vehicle_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS weather_phenomena (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS delivery_base_fees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    city_id INT NOT NULL,
    vehicle_type_id INT NOT NULL,
    fee DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id),
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(id),
    UNIQUE (city_id, vehicle_type_id)
);

CREATE TABLE IF NOT EXISTS condition_types (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS extra_fees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    condition_type_id INT NOT NULL,
    condition_min DOUBLE,
    condition_max DOUBLE,
    vehicle_type_id INT NOT NULL,
    fee DECIMAL(10, 2),
    phenomenon_id INT,
    error BOOLEAN,
    FOREIGN KEY (condition_type_id) REFERENCES condition_types(id),
    FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(id),
    FOREIGN KEY (phenomenon_id) REFERENCES weather_phenomena(id),
    UNIQUE (condition_type_id, condition_min, condition_max, vehicle_type_id, phenomenon_id)
);

CREATE TABLE IF NOT EXISTS weather (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_id INT NOT NULL,
    wmo_code VARCHAR(255),
    air_temperature DOUBLE,
    wind_speed DOUBLE,
    phenomenon_id INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id),
    FOREIGN KEY (phenomenon_id) REFERENCES weather_phenomena(id)
);
