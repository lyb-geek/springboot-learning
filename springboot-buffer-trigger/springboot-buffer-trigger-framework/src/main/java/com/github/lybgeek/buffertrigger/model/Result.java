package com.github.lybgeek.buffertrigger.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    private String code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data){
        return Result.<T>builder().code("200").msg("success").data(data).build();
    }



    public static <T> Result<T> fail(String code, String msg, T data){
        return Result.<T>builder().code(code).msg(msg).data(data).build();
    }

    public static <T> Result<T> fail(String code, String msg){
        return Result.<T>builder().code(code).msg(msg).build();
    }

    public static <T> Result<T> fail(String msg){
        return Result.<T>builder().code("500").msg(msg).build();
    }

    public static <T> Result<T> fail(){
        return Result.<T>builder().code("500").msg("fail").build();
    }
}
