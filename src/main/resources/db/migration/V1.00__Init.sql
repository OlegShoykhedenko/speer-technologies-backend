create table user
(
    id bigint auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    constraint user_pk
        primary key (id)
);

create unique index user_username_index
	on user (username);