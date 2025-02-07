package com.mengyunzhi.eduPlanner.dto;

import lombok.Data;
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

    public Response() {}

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setStatus(true);
        response.setData(data);
        return response;
    }

    public static <T> Response<T> fail(String message) {
        Response<T> response = new Response<>();
        response.setStatus(false);
        response.setMessage(message);
        return response;
    }

}
