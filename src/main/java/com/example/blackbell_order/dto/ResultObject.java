package com.example.blackbell_order.dto;

import com.example.blackbell_order.constant.ResultType;
import com.example.blackbell_order.exception.BaseException;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultObject implements Serializable {

    public HttpStatus status;

    public String code;

    public String desc;

    public ResultObject(ResultType resultType) {
        this.status = resultType.getStatus();
        this.code = resultType.getCode();
        this.desc = resultType.getDesc();
    }

    public ResultObject(ResultType resultCode, String message) {
        this.code = resultCode.getCode();
        this.desc = message;
    }

    public ResultObject(BaseException e) {
        this.code = e.getCode();
        this.desc = e.getDesc();
    }

    public static ResultObject getSuccess() {
        return new ResultObject(ResultType.SUCCESS);
    }
}
