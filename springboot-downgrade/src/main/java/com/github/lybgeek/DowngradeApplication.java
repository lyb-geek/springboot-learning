package com.github.lybgeek;

import com.github.lybgeek.downgrade.util.ResouceDowngradeUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DowngradeApplication implements ApplicationRunner {
    public static void main( String[] args )
    {

        SpringApplication.run(DowngradeApplication.class,args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ResouceDowngradeUtil.INSTANCE.init();
    }
}
