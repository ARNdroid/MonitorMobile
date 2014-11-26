package br.com.arndroid.monitormobile.app;

import android.app.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorMobileApp extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MonitorMobileApp.class);

    @Override
    public void onCreate() {
        super.onCreate();
        if(!(Thread.getDefaultUncaughtExceptionHandler() instanceof LogExceptionHandler)) {
            /*
                Attention:
                Some exceptions will not be caught by this handler:
                - All exceptions before this statement (like super.onCreate() above);
                - Exceptions outside the main thread.
                Therefore, a weird and not logged exception may be in console but NOT with '==>MOB'
                string prefix. Be careful!
             */
            LOG.trace("About to set new default uncaught exception handler.");
            Thread.setDefaultUncaughtExceptionHandler(new LogExceptionHandler());
            LOG.trace("New default uncaught exception handler set.");
        }
    }
}
