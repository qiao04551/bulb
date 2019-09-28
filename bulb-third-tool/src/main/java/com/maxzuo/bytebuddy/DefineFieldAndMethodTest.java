package com.maxzuo.bytebuddy;

import com.maxzuo.proxy.bytebuddy.annotations.TokenImpl;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.ClassFileVersion;
import net.bytebuddy.description.modifier.FieldManifestation;
import net.bytebuddy.description.modifier.Ownership;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.scaffold.subclass.ConstructorStrategy;
import net.bytebuddy.implementation.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import static net.bytebuddy.matcher.ElementMatchers.*;

/**
 * 使用ByteBuddy定义属性和方法
 * Created by zfh on 2019/01/28
 */
class DefineFieldAndMethodTest {

    private static final Logger logger = LoggerFactory.getLogger(DefineFieldAndMethodTest.class);

    @DisplayName("增强类的属性和方法")
    @Test
    void testEnhanceClass() throws NoSuchMethodException, IllegalAccessException, InstantiationException,
                           InvocationTargetException {
        DynamicType.Unloaded<Boss> dynamicType = new ByteBuddy()
        // 构造函数策略
            .subclass(Boss.class, ConstructorStrategy.Default.NO_CONSTRUCTORS)
            // 序列号 serialVersionUID
            .serialVersionUid(123456L)
            // 类的可见性
            .modifiers(Visibility.PUBLIC)
            // 类的注解
            .annotateType(new TokenImpl())
            // 类的泛型
            .typeVariable("T")
            // 继承接口
            .implement(Employee.class)
            // 定义构造函数
            .defineConstructor(Visibility.PUBLIC)
            // 调用特定的构造函数
            .intercept(MethodCall.invoke(Boss.class.getDeclaredConstructor()))
            // 构造函数的参数
            // 添加字段，设置修饰符 public static final
            .defineField("name", String.class, Visibility.PUBLIC, Ownership.STATIC, FieldManifestation.FINAL)
            // 设置默认值（只有在字段声明为 static final时，该值才对代码可见）；还可以为代码不可见的非静态字段设置默认值
            .value("dazuo")
            // 字段添加注解
            .annotateField(new TokenImpl())
            // 定义Bean的 age 属性（setter/getter），如果设置为 true 属性添加 final 关键字，默认为false
            .defineProperty("age", Integer.class, false)
            // 字段添加注解
            .annotateField(new TokenImpl())
            // 添加方法，返回值类型void
            .defineMethod("methodOne", void.class, Visibility.PUBLIC)
            // 当前方法添加 String 类型的参数
            .withParameters(String.class)
            // 方法抛出的异常
            .throwing(Exception.class)
            // 固定返回值，也可以委派给其它方法
            .intercept(FixedValue.value("hello")).name("com.maxzuo.demo.ByteBuddyDemo").make();

        writeToFile(dynamicType.getBytes());

        Class<? extends Boss> loaded = dynamicType.load(getClass().getClassLoader()).getLoaded();
        Constructor<?>[] constructors = loaded.getDeclaredConstructors();
        System.out.println("构造函数：" + Arrays.toString(constructors));
    }

    @DisplayName("方法固定返回值")
    @Test
    void testMatcherMethod() {
        DynamicType.Unloaded<Object> dynamicType = new ByteBuddy().subclass(Object.class)
            .name("com.maxzuo.demo.ByteBuddyDemo")
            // 按照方法名称匹配 方法
            .method(named("toString"))
            // 覆盖toString方法先前的默认值
            .intercept(FixedValue.value("Hello World!")).make();

        writeToFile(dynamicType.getBytes());
    }

