package com.yw.aiqywxbotapp.config;

import io.micrometer.observation.ObservationRegistry;
import io.modelcontextprotocol.client.McpSyncClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.observation.ChatClientObservationConvention;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class OpenAIConfig {

//    /**
//     * 从配置文件中读取到mcp service
//     * @param mcpClients
//     * @return
//     */
//    @Bean("syncMcpToolCallbackProvider")
//    public SyncMcpToolCallbackProvider syncMcpToolCallbackProvider(List<McpSyncClient> mcpClients) {
////        mcpClients.remove(0);
//
//        // 用于记录 name 和其对应的 index
//        Map<String, Integer> nameToIndexMap = new HashMap<>();
//        // 用于记录重复的 index
//        Set<Integer> duplicateIndices = new HashSet<>();
//
//        System.out.println("mcpClients:" + mcpClients.size());
//
//        // 遍历 mcpClients 列表
//        for (int i = 0; i < mcpClients.size(); i++) {
//            String name = mcpClients.get(i).getServerInfo().name();
//            if (nameToIndexMap.containsKey(name)) {
//                // 如果 name 已经存在，记录当前 index 为重复
//                duplicateIndices.add(i);
//            } else {
//                // 否则，记录 name 和 index
//                nameToIndexMap.put(name, i);
//            }
//        }
//
//        // 删除重复的元素，从后往前删除以避免影响索引
//        List<Integer> sortedIndices = new ArrayList<>(duplicateIndices);
//        sortedIndices.sort(Collections.reverseOrder());
//        for (int index : sortedIndices) {
//            mcpClients.remove(index);
//        }
//
//        return new SyncMcpToolCallbackProvider(mcpClients);
//    }
//
//    @Bean
//    public ChatClient chatClient(OpenAiChatModel openAiChatModel, @Qualifier("syncMcpToolCallbackProvider") ToolCallbackProvider syncMcpToolCallbackProvider, ChatMemory chatMemory) {
//        DefaultChatClientBuilder defaultChatClientBuilder = new DefaultChatClientBuilder(openAiChatModel, ObservationRegistry.NOOP, (ChatClientObservationConvention) null);
//        return defaultChatClientBuilder
//                .defaultTools(syncMcpToolCallbackProvider)
////       chat:
////        options:
////          model: gpt-4.1 yml配置方式可以注释掉这里的代码。
////                .defaultOptions(OpenAiChatOptions.builder()
////                        .model("gpt-4.1")
////                        .build())
//                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory))
//                .build();
//    }
//
//    /**
//     * InMemoryChatMemory 是一个内存中的存储实现，适用于临时保存聊天记录的场景。
//     * 如果你不希望聊天记录持久化，或者只需要在应用程序运行期间保留对话内容，它是一个很好的选择
//     * @return
//     */
//    @Bean
//    public ChatMemory chatMemory() {
//        return new InMemoryChatMemory();
//    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
}
