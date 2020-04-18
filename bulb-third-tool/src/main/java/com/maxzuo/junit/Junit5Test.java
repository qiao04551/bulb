package com.maxzuo.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Junit5 测试用例")
class Junit5Test {

    @DisplayName("Junit5 集成到Spring 5.0项目")
    @Test
    void testIntegrationSpring() {
        /// 解释说明
        /*
            依赖包：spring-test-5.0.7.RELEASE.jar
            集成到spring，使用注解：
              import org.springframework.test.context.junit.jupiter.SpringExtension;
              import org.springframework.boot.test.context.SpringBootTest;
              import org.junit.jupiter.api.extension.ExtendWith;
              import org.springframework.test.context.ContextConfiguration;
              @ExtendWith(SpringExtension.class)
              @ContextConfiguration("classpath:spring/Spring.xml")
         */
    }

    @DisplayName("Junit5 集成到SpringBoot 2项目")
    @Test
    void testIntegrationSpringBoot() {
        /// 解释说明
        /*
            依赖包：spring-test-5.0.7.RELEASE.jar
            使用注解
              import org.springframework.test.context.junit.jupiter.SpringExtension;
              import org.springframework.boot.test.context.SpringBootTest;
              import org.junit.jupiter.api.extension.ExtendWith;
              @SpringBootTest
              @ExtendWith(SpringExtension.class)
         */
    }

    @DisplayName("使用指定的参数，多次执行")
    @ParameterizedTest
    @CsvSource({ "dazuo, 2", "wang, 3" })
    void testQueryData(String name, Integer age) {
        System.out.println("name: " + name);
    }

    @DisplayName("重复测试（注解的值表示执行的次数）")
    @RepeatedTest(2)
    void testUsingRepeatedTest() {
        System.out.println("hello world");
    }

    @DisplayName("@Disabled 等效于 junit4 中的@Ignore 注解，表示不执行当前方法")
    @Disabled
    @Test
    void testUsingDisable() {
        System.out.println("hello disable");
    }

    @DisplayName("使用Junit5 的断言")
    @Test
    void testUsingAssertions() {
        String name = "dazuo";
        // org.junit.jupiter.api.Assertions;
    }

    @DisplayName("使用第三方断言库：比如，使用AssertJ 流式断言")
    @Test
    void testUsingAssertJ() {
        // 第三方断言库提供的对匹配器的内置支持
        //String name = "dazuo";
        //org.assertj.core.api.Assertions.assertThat(name).as("字符串判断").startsWith("da").endsWith("uo").hasSize(5);
    }

    @Nested
    @DisplayName("嵌套测试：只有非静态嵌套类（即内部类）才能用作@Nested测试类")
    class NestedTest {

        @Test
        @DisplayName("打印日志")
        void printLog() {
            System.out.println("hello printLog");
        }
    }

    /**
     * 使用@Tag注解可以选择性地执行相应的测试用例
     * maven插件：<plugin>
     *                 <artifactId>maven-surefire-plugin</artifactId>
     *                 <version>2.22.0</version>
     *                 <configuration>
     *                     <properties>
     *                         <includeTags>fast</includeTags>
     *                         <excludeTags>slow</excludeTags>
     *                     </properties>
     *                 </configuration>
     *             </plugin>
     * 执行命令：mvn test
     */
    @Test
    @DisplayName("fast tag")
    @Tag("fast")
    void testTagAnnotationFast() {
        System.out.println("fast tag");
    }

    @Test
    @DisplayName("flow tag")
    @Tag("slow")
    void testTagAnotationSlow() {
        System.out.println("slow tag");
    }
}
