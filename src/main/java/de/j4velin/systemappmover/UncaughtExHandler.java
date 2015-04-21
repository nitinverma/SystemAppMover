package de.j4velin.systemappmover;

import android.util.Log;

/**
 * Created by nitin.verma on 02/04/15.
 */
public class UncaughtExHandler implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler replaced;
    private static final String TAG = "UncaughtExHandler";

    public UncaughtExHandler(final Thread.UncaughtExceptionHandler replaced) {
        this.replaced = replaced;
    }

    @Override
    public void uncaughtException(final Thread thread, final Throwable ex) {
        Log.e(TAG, thread.getId() + "||" + thread.getName() + "||" + thread.getPriority(), ex);
        // restart services once we understand these failures.

        //you can call replaced for default behaviour.
        //replaced.uncaughtException(thread,ex);
    }
}
