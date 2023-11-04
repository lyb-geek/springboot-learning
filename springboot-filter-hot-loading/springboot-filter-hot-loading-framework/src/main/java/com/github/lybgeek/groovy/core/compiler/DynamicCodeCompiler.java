package com.github.lybgeek.groovy.core.compiler;

import java.io.File;

public interface DynamicCodeCompiler {
    Class<?> compile(String sCode, String sName) throws Exception;

    Class<?> compile(File file) throws Exception;
}