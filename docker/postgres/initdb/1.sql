create table project
(
    project_id       char(32)     not null
        constraint user_pk
            primary key,
    project_alias    varchar(32)  not null,
    project_name     varchar(100) not null,
    project_overview varchar(500) not null
);

comment on table project is 'プロジェクトテーブル';

comment on column project.project_id is 'プロジェクトID';

comment on column project.project_alias is 'プロジェクトエイリアス';

comment on column project.project_name is 'プロジェクト名称';

comment on column project.project_overview is 'プロジェクト概要';


create unique index project_project_name_uindex
    on project (project_name);

create table domain_model
(
    domain_model_id char(32)     not null
        constraint domain_model_pk
            primary key,
    project_id      char(32)     not null
        constraint domain_model_project_project_id_fk
            references project,
    japanese_name   varchar(50)  not null,
    english_name    varchar(100) not null,
    specification   text         not null
);

comment on table domain_model is 'ドメインモデルテーブル';

comment on column domain_model.domain_model_id is 'ドメインモデルID';

comment on column domain_model.project_id is '紐づくプロジェクトID';

comment on column domain_model.japanese_name is 'ドメインモデル日本語名';

comment on column domain_model.english_name is 'ドメインモデル英語名（プロジェクト内で一意）';

comment on column domain_model.specification is 'モデルの仕様';

create unique index domain_model_project_id_english_name_uindex
    on domain_model (project_id, english_name);

