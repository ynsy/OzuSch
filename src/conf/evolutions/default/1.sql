# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table student (
  id                        integer auto_increment not null,
  name                      varchar(255),
  surname                   varchar(255),
  display_name              varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  department_id             integer,
  school_id                 integer,
  constraint pk_student primary key (id))
;




# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table student;

SET FOREIGN_KEY_CHECKS=1;

