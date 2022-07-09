create table captcha_codes (id integer not null auto_increment, code TINYTEXT NOT NULL, secret_code TINYTEXT NOT NULL, time datetime(6) not null, primary key (id)) engine=InnoDB;
create table global_settings (id integer not null auto_increment, code varchar(255) not null, name varchar(255) not null, value varchar(255) not null, primary key (id)) engine=InnoDB;
create table post_comments (id integer not null auto_increment, text TEXT NOT NULL, time datetime(6) not null, parent_id integer, post_id integer not null, user_id integer not null, primary key (id)) engine=InnoDB;
create table post_votes (id integer not null auto_increment, time datetime(6) not null, value tinyint not null, post_id integer not null, user_id integer not null, primary key (id)) engine=InnoDB;
create table posts (id integer not null auto_increment, is_active bit not null, moderation_status enum('NEW','ACCEPTED','DECLINED') NOT NULL, text TEXT NOT NULL, time datetime(6) not null, title varchar(255) not null, view_count integer not null, moderator_id integer, user_id integer not null, primary key (id)) engine=InnoDB;
create table tag2post (id integer not null auto_increment, post_id integer not null, tag_id integer not null, primary key (id)) engine=InnoDB;
create table tags (id integer not null auto_increment, name varchar(255) not null, primary key (id)) engine=InnoDB;
create table users (id integer not null auto_increment, code varchar(255), email varchar(255) not null, is_moderator tinyint not null, name varchar(255) not null, password varchar(255) not null, photo TEXT NULL, reg_time datetime(6) not null, primary key (id)) engine=InnoDB;
alter table tags add constraint UK_t48xdq560gs3gap9g7jg36kgc unique (name);
alter table post_comments add constraint FKc3b7s6wypcsvua2ycn4o1lv2c foreign key (parent_id) references post_comments (id);
alter table post_comments add constraint FKaawaqxjs3br8dw5v90w7uu514 foreign key (post_id) references posts (id);
alter table post_comments add constraint FKsnxoecngu89u3fh4wdrgf0f2g foreign key (user_id) references users (id);
alter table post_votes add constraint FK9jh5u17tmu1g7xnlxa77ilo3u foreign key (post_id) references posts (id);
alter table post_votes add constraint FK9q09ho9p8fmo6rcysnci8rocc foreign key (user_id) references users (id);
alter table posts add constraint FK6m7nr3iwh1auer2hk7rd05riw foreign key (moderator_id) references users (id);
alter table posts add constraint FK5lidm6cqbc7u4xhqpxm898qme foreign key (user_id) references users (id);
alter table tag2post add constraint FKpjoedhh4h917xf25el3odq20i foreign key (post_id) references posts (id);
alter table tag2post add constraint FKjou6suf2w810t2u3l96uasw3r foreign key (tag_id) references tags (id);

INSERT INTO `users` (`id`,`email`, `is_moderator`, `name`, `password`, `reg_time`) VALUES (1, 'mymail@mail.com', '1', 'Anton', '123', '2000-10-10 10:00:00');
INSERT INTO `users` (`id`,`email`, `is_moderator`, `name`, `password`, `reg_time`) VALUES (2, 'mail1@mail.com', '0', 'Ivan', '1234', '2005-10-04 07:00:00');
INSERT INTO `users` (`id`,`email`, `is_moderator`, `name`, `password`, `reg_time`) VALUES (3, 'mail2@mail.ru', '0', 'Petr', 'pass1235', '2010-05-10 12:00:00');
INSERT INTO `users` (`id`,`email`, `is_moderator`, `name`, `password`, `reg_time`) VALUES (4, 'mail3@gmail.com', '0', 'Masha', '1236', '2015-10-10 17:00:00');

INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `text`, `time`, `title`, `view_count`, `moderator_id`, `user_id`) VALUES (1, 1, 'ACCEPTED', 'Moscow is the biggest city in Russia', '2001-05-05 10:00:00','Moscow', 44, 1, 2);
INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `text`, `time`, `title`, `view_count`, `moderator_id`, `user_id`) VALUES (2, 1, 'ACCEPTED', 'Dubai very nice place for travel at february', '2001-05-05 10:00:00','Dubai', 55, 1, 2);
INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `text`, `time`, `title`, `view_count`, `moderator_id`, `user_id`) VALUES (3, 1, 'ACCEPTED', 'London is the capital of Great Britain', '2001-05-05 10:00:00','London', 33, 1, 3);
INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `text`, `time`, `title`, `view_count`, `moderator_id`, `user_id`) VALUES (4, 1, 'ACCEPTED', 'Istanbul is the city on two sea', '2001-05-05 10:00:00','Istanbul', 22, 1, 4);
INSERT INTO `posts` (`id`, `is_active`, `moderation_status`, `text`, `time`, `title`, `view_count`, `moderator_id`, `user_id`) VALUES (5, 1, 'ACCEPTED', 'Manhattan is the heart of New-York ', '2001-05-05 10:00:00','New-York', 100, 1, 2);

INSERT INTO `tags` (`id`, `name`) VALUES (1, 'Moscow');
INSERT INTO `tags` (`id`, `name`) VALUES (2, 'Dubai');
INSERT INTO `tags` (`id`, `name`) VALUES (3, 'London');
INSERT INTO `tags` (`id`, `name`) VALUES (4, 'Istanbul');
INSERT INTO `tags` (`id`, `name`) VALUES (5, 'New-York');

INSERT INTO `tag2post` (`post_id`, `tag_id`) VALUES (1, 1);
INSERT INTO `tag2post` (`post_id`, `tag_id`) VALUES (2, 2);
INSERT INTO `tag2post` (`post_id`, `tag_id`) VALUES (3, 3);
INSERT INTO `tag2post` (`post_id`, `tag_id`) VALUES (4, 4);
INSERT INTO `tag2post` (`post_id`, `tag_id`) VALUES (5, 5);

INSERT INTO `post_comments` (`text`, `time`, `parent_id`, `post_id`, `user_id`) VALUES ('Super post about Moscow', '2010-05-05 10:10:10', null, 1, 1);
INSERT INTO `post_comments` (`text`, `time`, `parent_id`, `post_id`, `user_id`) VALUES ('Travel to Dubai', '2010-05-05 10:10:10', null, 2, 1);
INSERT INTO `post_comments` (`text`, `time`, `parent_id`, `post_id`, `user_id`) VALUES ('Very interest post', '2010-05-05 10:10:10', null, 2, 1);
INSERT INTO `post_comments` (`text`, `time`, `parent_id`, `post_id`, `user_id`) VALUES ('New-York', '2010-05-05 10:10:10', null, 5, 1);
