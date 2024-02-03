create table question
(
    id            INT auto_increment PRIMARY KEY,
    title         VARCHAR(50)   null,
    description   TEXT          null,
    gmt_create    BIGINT        null,
    gmt_modified  BIGINT        null,
    creator       INT           null,
    comment_count INT default 0 null,
    view_count    INT default 0 null,
    like_count    INT default 0 null,
    tag            VARCHAR(256) null
);