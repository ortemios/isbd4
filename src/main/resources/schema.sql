-- Удаление таблиц
drop table if exists person_item cascade ;
drop table if exists item cascade;
drop type if exists item_type cascade;
drop table if exists person cascade;
drop table if exists npc cascade;
drop table if exists entity cascade;
drop table if exists npc_info cascade;
drop table if exists location_near_location cascade;
drop table if exists location cascade;
drop table if exists person_class_ability cascade;
drop table if exists person_class cascade;
drop table if exists ability cascade;
drop table if exists race cascade;
drop table if exists side cascade;
drop table if exists account cascade;

-- Создание таблицй
create table ability
(
    id     serial primary key,
    damage int not null check(damage >= 0),
    health int not null check(health >= 0),
    name   varchar(255)  not null,
    unique(name)
);

create table account
(
    id     serial primary key,
    email    varchar(255)  not null,
    password varchar(255)  not null,
    unique(email)
);

create table person_class
(
    id     serial primary key,
    damage int not null check(damage > 0),
    health int not null check(health > 0),
    name   varchar(255)  not null,
    unique(name)
);

create table person_class_ability
(
    person_class_id int not null references person_class (id) on delete restrict,
    ability_id  int not null references ability (id) on delete restrict,
    primary key (person_class_id, ability_id)
);

create table location
(
    id   serial primary key,
    name varchar(255)  not null,
    unique(name)
);

create table location_near_location
(
    location_id      int not null references location (id) on delete cascade,
    near_location_id int not null references location (id) on delete cascade,
    primary key (location_id, near_location_id)
);

create table entity
(
    id          serial primary key,
    health      int not null check (health >= 0),
    location_id int not null references location (id) on delete restrict
);

create type item_type as enum('wearable', 'usable');
create table item
(
    id     serial primary key,
    damage int not null check(damage >= 0),
    health int not null check(damage >= 0),
    type   item_type not null,
    name   varchar(255)  not null,
    unique(name)
);

create table side
(
    id     serial primary key,
    name varchar(255)  not null,
    unique(name)
);

create table race
(
    id      serial primary key,
    damage  int not null check(damage > 0),
    health  int not null check(health > 0),
    name    varchar(255)  not null,
    side_id int references side (id) on delete restrict,
    unique(name)
);

create table npc
(
    id        serial primary key,
    damage    int not null check(damage >= 0),
    entity_id int not null references entity (id) on delete cascade,
    name    varchar(255)  not null
);

create table npc_info
(
    id        serial primary key,
    damage    int not null check(damage >= 0),
    name      varchar(255)  not null,
    health    int not null check(health >= 0),
    location_id int not null references location (id) on delete restrict
);

create table person
(
    id            serial primary key,
    account_id    int not null references account (id) on delete cascade,
    experience    int not null check (experience >= 0) default 0,
    level         int not null check (level > 0) default 0,
    race_id       int not null references race (id) on delete restrict,
    name   varchar(255)  not null,
    person_class_id int references person_class (id) on delete restrict,
    entity_id     int references entity (id) on delete restrict,
    unique(name)
);

create table person_item
(
    id        serial primary key,
    item_id   int not null references item (id) on delete cascade,
    person_id int not null references person (id) on delete cascade
);

-- помогает найти всех персонажей принадлежащих этому аккаунту
create index person_account_id_idx ON person USING HASH (account_id);
-- помогает найти person по id его существа(Был выбран HASH по причине того, что он быстрее чем B-tree и нам необходимо сравнение только на совпадение )
create index person_entity_id_idx ON person USING HASH (entity_id);
-- помогает найти npc по id его существа(Был выбран HASH по причине того, что он быстрее чем B-tree и нам необходимо сравнение только на совпадение )
create index npc_entity_id_idx ON person USING HASH (entity_id);
-- Помогает найти пароль по user(Был выбран HASH по причине того, что он быстрее чем B-tree и нам необходимо сравнение только на совпадение )
create index account_email_id_idx ON account USING HASH(email);
-- помогает найти все предметы указанного пользователя(Был выбран HASH по причине того, что он быстрее чем B-tree и нам необходимо сравнение только на совпадение )
create index person_item_id_idx ON person_item USING HASH(person_id);
-- помогает найти все предметы указанного пользователя(Был выбран HASH по причине того, что он быстрее чем B-tree и нам необходимо сравнение только на совпадение )
create index entity_location_id_idx ON entity USING HASH(location_id);

