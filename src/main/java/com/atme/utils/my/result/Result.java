package com.atme.utils.my.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author amao
 * @create 2022-06-16-22:38
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    public static final int SUCCESS = 0;

    private Integer status;

    private String msg;

    private T data;

    @JsonIgnore
    public boolean isSuccess() {
        return getStatus() == SUCCESS;
    }

}
