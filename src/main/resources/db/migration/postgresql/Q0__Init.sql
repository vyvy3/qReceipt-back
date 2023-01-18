--create tables
create table if not exists qreceipt_role
(
    id   bigserial
    primary key,
    name varchar(255)
    );

create table if not exists qreceipt_user
(
    id             bigserial
    primary key,
    name           varchar(255),
    password       varchar(255),
    phone          varchar(255),
    username       varchar,
    verified_email boolean
    );

create table if not exists qreceipt_user_roles
(
    user_id  bigint not null
    constraint fkbm37401u0t762a5cys56jveic
    references qreceipt_user,
    roles_id bigint not null
    constraint fk23pkeljgf974hjj6eriocm0h8
    references qreceipt_role,
    primary key (user_id, roles_id)
    );

create table if not exists qreceipt_email_verification
(
    id               bigserial
    primary key,
    code             varchar(255),
    user_id          bigint
    constraint fkr4lipvyjdtn9d716xqgu3cxk3
    references qreceipt_user,
    verification_key varchar(255)
    );

create table if not exists qreceipt_user_info
(
    id           bigserial
    primary key,
    about_me     varchar(255),
    birthday     date,
    country      varchar(255),
    first_name   varchar(255),
    gender       varchar(255),
    job          varchar(255),
    last_name    varchar(255),
    patronymic   varchar(255),
    phone_number varchar(255),
    user_id      bigint
    constraint fkktiaiu5smhfiwtokb5ud1ws3q
    references qreceipt_user
    );

--insert data

INSERT INTO qreceipt_user(id, verified_email, password, username)
VALUES (1, true, 'SYSTEM', 'SYSTEM');
INSERT INTO qreceipt_user(id, verified_email, password, username)
VALUES (2, true, 'bcrypt:13:60:16:y::$2y$13$KVH77D9Y3ByP1c7qEgso4.ZD20a7qDQDlqAZ7uUcIKD/0RCfBfRq6', 'admin');
INSERT INTO qreceipt_role(id, name)
VALUES (1, 'USER');
INSERT INTO qreceipt_role(id, name)
VALUES (2, 'ADMIN');
INSERT INTO qreceipt_user_roles(user_id, roles_id)
VALUES (2, 1);
INSERT INTO qreceipt_user_roles(user_id, roles_id)
VALUES (2, 2);