-- Триггер
-- Если поле "health" сущности "entity" становится <= 0:
--     1) "entity" удаляется, если является "npc"
--     2) поле health восстанавливается, если "entity" является "person",
--     также происходит перемещение в стартовую локацию
drop trigger if exists check_entity_health_trigger on entity;
create or replace function check_entity_health() returns trigger as '
declare
r_npc_id int;
    r_person person%ROWTYPE;
begin
    if new.health <= 0 then
select npc.id into r_npc_id from npc where npc.entity_id = new.id;
if found then
delete from npc where npc.id = r_npc_id;
delete from entity where entity.id = new.id;
return null;
else
select * into r_person from person where person.entity_id = new.id;
new.location_id = (select location.id from location order by id limit 1);
            new.health = (select health from race where race.id = r_person.race_id) + (select health from person_class where person_class.id = r_person.person_class_id);
return new;
end if;
end if;
end;
' language plpgsql;
create trigger check_entity_health_trigger
    before update on entity
    for each row when (new.health <= 0)
    execute procedure check_entity_health();

-- При удалении person, удаляются связанные с ним entity
drop trigger if exists delete_person_entity_trigger on person;
create or replace function delete_person_entity() returns trigger as '
begin
delete from entity where entity.id = old.entity_id;
return null;
end;
' language plpgsql;
create trigger delete_person_entity_trigger
    after delete on person
    for each row
    execute procedure delete_person_entity();


-- При удалении npc, удаляются связанные с ним entity
drop trigger if exists delete_npc_entity_trigger on npc;
create or replace function delete_npc_entity() returns trigger as '
begin
delete from entity where entity.id = old.entity_id;
return null;
end;
' language plpgsql;
create trigger delete_npc_entity_trigger
    after delete on npc
    for each row
    execute procedure delete_npc_entity();

-- При накоплении опыта - уровень повышается, опыт обнуляется
drop trigger if exists check_person_experience_trigger on person;
create or replace function check_person_experience() returns trigger as '
begin
    if new.experience >= 10 then
        new.level = new.level + new.experience / 10;
        new.experience = new.experience % 10;
end if;
return new;
end;
' language plpgsql;
create trigger check_person_experience_trigger
    before update on person
    for each row when (new.experience >= 10)
    execute procedure check_person_experience();



-- Процедуры и функции

-- create_person - создает "entity" в стартовой локации и базовым запасом здоровья,
-- а также связанный с ней "person" с именем, расой и классом в указанном "account"
create or replace procedure create_person(p_account_id int, p_name varchar(255), p_race_id int, p_class_id int) as '
begin
with tmp as (
insert into entity (health, location_id)
values ((select health from race where race.id = p_race_id) +
    (select health from person_class where person_class.id = p_class_id),
    (select location.id from location order by id limit 1)
    )
    returning id
    )
insert into person(account_id, experience, level, race_id, name, person_class_id, entity_id)
values (p_account_id,
    0,
    1,
    p_race_id,
    p_name,
    p_class_id,
    (select id from tmp)
    );
end;
' language  plpgsql;

-- Перерождает "npc" согласно данным из "npc_info"
create or replace procedure spawn_npc() as '
declare
r_npc_info npc_info%ROWTYPE;
begin
delete from npc where true;
for r_npc_info in select * from npc_info loop
    with tmp as (
                insert into entity (health, location_id)
                    values (r_npc_info.health, r_npc_info.location_id)
                    returning id
            )
                  insert into npc(damage, entity_id, name)
                  values (
                      r_npc_info.damage,
                      (select id from tmp),
                      r_npc_info.name
                      );
