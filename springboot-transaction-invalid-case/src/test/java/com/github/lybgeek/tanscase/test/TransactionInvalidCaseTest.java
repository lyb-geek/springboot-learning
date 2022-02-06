package com.github.lybgeek.tanscase.test;

import com.github.lybgeek.transcase.aopsort.TranInvalidCaseWithAopSort;
import com.github.lybgeek.transcase.callbyself.TranInvalidCaseByCallMethodSelf;
import com.github.lybgeek.transcase.finalandstatic.TranInvalidCaseWithFinalAndStaticMethod;
import com.github.lybgeek.transcase.instantiatedTooEarly.TranInvalidCaseInstantiatedTooEarly;
import com.github.lybgeek.transcase.multThread.TranInvalidCaseWithMultThread;
import com.github.lybgeek.transcase.serviceWithoutInjectSpring.TranInvalidCaseWithoutInjectSpring;
import com.github.lybgeek.transcase.throwCheckException.TranInvalidCaseByThrowCheckException;
import com.github.lybgeek.transcase.trycatch.TranInvalidCaseWithCatchException;
import com.github.lybgeek.transcase.wrongPropagation.TranInvalidCaseByWrongPropagation;
import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import com.github.lybgeek.user.util.UserUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

/**
 * @description: 事务失效场景测试
 *
 **/
@SpringBootTest
@RunWith(SpringRunner.class)
public class TransactionInvalidCaseTest {

    @Autowired
    private UserService userService;

    private User user;

    @Autowired
    private ApplicationContext applicationContext;



    @Before
    public void before(){
       user = UserUtils.getUser();

    }


    /**
     * 场景一：service没有托管给spring.
     * 原因：spring事务生效的前提是，service必须是一个bean对象
     * 解决方案：将service注入spring
     */
    @Test
    public void testServiceWithoutInjectSpring(){
        boolean randomBoolean = new Random().nextBoolean();
        TranInvalidCaseWithoutInjectSpring tranInvalidCaseWithoutInjectSpring;
        if(randomBoolean){
            tranInvalidCaseWithoutInjectSpring = applicationContext.getBean(TranInvalidCaseWithoutInjectSpring.class);
            System.out.println("service已经被spring托管");
        }else{
            tranInvalidCaseWithoutInjectSpring = new TranInvalidCaseWithoutInjectSpring(userService);
            System.out.println("service没被spring托管");
        }

        boolean isSuccess = tranInvalidCaseWithoutInjectSpring.add(user);
        Assert.assertTrue(isSuccess);

    }

    /**
     * 场景二：抛出受检异常
     * 原因：spring默认只会回滚非检查异常
     * 解决方案：配置rollbackFor
     */
    @Test
    public void testThrowCheckException() throws Exception{
        boolean randomBoolean = new Random().nextBoolean();
        boolean isSuccess = false;
        TranInvalidCaseByThrowCheckException tranInvalidCaseByThrowCheckException = applicationContext.getBean(TranInvalidCaseByThrowCheckException.class);
        if(randomBoolean){
            System.out.println("配置@Transactional(rollbackFor = Exception.class)");
            isSuccess = tranInvalidCaseByThrowCheckException.save(user);
        }else{
            System.out.println("配置@Transactional");
            tranInvalidCaseByThrowCheckException.add(user);
        }

        Assert.assertTrue(isSuccess);

    }


    /**
     * 场景三：业务自己捕获了异常
     * 原因：spring事务只有捕捉到了业务抛出去的异常，才能进行后续的处理，如果业务自己捕获了异常，则事务无法感知
     * 解决方案：1、将异常原样抛出；2、设置TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();;
     */
    @Test
    public void testCatchExecption() throws Exception{
        boolean randomBoolean = new Random().nextBoolean();
        boolean isSuccess = false;
        TranInvalidCaseWithCatchException tranInvalidCaseByThrowCheckException = applicationContext.getBean(TranInvalidCaseWithCatchException.class);
        if(randomBoolean){
            randomBoolean = new Random().nextBoolean();
            if(randomBoolean){
                System.out.println("将异常原样抛出");
                tranInvalidCaseByThrowCheckException.save(user);
            }else{
                System.out.println("设置TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();");
                tranInvalidCaseByThrowCheckException.addWithRollBack(user);
            }
        }else{
            System.out.println("业务自己捕获了异常");
            tranInvalidCaseByThrowCheckException.add(user);
        }

        Assert.assertTrue(isSuccess);

    }


    /**
     * 场景四：切面顺序导致
     * 原因：spring事务切面的优先级顺序最低，但如果自定义的切面优先级和他一样，
     * 且自定义的切面没有正确处理异常，则会同业务自己捕获异常的那种场景一样
     * 解决方案：1、将异常原样抛出；2、设置TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();;
     *
     * 示例只演示失效情况
     */
    @Test
    public void testAopSort() throws Exception{
        TranInvalidCaseWithAopSort aopSort = applicationContext.getBean(TranInvalidCaseWithAopSort.class);
        boolean isSuccess = aopSort.save(user);
        Assert.assertTrue(isSuccess);

    }


