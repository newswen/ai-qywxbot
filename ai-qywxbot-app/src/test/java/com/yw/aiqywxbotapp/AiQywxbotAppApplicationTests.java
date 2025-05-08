package com.yw.aiqywxbotapp;

import com.yw.aiqywxbotapp.schedulding.UsTwoBotScheduler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
@Disabled("仅用于手动测试，不参与自动化构建")
class AiQywxbotAppApplicationTests {

    @Resource
    private ChatClient chatClient;

    @Resource
    private UsTwoBotScheduler usTwoBotScheduler;

    @Test
    void contextLoads() {
        String prompt = "查询宁波天气预报";
        log.info(chatClient.prompt(prompt).call().content());
    }


    @Test
    public void test_tool() {
        String userInput = "有哪些工具可以使用";

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test() {
        String userInput = "获取电脑配置";
//        userInput = "在 /Users/fuzhengwei/Desktop 文件夹下，创建 电脑.txt";
        userInput = "在当前电脑下的 D:\\mcp\\test 文件夹下，创建 电脑.txt 把”123456“写入 电脑.txt";

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_send_message() {
        usTwoBotScheduler.sendTestMessage();
    }


}
