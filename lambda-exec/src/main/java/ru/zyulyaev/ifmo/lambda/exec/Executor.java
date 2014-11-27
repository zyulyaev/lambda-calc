package ru.zyulyaev.ifmo.lambda.exec;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.zyulyaev.ifmo.lambda.exec.task.TaskExecutor;

import java.io.*;

/**
 * @author zyulyaev
 */
public class Executor {
    public static void main(String[] args) throws IOException {
        String taskName = "task" + args[0];
        ApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        try {
            TaskExecutor executor = context.getBean(taskName, TaskExecutor.class);
            try (InputStream in = new FileInputStream(taskName + ".in");
                 OutputStream out = new FileOutputStream(taskName + ".out")) {
                executor.execute(in, out);
            }
        } catch (NoSuchBeanDefinitionException e) {
            System.out.println("Task named " + args[0] + " not implemented");
        }
    }
}
