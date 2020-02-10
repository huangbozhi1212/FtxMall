package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
    BRAND_NOT_FOUND(400,"商品查询为空"),
    PRICE_CANNON_BE_NULL(400,"价格不能为空"),
    CATEGORY_NOT_FOND(404,"商品分类没查到"),
    SPEC_GROUP_NOT_FOND(404,"商品规格不存在"),
    GOODS_NOT_FOND(404,"商品规格不存在"),
    SPEC_PARAM_NOT_FOND(404,"商品参数不存在"),
    INSERT_PARAM_ERROR(404,"新增商品参数失败"),
    INSERT_GROUP_ERROR(404,"新增分组失败"),
    BRAND_SAVE_ERROR(500,"新增品牌失败"),
    UPLOAD_FILE_ERROR(400,"文件上传失败"),
    INVALID_FILE_TYPE(400,"文件类型错误"),
    USER_DATA_TYPE_ERROR(400,"请求参数有误"),
    INVALID_CODE_ERROR(400,"验证码错误"),
    INVALID_USERNAME_PASSWORD(400,"账号或密码错误"),


    ;
    private int code;
    private String msg;
}
