CREATE TABLE IF NOT EXISTS worker (
  "id" SERIAL PRIMARY KEY,
  "username" VARCHAR(50) NOT NULL,
  "password" VARCHAR(50) NOT NULL,
  "nickname" VARCHAR(30) NOT NULL,
  "phone_number" VARCHAR(15) NOT NULL,
  "email" VARCHAR(30) NOT NULL
);