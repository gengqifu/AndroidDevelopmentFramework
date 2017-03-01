package com.example.examplelib.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * 修改自 java.util.logging.SimpleFormatter
 */
class SimpleFormatter extends Formatter {
    /**
     * date object.
     */
    private final Date dat = new Date();
    /**
     * message format
     */
    private MessageFormat formatter;
    /**
     * MessageFormat  args
     */
    private final Object[] args = new Object[1];

    /**
     * Format the given LogRecord.
     *
     * @param record the log record to be formatted.
     * @return a formatted log record
     */
    public synchronized String format(LogRecord record) {

        // 重新设置 LogRecord className 和 MethodName等信息
        String sourceClassName = null;
        String sourceMethodName = null;
        int lineNumber = 0;
        boolean sawLogger = false;
        for (StackTraceElement element : new Throwable().getStackTrace()) {
            String current = element.getClassName();
            if (current.startsWith(Log.class.getName())) {
                sawLogger = true;
            } else if (sawLogger) {
                sourceClassName = element.getClassName();
                sourceMethodName = element.getMethodName();
                lineNumber = element.getLineNumber();
                break;
            }
        }
        record.setSourceClassName(sourceClassName);
        record.setSourceMethodName(sourceMethodName);

        // 排版。
        StringBuilder sb = new StringBuilder();
        // Minimize memory allocations here.
        dat.setTime(record.getMillis());
        args[0] = dat;
        StringBuffer text = new StringBuffer();
        if (formatter == null) {
            /* date format */
            String format = "{0,date} {0,time}";
            formatter = new MessageFormat(format);
        }
        formatter.format(args, text, null);
        sb.append(text);
        sb.append(".").append(record.getMillis() % 1000); //毫秒 // SUPPRESS CHECKSTYLE
        sb.append(" ");

        // 调用函数类名，方法名，行号
        if (record.getSourceClassName() != null) {
            sb.append(record.getSourceClassName());
        } else {
            sb.append(record.getLoggerName());
        }
        if (record.getSourceMethodName() != null) {
            sb.append(" ");
            sb.append(record.getSourceMethodName());
        }

        sb.append(" ");
        sb.append(lineNumber); //行号

        sb.append(" "); // sb.append(lineSeparator);
        String message = formatMessage(record);
        sb.append(record.getLevel().getLocalizedName());
        sb.append(": ");
        sb.append(message);
        sb.append("\n");
        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                sb.append(sw.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sb.toString();
    }
}