    @DisplayName("使用多个规则")
    @Test
    void testUseMultipleRules() {
        try {
            Foo foo = new ByteBuddy().subclass(Foo.class)
                // 规则一：匹配已声明元素的原始声明类型。
                .method(isDeclaredBy(Foo.class)).intercept(FixedValue.value("one！"))
                // 规则二：匹配foo命名的方法
                .method(named("foo")).intercept(FixedValue.value("two!"))
                // 规则三：匹配food命名且参数个数为 1 个的方法
                .method(named("foo").and(takesArguments(1))).intercept(FixedValue.value("three！"))
                // 忽略匹配的方法，不会被覆盖
                .ignoreAlso(named("foo").and(takesArguments(1))).make().load(ClassLoader.getSystemClassLoader())
                .getLoaded().newInstance();

            // 按照规则顺序匹配，依次覆盖上一级方法
            System.out.println("methodOne：" + foo.bar());
            System.out.println("methodTwo：" + foo.foo());
            System.out.println("methodThree：" + foo.foo("hello"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("参数类型无法转换")
    @Test
    void testIllegalAssignment() {
        try {
            new ByteBuddy().subclass(Foo.class)
            // 匹配目标方法
                .method(named("foo"))
                // 目标方法的返回值是 String 类型，此时赋值Integer类型，将由于 参数类型无法转换 抛出异常
                .intercept(FixedValue.value(111)).make();

        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }

    @DisplayName("委派静态方法调用")
    @Test
    void testInvokeDelegationMethod() {
        /*
            在许多情况下，从方法返回固定值当然是不够的。为了获得更大的灵活性，Byte Buddy提供了MethodDelegation实现，它为方法调用
            提供了最大的自由度。方法委托定义了动态创建类型的方法，以将任何调用转发给可能位于动态类型之外的另一个方法。这样，动态类
            的逻辑可以使用普通Java表示，而只有通过代码生成实现与另一个方法的绑定。

            目标方法的注解：
              @Argument(0)：表示源方法参数的位置
              @AllArguments：目标方法的参数必须是一个数组，源方法的参数必须可分配给目标方法的数组；否则不将当前目标方法视为绑定到源方法的候选方法。
                           这里说明 候选方法 不代表一定绑定，如果有其它更符合的绑定，将优先匹配。
              @this：源实例对象的引用 将分配到 目标方法的参数。生成代码：return Target.print3(this)
              @SuperCall：

            总结：
              1.Byte Buddy不要求将目标方法与 源方法 相同命名。String类型比Object类型更具体，因此匹配第一个方法。
              2.Target类如果存在 多个 相同参数不同命名的方法，将抛出不能匹配模糊性异常。
              3.Target类的方法必须是 public static 修饰的方法。
         */
        DynamicType.Unloaded<Source> dynamicType = new ByteBuddy().subclass(Source.class).method(named("hello"))
            .intercept(MethodDelegation.to(Target.class)).name("com.maxzuo.bytebuddy.SourceSub").make();

        writeToFile(dynamicType.getBytes());
    }

    @DisplayName("@SuperCall注解")
    @Test
    void testInvokeDelegationMethodAndAnnotationArgument() {
        try {
            DynamicType.Unloaded<MemoryDatabase> dynamicType = new ByteBuddy().subclass(MemoryDatabase.class)
                .method(named("queryOrder"))
                .intercept(MethodDelegation.withDefaultConfiguration().to(new LoggerInterceptor()))
                .name("com.maxzuo.bytebuddy.SourceSub").make();

            // 字节码文件
            writeToFile(dynamicType.getBytes());

            MemoryDatabase memoryDatabase = dynamicType.load(ClassLoader.getSystemClassLoader()).getLoaded()
                .newInstance();

            // 调用方法
            memoryDatabase.queryOrder(3);
        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }

    @DisplayName("委托实例方法")
    @Test
    void testDelegationInstanceMethod() {
        DynamicType.Unloaded<Source> dynamicType = new ByteBuddy().subclass(Source.class).method(named("hello"))
            .intercept(MethodDelegation.to(new Target())).name("com.maxzuo.bytebuddy.SourceSub").make();

        writeToFile(dynamicType.getBytes());
    }

    @DisplayName("调用接口的默认方法")
    @Test
    void testInvokeDefaultMethod() {
        try {
            DynamicType.Unloaded<Object> dynamicType = new ByteBuddy(ClassFileVersion.JAVA_V8).subclass(Object.class)
                .implement(IFast.class).implement(ISlow.class)
                // 实现不同接口的相同方法，定义优先加载
                .method(named("m")).intercept(DefaultMethodCall.prioritize(IFast.class)).make();

            // 写入文件
            writeToFile(dynamicType.getBytes());

            Class<?> aClass = dynamicType.load(ClassLoader.getSystemClassLoader()).getLoaded();
            Object o1 = aClass.newInstance();
            Method m = aClass.getMethod("m");
            Object invoke = m.invoke(o1);
            System.out.println("invoke: " + invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("调用特定的方法")
    @Test
    void testInvokeSpecificMethod() throws NoSuchMethodException, IllegalAccessException, InstantiationException,
                                   InvocationTargetException {
        DynamicType.Unloaded<Boss> dynamicType = new ByteBuddy().subclass(Boss.class)
        // 拦截目标方法
            .method(named("talk"))
            /// 调用特定的静态方法，无参
            .intercept(MethodCall.invoke(Source.class.getMethod("methodOne")))
            /// 调用特定的静态方法，同时指定参数
            //.intercept(MethodCall.invoke(Source.class.getMethod("methodTwo", String.class)).with("dazuo"))
            /// 调用特定的实例方法，同时指定参数
            //.intercept(MethodCall.invoke(Source.class.getMethod("methodThree", String.class)).on(new Source()).with("dazuo"))
            .name("com.maxzuo.bytebuddy.SourceSub").make();

        writeToFile(dynamicType.getBytes());

        // 调用方法（委托方法 覆盖 原始方法）
        dynamicType.load(getClass().getClassLoader()).getLoaded().newInstance().talk();
    }

    @DisplayName("访问字段")
    @Test
    void testFieldAccessor() {
        try {
            Class<? extends UserType> dynamicUserType = new ByteBuddy()
                .subclass(UserType.class)
                .method(not(isDeclaredBy(Object.class)))
                // 任何拦截的方法在提供的 实例字段 上调用非静态方法。要被认为是有效的委托目标，方法必须对插装类型可见和可访问。
                .intercept(MethodDelegation.toField("interceptor"))
                .defineField("interceptor", Interceptor.class, Visibility.PRIVATE)
                .implement(InterceptionAccessor.class)
                /// 通过方法名称，派生出字段的名称
                //.intercept(FieldAccessor.ofBeanProperty())
                // 或者指定访问的字段名称
                .intercept(FieldAccessor.ofField("interceptor")).name("com.maxzuo.bytebuddy.SourceSub").make()
                .load(getClass().getClassLoader()).getLoaded();

            DynamicType.Unloaded<InstanceCreator> dynamicType = new ByteBuddy().subclass(InstanceCreator.class)
                .method(named("makeInstance"))
                // 对于截获方法的调用都会返回给定目标类型的新实例。
                .intercept(MethodDelegation.toConstructor(dynamicUserType)).make();

            // 写入文件
            writeToFile(dynamicType.getBytes());

            InstanceCreator factory = dynamicType.load(ClassLoader.getSystemClassLoader()).getLoaded().newInstance();

            // 创建动态增强的新实例
            UserType userType = (UserType) factory.makeInstance();
            // 应用于新创建的实例
            ((InterceptionAccessor) userType).setInterceptor(new HelloWorldInterceptor());
            System.out.println(((InterceptionAccessor) userType).getInterceptor().doSomethingElse());

        } catch (Exception e) {
            logger.error("异常信息", e);
        }
    }

    /**
     * 将字节数组写入.class文件中
     * @param bytes 字节数组
     */
    private void writeToFile(byte[] bytes) {
        File file = new File("ByteBuddy.class");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.flush();
            fos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
