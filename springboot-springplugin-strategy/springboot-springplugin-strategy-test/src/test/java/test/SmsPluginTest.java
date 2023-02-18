package test;


import com.github.lybgeek.sms.enums.SmsType;
import com.github.lybgeek.sms.model.SmsRequest;
import com.github.lybgeek.sms.model.SmsResponse;
import com.github.lybgeek.sms.service.SmsService;
import com.github.lybgeek.sms.test.SmsPluginApplication;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SmsPluginApplication.class)
@RunWith(SpringRunner.class)
public class SmsPluginTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void testAliyunSms(){
        SmsRequest smsRequest = SmsRequest.builder()
                .message("模拟使用阿里云短信发送")
                .to("136000000001")
                .smsType(SmsType.ALIYUN)
                .build();

        SmsResponse smsResponse = smsService.sendSms(smsRequest);
        Assert.assertTrue(smsResponse.isSuccess());
        System.out.println(smsResponse);

    }

    @Test
    public void testTencentSms(){
        SmsRequest smsRequest = SmsRequest.builder()
                .message("模拟使用腾讯云短信发送")
                .to("136000000001")
                .smsType(SmsType.TENCENT)
                .build();

        SmsResponse smsResponse = smsService.sendSms(smsRequest);
        Assert.assertTrue(smsResponse.isSuccess());
        System.out.println(smsResponse);

    }
}
