CREATE TABLE worker (
  "worker_pk" SERIAL PRIMARY KEY,
  "id" VARCHAR(50) NOT NULL,
  "pw" VARCHAR(50) NOT NULL,
  "name" VARCHAR(30) NOT NULL,
  "phone_number" VARCHAR(15) NOT NULL,
  "email" VARCHAR(30) NOT NULL,
  "acc_status" BOOLEAN NOT NULL DEFAULT TRUE
);
