package com.github.lybgeek;

import com.github.lybgeek.model.OperateLog;
import com.github.lybgeek.service.OperateLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DbTemplateAppliactionTest
{
    @Autowired
    private OperateLogService operateLogService;

    @Test
    public void listLogs() {
        List<OperateLog> list = operateLogService.listOperateLogs();
        Assert.assertNotNull(list);

        list.forEach(log-> System.out.println(log));

    }
}
