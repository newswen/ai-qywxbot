package com.yw.aiqywxbotapp.schedulding;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RefreshScope
public class UsTwoBotScheduler {

    @Value("${ustwo.robot.webhook-key}")
    private String usTwoHookKey;

    @Value("${ustwo.robot.enable}")
    private boolean usTwoEnable;

    @Value("${ustwo.address}")
    private List<String> addresses;

    @Value("${ustwo.protagonist}")
    private List<String> protagonists;

    @Resource
    private ChatClient chatClient;

    private final String dailyReminderPrompt = "\uD83D\uDD0D 1. 查询天气：\n" +
            "- 获取 河南南阳唐河 和 浙江宁波江北 的最新天气信息。\n" +
            "\uD83C\uDF08 2. 格式化输出：\n" +
            "\uD83C\uDF08唐河县天气预报\uD83C\uDF08\n" +
            " \uD83D\uDDD3今日【{日期}】\n" +
            " \uD83C\uDF24天气：{唐河天气}\n" +
            " \uD83C\uDF21温度：{唐河温度}\n" +
            " \uD83C\uDF00风力：{唐河风力}\n" +
            "---------------------------------------\n" +
            "\uD83C\uDF08宁波江北区天气预报\uD83C\uDF08\n" +
            " \uD83D\uDDD3今日【{日期}】\n" +
            " \uD83C\uDF24天气：{江北天气}\n" +
            " \uD83C\uDF21温度：{江北温度}\n" +
            " \uD83C\uDF00风力：{江北风力}\n" +
            "✨✨✨【小精灵说】✨✨✨\n" +
            "查询每日搞笑段子进行填充\n" +
            "\n" +
            "---\n" +
            "✨ 3. 示例输出：\n" +
            "\uD83C\uDF08唐河县天气预报\uD83C\uDF08\n" +
            " \uD83D\uDDD3今日【2025-05-07】\n" +
            " \uD83C\uDF24天气：小雨 / 阴\n" +
            " \uD83C\uDF21温度：16°C ~ 31°C\n" +
            " \uD83C\uDF00风力：南风1-3级\n" +
            "----------------------------\n" +
            "\uD83C\uDF08宁波江北区天气预报\uD83C\uDF08\n" +
            " \uD83D\uDDD3今日【2025-05-07】\n" +
            " \uD83C\uDF24天气：多云 / 多云\n" +
            " \uD83C\uDF21温度：18°C ~ 25°C\n" +
            " \uD83C\uDF00风力：东南风1-3级\n" +
            "✨✨✨【小精灵说】✨✨✨\n" +
            "   初中和同学在网吧玩劲舞团，我爸网吧抓住我了，给了我一耳光，我同学没见过我爸，以为外人欺负我，耳机一摔就朝我爸扑了上去。别提了现在我老公怕死我爸了！生怕我爸想起这件事\n" +
            "\n" +
            "---\n" +
            "\uD83C\uDF1F 5. 特殊注意：\n" +
            "- 输出必须整齐、可爱，符合聊天窗口的美观格式。小精灵说下面的段落文本首行需要2个空格，表示段首\n" +
            "- 严格按照模板输出\n" +
            "\uD83C\uDF89 6. 新增幽默元素：\n" +
            "- 查询得出的每日搞笑段子应当结合当前流行的网络用语、表情符号和幽默的时事背景，让用户在获取天气预报的同时感到放松。在保证模板输出的同时，这个也很重要。";

    @Scheduled(cron = "0 0 7 * * ?")
    public void sendTestMessage() {
        if (usTwoEnable) {
            sendTextMessage(usTwoHookKey);
        }
    }

    public void sendTextMessage(String webhookKey) {
        String webhookUrl = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=" + webhookKey;
        int i = 0;
        String responseText = chatClient.prompt(dailyReminderPrompt).call().content();
        // 构造符合企业微信要求的 JSON 消息体
        String messageJson = String.format(
                "{ \"msgtype\": \"text\", \"text\": { \"content\": \"%s\" } }",
                responseText.replace("\"", "\\\"") // 防止特殊字符导致 JSON 格式错误
        );
        log.info("最终结果：{}", messageJson);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(webhookUrl);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(messageJson, ContentType.APPLICATION_JSON));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                log.info("企微发送结果：{}", responseString);
            }
        } catch (Exception e) {
            log.error("企微发送失败：{}", e.getMessage(), e);
        }
    }
}