end loop;
end;
' language  plpgsql;

-- Восстанваливет здоровье "person", запас которого меньше базового
create or replace procedure heal() as '
begin
with tmp as (
    select entity.id as entity_id from entity
                                           join person on entity.id = person.entity_id
                                           join race on person.race_id = race.id
                                           join person_class on person.person_class_id = person_class.id
    where entity.health < race.health + person_class.health
)
update entity set health = health + 1 where entity.id = any(select entity_id from tmp);
end;
' language  plpgsql;

-- Перемещает person в соседнюю локацию с указанным location_id
create or replace procedure move_person(person_id int, next_location_id int) as '
declare
person_entity_id int;
    curr_location_id int;
begin
select person.entity_id into person_entity_id from person where person_id = person.id;
select entity.location_id into curr_location_id from entity where entity.id = person_entity_id;
if exists(select * from location_near_location
        where location_near_location.location_id = curr_location_id
            and location_near_location.near_location_id = next_location_id
    ) then
update entity set location_id = next_location_id where entity.id = person_entity_id;
end if;
end;
' language  plpgsql;

-- Возвращает true, если обе "entity" являются "person", принадлежащими одной стороне
create or replace function are_friends(a_entity_id int, b_entity_id int) returns boolean as '
    declare
a_side int;
        b_side int;
begin
        a_side := coalesce((select side.id from side
            join race on side.id = race.side_id
            join person on race.id = person.race_id and person.entity_id = a_entity_id), -1);
        b_side := coalesce((select side.id from side
            join race on side.id = race.side_id
            join person on race.id = person.race_id and person.entity_id = b_entity_id), -2);
return a_side = b_side;
end;
' language  plpgsql;

-- Вычисляет запас здоровья entity с a_entity_id во время взаимодействия с b_entity_id
-- В случае взаимодействия с враждебеным существом, исцеляющий эффект предметов и способностей применяется к a_entity
-- В случае взаимодействия с дружественным существом, исцеляющий эффект на a_entity не действует
create or replace function entity_health(a_entity_id int, b_entity_id int, person_item_ids int[]) returns real as '
    declare
r_health int := 0;
        r_person person%ROWTYPE;
begin
select entity.health into r_health from entity where entity.id = a_entity_id;
select * into r_person from person where person.entity_id = a_entity_id;
if found then
            -- Race
            r_health := r_health + (select health from race where race.id = r_person.race_id);
            -- Person
            r_health := r_health + (select health from person_class where person_class.id = r_person.person_class_id);
            if (select are_friends(a_entity_id, b_entity_id)) = false then
                -- Abilities
                r_health := r_health +
                    coalesce((select sum(health) from ability join person_class_ability
                        on ability.id = person_class_ability.ability_id
                                and person_class_ability.person_class_id = r_person.person_class_id
                        group by person_class_ability.person_class_id
                    ), 0);
-- Items (wearable)
r_health := r_health +
                    coalesce((select sum(health) from item join person_item
                        on person_item.item_id = item.id
                               and person_item.person_id = r_person.id
                               and item.type = ''wearable''
                        group by person_item.person_id
                    ), 0);
                -- Items (usable)
                    r_health := r_health +
                    coalesce((select sum(health) from item join person_item
                        on person_item.item_id = item.id
                               and person_item.person_id = r_person.id
                               and item.type = ''usable''
                               and person_item.id = any (person_item_ids)
                        group by person_item.person_id
                    ), 0);
end if;
end if;
return r_health;
end;
' language  plpgsql;

-- Вычисляет запас здоровья entity с a_entity_id во время взаимодействия с b_entity_id
-- В случае взаимодействия с враждебеным существом, урон предметов и способностей применяется к b_entity
-- В случае взаимодействия с дружественным существом, исцеляющий эффект действует на b_entity
create or replace function entity_damage(a_entity_id int, b_entity_id int, person_item_ids int[]) returns int as '
declare
r_damage int := 0;
    r_npc npc%ROWTYPE;
    r_person person%ROWTYPE;
