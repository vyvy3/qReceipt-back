create table qrecipient_receipt (
    id bigserial primary key,
    file_name varchar,
    extension varchar,
    print_date timestamp,
    author varchar
);