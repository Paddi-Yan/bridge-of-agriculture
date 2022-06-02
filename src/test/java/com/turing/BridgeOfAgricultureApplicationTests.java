package com.turing;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teautil.models.RuntimeOptions;
import com.turing.common.OrderStatus;
import com.turing.entity.Type;
import com.turing.service.TypeService;
import com.turing.utils.SendSmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BridgeOfAgricultureApplicationTests {

    @Value("${alibaba-send-message.access-key-id}")
    private String accessKeyId;

    @Value("${alibaba-send-message.access-key-secret}")
    private String accessKeySecret;


    @Test
    public void contextLoads() throws Exception {
        Client client = null;
        try {
            client = SendSmsUtils.createClient(accessKeyId, accessKeySecret);
        } catch(Exception e) {
            e.printStackTrace();
        }
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers("13530175677")
                .setTemplateParam("{\"code\":\"8888\"}");
        RuntimeOptions runtime = new RuntimeOptions();
        // 复制代码运行请自行打印 API 的返回值
        SendSmsResponse response = client.sendSmsWithOptions(sendSmsRequest, runtime);
        SendSmsResponseBody body = response.getBody();
        System.out.println(response);
        System.out.println("response.getBody().getCode() = " + response.getBody().getCode());
        System.out.println("response.getBody().getMessage() = " + response.getBody().getMessage());
        System.out.println(response.getHeaders());
    }

    @Resource
    private TypeService typeService;

    @Test
    public void insertTypeTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("拖拉机");
        list.add("耕地机");
        list.add("锄地机");
        list.add("旋耕机");
        list.add("打捆机");
        list.add("收割机");
        list.add("播种机");
        list.add("青贮机");
        for(String name : list) {
            Type type = new Type();
            type.setType(name);
            typeService.save(type);
        }
    }

    @Test
    public void enumTest() {
        System.out.println(OrderStatus.SUBMIT_ORDER);
    }

}
