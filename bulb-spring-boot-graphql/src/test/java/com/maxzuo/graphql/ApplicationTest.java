package com.maxzuo.graphql;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.*;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * Created by zfh on 2019/08/09
 */
public class ApplicationTest {

    /**
     * 入门示例
     */
    @Test
    public void gettingStarted () {
        URL url = this.getClass().getResource("/hello.graphqls");
        File file = new File(url.getFile());

        // 1.解析
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(file);

        // 2.运行时连接是数据获取器、类型解析器和自定义标量的规范
        RuntimeWiring runtimeWiring = RuntimeWiring.newRuntimeWiring()
                .type(
                        "Query", new UnaryOperator<TypeRuntimeWiring.Builder>() {
                            @Override
                            public TypeRuntimeWiring.Builder apply(TypeRuntimeWiring.Builder builder) {
                                return builder
                                        .dataFetcher("hello", new StaticDataFetcher("world"))
                                        .dataFetcher("human", new DataFetcher() {
                                            @Override
                                            public List<Human> get(DataFetchingEnvironment environment) throws Exception {
                                                // 参数
                                                Map<String, Object> arguments = environment.getArguments();
                                                System.err.println("arguments: " + arguments);
                                                // 变量
                                                Map<String, Object> variables = environment.getVariables();
                                                System.err.println("variables: " + variables);

                                                List<Human> humans = new ArrayList<>(5);
                                                humans.add(new Human("1", "dazuo", "EMPIRE"));
                                                humans.add(new Human("2", "dazuo", "EMPIRE"));
                                                humans.add(new Human("3", "dazuo", "EMPIRE"));
                                                humans.add(new Human("4", "dazuo", "EMPIRE"));

                                                return humans;
                                            }
                                        });
                            }
                        }
                )
                .build();

        // 3.创建一个可执行的模式
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);

        // 4.该类是所有graphql-java查询执行的起点。它组合了成功执行graphql查询所需的对象，其中最重要的是构建该对象的模式和执行策略
        GraphQL build = GraphQL.newGraphQL(graphQLSchema).build();

        // ExecutionResult executionResult = build.execute("query queryHuman {\nhello \nhuman{ \n id \nname \nhomePlanet}}");
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("query queryHuman($id: ID){hello,human(id: $id){id,name,homePlanet}}")
                .operationName("queryHuman")
                .variables(ImmutableMap.of("id", "9"))
                .build();
        ExecutionResult executionResult = build.execute(executionInput);

        System.out.println(JSONObject.toJSONString(executionResult.getData()));
    }

    public static class Human {

        private String id;

        private String name;

        private String homePlanet;

        public Human(String id, String name, String homePlanet) {
            this.id = id;
            this.name = name;
            this.homePlanet = homePlanet;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHomePlanet() {
            return homePlanet;
        }

        public void setHomePlanet(String homePlanet) {
            this.homePlanet = homePlanet;
        }
    }
}
