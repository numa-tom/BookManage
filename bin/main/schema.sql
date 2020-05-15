CREATE TABLE book (
  id int(2) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  type varchar(50) NOT NULL,
  writer varchar(50) NOT NULL,
  publisher varchar(20) NOT NULL, 
  pageNumber int NOT NULL,
  charge int NOT NULL,
  place varchar(20) NOT NULL,
  comment varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
);
