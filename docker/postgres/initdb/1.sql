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

