drop table qrecipient_user_info;
alter table qrecipient_user
add column first_name varchar,
add column last_name varchar,
add column gender varchar;