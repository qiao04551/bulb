package com.maxzuo.classloader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 类的加载器的使用
 * <p>
 * Created by zfh on 2019/02/23
 */
class InvokerExample {

    @DisplayName("ClassLoader加载资源文件")
    @Test
    void testLoadResourceFile() {
        ClassLoader classLoader = InvokerExample.class.getClassLoader();
        // 输出：AppClassLoader
        System.out.println("getClassLoader(): " + classLoader);

        // 最终：AppClassLoader继承自URLClassLoader并没实现findResource()，因此最终调用的是URLClassLoader.findResource()方法
        URL resource = InvokerExample.class.getClassLoader().getResource("spring.png");
        if (resource != null) {
            System.out.println(resource.getFile());
        }
    }

    @DisplayName("ClassLoader加载class文件，并转换为类的实例")
    @Test
    void testLoadClassFileAndConvertToInstance() {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            /*
                类加载过程分析：
                  1.首先调用CustomClassLoader.loadClass()方法，因为CustomClassLoader没有重写loadClass()方法（也不推荐重写）
                    因此会调用父类ClassLoader中loadClass()方法。
                  2.ClassLoader的loadClass()首先会调用 Class<?> c = findLoadedClass(name) 从缓存中查找。
                    （不同的ClassLoader实例对象都拥有不同的独立的类名称空间，所以加载的class对象也会存在不同的类名空间中）
                    从缓存查找，类名完整名称相同则不会再次被加载，因此我们必须绕过缓存查询才能重新加载class对象。当然也可直接调用
                    findClass()方法，这样也避免从缓存查找。
                  3.委派双亲查询，在都无法找到的情况下，会调用CustomClassLoader.findClass()方法执行加载逻辑以及获取字节码流的逻辑。
             */

            // 通过CustomClassLoader.findClass()加载
            Class<?> aClass1 = customClassLoader.loadClass("com.maxzuo.agent.PerfMonAgent");
            // 从customClassLoader实例对象下的缓冲中加载
            Class<?> aClass2 = customClassLoader.loadClass("com.maxzuo.agent.PerfMonAgent");
            // 跳过缓存，直接冲字节码中加载。如果已加载过，此时会抛出异常。
            //Class<?> aClass3 = customClassLoader.findClass("com.maxzuo.agent.PerfMonAgent");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @DisplayName("使用Class.forName()加载类")
    @Test
    void testClassForNameAndClassLoaderLoadClass() {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        try {
            /*
                加载类，通过Reflection.getCallerClass()指定获取调用此方法的方法调用者的类，从而获取类加载器，
                底层加载过程是native方法。该类的缓存存放在类加载器中。

                默认进行类的初始化。
             */
            //Class<?> aClass1 = Class.forName("com.maxzuo.agent.PerfMonAgent");

            /*
                使用指定的类加载器强制加载，如果为true，初始化该类。
             */
            Class<?> aClass = Class.forName("com.maxzuo.agent.model.Thuesday", true, customClassLoader);

            /*
                使用ClassLoader加载类，默认不会初始化类
             */
            customClassLoader.loadClass("com.maxzuo.agent.model.Thuesday");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 动态加载外部jar文件
     * <pre>
     *  1.ClassLoader只能加载classpath下面的类，而URLClassLoader可以加载任意路径下的类。
     *  2.URLClassLoader提供了这个功能，它让我们可以通过以下几种方式进行加载：
     *    文件: (从文件系统目录加载)
     *    jar包: (从Jar包进行加载)
     *    Http: (从远程的Http服务进行加载)
     * </pre>
     */
    @DisplayName("使用URLClassLoader从外部jar文件中加载类")
    @Test
    void testLoadJarFile() {
        try {
            URL urls = new URL("file:F:\\bulb\\bulb-agent\\target\\bulb-agent.jar");
            URLClassLoader urlLoader = new URLClassLoader(new URL[] { urls });
            Class<?> aClass = urlLoader.loadClass("com.maxzuo.agent.PerfMonAgent");
            System.out.println("类的加载器：" + aClass.getClassLoader());
            // 关闭，释放资源，此后将不能使用urlLoader类加载新类，但是已经加载的class不受影响。
            urlLoader.close();

            // 可以加载
            urlLoader.loadClass("com.maxzuo.agent.PerfMonAgent");
            // 无法加载 java.lang.ClassNotFoundException
            urlLoader.loadClass("com.maxzuo.agent.model.Thuesday");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("线程上下文类加载器")
    @Test
    void testThreadContextClassLoader() {
        // 线程上下文类加载器破坏了“双亲委派模型”，可以在执行线程中抛弃双亲委派加载链模式，使程序可以逆向使用类加载器。
        // 示例：父级类加载器加载的类 调用 子级类加载器加载的类
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        System.out.println(contextClassLoader);
    }

    @DisplayName("隐式加载")
    @Test
    void testLoadReferenceClass() {
        try {
            CustomClassLoader customClassLoader = new CustomClassLoader();
            // 加载不会，初始化类
            Class<?> aClass = customClassLoader.loadClass("com.maxzuo.agent.model.Monday");
            System.out.println("getClassLoader()：" + aClass.getClassLoader());
            System.out.println("getParent()：" + customClassLoader.getParent());

            /*
                隐式加载分析：
                  1.当实例化Monday类时，会初始化Thuesday类；隐式加载Thuesday类
                  2.默认由尝试CustomClassLoader加载，由于需要遵循双亲委派模型，且CustomClassLoader继承自AppClassLoader，
                    因此，由BootStrapClassLoader -> ExtClassLoader -> AppClassLoader依次尝试加载，直到都无法加载，
                    最终由CustomClassLoader.findClass()方法进行加载。

                  3.注意：隐式加载 默认由CustomClassLoader尝试加载（既：由加载Monday类的ClassLoader 去执行隐式加载），和线程上下文中定义的ClassLoader无关。
             */
            aClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @DisplayName("通过UrlClassLoader加载引用的Slf4j")
    @Test
    void testUrlClassLoader() {
        /*
            ClassLoader和URLClassLoader的区别：
              1.ClassLoader只能加载classpath下面的类。
              2.URLClassLoader可以加载任意路径下的类，比如：
                 文件: (从文件系统目录加载)
        	     jar包: (从Jar包进行加载)
        	     Http: (从远程的Http服务进行加载)
         */
        try {
            String agentLibPath = "F:\\bulb\\bulb-agent\\target\\lib";
            List<URL> urls = resolveLib(agentLibPath);
            URLClassLoader urlLoader = new URLClassLoader(urls.toArray(new URL[0]), null);
            System.out.println("getParent()：" + urlLoader.getParent());

            Class<?> aClass = urlLoader.loadClass("com.maxzuo.agent.model.Monday");
            aClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析lib目录，获取所有的jar
     */
    private List<URL> resolveLib(String agentLibPath) {
        File libDir = new File(agentLibPath);
        if (checkDirectory(libDir)) {
            return Collections.emptyList();
        }
        final List<URL> jarURLList = new ArrayList<>();

        final File[] findJarList = findJar(libDir);
        if (findJarList != null) {
            for (File file : findJarList) {
                URL url = toURI(file);
                if (url != null) {
                    jarURLList.add(url);
                }
            }
        }

        URL agentDirUri = toURI(new File(agentLibPath));
        if (agentDirUri != null) {
            jarURLList.add(agentDirUri);
        }
        return jarURLList;
    }

    /**
     * 检查目录
     */
    private boolean checkDirectory(File file) {
        if (!file.exists()) {
            System.out.println(file + " not found");
            return true;
        }
        if (!file.isDirectory()) {
            System.out.println(file + " is not a directory");
            return true;
        }
        return false;
    }

    private URL toURI(File file) {
        URI uri = file.toURI();
        try {
            return uri.toURL();
        } catch (MalformedURLException e) {
            System.out.println(file.getName() + ".toURL() failed.");
            return null;
        }
    }

    /**
     * 加载目录中的所有文件
     */
    private File[] findJar(File libDir) {
        return libDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String path = pathname.getName();
                for (String extension : getDefaultFileExtensionList()) {
                    if (path.lastIndexOf("." + extension) != -1) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 过滤文件（文件扩展名）
     */
    private List<String> getDefaultFileExtensionList() {
        List<String> extensionList = new ArrayList<>(3);
        extensionList.add("jar");
        extensionList.add("xml");
        extensionList.add("properties");
        return extensionList;
    }
}
