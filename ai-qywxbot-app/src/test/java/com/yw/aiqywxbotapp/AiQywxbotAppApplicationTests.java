package com.yw.aiqywxbotapp;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class AiQywxbotAppApplicationTests {

    @Resource
    private ChatClient chatClient;

    @Test
    void contextLoads() {
        String prompt = "天气预报";
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


}
