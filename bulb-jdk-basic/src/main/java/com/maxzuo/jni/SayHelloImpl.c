#include "SayHello.h"
#include <stdio.h>

// C函数是用下划线(_)作为命名分隔（对应java的包名）
JNIEXPORT void JNICALL Java_com_maxzuo_jni_SayHello_sayHello(JNIEnv *env,jobject obj){
    printf("Hello JNI\n");
    return;
}
