package com.atme.utils.my.result.exception;


/**
 * 视图结果枚举码(2000-3000).
 *
 * @author S
 * @version 1.0 2020/2/12
 * @since 1.0
 */
public enum ViewResultCodeEnum implements ResultCodeEnumMsg {
    COMPONENT_TYPE_ERROR(1017, "组件缺少配置-类型"),
    COMPONENT_DESERIALIZE_ERROR(1018, "组件配置反序列化失败"),
    COMPONENT_TITLE_ERROR(1019, "组件缺少配置-标题"),
    COMPONENT_TITLE_DUPLICATE(1020, "组件标题不能重复"),
    COMPONENT_TYPE_INVALID(1021, "组件类型配置异常"),
    COMPONENT_CLASSIFY_ERROR(1022, "组件缺少配置-分类"),
    VOLUNTEER_NOT_EXIST(1023, "志愿者不存在"),

    TALLY_OVER(2000, "在线测试次数已达上限"),
    TALLY_OVER_EN(2000, "The number of online tests has reached the upper limit"),
    USER_OP_INVALID(2001, "用户异常操作,请退出后重试"),
    USER_OP_INVALID_EN(2001, "User's abnormal operation, please exit and try again"),
    IMAGE_CAPTCHA_ERROR(2002, "生成图形验证码失败"),
    IMAGE_CAPTCHA_ERROR_EN(2002, "Failed to generate graphic captcha"),
    CODE_ERROR(2003, "验证码错误"),
    CODE_ERROR_EN(2003, "Verification code error"),
    USER_NOT_EXIST(2004, "账号未注册或已注销"),
    USER_NOT_EXIST_EN(2004, "Account number not registered or cancelled"),
    SITE_NOT_EXIST(2005, "站点不存在"),
    SITE_NOT_EXIST_EN(2005, "The site does not exist"),
    MAIL_CONFIG_NOT_EXIST(2006, "站点邮件服务未配置"),
    MAIL_CONFIG_NOT_EXIST_EN(2006, "Site mail service is not configured"),
    USER_EXIST(2007, "账号已注册"),
    USER_EXIST_EN(2007, "Account registered"),
    PASSWORD_STRENGTH_ERROR(2008, "密码强度不足"),
    PASSWORD_STRENGTH_ERROR_EN(2008, "Insufficient password strength"),
    INVITE_CODE_NOT_EXIST(2009, "邀请码不存在"),
    INVITE_CODE_NOT_EXIST_EN(2009, "Invitation code does not exist"),
    VOLUNTEER_TYPE_NOT_EXIST(2010, "志愿者类型不存在"),
    VOLUNTEER_TYPE_NOT_EXIST_EN(2010, "Volunteer type does not exist"),
    INVITE_CODE_IS_EMPTY(2011, "邀请码不能为空"),
    INVITE_CODE_IS_EMPTY_EN(2011, "Invitation code cannot be empty"),
    INSERT_RECORD_ERROR(2012, "提交表单失败，字段异常"),
    INSERT_RECORD_ERROR_EN(2012, "Failed to submit form, field exception"),
    TABLE_COMMIT_ERROR(2013, "表单提交的字段不足，请退出重试"),
    TABLE_COMMIT_ERROR_EN(2013, "There are not enough fields submitted in the form. Please exit and try again"),
    COMMIT_TIME_ERROR(2014,"未在开放的时间段内，如有疑问请联系管理员"),
    COMMIT_TIME_ERROR_EN(2014,"The operation time does not meet the requirements, please operate within the specified period of time"),
    TYPE_TABLE_NOT_EXIT(2015,"当前类型的志愿者表单暂无，请联系管理员添加"),
    TYPE_TABLE_NOT_EXIT_EN(2015,"There is no volunteer form of current type, please contact the administrator to add it"),
    INVITE_CODE_EXIST(2016, "已选择邀请码,请勿重复选择"),
    INVITE_CODE_EXIST_EN(2016, "Invitation code has been selected, please do not select again"),
    LOGIN_ERROR(2017,"用户名或者密码错误"),
    LOGIN_ERROR_EN(2017,"Wrong user name or password"),
    PASSWORD_OLD_ERROR(2018,"密码超过3个月未更换，请点击“忘记密码”进行密码更新，原密码将不可用!"),
    PASSWORD_OLD_ERROR_EN(2018,"If your password has not been changed for more than 3 months, click Forget Password to change your password, The original password will not be available!"),
    COMMIT_ERROR_DONE(2019,"报名表已提交"),
    COMMIT_ERROR_DONE_EN(2019,"Application form submitted"),
    FILE_TYPE_ERROR(2020,"文件类型暂不支持"),
    FILE_TYPE_ERROR_EN(2021,"The file type is not supported at the moment"),
    REPEAT_ERROR(2022,"禁止时间段内重复提交"),
    STRINGCODE_NOT_EXIST(2023,"无效的用户码！"),
    REFUSE_SUBMIT(2024,"报名初审时间已过，提交失败！"),
    MAIL_HAS_SEND(2025, "验证码已发送至您邮箱，如您未接收到，请检查您输入的邮箱是否正确！"),
    MAIL_HAS_SEND_EN(2025, "Verification code has been sent to your email. If you do not receive it, please check email you entered is correct!"),
    HALL_NOT_EXIST(2026, "考场异常"),
    HALL_NOT_START(2027, "考试未开始"),
    HALL_IS_END(2028, "考试已结束"),
    CAN_NOT_START_COMMON_TEST(2029, "已进行考试，请勿重复考试"),
    PAPER_NOT_EXIST(2030, "试卷异常"),
    NO_START_RECORD(2031, "未找到开始考试记录"),
    QUESTION_NOT_EXIST(2032, "试卷题目异常"),
    REPETITION_COMMIT(2033, "无法重复交卷"),
    SAME_PASSWORD(2034,"新密码不能和原密码相同！"),
    SEND_MAIL_LIMIT(1047,"发送邮件过于频繁,请稍后再试"),
    CHECK_EMAIL_CODE(1048,"请检查邮箱或验证码是否输入正确!"),
    CHECK_EMAIL_CODE_EN(1049,"Please check whether the email address or verification code is entered correctly!"),
    CHECK_USER_PASSWORD_CODE(1050,"请检查输入的用户名或密码及验证码是否正确!"),
    CHECK_USER_PASSWORD_CODE_EN(1051,"Please check whether the entered user name or password and verification code are correct!"),
    LOGIN_FAIL_MUTI(1052,"用户名或者密码多次错误！用户已被锁定，请5分钟后再试！"),
    SAVE_ERR(1053,"保存失败"),
    TRAIN_STATUS_ERROR(1054,"请先完成通用培训"),
    PASSWORD_CANNOT_SAMPLE(1055,"新密码不能与旧密码一样！"),
    COMMON_TRAIN_NOT_START(1056,"通用培训未开启"),
    NOT_YET_OPEN(1057,"入口暂未开放！")
    ;

    private int code;

    private String msg;

    ViewResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    ViewResultCodeEnum(EnumCode<Integer> enumCode, String msg) {
        this.code = enumCode.getCode();
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
