use remotequery_getstarted
;

create table T_RQ_SERVICE (
   SERVICE_ID varchar(256) primary key,
   STATEMENTS varchar(4000),
   ROLES varchar(4000)
)
;


insert into T_RQ_SERVICE (SERVICE_ID,STATEMENTS,ROLES)
values ('rqService.save',
'
delete from T_RQ_SERVICE
where SERVICE_ID = :SERVICE_ID;insert into T_RQ_SERVICE
(SERVICE_ID, STATEMENTS,ROLES)
values
(:SERVICE_ID,:statements,:ROLES)
', 'SYSTEM');

;
