drop table if exists customer;
drop table if exists drink;
create table customer
(
    version     integer,
    create_time datetime(6),
    update_time datetime(6),
    uuid        varchar(36) not null,
    name        varchar(255),
    primary key (uuid)
) engine = InnoDB;
create table drink
(
    drink_style      tinyint        not null,
    price            decimal(38, 2) not null,
    quantity_on_hand integer,
    version          integer,
    create_time      datetime(6),
    update_time      datetime(6),
    uuid             varchar(36)    not null,
    drink_name       varchar(50)    not null,
    upc              varchar(255)   not null,
    primary key (uuid)
) engine = InnoDB;
