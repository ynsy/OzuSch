# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table courses (
  id                        integer auto_increment not null,
  subject_name              varchar(255),
  course_no                 integer,
  display_name              varchar(255),
  section                   varchar(255),
  constraint pk_courses primary key (id))
;

create table departments (
  id                        integer auto_increment not null,
  name                      varchar(255),
  university_id             integer,
  constraint pk_departments primary key (id))
;

create table instructors (
  id                        integer auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  university_id             integer,
  constraint pk_instructors primary key (id))
;

create table lecture_intervals (
  id                        integer auto_increment not null,
  start_hour                datetime,
  end_hour                  datetime,
  day                       varchar(255),
  room_code                 varchar(255),
  course_id                 integer,
  constraint pk_lecture_intervals primary key (id))
;

create table past_courses (
  id                        varchar(255) not null,
  student_id                varchar(255),
  course_id                 varchar(255),
  constraint pk_past_courses primary key (id))
;

create table student_courses (
  id                        integer auto_increment not null,
  student_id                integer,
  course_id                 integer,
  constraint pk_student_courses primary key (id))
;

create table students (
  id                        integer auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  display_name              varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  department_id             integer,
  university_id             integer,
  constraint pk_students primary key (id))
;

create table universities (
  id                        integer auto_increment not null,
  name                      varchar(255),
  constraint pk_universities primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table courses;

drop table departments;

drop table instructors;

drop table lecture_intervals;

drop table past_courses;

drop table student_courses;

drop table students;

drop table universities;

SET FOREIGN_KEY_CHECKS=1;

