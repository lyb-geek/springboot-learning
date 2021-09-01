package com.github.lybgeek.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "echo",defaultPhase = LifecyclePhase.PACKAGE)
public class EchoMojo extends AbstractMojo {


    @Parameter
    private String applicationName;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        getLog().info("echo-->" + applicationName);
    }
}
