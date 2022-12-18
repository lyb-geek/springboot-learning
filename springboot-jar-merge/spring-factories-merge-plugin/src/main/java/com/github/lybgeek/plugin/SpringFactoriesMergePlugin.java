package com.github.lybgeek.plugin;


import com.github.lybgeek.plugin.util.SpringFactoriesFileUtils;
import lombok.SneakyThrows;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "springFactoriesMerge", defaultPhase = LifecyclePhase.PACKAGE)
public class SpringFactoriesMergePlugin extends AbstractMojo {

    @Parameter(required = true)
    private String factoriesBaseClassPathDir;

    @Parameter(required = true)
    private String finalJarName;

    @SneakyThrows
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
       SpringFactoriesFileUtils.writeFactoriesFile(factoriesBaseClassPathDir, finalJarName);

    }



}
