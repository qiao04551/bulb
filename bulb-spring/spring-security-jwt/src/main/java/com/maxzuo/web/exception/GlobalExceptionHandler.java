package com.maxzuo.web.exception;

import com.maxzuo.web.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 全局异常处理
 * <p>
 * Created by zfh on 2019/07/19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result> handlerException(Exception e) {
        logger.error("【全局异常处理】errMessage = {}", e.getMessage(), e);
        Result result = new Result(Result.RESULT_FAILURE);
        result.setMsg("系统繁忙！");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
