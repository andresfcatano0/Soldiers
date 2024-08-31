drop database if exists soldier;
create database soldier;
use soldier;

-- create tables and relationships
create table soldier (
    soldier_id int primary key auto_increment,
    first_name varchar(50) not null,
    middle_name varchar(50) null,
    last_name varchar(50) not null,
    dob date null,
    height_in_inches int not null
);


-- data
insert into soldier (first_name, middle_name, last_name, dob, height_in_inches) values
	('Andres','F','Catano','1987-12-16',76),
	('Ana','M','Gomez','1961-10-17',41),
	('Shu','C','Lin','1986-10-12',60),
	('Mia','S','Catano','1999-04-04',52),
	('Alejandra','G','Gomez','1967-04-18',44),
	('Piedad','S','Gonzalez',null,58),
	('Norelly','D','Guzman','1979-03-28',68);
