CREATE TABLE customer_profile (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  email VARCHAR(255) NOT NULL UNIQUE,
                                  password VARCHAR(255) NOT NULL
);

CREATE TABLE transaction (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             usage DOUBLE NOT NULL,
                             cost DOUBLE NOT NULL,
                             date VARCHAR(255) NOT NULL,
                             customer_profile_id BIGINT,
                             FOREIGN KEY (customer_profile_id) REFERENCES customer_profile(id)
);
