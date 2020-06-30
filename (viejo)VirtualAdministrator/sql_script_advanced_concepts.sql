select now();

select concat(unix_timestamp(),floor((rand()*500)));

create database granero;

show databases;

show tables from granero;


INSERT INTO `granero`.`ventas`
(`id`,`idconjunto`,`codigo`,`N`,`time`,`cliente`,`tipoventa`)
select 0, ifnull(max((`ventas`.`idconjunto`)+1), 1), 77012, 1 ,now(), 123, 2 from ventas;

select * from ventas;


select 1, 2, 'test', (`ventas`.`idconjunto`+1) from ventas;


select ifnull(max((`ventas`.`idconjunto`))+1, 1) as a from ventas;

INSERT INTO `granero`.`ventas` 
(`id`,`idconjunto`,`codigo`,`N`,`time`,`cliente`,`tipoventa`)
values (0, 11, 2, 10, now(), 1, 1);



SELECT * FROM ventas WHERE DATE(`ventas`.`time`) = "2016-06-05";

select count(idconjunto) from ventas group by idconjunto;

use granero;

select * from ventas;


select count(*) as num from (select count(idconjunto) from ventas WHERE DATE(`ventas`.`time`) = "2016-06-05" group by idconjunto) as counts;



select CURDATE();



#adicion columna N productos.


use granero;

select * from productos;

select * from productos where codigo like '0%';

select * from ventas;

use inventarios;

select * from `2015/11/08 10:40:45`;


#query para restar n unidades de stock
UPDATE `granero`.`productos` 
SET `stock` = `productos`.`stock`-2 
WHERE `codigo` = '0001';

select * from productos where codigo='0001';

