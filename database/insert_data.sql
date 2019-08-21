use inventory_management_system;

insert into role(code,name) values('ADMIN','Quản trị hệ thống');
insert into role(code,name) values('STUDENT','sinh vien');


insert into user(name,password,fullname,status)
values('admin','123456','admin',1);
insert into user(name,password,fullname,status)
values('nguyenvana','123456','nguyen van a',1);
insert into user(name,password,fullname,status)
values('nguyenvanb','123456','employee',1);

INSERT INTO user_role(userid,roleid) VALUES (1,1);
INSERT INTO user_role(userid,roleid) VALUES (2,2);
