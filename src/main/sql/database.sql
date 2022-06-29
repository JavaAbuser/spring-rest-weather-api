CREATE TABLE Measurement(
                            id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                            value INT NOT NULL CHECK (value > -100 AND value < 100),
                            is_raining BOOLEAN NOT NULL,
                            sensor_id INT NOT NULL REFERENCES Sensor(id),
                            created_at TIMESTAMP
);

CREATE TABLE Sensor(
                       id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(30) NOT NULL
);