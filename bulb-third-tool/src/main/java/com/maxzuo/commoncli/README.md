## Apache Commons-cli 解析命令行参数

构建命令行程序需要三个步骤. 分别是定义, 解析和询问

> 定义阶段（Create the Options）

每一条命令行都必须定义一组参数，它们被用来定义应用程序的接口.

CLI 使用 Options 类来定义和设置参数, 它是所有  Option 实例的容器. 目前有两种方式来创建 Options in CLI. 一种是通过构造函数，
另外一种方法是通过 Options 中定义的工厂方式来实现。

```
// create Options object
Options options = new Options();

// add t option
options.addOption("t", false, "display current time");
```

> 解析阶段（Parsing the command line arguments）

```
CommandLineParser parser = new DefaultParser();
CommandLine cmd = parser.parse( options, args);
```

> 询问阶段

在询问阶段中,应用程序通过查询 CommandLine，并通过其中的布尔参数和提供给应用程序的参数值来决定需要执行哪些程序分支.

这个阶段在用户的代码中实现，CommandLine 中的访问方法为用户代码提供了 CLI 的询问能力.

询问阶段的目的是根据命令行和解析器处理的Options规则与用户的代码相匹配.


