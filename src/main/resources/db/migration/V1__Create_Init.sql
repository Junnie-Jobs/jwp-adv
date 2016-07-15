drop table if exists Card;
drop table if exists Deck;
drop table if exists Board;
drop table if exists User;

create table User (
    USER_TYPE varchar(31) not null,
    id bigint not null auto_increment,
    email varchar(255),
    userId varchar(255),
    accessToken varchar(255),
    name varchar(255),
    password varchar(255),
    primary key (id)
);

create table Board (
    id bigint not null auto_increment,
    name varchar(255) not null,
    creator_id bigint not null,
    primary key (id)
);

create table Deck (
    id bigint not null auto_increment,
    name varchar(255),
    board_id bigint not null,
    primary key (id)
);

create table Card (
    id bigint not null auto_increment,
    description varchar(5000),
    title varchar(200),
    deck_id bigint,
    primary key (id)
);

alter table Board 
    add constraint fk_creator_id 
    foreign key (creator_id) 
    references User (id);
    
alter table Card 
    add constraint fk_deck_id 
    foreign key (deck_id) 
    references Deck (id);
    
alter table Card 
    add constraint fk_deck_id 
    foreign key (deck_id) 
    references Deck (id);