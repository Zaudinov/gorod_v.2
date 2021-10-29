INSERT INTO SERVICE (SERVICE_ID,name) VALUES
(1, 'Жилищно-коммунальные услуги'),
(2, 'Отопление'),
(3, 'Вода'),
(4, 'Горячяя Вода'),
(8, 'Нагрев горячей воды'),
(5, 'Холодная Вода'),
(6, 'Детский сад'),
(7, 'Ясли');

INSERT INTO USR (USER_ID, FIO, ACCOUNT, SERVICE_ID) VALUES
(1, 'Ivanov Petr Ivanovich', '1235213', 3),
(2, 'Petrov Aleksey Sergeevich', '2235213', 2);

INSERT INTO service_hierarchy (SERVICE_ID, PARENT_ID) values
(1, NULL),
(2, 1),
(3, 1),
(4, 3),
(5, 3),
(8, 4),
(6, NULL),
(7, 6);
