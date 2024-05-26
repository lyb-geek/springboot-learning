package com.github.lybgeek.pipeline.test;


import com.github.lybgeek.pipeline.Pipeline;
import com.github.lybgeek.valve.context.ValveContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PipelineBootApplication implements ApplicationRunner {

    @Autowired
    private Pipeline pipeline;

    public static void main(String[] args) {
        SpringApplication.run(PipelineBootApplication.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        pipeline.process(new ValveContext());

    }
}
