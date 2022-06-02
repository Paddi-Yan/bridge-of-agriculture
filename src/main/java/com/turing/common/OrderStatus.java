package com.turing.common;

/**
 * @Author: 又蠢又笨的懒羊羊程序猿
 * @CreateTime: 2022年05月20日 16:29:22
 */
public enum OrderStatus {
    SUBMIT_ORDER(101, "提交订单"),
    COMPLETE_PAYMENT(102, "完成支付"),
    IN_TRANSIT(103, "运输中"),
    RECEIVED(104, "已收货,租赁开始"),
    RETURNED(105, "已归还,订单结束");

    private Integer code;
    private String status;

    OrderStatus(Integer code, String message) {
        this.code = code;
        this.status = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
