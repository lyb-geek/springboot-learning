package com.github.lybgeek.circuitbreaker.fallback;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface FallbackFactory<T> {


    T create(Throwable ex);

    final class Default<T> implements FallbackFactory<T> {
        final Logger logger;
        final T constant;

        public Default(T constant) {
            this(constant, Logger.getLogger(Default.class.getName()));
        }

        public Default(T constant, Logger logger) {
            this.constant = constant;
            this.logger = logger;
        }

        @Override
        public T create(Throwable cause) {
            if (this.logger.isLoggable(Level.FINE)) {
                this.logger.log(Level.FINE, "fallback due to: " + cause.getMessage(), cause);
            }

            return this.constant;
        }

        @Override
        public String toString() {
            return this.constant.toString();
        }
    }
}
