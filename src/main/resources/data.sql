-- Заполнение таблиц ключевыми данными
INSERT INTO side (name) VALUES ('Альянс'),
                               ('Орда');

INSERT INTO race (damage, health, name, side_id) VALUES (10, 100, 'Человек', 1),
                                                            (20, 80, 'Дворф', 1),
                                                            (15, 95, 'Орк', 2),
                                                            (25, 70, 'Нежить', 2);

INSERT INTO account (email, password) VALUES ('anton@gmail.com', '123321'),
                                                 ('artem@gmail.com', '321123');

INSERT INTO ability (damage, health, name) VALUES (20, 0, 'Огненый шар'),
                                                      (10, 0, 'Удар ногой'),
                                                      (15, 0, 'выстрел'),
                                                      (25, 0, 'бросок оружия'),
                                                      (30, 0, 'ледяной шар'),
                                                      (0, 5, 'Легкое исцеление'),
                                                      (0, 10, 'Исцеление');


INSERT INTO person_class (damage, health, name) VALUES (22, 14, 'Маг'),
                                                           (13, 12, 'Воин'),
                                                           (43, 33, 'Жрец'),
                                                           (12, 21, 'Охотник'),
                                                           (31, 32, 'Друид');

INSERT INTO item (damage, health, type, name) VALUES (213, 21, 'wearable', 'Молоток'),
                                                         (123, 23, 'wearable', 'кинжал'),
                                                         (3, 23, 'wearable', 'пистолет'),
                                                         (1, 43, 'wearable', 'Kук'),
                                                         (32, 12, 'usable', 'Исцеляющее зелье '),
                                                         (34, 32, 'wearable', 'Штаны классические'),
                                                         (121, 43, 'wearable', 'Штаны модные'),
                                                         (23, 12, 'usable', 'Сок');

INSERT INTO location (name) VALUES ('Пустыня'),
                                       ('Лес'),
                                       ('Болота'),
                                       ('Город'),
                                       ('Степь'),
                                       ('Ледниковая зона');

INSERT INTO npc_info (damage, name, health, location_id) VALUES (23, 'Конь', 12, 1),
                                                                     (12, 'Зомби', 2, 2),
                                                                     (34, 'Заяц', 23, 3),
                                                                     (21, 'Свинья', 1, 2),
                                                                     (53, 'Богомол', 21, 2),
                                                                     (12, 'Олень', 21, 1);



INSERT INTO location_near_location (location_id, near_location_id) VALUES (1, 2),
                                                              (1, 3),
                                                              (2, 1),
                                                              (3, 1),
                                                              (2, 4);

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