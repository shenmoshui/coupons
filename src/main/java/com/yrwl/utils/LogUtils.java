package com.yrwl.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author shentao
 * @date 2020-01-16
 */
@UtilityClass
@Slf4j
public class LogUtils {

    public String getStackTraceInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            //将出错的栈信息输出到printWriter中
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();

            return sw.toString();
        } catch (Exception ex) {
            log.error("日志转换异常:{}", ex.getMessage());
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    log.error("日志输入流关闭异常:{}", e1.getMessage());
                }
            }
            if (pw != null) {
                pw.close();
            }
        }

        return e.getMessage();
    }
}
