CREATE DATABASE chatdb
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

create table messages(
  message_time BIGINT,
  message_body VARCHAR(4000)
);