    /**
     * 场景五：非public方法
     * 原因：spring事务默认生效的方法权限都必须为public
     * 解决方案：1、将方法改为public、2、修改TansactionAttributeSource,将publicMethodsOnly改为false
     * 3、开启 AspectJ 代理模式
     *
     * 因为单元测试和示例不是同一个包，不好测试，因此该场景挪到com.github.lybgeek.transcase.accessperm.TranInvalidCaseWithAccessPermTest进行验证
     *
     */
    @Test
    public void testAccessPerm() {


    }


    /**
     * 场景六：父子容器
     * 原因：子容器扫描范围过大，将未加事务配置的serivce扫描进来
     * 解决方案：1、父子容器个扫个的范围、2、不用父子容器，所有bean都交给同一容器管理
     *
     * 因为springboot启动默认没有父子容器,只有一个容器，因此就略过示例
     *
     */
    @Test
    public void testParentContainer() {


    }


    /**
     * 场景七：方法用final修饰
     * 原因：因为spring事务是用动态代理实现，因此如果方法使用了final修饰，则代理类无法对目标方法进行重写，植入事务功能
     * 解决方案：方法不要用final修饰
     *
     * 示例仅失效情况
     *
     */
    @Test
    public void testFinal() {
        TranInvalidCaseWithFinalAndStaticMethod tranInvalidCaseWithFinalAndStaticMethod = applicationContext.getBean(TranInvalidCaseWithFinalAndStaticMethod.class);
        UserService userService = applicationContext.getBean(UserService.class);
        boolean isSuccess = tranInvalidCaseWithFinalAndStaticMethod.add(user,userService);
        Assert.assertTrue(isSuccess);
    }

    /**
     * 场景八：方法用static修饰
     * 原因：原因和final一样
     * 解决方案：方法不要用static修饰
     *
     * 示例仅失效情况
     *
     */
    @Test
    public void testStatic() {
        TranInvalidCaseWithFinalAndStaticMethod tranInvalidCaseWithFinalAndStaticMethod = applicationContext.getBean(TranInvalidCaseWithFinalAndStaticMethod.class);
        UserService userService = applicationContext.getBean(UserService.class);
        boolean isSuccess = tranInvalidCaseWithFinalAndStaticMethod.save(user,userService);
        Assert.assertTrue(isSuccess);

    }

    /**
     * 场景九：调用本类方法
     * 原因：本类方法不经过代理，无法进行增强
     * 解决方案：1、注入自己来调用。2、使用@EnableAspectJAutoProxy(exposeProxy = true) + AopContext.currentProxy()
     *
     *
     *
     */
    @Test
    public void testCallMethodBySelf() {
        TranInvalidCaseByCallMethodSelf tranInvalidCaseByCallMethodSelf = applicationContext.getBean(TranInvalidCaseByCallMethodSelf.class);
        boolean isSuccess = tranInvalidCaseByCallMethodSelf.save(user);
        Assert.assertTrue(isSuccess);
    }


    /**
     * 场景十：多线程调用
     * 原因：因为spring的事务是通过数据库连接来实现，而数据库连接spring是放在threalocal里面。
     * 同一个事务，只能用同一个数据库连接。而多线程场景下，拿到的数据库连接是不一样的，即是属于不同事务
     *
     *示例仅失效情况
     *
     *
     */
    @Test
    public void testMultThread() throws Exception{
        TranInvalidCaseWithMultThread tranInvalidCaseWithMultThread = applicationContext.getBean(TranInvalidCaseWithMultThread.class);
        boolean isSuccess = tranInvalidCaseWithMultThread.save(user);
        Assert.assertTrue(isSuccess);
    }


    /**
     * 场景十一：错误的传播行为
     * 原因：使用的传播特性不支持事务
     *
     *
     *示例仅失效情况
     *
     *
     */
    @Test
    public void testWrongPropagation() throws Exception{
        TranInvalidCaseByWrongPropagation tranInvalidCaseByWrongPropagation = applicationContext.getBean(TranInvalidCaseByWrongPropagation.class);
        boolean isSuccess = tranInvalidCaseByWrongPropagation.save(user);
        Assert.assertTrue(isSuccess);
    }


    /**
     * 场景十二：使用了不支持事务的存储引擎
     * 原因：使用了不支持事务的存储引擎。比如mysql中的MyISAM
     *
     *
     *这个就不测试了，略过
     *
     *
     */
    @Test
    public void testMyISAM() throws Exception{

    }


    /**
     * 场景十三：数据源没有配置事务管理器
     *
     *因为sprinboot，他默认已经开启事务管理器。org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration。因此示例略过
     *
     */
    @Test
    public void testWithoutPlatformTransactionManager () throws Exception{

    }

    /**
     * 场景十四：被代理的类过早实例化
     *
     *原因：当代理类的实例化早于AbstractAutoProxyCreator后置处理器，就无法被AbstractAutoProxyCreator后置处理器增强
     *
     */
    @Test
    public void testInstantiatedTooEarly () throws Exception{
        TranInvalidCaseInstantiatedTooEarly tranInvalidCaseInstantiatedTooEarly = applicationContext.getBean(TranInvalidCaseInstantiatedTooEarly.class);
        boolean isSuccess = tranInvalidCaseInstantiatedTooEarly.save(user);
        Assert.assertTrue(isSuccess);
    }












}
