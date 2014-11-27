package ru.zyulyaev.ifmo.lambda.exec.task;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author zyulyaev
 */
public interface TaskExecutor {
    public void execute(InputStream in, OutputStream out);
}
