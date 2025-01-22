package com.mengyunzhi.eduPlanner.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 返回体的类型
 */
public class Response<T> {
    @Getter
    @Setter
    private boolean status;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private T data;

    public Response(boolean status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

}
