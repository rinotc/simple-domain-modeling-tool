-- projects
create table projects
(
    project_id       char(36)     not null
        constraint user_pk
            primary key,
    project_alias    varchar(32)  not null,
    project_name     varchar(100) not null,
    project_overview varchar(500) not null
);

comment on table projects is 'プロジェクトテーブル';

comment on column projects.project_id is 'プロジェクトID';

comment on column projects.project_alias is 'プロジェクトエイリアス';

comment on column projects.project_name is 'プロジェクト名称';

comment on column projects.project_overview is 'プロジェクト概要';

create unique index project_project_name_uindex
    on projects (project_name);

-- domain_models
create table domain_models
(
    domain_model_id char(36)     not null
        constraint domain_model_pk
            primary key,
    project_id      char(36)     not null
        constraint domain_model_project_project_id_fk
            references projects,
    japanese_name   varchar(50)  not null,
    english_name    varchar(100) not null,
    specification   text         not null
);

comment on table domain_models is 'ドメインモデルテーブル';

comment on column domain_models.domain_model_id is 'ドメインモデルID';

comment on column domain_models.project_id is '紐づくプロジェクトID';

comment on column domain_models.japanese_name is 'ドメインモデル日本語名';

comment on column domain_models.english_name is 'ドメインモデル英語名（プロジェクト内で一意）';

comment on column domain_models.specification is 'モデルの仕様';

create unique index domain_model_project_id_english_name_uindex
    on domain_models (project_id, english_name);


-- users

create table users
(
    user_id       char(36)                not null
        constraint users_pk
            primary key,
    user_name     varchar(50)             not null,
    email_address varchar(100)            not null,
    avatar_url    varchar(1000),
    created_at    timestamp default now() not null,
    updated_at    timestamp default now() not null
);

comment on table users is 'ユーザーテーブル';

comment on column users.user_id is 'ユーザーID';

comment on column users.user_name is 'ユーザー名';

comment on column users.email_address is 'メールアドレス';

comment on column users.avatar_url is 'アバター画像のURL';

comment on column users.created_at is '作成日時';

comment on column users.updated_at is '更新日時';

create unique index users_email_address_uindex
    on users (email_address);

