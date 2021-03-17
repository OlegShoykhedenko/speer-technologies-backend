create table tweet
(
    id            bigint auto_increment,
    message_tweet varchar(255) not null,
    user_id       bigint       null,
    constraint tweet_pk
        primary key (id),
    constraint tweet_user_id_fk
        foreign key (user_id) references user (id)
);