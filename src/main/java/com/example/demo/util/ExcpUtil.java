package com.example.demo.util;

public class ExcpUtil {
    //打印异常堆栈信息
    public static String getStackTraceString(Throwable ex){//(Exception ex) {
        StackTraceElement[] traceElements = ex.getStackTrace();

        StringBuilder traceBuilder = new StringBuilder();

        if (traceElements != null && traceElements.length > 0) {
            for (StackTraceElement traceElement : traceElements) {
                traceBuilder.append(traceElement.toString());
                traceBuilder.append("\n");
            }
        }

        return traceBuilder.toString();
    }

    //构造异常堆栈信息
    public static String buildErrorMessage(Exception ex) {

        String result;
        String stackTrace = getStackTraceString(ex);
        String exceptionType = ex.toString();
        String exceptionMessage = ex.getMessage();

        result = String.format("异常报错:\n%s : %s \r\n %s", exceptionType, exceptionMessage, stackTrace);

        return result;
    }

    public static void main(String[] args) {
        try {
            throw new NullPointerException();

        }catch (Exception  e){
            e.printStackTrace();
            System.out.print(buildErrorMessage(e));
        }
    }
}