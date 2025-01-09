package com.autoparams.common.base.exception;



import com.autoparams.common.base.constant.HttpStatus;
import com.autoparams.common.base.core.domain.AjaxResult;
import com.autoparams.common.base.core.domain.model.LoginUser;
import com.autoparams.common.base.utils.MessageUtils;
import com.autoparams.common.base.utils.SecurityUtils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

//    @Autowired
//    private BusinessErrorLogService businessErrorLogService;

    @ExceptionHandler({Exception.class})
    public AjaxResult handleAnyException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.ERROR, MessageUtils.message("exception.error"), "error");
    }

    @ExceptionHandler({RuntimeException.class})
    public AjaxResult handleRunTimeException(HttpServletRequest request, Exception exception) {
        LoginUser loginUser = null;
        try {
            loginUser = SecurityUtils.getLoginUser();
        } catch (Exception e) {

        }
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(Objects.isNull(loginUser) ? "UnLoginUser" : SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage().replace("$$:", ""))
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage().contains("$$:") ?
                exception.getLocalizedMessage().replace("$$:", "") :
                MessageUtils.message("exception.error"), "error");
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    public AjaxResult handleMethodArgumentNotValidException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
        MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;
        String msg = methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
                .map(item -> item.getDefaultMessage() + ";\n")
                .collect(Collectors.joining());
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(msg)
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);

        return AjaxResult.result(HttpStatus.WARN, msg, "error");
    }

    @ExceptionHandler({SQLException.class})
    public AjaxResult handleSqlException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, MessageUtils.message("exception.sql"), "error");
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public AjaxResult handleDataIntegrityViolationException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, MessageUtils.message("exception.valid"), "error");
    }

    @ExceptionHandler({NoSuchElementException.class})
    public AjaxResult handleNoSuchElementException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, MessageUtils.message("exception.error"), "error");
    }

    @ExceptionHandler({UncategorizedSQLException.class})
    public AjaxResult handleUncategorizedSQLException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, MessageUtils.message("exception.sql"), "error");
    }

    @ExceptionHandler({IllegalStateException.class})
    public AjaxResult handleIllegalStateException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, MessageUtils.message("exception.illegal"), "error");
    }


    @ExceptionHandler({NullPointerException.class})
    public AjaxResult handleNullException(HttpServletRequest request, Exception exception) {
        exception.printStackTrace();
//        BusinessErrorLog errorLog = BusinessErrorLog.builder()
//                .optBy(SecurityUtils.getLoginUser().getUser().getNickName())
//                .optTime(new Date())
//                .apiName(request.getRequestURI())
//                .exceptClass(exception.getClass().getName())
//                .exceptMessage(exception.getLocalizedMessage())
//                .exceptStackTrace(Arrays.stream(exception.getStackTrace()).limit(5)
//                        .map(item -> String.format("%s(%d)", item.getFileName(), item.getLineNumber()))
//                        .toArray(String[]::new))
//                .build();
//        businessErrorLogService.save(errorLog);
        return AjaxResult.result(HttpStatus.BAD_REQUEST, MessageUtils.message("exception.null"), "error");
    }
}
