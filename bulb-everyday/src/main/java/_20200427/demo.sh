#!/usr/bin/env bash

## To day Learning Bash
## Bash脚本教程：https://wangdoc.com/bash

# 查看当前运行的 Shell
echo $SHELL

# 本机的 Bash 版本
bash --version

# 输出多行文本
echo "<html>
    <head>
        <title>Page Title</title>
    </head>
    <body>
        Page Body.
    </body>
</html>
"

# -n 参数可以取消末尾的回车符，分号（;）是命令的结束符
echo -n a; echo b

# -e 参数会解释引号里面的特殊字符
echo -e "hello\nworld"

# 命令的组合符 && 和 ||
# Command1 && Command2  // 如果Command1命令运行成功，则继续运行Command2命令
# Command1 || Command2  // 如果Command1命令运行失败，则继续运行Command2命令


### Bash 的模式扩展

# 波浪线~会自动扩展成当前用户的主目录。
echo ~

# ? 字符代表文件路径里面的任意单个字符，不包括空字符
ls ?.txt

# * 字符代表文件路径里面的任意数量的字符，包括零个字符
ls *.txt

# 方括号扩展，类似于正则匹配
ls [a-z][a-z][a-z][a-z].sh

# 大括号扩展
echo {a..z}
echo {a..c}{1..3}

# 变量扩展，变量名除了放在 $ 后面，也可以放在 ${} 里面。
echo $SHELL
echo ${SHELL}

# 子命令扩展
echo $(date)

# 算术扩展
echo $((2 + 2))

# export用来向子 Shell 输出变量
export NAME="base"

# 字符串长度
echo ${#NAME}

# 子字符串
echo ${NAME:0:1}

# 算术表达式
((VAL = 5 + 5))
echo $VAL
echo $((2 + 2))

# read命令
read -p "Please, enter your firstname and lastname: " FN LN
echo "Hi！$FN, $LN !"

# 如果read命令之后没有定义变量名，那么环境变量REPLY会包含所有的输入
echo -n "Enter one or more values > "
read
echo $REPLY

# 读取文件，每次读取一行，直到文件读取完毕
filename="/Users/dazuo/workplace/bulb/bulb-everyday/src/main/java/_20200427/mock.json"
while read myline
do
    echo $myline
done < $filename

# read 超时设置
if read -t 3 response; then
  echo "用户输入："$response
else
  echo "用户没有输入"
fi

# set 命令
set -x
echo $NAME
