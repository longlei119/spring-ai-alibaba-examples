/*
 * Copyright 2024-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.example.controller;

import com.alibaba.cloud.ai.dbconnector.DbConfig;
import com.alibaba.cloud.ai.request.SchemaInitRequest;
import com.alibaba.cloud.ai.service.analytic.AnalyticNl2SqlService;
import com.alibaba.cloud.ai.service.simple.SimpleNl2SqlService;
import com.alibaba.cloud.ai.service.simple.SimpleVectorStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
public class ChatController {

    @Autowired
    private AnalyticNl2SqlService nl2SqlService;

    @Autowired
    private SimpleNl2SqlService simpleNl2SqlService;

    @Autowired
    private SimpleVectorStoreService simpleVectorStoreService;

    @Autowired
    private DbConfig dbConfig;

    @PostMapping("/chat")
    public String nl2Sql(@RequestBody String input) throws Exception {
        return nl2SqlService.nl2sql(input);
    }

    @PostMapping("/simpleChat")
    public String simpleNl2Sql(@RequestBody String input) throws Exception {
        SchemaInitRequest schemaInitRequest = new SchemaInitRequest();
        schemaInitRequest.setDbConfig(dbConfig);
        // 这里设置你需要查询的表
        schemaInitRequest.setTables(Arrays.asList("tableName"));
        simpleVectorStoreService.schema(schemaInitRequest);
        return simpleNl2SqlService.nl2sql(input);
    }
}

