/*
 * Copyright 2012 Thomas Hoffmann
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.j4velin.systemappmover;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class Logger {

    private static PrintWriter fw;
    private static Date date = new Date();
    private final static String APP = "SystemAppMover";

    static {
        final File appLogFile = new File(Environment.getExternalStorageDirectory(), APP + ".log");
        if (fw == null) {
            try {
                fw = new PrintWriter(new FileWriter(appLogFile, true));
                Log.i(APP, "sending app logs to " + appLogFile.getAbsolutePath());
            } catch (IOException e) {
                Log.e(APP, "Could not create file writer", e);
            }
        }
    }

    public static void log(Throwable ex) {
        ex.printStackTrace(fw);
        ex.printStackTrace(System.err);
        log(ex.getMessage());
        for (StackTraceElement ste : ex.getStackTrace()) {
            log(ste.toString());
        }
    }

    //@SuppressWarnings("deprecation")
    public static void log(String msg) {
        if (!BuildConfig.DEBUG)
            return;
        if (msg == null) {
            msg = "null";
        }
        android.util.Log.d(APP, msg);
        try {
            date.setTime(System.currentTimeMillis());
            fw.write(date.toLocaleString() + " - " + msg + "\n");
        } finally {
            fw.flush();
        }

    }

    protected void finalize() throws Throwable {
        try {
            if (fw != null)
                fw.close();
        } finally {
            super.finalize();
        }
    }

}
