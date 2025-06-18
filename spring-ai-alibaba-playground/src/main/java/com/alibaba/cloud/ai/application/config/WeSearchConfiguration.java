package com.alibaba.cloud.ai.application.config;

//import com.alibaba.cloud.ai.application.rag.postretrieval.DashScopeDocumentRanker;
import com.alibaba.cloud.ai.application.rag.preretrieval.query.expansion.MultiQueryExpander;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.model.RerankModel;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.preretrieval.query.expansion.QueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.QueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

@Configuration
public class WeSearchConfiguration {
//
//	@Bean
//	public DashScopeDocumentRanker dashScopeDocumentRanker(
//			RerankModel rerankModel
//	) {
//		return new DashScopeDocumentRanker(rerankModel);
//	}

	@Bean
	public QueryTransformer queryTransformer(
			@Qualifier("dashscopeChatModel") ChatModel chatModel,
			@Qualifier("transformerPromptTemplate") PromptTemplate transformerPromptTemplate
	) {

		ChatClient chatClient = ChatClient.builder(chatModel)
				.defaultOptions(
						DashScopeChatOptions.builder()
								.withModel("qwen-plus")
								.build()
				).build();

		return RewriteQueryTransformer.builder()
				.chatClientBuilder(chatClient.mutate())
				.promptTemplate(transformerPromptTemplate)
				.targetSearchSystem("Web Search")
				.build();
	}

	@Bean
	public QueryExpander queryExpander(
			@Qualifier("dashscopeChatModel") ChatModel chatModel
	) {

		ChatClient chatClient = ChatClient.builder(chatModel)
				.defaultOptions(
						DashScopeChatOptions.builder()
								.withModel("qwen-plus")
								.build()
				).build();

		return MultiQueryExpander.builder()
				.chatClientBuilder(chatClient.mutate())
				.numberOfQueries(2)
				.build();
	}

}
