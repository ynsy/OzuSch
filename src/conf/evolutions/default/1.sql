# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table student (
  id                        varchar(255) not null,
  name                      varchar(255),
  surname                   varchar(255),
  display_name              varchar(255),
  email                     varchar(255),
  constraint pk_student primary key (id))
;

create sequence student_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists student;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists student_seq;

