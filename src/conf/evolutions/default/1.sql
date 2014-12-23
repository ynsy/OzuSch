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
  department_id             integer,
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
  student_id                integer,
  course_id                 integer,
  constraint pk_past_courses primary key (id))
;

create table requested_courses (
  id                        integer auto_increment not null,
  student_id                integer,
  course_id                 integer,
  constraint pk_requested_courses primary key (id))
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

alter table departments add constraint fk_departments_university_1 foreign key (university_id) references universities (id) on delete restrict on update restrict;
create index ix_departments_university_1 on departments (university_id);
alter table instructors add constraint fk_instructors_department_2 foreign key (department_id) references departments (id) on delete restrict on update restrict;
create index ix_instructors_department_2 on instructors (department_id);
alter table lecture_intervals add constraint fk_lecture_intervals_course_3 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_lecture_intervals_course_3 on lecture_intervals (course_id);
alter table past_courses add constraint fk_past_courses_student_4 foreign key (student_id) references students (id) on delete restrict on update restrict;
create index ix_past_courses_student_4 on past_courses (student_id);
alter table past_courses add constraint fk_past_courses_course_5 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_past_courses_course_5 on past_courses (course_id);
alter table requested_courses add constraint fk_requested_courses_student_6 foreign key (student_id) references students (id) on delete restrict on update restrict;
create index ix_requested_courses_student_6 on requested_courses (student_id);
alter table requested_courses add constraint fk_requested_courses_course_7 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_requested_courses_course_7 on requested_courses (course_id);
alter table students add constraint fk_students_department_8 foreign key (department_id) references departments (id) on delete restrict on update restrict;
create index ix_students_department_8 on students (department_id);
alter table students add constraint fk_students_university_9 foreign key (university_id) references universities (id) on delete restrict on update restrict;
create index ix_students_university_9 on students (university_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table courses;

drop table departments;

drop table instructors;

drop table lecture_intervals;

drop table past_courses;

drop table requested_courses;

drop table students;

drop table universities;

SET FOREIGN_KEY_CHECKS=1;

