create database if not exists exercise_manage;

create table if not exists running_time (
    id bigint unsigned not null primary key auto_increment comment 'ID',
    time bigint unsigned not null comment 'ランニング時間',
    implementation_date date not null comment '実施日',
    create_time datetime not null default current_timestamp comment '作成日時',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新日時'
)
comment='ランニング時間';

create table if not exists learning_time (
    id bigint unsigned not null primary key auto_increment comment 'ID',
    content_id bigint unsigned not null comment '内容ID',
    time bigint unsigned not null comment '学習時間',
    implementation_date date not null comment '実施日',
    create_time datetime not null default current_timestamp comment '作成日時',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新日時'
)
comment='学習時間';

create table if not exists reading_time (
    id bigint unsigned not null primary key auto_increment comment 'ID',
    content_id bigint unsigned not null comment '内容ID',
    time bigint unsigned not null comment '読書時間',
    implementation_date date not null comment '実施日',
    create_time datetime not null default current_timestamp comment '作成日時',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新日時'
)
comment='読書時間';

create table if not exists learning_content (
    id bigint unsigned not null primary key auto_increment comment 'ID',
    title varchar(30) unique not null comment 'タイトル',
    description text comment '詳細',
    status int unsigned not null default 0 comment '状態',
    color varchar(7) comment '設定色',
    create_time datetime not null default current_timestamp comment '作成日時',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新日時'
)
comment='学習内容';

create table if not exists reading_content (
    id bigint unsigned not null primary key auto_increment comment 'ID',
    title varchar(30) unique not null comment 'タイトル',
    description text comment '詳細',
    status int unsigned not null default 0 comment '状態',
    color varchar(7) comment '設定色',
    image_path text comment '画像パス',
    create_time datetime not null default current_timestamp comment '作成日時',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新日時'
)
comment='読書内容';