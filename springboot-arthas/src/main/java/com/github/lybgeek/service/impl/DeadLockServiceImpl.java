package com.github.lybgeek.service.impl;


import com.github.lybgeek.service.DeadLockService;
import org.springframework.stereotype.Service;

@Service
public class DeadLockServiceImpl implements DeadLockService {

    private Object lockOne = new Object();
    private Object lockTwo = new Object();

    @Override
    public String deadLock() {

        Thread threadOne = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " try to get lockOne");
            synchronized (lockOne){
                try{
                    System.out.println(Thread.currentThread().getName() + " get lockOne ");
                    Thread.sleep(500);
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" try to get lockTow");
                synchronized (lockTwo){
                    System.out.println(Thread.currentThread().getName()+" get lockTwo");
                }
            }
        },"threadOne");

        Thread threadTwo = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " try to get lockTwo");
            synchronized (lockTwo){
                try{
                    System.out.println(Thread.currentThread().getName() + " get lockTwo ");
                    Thread.sleep(500);
                }catch(Exception e){
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " try to get lockOne");
                synchronized (lockOne){
                    System.out.println(Thread.currentThread().getName() + " get lockOne");
                }
            }
        },"threadTwo");

        threadOne.start();
        threadTwo.start();

        return "test deadLock";
    }
}
