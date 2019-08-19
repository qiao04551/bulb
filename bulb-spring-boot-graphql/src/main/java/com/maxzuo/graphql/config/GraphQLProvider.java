package com.maxzuo.graphql.config;

import com.maxzuo.graphql.resolver.GraphQLQueryResolver;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * GraphQL query provider.
 * <p>
 * Created by zfh on 2019/08/08
 */
@Component
public class GraphQLProvider implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(GraphQLProvider.class);

    private TypeRuntimeWiring.Builder builder = newTypeWiring("Query");

    public GraphQL graphQL;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            Map<String, GraphQLQueryResolver> beansOfType = event.getApplicationContext().getBeansOfType(GraphQLQueryResolver.class);
            logger.info("================== load graphql resolver ===================");

            for (GraphQLQueryResolver resolver : beansOfType.values()) {
                Class<? extends GraphQLQueryResolver> aClass = resolver.getClass();
                logger.info("className: " + aClass.getName());

                Method[] declaredMethods = aClass.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    String methodName = method.getName();
                    try {
                        builder = this.builder.dataFetcher(methodName, (DataFetcher) method.invoke(resolver));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            logger.info("================== load graphql resolver ===================");

            initGraphQL();
        }
    }

    private void initGraphQL () {
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        GraphQLSchema graphQLSchema = schemaGenerator.makeExecutableSchema(buildTypeRegistry(), buildWiring());
        graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private TypeDefinitionRegistry buildTypeRegistry () {
        SchemaParser schemaParser = new SchemaParser();
        TypeDefinitionRegistry typeRegistry = new TypeDefinitionRegistry();

        String dir = GraphQLProvider.class.getResource("/query-protocol/").getFile();
        File[] files = loadSchemaFiles(new File(dir));
        for (File file : files) {
            typeRegistry = typeRegistry.merge(schemaParser.parse(file));
        }
        return typeRegistry;
    }

    /**
     * 加载目录中的所有文件
     */
    private static File[] loadSchemaFiles(File dir) {
        return dir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String path = pathname.getName();
                if (path.endsWith(".graphqls")) {
                    return true;
                }
                return false;
            }
        });
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring().type(builder).build();
    }
}
