-- Заполнение таблиц ключевыми данными
INSERT INTO race (id, damage, health, name, side_id) VALUES (DEFAULT, 10, 100, 'Человек', 2),
                                                            (DEFAULT, 20, 80, 'Дворф', 2),
                                                            (DEFAULT, 15, 95, 'Орк', 1),
                                                            (DEFAULT, 25, 70, 'Нежить', 1);

INSERT INTO account (id, email, password) VALUES (DEFAULT, 'anton@gmail.com', '123321'),
                                                 (DEFAULT, 'artem@gmail.com', '321123');

INSERT INTO ability (id, damage, health, name) VALUES (DEFAULT, 20, 0, 'Огненый шар'),
                                                      (DEFAULT, 10, 0, 'Удар ногой'),
                                                      (DEFAULT, 15, 0, 'выстрел'),
                                                      (DEFAULT, 25, 0, 'бросок оружия'),
                                                      (DEFAULT, 30, 0, 'ледяной шар'),
                                                      (DEFAULT, 0, 5, 'Легкое исцеление'),
                                                      (DEFAULT, 0, 10, 'Исцеление');


INSERT INTO person_class (id, damage, health, name) VALUES (DEFAULT, 22, 14, 'Маг'),
                                                           (DEFAULT, 13, 12, 'Воин'),
                                                           (DEFAULT, 43, 33, 'Жрец'),
                                                           (DEFAULT, 12, 21, 'Охотник'),
                                                           (DEFAULT, 31, 32, 'Друид');

INSERT INTO item (id, damage, health, type, name) VALUES (DEFAULT, 213, 21, 'wearable', 'Молоток'),
                                                         (DEFAULT, 123, 23, 'wearable', 'кинжал'),
                                                         (DEFAULT, 3, 23, 'wearable', 'пистолет'),
                                                         (DEFAULT, 1, 43, 'wearable', 'лук'),
                                                         (DEFAULT, 32, 12, 'usable', 'Исцеляющее зелье '),
                                                         (DEFAULT, 34, 32, 'wearable', 'Штаны классические'),
                                                         (DEFAULT, 121, 43, 'wearable', 'Штаны модные'),
                                                         (DEFAULT, 23, 12, 'usable', 'Сок');

INSERT INTO location (id, name) VALUES (DEFAULT, 'Пустыня'),
                                       (DEFAULT, 'Лес'),
                                       (DEFAULT, 'Болота'),
                                       (DEFAULT, 'Город'),
                                       (DEFAULT, 'Степь'),
                                       (DEFAULT, 'Ледниковая зона');

INSERT INTO npc_info (id, damage , name, health, location_id) VALUES (DEFAULT, 23, 'Конь', 12, 1),
                                                                     (DEFAULT, 12, 'Зомби', 2, 2),
                                                                     (DEFAULT, 34, 'Заяц', 23, 3),
                                                                     (DEFAULT, 21, 'Свинья', 1, 2),
                                                                     (DEFAULT, 53, 'Богомол', 21, 2),
                                                                     (DEFAULT, 12, 'Олень', 21, 1);



INSERT INTO location_near_location (location_id, near_location_id) VALUES (1, 2),
                                                                          (1, 3),
                                                                          (2, 1),
                                                                          (3, 1),
                                                                          (2, 4);

INSERT INTO person_class_ability (person_class_id, ability_id) VALUES (1, 1),
                                                                      (1, 2),
                                                                      (1, 3),
                                                                      (2, 2),
                                                                      (2, 3),
                                                                      (2, 4),
                                                                      (2, 5),
                                                                      (3, 1),
                                                                      (3, 3);

INSERT INTO person_class_ability (person_class_id, ability_id) VALUES (4, 1),
                                                                      (4, 3),
                                                                      (4, 2),
                                                                      (5, 1),
                                                                      (5, 2),
                                                                      (5, 3),
                                                                      (5, 4);

call create_person(2, 'Bob', 3, 1);
call create_person(2, 'Alice', 2, 2);
call create_person(1, 'John', 1, 3);
call create_person(1, 'Sam', 4, 4);

INSERT INTO person_item (item_id, person_id) VALUES (1, 1),
                                                    (2, 1),
                                                    (3, 1),
                                                    (4, 2),
                                                    (5, 2),
                                                    (6, 2),
                                                    (7, 3),
                                                    (8, 3),
                                                    (8, 3);