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

package com.alibaba.cloud.ai.application.config.mcp;

import java.lang.reflect.Field;

import io.modelcontextprotocol.client.McpSyncClient;

import org.springframework.ai.mcp.SyncMcpToolCallback;

/**
 * @author yuluo
 * @author <a href="mailto:yuluo08290126@gmail.com">yuluo</a>
 *
 * emmm 没有啥好办法了，先这样搞吧~~~~
 */

public class SyncMcpToolCallbackWrapper {

	private final SyncMcpToolCallback callback;

	public SyncMcpToolCallbackWrapper(SyncMcpToolCallback callback) {
		this.callback = callback;
	}

	public McpSyncClient getMcpClient() {

		try {
			Field field = SyncMcpToolCallback.class.getDeclaredField("mcpClient");
			field.setAccessible(true);
			return (McpSyncClient) field.get(callback);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
