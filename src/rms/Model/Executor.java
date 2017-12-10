/*
 * Executor
 */
package rms.Model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Executor {
    public static ExecutorService staffExecutor = Executors.newFixedThreadPool(3);
    public static ExecutorService tablesExecutor = Executors.newCachedThreadPool();
    public static ExecutorService customerProducerExecutor = Executors.newSingleThreadExecutor();
    public static ExecutorService clockExecutor = Executors.newSingleThreadExecutor();
}
