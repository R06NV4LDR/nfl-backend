create database `nfl-db`;

create table team (
id int(32) primary key auto_increment,
name varchar(64) not null,
city varchar(45) not null,
state varchar(45) not null,
conference varchar(3) not null,
division varchar(5) not null
);

create table player (
id int(255) primary key auto_increment,
name varchar(64) not null,
pos varchar(5) not null,
college varchar(45)
);

use `nfl-db`;
select * from team;
select abbreviation as ABBR, name as Name, conference as CONF, division as DIVISION, city as City, state as State from team;
select * from user;