create table chat
(
    id             bigint auto_increment,
    first_user_id  bigint       not null,
    second_user_id bigint       not null,
    from_user      bigint       not null,
    message        varchar(255) not null,
    timestamp      date         not null,
    constraint chat_pk
        primary key (id),
    constraint first_user_fk
        foreign key (first_user_id) references user (id)
            on update cascade on delete cascade,
    constraint from_user__fk
        foreign key (from_user) references user (id)
            on update cascade on delete cascade,
    constraint second_user_fk
        foreign key (second_user_id) references user (id)
            on update cascade on delete cascade
);