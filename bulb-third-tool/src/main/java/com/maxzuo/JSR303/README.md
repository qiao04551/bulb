## JSR 303

JSR303是JAVA EE6中的子规范。用于对Java Bean的字段值进行校验，确保输入进来的数据在语义上是正确的，使验证逻辑从业务代码
中脱离出来。JSR303是运行时数据验证框架，验证之后验证的错误信息会马上返回。

> **JSR303(BeanValidation1.0)和JSR349(BeanValidation1.1)**

1）JSR 303主要是对JavaBean进行验证。

2）JSR 349是对JSR 303的补充，提供了如方法级别（方法参数/返回值）、依赖注入等的验证。

> **依赖的包**

```
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>1.1.0.Final</version>
</dependency>
<!-- hibernate validator-->
<dependency>
    <groupId>org.hibernate</groupId>
    <artifactId>hibernate-validator</artifactId>
    <version>5.2.0.Final</version>
</dependency>
```

> **Bean Validation内置校验规则**

```
@Null	        元素必须为 null
@NotNull	    元素必须不为 null
@AssertTrue	    元素必须为 true
@AssertFalse	元素必须为 false
@Min            元素必须是一个数字，其值必须大于等于指定的最小值
@Max            元素必须是一个数字，其值必须小于等于指定的最大值
@DecimalMin 	元素必须是一个数字，其值必须大于等于指定的最小值
@DecimalMax     元素必须是一个数字，其值必须小于等于指定的最大值
@Size           元素的大小必须在指定的范围内
@Digits	        元素必须是一个数字，其值必须在可接受的范围内
@Past	        元素必须是一个过去的日期
@Future	        元素必须是一个将来的日期
@Pattern	    元素必须符合指定的正则表达式

# Hibernate Validator 附加的 constraint

@Email	        元素必须是电子邮箱地址
@Length	        字符串的大小必须在指定的范围内
@NotEmpty	    字符串的必须非空
@Range	        元素必须在合适的范围内
```

示例：@Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")


> **Spring中的JSR 303验证**

1.@Validated和@Valid的区别

- @Valid是javax.validation里的。
- @Validated是@Valid 的一次封装，是spring提供的校验机制使用。@Validated 提供分组功能

2.自定义Validate注解，使用javax.validation.@Constraint类。

3.验证分两种：
  
- 对封装的Bean进行验证（POJO对象）

- 对方法简单参数的验证：（不推荐）

```
1.注入MethodValidationPostProcessor Bean
2.在要MethodValidate的类上加上注解@Validated
3.在方法中使用：
    public String check(@Min(value = 2,message = "age必须大于2") int age) {}
4.处理校验失败：
    @ExceptionHandler(value = { ConstraintViolationException.class })
5.如果使用了@Validated，那么Bean Validate也会抛出异常而不是之前的封装在BindingResult中。
```