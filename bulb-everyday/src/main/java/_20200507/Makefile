BASH_PATH= /Users/dazuo

.PHONY: test build

test: 
	@echo "test ..."

build: test
	@echo "build ..."

var-kept:
	@# 两行命令在两个不同的进程执行
	@# 调用Shell变量，需要在美元符号前，再加一个美元符号，这是因为Make命令会对美元符号转义。
	@export foo=bar; \
	echo "foo= $$foo"

var:
	@echo ${BASH_PATH}
