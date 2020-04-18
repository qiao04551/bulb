package _20190226;

import sun.security.action.GetPropertyAction;

import java.security.AccessController;
import java.time.LocalDateTime;
import java.util.Formatter;

/**
 * 日志记录器实现类
 * Created by zfh on 2019/02/26
 */
public class RecordLogger implements Logger {

    private static final String LOGGER_FORMAT = "%s [%s] %-5s [%s] - %s";

    private String              lineSeparator;

    /** 全限定类名 */
    private String              name;

    public RecordLogger(String name) {
        this.name = name;
        this.lineSeparator = AccessController.doPrivileged(new GetPropertyAction("line.separator"));
    }

    @Override
    public void debug(String msg) {
        String content = format("DEBUG", msg, null);
        recordLogToFile(content);
    }

    @Override
    public void debug(String msg, Throwable t) {
        String content = format("DEBUG", msg, t);
        recordLogToFile(content);
    }

    @Override
    public void debug(String format, Object... arguments) {
        Throwable t = null;
        for (Object item : arguments) {
            if (item instanceof Throwable) {
                t = (Throwable) item;
                continue;
            }
            format = format.replaceFirst("\\{\\s*}", String.valueOf(item));
        }
        String content = format("DEBUG", format, t);
        recordLogToFile(content);
    }

    @Override
    public void info(String msg) {
        String content = format("INFO", msg, null);
        recordLogToFile(content);
    }

    @Override
    public void info(String msg, Throwable t) {
        String content = format("INFO", msg, t);
        recordLogToFile(content);
    }

    @Override
    public void info(String format, Object... arguments) {
        Throwable t = null;
        for (Object item : arguments) {
            if (item instanceof Throwable) {
                t = (Throwable) item;
                continue;
            }
            format = format.replaceFirst("\\{\\s*}", String.valueOf(item));
        }
        String content = format("INFO", format, t);
        recordLogToFile(content);
    }

    @Override
    public void warn(String msg) {
        String content = format("WARN", msg, null);
        recordLogToFile(content);
    }

    @Override
    public void warn(String msg, Throwable t) {
        String content = format("WARN", msg, t);
        recordLogToFile(content);
    }

    @Override
    public void warn(String format, Object... arguments) {
        Throwable t = null;
        for (Object item : arguments) {
            if (item instanceof Throwable) {
                t = (Throwable) item;
                continue;
            }
            format = format.replaceFirst("\\{\\s*}", String.valueOf(item));
        }
        String content = format("WARN", format, t);
        recordLogToFile(content);
    }

    @Override
    public void error(String msg) {
        String content = format("ERROR", msg, null);
        recordLogToFile(content);
    }

    @Override
    public void error(String msg, Throwable t) {
        String content = format("ERROR", msg, t);
        recordLogToFile(content);
    }

    @Override
    public void error(String format, Object... arguments) {
        Throwable t = null;
        for (Object item : arguments) {
            if (item instanceof Throwable) {
                t = (Throwable) item;
                continue;
            }
            format = format.replaceFirst("\\{\\s*}", String.valueOf(item));
        }
        String content = format("ERROR", format, t);
        recordLogToFile(content);
    }

    /** 日志输出格式化 */
    private String format(String level, String msg, Throwable t) {
        if (t != null) {
            String stackTrace = getStackTrace(t);
            msg = msg + lineSeparator + stackTrace;
        } else {
            msg = msg + lineSeparator;
        }
        LocalDateTime timestamp = LocalDateTime.now();
        String threadName = Thread.currentThread().getName();
        Formatter formatter = new Formatter();
        return formatter.format(LOGGER_FORMAT, timestamp, threadName, level, name, msg).toString();
    }

    /** 获取异常堆栈信息 */
    private String getStackTrace(Throwable t) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(t.getClass().getName()).append(": ").append(t.getMessage()).append(lineSeparator);
        StackTraceElement[] stackTrace = t.getStackTrace();
        for (StackTraceElement item : stackTrace) {
            Formatter formatter = new Formatter();
            String format = "\tat %s.%s(%s:%d)";
            String stackMsg = formatter.format(format, item.getClassName(), item.getMethodName(), item.getFileName(),
                item.getLineNumber()).toString();
            stringBuilder.append(stackMsg).append(lineSeparator);
        }
        return stringBuilder.toString();
    }

    private void recordLogToFile(String content) {
        // 输出到控制台
        System.out.print(content);
    }
}
