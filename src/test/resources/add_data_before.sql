DELETE FROM usr;
DELETE FROM service;
TRUNCATE TABLE usr RESTART IDENTITY;

INSERT INTO SERVICE (service_id, name, parent_id) VALUES
(1, 'Жилищно-коммунальные услуги', null),
(2, 'Отопление', 1),
(3, 'Вода', 1),
(4, 'Горячяя Вода', 3),
(8, 'Нагрев горячей воды', 4),
(5, 'Холодная Вода', 3),
(6, 'Детский сад', null),
(7, 'Ясли', 6);

INSERT INTO USR (USER_ID, ACCOUNT, FIO, SERVICE_ID) VALUES
(1, '1234513', 'Ivanov Petr Ivanovich',  3),
(2, '1234514', 'Petrov Aleksey Sergeevich', 2),
(3, '1234525', 'Petrov Petr Sergeevich', 7),
(4, '1234526', 'Sychev Vladimir Alekseevich', 1);