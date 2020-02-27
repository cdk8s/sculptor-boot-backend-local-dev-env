DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    id                   bigint      NOT NULL COMMENT '主键ID',
    username             varchar(50) NOT NULL COMMENT '用户账号:oneParam',
    real_name            varchar(50) NULL COMMENT '真实姓名',
    user_password        varchar(50) NOT NULL COMMENT '登录密码',
    password_salt        varchar(10) NOT NULL COMMENT '密码盐:放于密码后面',
    user_email           varchar(50) NULL COMMENT '邮箱地址:oneParam',
    telephone            varchar(20) NULL COMMENT '固话',
    mobile_phone         varchar(20) NULL COMMENT '手机号:oneParam',
    gender_enum          smallint    NOT NULL DEFAULT '1' COMMENT '性别:[1=保密=PRIVACY, 2=男性=MALE, 3=女性=FEMALE, 4=中性=NEUTRAL]max=4',
    register_type_enum   smallint    NOT NULL DEFAULT '1' COMMENT '注册方式:[1=系统预置=SYSTEM_INIT, 2=后台管理系统新增=MANAGEMENT_ADD, 3=主动注册=REGISTER, 4=被邀请注册=INVITE]max=4',
    register_origin_enum smallint    NOT NULL DEFAULT '1' COMMENT '注册来源:[1=WEB方式=WEB, 2=安卓APP=ANDROID, 3=苹果APP=IOS, 4=H5=H5, 5=微信小程序=WECHAT_MINI_PROGRAM, 6=微信公众号=WECHAT_OFFICIAL_ACCOUNT]max=6',
    state_enum           smallint    NOT NULL DEFAULT '1' COMMENT '启用状态:[1=启用=ENABLE, 2=禁用=DISABLE]max=2',
    delete_enum          smallint    NOT NULL DEFAULT '1' COMMENT '删除状态:[1=未删除=NOT_DELETED, 2=已删除=DELETED]max=2',
    create_date          bigint      NOT NULL COMMENT '创建时间',
    create_user_id       bigint      NOT NULL COMMENT '创建人',
    update_date          bigint      NOT NULL COMMENT '更新时间',
    update_user_id       bigint      NOT NULL COMMENT '更新人',
    delete_date          bigint      NULL COMMENT '删除时间',
    delete_user_id       bigint      NULL COMMENT '删除人',
    PRIMARY KEY (id)
) COMMENT ='用户表';
