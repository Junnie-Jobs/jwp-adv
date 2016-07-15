drop table if exists card;
drop table if exists deck;
drop table if exists board;
drop table if exists user;

create table user (
    USER_TYPE varchar(31) not null,
    id bigint not null auto_increment,
    email varchar(255),
    user_id varchar(255),
    access_token varchar(255),
    name varchar(255),
    password varchar(255),
    primary key (id)
);

create table board (
    id bigint not null auto_increment,
    name varchar(255) not null,
    creator_id bigint not null,
    primary key (id)
);

create table deck (
    id bigint not null auto_increment,
    name varchar(255),
    board_id bigint not null,
    primary key (id)
);

create table card (
    id bigint not null auto_increment,
    description varchar(5000),
    title varchar(200),
    deck_id bigint,
    primary key (id)
);

alter table board 
	add constraint fk_creator_id 
    foreign key (creator_id) 
    references user (id);
    
alter table card 
    add constraint fk_deck_id 
    foreign key (deck_id) 
    references deck (id);