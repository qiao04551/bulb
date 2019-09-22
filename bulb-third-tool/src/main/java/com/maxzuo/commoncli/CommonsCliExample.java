package com.maxzuo.commoncli;

import org.apache.commons.cli.*;

import java.util.Scanner;

/**
 * Apache Common-cli 使用示例
 * <p>
 * Created by zfh on 2019/09/22
 */
public class CommonsCliExample {

    public static void main(String[] args) {
        // simpleUseCase();
        parseScannerInput();
    }

    /**
     * 简单使用
     */
    private static void simpleUseCase () {
        String[] args = {"-c", "config.xml", "-d", "dev"};

        Options options = new Options();
        options.addOption(new Option("d", "debug", true, "Turn on debug."));
        options.addOption(new Option("c", "configFile", true, "Name server config properties file"));
        options.addOption(new Option("h", "help", false, "Print help"));

        CommandLineParser parser = new DefaultParser();
        HelpFormatter hf = new HelpFormatter();
        try {
            CommandLine commandLine = parser.parse(options, args);
            if (commandLine.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("AppName", options, true);
            }

            // 打印opts的名称和值
            Option[] opts = commandLine.getOptions();
            if (opts != null) {
                for (Option opt : opts) {
                    String name = opt.getLongOpt();
                    String value = commandLine.getOptionValue(name);
                    System.out.println(name + "=>" + value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            hf.printHelp("AppName", options, true);
        }
    }

    /**
     * 结合控制台输入，解析参数
     */
    private static void parseScannerInput () {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Options options = new Options();
                options.addOption(new Option("d", "debug", true, "Turn on debug."));
                options.addOption(new Option("c", "configFile", true, "Name server config properties file"));
                CommandLineParser parser = new DefaultParser();

                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("请输入 ...");
                    String newLine = scanner.nextLine();
                    String[] args = newLine.split(" +");
                    try {
                        CommandLine commandLine = parser.parse(options, args);

                        // 打印opts的名称和值
                        Option[] opts = commandLine.getOptions();
                        if (opts != null) {
                            for (Option opt : opts) {
                                String name = opt.getLongOpt();
                                String value = commandLine.getOptionValue(name);
                                System.out.println(name + "=>" + value);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setName("Scanner-thread");
        t.start();
    }
}
