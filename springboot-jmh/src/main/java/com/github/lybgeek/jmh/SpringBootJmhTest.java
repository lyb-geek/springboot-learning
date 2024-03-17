package com.github.lybgeek.jmh;

import cn.hutool.core.date.DateUtil;
import com.github.lybgeek.jmh.service.MockBizService;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @description: jmh准测试
 * 相关资料：https://zhuanlan.zhihu.com/p/479312752
 * http://aizuda.com/article/1085905
 * https://cloud.tencent.com/developer/article/1760933
 * https://github.com/openjdk/jmh
 *
 *
 * @BenchmarkMode: 指定测试某个接口的指标，如吞吐量、平均执行时间，一般我都是选择 ALL
 *
 * Mode有：
 *
 * Throughput: 整体吞吐量，例如“1秒内可以执行多少次调用”
 * AverageTime: 调用的平均时间，例如“每次调用平均耗时xxx毫秒”。
 * SampleTime: 随机取样，最后输出取样结果的分布，例如“99%的调用在xxx毫秒以内，99.99%的调用在xxx毫秒以内”（simple）
 * SingleShotTime: 以上模式都是默认一次 iteration 是 1s，唯有 SingleShotTime 是只运行一次。往往同时把 warmup 次数设为0，用于测试冷启动时的性能。（ss）
 *
 *
 * @Measurement: 用于控制压测的次数、时间和批处理数量
 *
 * @Warmup: 预热
 * 由于JVM会使用JIT对热点代码进行编译，因此同一份代码可能由于执行次数的增加而导致执行时间差异太大，因此我们可以让代码先预热几轮，预热时间不算入测量计时。@WarmUp 的使用和 @Measurement 一致。
 *
 * @Fork: 用于指定 fork 出多少个 子进程 来执行同一基准测试方法
 *
 * @Threads: 注解用于指定使用多少个线程来执行基准测试方法，
 *
 * @State: 表明类的所有属性的作用域。只能用于类上
 * Scope.Thread ：默认的State，每个测试线程分配一个实例；
 * Scope.Benchmark ：所有测试线程共享一个实例，用于测试有状态实例在多线程共享下的性能；
 * Scope.Group ：每个线程组共享一个实例；
 *
 * @OutputTimeUnit: 可以指定输出的时间单位
 */
@Measurement(iterations = 2, time = 10)
@Warmup(iterations = 2, time = 10)
@Fork(1)
@Threads(value = 2)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.All)
@OutputTimeUnit(TimeUnit.SECONDS)
public class SpringBootJmhTest {

    private ConfigurableApplicationContext context;
    private MockBizService mockBizService;


    /**
     * @Param 允许使用一份基准测试代码跑多组数据，特别适合测量方法性能和参数取值的关系
     */
    @Param({"100","500","1"})
    public long mockBizQueryTime;

    /**
     *   注意要使用 run模式启动main函数，不要使用debug模式启动。
     *  否则会报错：transport error 202: connect failed: Connection refused ERROR
     * @param args
     * @throws RunnerException
     */

    public static void main(String[] args) throws RunnerException {
        String report = DateUtil.today() + "-jmhReport.json";
        Options opt = new OptionsBuilder()
                .include(SpringBootJmhTest.class.getSimpleName())
                // 参数优先级顺序：类 ＜ 方法 ＜ Options
                // 因此如下配置会覆盖@Warmup配置
                .warmupIterations(1)
                .warmupTime(TimeValue.seconds(5))
                //报告输出.可以将结果上传到 https://jmh.morethan.io 或者/http://deepoove.com/jmh-visual
                // 进行分析
                .result(report)
                //报告格式
                .resultFormat(ResultFormatType.JSON).build();


          new Runner(opt).run();

    }


    /**
     * @Setup 用于基准测试前的初始化动作
     *
     * Level参数表明粒度，粒度从粗到细分别是
     *
     * Level.Trial：Benchmark级别
     * Level.Iteration：执行迭代级别
     * Level.Invocation：每次方法调用级别
     */
    @Setup(Level.Trial)
    public void setUp(){
        context = SpringApplication.run(SpringBootJmhApplication.class);
        mockBizService = context.getBean(MockBizService.class);
    }


    /**
     *
     * @Benchmark 来标记需要基准测试的方法.该方法需要为public
     * @param blackhole 的作用是：防止无用代码被JVM优化导致的基准测试结果不准确
     */
    @Benchmark
    public void testMockBizService(Blackhole blackhole) {
        blackhole.consume(mockBizService.query(mockBizQueryTime));

    }


    /**
     * @TearDown 用于基准测试后执行
     */
    @TearDown
    public void tearDown() {
        context.close();
    }


}
