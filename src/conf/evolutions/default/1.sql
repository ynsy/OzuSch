# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table course_instructors (
  id                        integer auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  is_primary                tinyint(1) default 0,
  course_id                 integer,
  constraint pk_course_instructors primary key (id))
;

create table courses (
  id                        integer auto_increment not null,
  subject_name              varchar(255),
  course_no                 varchar(255),
  display_name              varchar(255),
  section_no                varchar(255),
  constraint pk_courses primary key (id))
;

create table instructors (
  id                        integer auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  email                     varchar(255),
  constraint pk_instructors primary key (id))
;

create table lecture_intervals (
  id                        integer auto_increment not null,
  start_hour                varchar(255),
  end_hour                  varchar(255),
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
  university_id             integer,
  constraint pk_students primary key (id))
;

create table universities (
  id                        integer auto_increment not null,
  name                      varchar(255),
  constraint pk_universities primary key (id))
;

alter table course_instructors add constraint fk_course_instructors_course_1 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_course_instructors_course_1 on course_instructors (course_id);
alter table lecture_intervals add constraint fk_lecture_intervals_course_2 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_lecture_intervals_course_2 on lecture_intervals (course_id);
alter table past_courses add constraint fk_past_courses_student_3 foreign key (student_id) references students (id) on delete restrict on update restrict;
create index ix_past_courses_student_3 on past_courses (student_id);
alter table past_courses add constraint fk_past_courses_course_4 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_past_courses_course_4 on past_courses (course_id);
alter table requested_courses add constraint fk_requested_courses_student_5 foreign key (student_id) references students (id) on delete restrict on update restrict;
create index ix_requested_courses_student_5 on requested_courses (student_id);
alter table requested_courses add constraint fk_requested_courses_course_6 foreign key (course_id) references courses (id) on delete restrict on update restrict;
create index ix_requested_courses_course_6 on requested_courses (course_id);
alter table students add constraint fk_students_university_7 foreign key (university_id) references universities (id) on delete restrict on update restrict;
create index ix_students_university_7 on students (university_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table course_instructors;

drop table courses;

drop table instructors;

drop table lecture_intervals;

drop table past_courses;

drop table requested_courses;

drop table students;

drop table universities;

SET FOREIGN_KEY_CHECKS=1;