begin
select * into r_npc from npc where npc.entity_id = a_entity_id;
if found then
        r_damage := r_npc.damage;
else
select * into r_person from person where person.entity_id = a_entity_id;
if (select are_friends(a_entity_id, b_entity_id)) = true then
            -- Abilities
            r_damage := r_damage -
                       coalesce((select sum(health) from ability join person_class_ability
                         on ability.id = person_class_ability.ability_id and person_class_ability.person_class_id = r_person.person_class_id
                         group by person_class_ability.person_class_id
                       ), 0);
-- Items (usable)
r_damage := r_damage -
                       coalesce((select sum(health) from item join person_item
                                                           on person_item.item_id = item.id
                                                               and person_item.person_id = r_person.id
                                                               and item.type = ''usable''
                                                               and person_item.id = any (person_item_ids)
                         group by person_item.person_id
                       ), 0);
else
            -- Abilities
            r_damage := r_damage +
                       coalesce((select sum(damage) from ability join person_class_ability
                                                              on ability.id = person_class_ability.ability_id and person_class_ability.person_class_id = r_person.person_class_id
                         group by person_class_ability.person_class_id
                       ), 0);
            -- Items (wearable)
            r_damage := r_damage +
                       coalesce((select sum(damage) from item join person_item
                                                           on person_item.item_id = item.id and person_item.person_id = r_person.id and item.type = ''wearable''
                         group by person_item.person_id
                       ), 0);
            -- Items (usable)
            r_damage := r_damage +
                       coalesce((select sum(damage) from item join person_item
                                                           on person_item.item_id = item.id
                                                               and person_item.person_id = r_person.id
                                                               and item.type = ''usable''
                                                               and person_item.id = any (person_item_ids)
                         group by person_item.person_id
                       ), 0);
end if;
end if;
return r_damage;
end;
' language  plpgsql;

-- Взаимодействие двух entity
-- При взаимодействии враждебных существ, их здоровье уменьшается, согласно наносимому друг другу урону
-- Дружественные существа лечат друг друга
-- При получении отрицательных значений - см. триггер check_entity_health
-- Удаляются использованные предметы
create or replace procedure interact(attacker_id int, victim_id int, person_item_ids int[])  as '
declare
h1 int;
    h2 int;
    d1 int;
    d2 int;
    real_h1 int;
    real_h2 int;
    person_id int;
begin
    if (select location_id from entity where id = attacker_id) = (select location_id from entity where id = victim_id) then
        h1 := (select entity_health(attacker_id, victim_id, person_item_ids));
h2 := (select entity_health(victim_id, attacker_id, person_item_ids));
        d1 := (select entity_damage(attacker_id, victim_id, person_item_ids));
        d2 := (select entity_damage(victim_id, attacker_id, person_item_ids));

        h1 := h1 - d2;
        h2 := h2 - d1;

        real_h1 := (select health from entity where id = attacker_id);
        real_h2 := (select health from entity where id = victim_id);
        if h1 < real_h1 then
            real_h1 = h1;
end if;
        if h2 < real_h2 then
            real_h2 = h2;
end if;
        if real_h1 > 0 and real_h2 <= 0 then
select id into person_id from person where person.entity_id = attacker_id;
if found then
update person set experience = experience + 1 where person.entity_id = attacker_id;
insert into person_item (item_id, person_id) values (
                                                        (select id from item order by random() limit 1),
    person_id
    );
end if;
end if;
update entity set health = real_h1 where entity.id = attacker_id;
update entity set health = real_h2 where entity.id = victim_id;

delete from person_item where person_item.id = any(
    select person_item.id from person_item
                                   join person on person.id = person_item.person_id and person.entity_id = attacker_id
                                   join item on person_item.item_id = item.id and item.type = ''usable''
    where person_item.id = any(person_item_ids)
);
end if;
end;
' language  plpgsql;


