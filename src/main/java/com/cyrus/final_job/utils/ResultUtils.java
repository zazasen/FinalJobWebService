package com.cyrus.final_job.utils;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.enums.ResultEnum;

public class ResultUtils {

    public static Result success(Object object) {
        Result<Object> result = new Result<>();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success() {
        return success(null);
    }

    public static Result error(Integer code, String msg, Object object) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }

    public static Result error(Integer code, String msg) {
        return error(code, msg, null);
    }
}
