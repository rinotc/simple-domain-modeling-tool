create table bounded_contexts
(
    bounded_context_id       char(36)     not null
        constraint user_pk
            primary key,
    bounded_context_alias    varchar(32)  not null,
    bounded_context_name     varchar(100) not null,
    bounded_context_overview varchar(500) not null
);

comment on table bounded_contexts is 'プロジェクトテーブル';

comment on column bounded_contexts.bounded_context_id is '境界づけられたコンテキストID';

comment on column bounded_contexts.bounded_context_alias is '境界づけられたコンテキストのエイリアス';

comment on column bounded_contexts.bounded_context_name is '境界づけられたコンテキストの名称';

comment on column bounded_contexts.bounded_context_overview is '境界づけられたコンテキストの概要';

create unique index uq_bounded_contexts_bounded_context_alias
    on bounded_contexts (bounded_context_alias);

create table domain_models
(
    domain_model_id    char(36)     not null
        constraint domain_model_pk
            primary key,
    bounded_context_id char(36)     not null
        constraint domain_models_bounded_contexts_bounded_context_id_fk
            references bounded_contexts,
    ubiquitous_name    varchar(50)  not null,
    english_name       varchar(100) not null,
    knowledge          text         not null
);

comment on table domain_models is 'ドメインモデルテーブル';

comment on column domain_models.domain_model_id is 'ドメインモデルID';

comment on column domain_models.bounded_context_id is '紐づく境界づけられたコンテキストID';

comment on column domain_models.ubiquitous_name is 'ユビキタス名';

comment on column domain_models.english_name is 'ドメインモデル英語名（プロジェクト内で一意）';

comment on column domain_models.knowledge is 'モデルの知識';

create unique index domain_model_project_id_english_name_uindex
    on domain_models (bounded_context_id, english_name);


create table users
(
    user_id       char(36)                not null
        constraint users_pk
            primary key,
    user_name     varchar(50),
    email_address varchar(255)            not null,
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

create table auth_infos
(
    auth_info_id    char(36)     not null
        constraint auth_info_pk
            primary key,
    user_id         char(36)     not null,
    hashed_password varchar(255) not null,
    salt            varchar(255) not null
);

comment on table auth_infos is '認証情報';

comment on column auth_infos.auth_info_id is '認証情報ID';

comment on column auth_infos.user_id is 'ユーザーID';

comment on column auth_infos.hashed_password is 'ハッシュ済みパスワード';

comment on column auth_infos.salt is 'ソルト';
