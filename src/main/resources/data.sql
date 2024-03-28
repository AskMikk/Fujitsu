INSERT INTO cities (name) VALUES ('Tallinn'), ('Tartu'), ('Pärnu');

INSERT INTO vehicle_types (type) VALUES ('Car'), ('Scooter'), ('Bike');

INSERT INTO weather_phenomena (name) VALUES
('Snow'),
('Sleet'),
('Rain'),
('Glaze'),
('Hail'),
('Thunder'),
('Clear'),
('Few clouds'),
('Variable clouds'),
('Cloudy with clear spells'),
('Overcast'),
('Light snow shower'),
('Moderate snow shower'),
('Heavy snow shower'),
('Light shower'),
('Moderate shower'),
('Heavy shower'),
('Light rain'),
('Moderate rain'),
('Heavy rain'),
('Light sleet'),
('Moderate sleet'),
('Light snowfall'),
('Moderate snowfall'),
('Heavy snowfall'),
('Blowing snow'),
('Drifting snow'),
('Mist'),
('Fog'),
('Thunderstorm');

INSERT INTO condition_types (id, type) VALUES
(1, 'Temperature'),
(2, 'Wind Speed'),
(3, 'Phenomenon');

INSERT INTO delivery_base_fees (city_id, vehicle_type_id, fee) VALUES
(1, 1, 4.00), -- Tallinn, Car
(1, 2, 3.50), -- Tallinn, Scooter
(1, 3, 3.00); -- Tallinn, Bike

INSERT INTO delivery_base_fees (city_id, vehicle_type_id, fee) VALUES
(2, 1, 3.50), -- Tartu, Car
(2, 2, 3.00), -- Tartu, Scooter
(2, 3, 2.50); -- Tartu, Bike

INSERT INTO delivery_base_fees (city_id, vehicle_type_id, fee) VALUES
(3, 1, 3.00), -- Pärnu, Car
(3, 2, 2.50), -- Pärnu, Scooter
(3, 3, 2.00); -- Pärnu, Bike

INSERT INTO extra_fees (condition_type_id, condition_min, condition_max, vehicle_type_id, fee) VALUES
(1, NULL, -10.01, 2, 1.00), -- Scooter, < -10°C
(1, NULL, -10.01, 3, 1.00), -- Bike, < -10°C
(1, -10.00, 0.00, 2, 0.50),    -- Scooter, -10°C to 0°C
(1, -10.00, 0.00, 3, 0.50);    -- Bike, -10°C to 0°C

INSERT INTO extra_fees (condition_type_id, condition_min, condition_max, vehicle_type_id, fee) VALUES
(2, 10.00, 20.00, 3, 0.50); -- Bike, 10m/s to 20m/s

INSERT INTO extra_fees (condition_type_id, vehicle_type_id, fee, phenomenon_id) VALUES
(3, 2, 1.00, 1), -- Scooter, Snow
(3, 3, 1.00, 1), -- Bike, Snow
(3, 2, 1.00, 2), -- Scooter, Sleet
(3, 3, 1.00, 2), -- Bike, Sleet
(3, 2, 0.50, 3), -- Scooter, Rain
(3, 3, 0.50, 3), -- Bike, Rain
(3, 2, 1.00, 12), -- Scooter, Light snow shower
(3, 3, 1.00, 12), -- Bike, Light snow shower
(3, 2, 1.00, 13), -- Scooter, Moderate snow shower
(3, 3, 1.00, 13), -- Bike, Moderate snow shower
(3, 2, 1.00, 14), -- Scooter, Heavy snow shower
(3, 3, 1.00, 14), -- Bike, Heavy snow shower
(3, 2, 0.50, 15), -- Scooter, Light shower
(3, 3, 0.50, 15), -- Bike, Light shower
(3, 2, 0.50, 16), -- Scooter, Moderate shower
(3, 3, 0.50, 16), -- Bike, Moderate shower
(3, 2, 0.50, 17), -- Scooter, Heavy shower
(3, 3, 0.50, 17), -- Bike, Heavy shower
(3, 2, 1.00, 21), -- Scooter, Light sleet
(3, 3, 1.00, 21), -- Bike, Light sleet
(3, 2, 1.00, 22), -- Scooter, Moderate sleet
(3, 3, 1.00, 22), -- Bike, Moderate sleet
(3, 2, 1.00, 23), -- Scooter, Light snowfall
(3, 3, 1.00, 23), -- Bike, Light snowfall
(3, 2, 1.00, 24), -- Scooter, Moderate snowfall
(3, 3, 1.00, 24), -- Bike, Moderate snowfall
(3, 2, 1.00, 25), -- Scooter, Heavy snowfall
(3, 3, 1.00, 25), -- Bike, Heavy snowfall
(3, 2, 1.00, 26), -- Scooter, Blowing snow
(3, 3, 1.00, 26), -- Bike, Blowing snow
(3, 2, 1.00, 27), -- Scooter, Drifting snow
(3, 3, 1.00, 27); -- Bike, Drifting snow

INSERT INTO extra_fees (condition_type_id, vehicle_type_id, error, phenomenon_id) VALUES
(3, 2, 1, 4), -- Scooter, Glaze
(3, 3, 1, 4), -- Bike, Glaze
(3, 2, 1, 5), -- Scooter, Hail
(3, 3, 1, 5), -- Bike, Hail
(3, 2, 1, 6), -- Scooter, Thunder
(3, 3, 1, 6), -- Bike, Thunder
(3, 2, 1, 30), -- Scooter, Thunderstorm
(3, 3, 1, 30); -- Bike, Thunderstorm

INSERT INTO extra_fees (condition_type_id, condition_min, vehicle_type_id, error) VALUES
(2, 20.00, 3, 1); -- Bike, > 20m/s
