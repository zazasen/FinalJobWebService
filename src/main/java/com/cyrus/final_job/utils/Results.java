package com.cyrus.final_job.utils;

import com.cyrus.final_job.entity.base.Result;
import com.cyrus.final_job.entity.base.ResultPage;

import java.util.List;

public abstract class Results {

    public static final Result OK = new Result(200, "ok"); // ok

    // 参数非法
    public static Result createBadRequest(String param) {
        return new Result(400, String.format("无效参数:%s", param));
    }

    public static Result createOk(String msg) {
        return new Result(200, msg);
    }

    public static Result createOk(Object data) {
        return new Result(200, null, data);
    }

    public static Result createOk(String msg, Object data) {
        return new Result(200, msg, data);
    }

    public static Result error(String msg) {
        return new Result(500, msg, null);
    }

    public static Result error(String msg, Object obj) {
        return new Result(500, msg, obj);
    }


    public static ResultPage createOk(Long total, List<?> data) {
        return new ResultPage(200, total, data);
    }

    // 参数非法
    public static Result createInvalidParam(String param) {
        return new Result(500, "抱歉,网络开小差了,请稍后重试");
    }
}
