package com.github.lybgeek.common.logger.thread.util;

import com.github.lybgeek.common.logger.util.TraceIdUtils;

import java.util.Map;
import java.util.concurrent.Callable;

public class ThreadMdcUtils {


    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            TraceIdUtils.setParentMdcToChildMdc(context);
            try {
                return callable.call();
            } finally {
               TraceIdUtils.remove();
            }
        };
    }


    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            TraceIdUtils.setParentMdcToChildMdc(context);
            try {
                runnable.run();
            } finally {
                TraceIdUtils.remove();
            }
        };
    }



}