/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.ai.examples.controller.client;

import java.util.Set;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import reactor.core.publisher.Flux;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 */

@RestController
@RequestMapping("/more-model-chat-client")
public class MoreModelChatClientController {

	private final Set<String> modelList = Set.of(
			"deepseek-r1",
			"deepseek-v3",
			"qwen-plus",
			"qwen-max"
	);

	private final ChatClient chatClient;

	public MoreModelChatClientController(
			@Qualifier("dashscopeChatModel") DashScopeChatModel chatModel
	) {

		// 构建 chatClient
		this.chatClient = ChatClient.builder(chatModel).build();
	}

	@GetMapping
	public Flux<String> stream(
			@RequestParam("prompt") String prompt,
			@RequestHeader(value = "models", required = false) String models
	) {

		if (!modelList.contains(models)) {

			return Flux.just("model not exist");
		}

		return chatClient.prompt(prompt)
				.options(DashScopeChatOptions.builder()
						.withModel(models)
						.build()
				).stream()
				.content();
	}

//	public Flux<String> stream(
//			@RequestParam("prompt") String prompt
//	) {
//
//
//		DashScopeApi dashScopeApi = DashScopeApi.builder().apiKey(System.getenv("${AI_DASHSCOPE_API_KEY}")).build();
//		ChatClient build = ChatClient.builder(
//				DashScopeChatModel.builder().dashScopeApi(dashScopeApi)
//						.build()
//		).build();
//	}

}
