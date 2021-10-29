INSERT INTO SERVICE (SERVICE_ID,name, PARENT_ID) VALUES
(1, 'Жилищно-коммунальные услуги', null),
(2, 'Отопление', 1),
(3, 'Вода', 1),
(4, 'Горячяя Вода', 3),
(8, 'Нагрев горячей воды', 4),
(5, 'Холодная Вода', 3),
(6, 'Детский сад', null),
(7, 'Ясли', 6);

INSERT INTO USR (USER_ID, FIO, ACCOUNT, SERVICE_ID) VALUES
(1, 'Ivanov Petr Ivanovich', '1235213', 3),
(2, 'Petrov Aleksey Sergeevich', '2235213', 2);

