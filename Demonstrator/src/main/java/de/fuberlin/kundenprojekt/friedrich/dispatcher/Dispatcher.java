package de.fuberlin.kundenprojekt.friedrich.dispatcher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author davidbohn
 */
public class Dispatcher {
    private static ExecutorService executorService = Executors.newFixedThreadPool(3);
    public static void dispatch(final Dispatchable dispatchable) {
        executorService.submit(dispatchable::dispatch);
    }
}
