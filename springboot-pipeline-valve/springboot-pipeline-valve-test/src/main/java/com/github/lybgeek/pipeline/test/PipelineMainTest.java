package com.github.lybgeek.pipeline.test;


import com.github.lybgeek.pipeline.Pipeline;
import com.github.lybgeek.pipeline.support.StandardPipeline;
import com.github.lybgeek.pipeline.test.valve.BasicValve;
import com.github.lybgeek.pipeline.test.valve.FirstValve;
import com.github.lybgeek.pipeline.test.valve.SecondValve;
import com.github.lybgeek.pipeline.test.valve.ThirdValve;
import com.github.lybgeek.valve.context.ValveContext;

public class PipelineMainTest {

    public static void main(String[] args) {
        Pipeline pipeline = new StandardPipeline();
        pipeline.setBasic(new BasicValve());
        pipeline.addValve(new FirstValve());
        pipeline.addValve(new SecondValve());
        pipeline.addValve(new ThirdValve());
        pipeline.process(new ValveContext());
    }
}
