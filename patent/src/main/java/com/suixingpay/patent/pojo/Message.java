package com.suixingpay.patent.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class Message {
    private Object object; //返回数据，没有设置为null
    private Integer status; //状态码:200 400 500等
    private String message; //提示信息
    private boolean flag; //执行结果是否通过，通过 true 不通过 false

    /**
     * 设置返回信息
     * @param object 数据
     * @param status 状态码
     * @param message 提示信息
     * @param flag 执行结果
     */
    public void setMessage(Object object, Integer status, String message, boolean flag) {
        this.object = object;
        this.status = status;
        this.message = message;
        this.flag = flag;
    }
}
