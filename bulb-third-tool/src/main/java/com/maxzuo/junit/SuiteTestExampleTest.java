package com.maxzuo.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Junit - 套件测试
 * <pre>
 *   Junit 4允许通过使用测试套件类批量运行测试类。为一套测试类创建一个测试套件，要为测试类添加以下注解：
 *   1. @RunWith(Suite.class)
 *   2. @SuiteClasses(TestClass1.class, TestClass2.class)
 *   当运行时，所有包含在@SuiteClasses注解内的所有测试类都会被执行。
 * </pre>
 *
 * Created by zfh on 2019/05/07
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
    JunitExampleTest.class
})
public class SuiteTestExampleTest {
    /*
        SuiteTestExampleTest类的内部不能有测试用例
     */
}
