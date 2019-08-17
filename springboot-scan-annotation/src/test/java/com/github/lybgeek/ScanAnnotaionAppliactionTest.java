package com.github.lybgeek;

import com.github.lybgeek.model.Author;
import com.github.lybgeek.model.OperateLog;
import com.github.lybgeek.service.AuthorService;
import com.github.lybgeek.service.OperateLogService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScanAnnotaionAppliactionTest
{
    @Autowired
    private AuthorService authorService;

    @Autowired
    private OperateLogService operateLogService;


    private void printLogs(){
        List<OperateLog> operateLogs = operateLogService.listOperateLogs();
        Assert.assertNotNull(operateLogs);
        operateLogs.forEach(operateLog-> System.out.println(operateLog));
    }


    @Test
    public void testAdd(){
        Author author = Author.builder().job("test1").name("test").sex("男").build();
        authorService.saveAuthor(author);
        printLogs();
    }

    @Test
    public void testList(){
        List<Author> authors = authorService.listAuthors();
        Assert.assertNotNull(authors);
        authors.forEach(author -> System.out.println(author));
        printLogs();
    }

    @Test
    public void testUpdate(){
        Author author = authorService.getAuthordById(1L);
        author.setJob("programer123");
        authorService.saveAuthor(author);
        printLogs();
    }

    @Test
    public void testDelete(){
        authorService.deleteAuthorById(4L);
        printLogs();
    }
}
