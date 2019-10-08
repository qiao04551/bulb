package com.maxzuo.everyday.binlog;

import javax.sql.DataSource;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * rollback.sql 数据清洗
 * <p>
 * Created by zfh on 2019/10/08
 */
public class DataClean {

    private static String path = DataSource.class.getResource("/binlog").getPath();


    public static void main(String[] args) {
        try {
            // firstOne();
            // firstTwo();
            extractPrimaryKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一次清洗
     */
    private static void firstOne () throws IOException {
        String[] keywords = {"UPDATE", "SET", "@1=", "@10=", "WHERE"};

        File file = new File(path + "/rollback.sql");
        File outFile = new File(path + "/roll-one.sql");

        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            fr = new FileReader(file);
            fw = new FileWriter(outFile);

            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

            String s;
            while ((s = br.readLine()) != null) {
                boolean flag = false;
                for (String kw : keywords) {
                    if (s.trim().startsWith(kw)) {
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    if (s.trim().startsWith("@1=")) {
                        s = s.replace("@1=", "id = ");
                    }
                    if (s.trim().startsWith("@10=")) {
                        s = s.replace("@10=", "pay_status = ");
                    }
                    if (s.trim().startsWith("UPDATE")) {
                        bw.write("\n");
                    }
                    bw.write(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
            if (br != null) {
                br.close();
            }
            if (fw != null) {
                fw.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    /**
     * 第二次清洗
     */
    private static void firstTwo () throws IOException {
        File file = new File(path + "/roll-one.sql");
        File outFile = new File(path + "/roll-two.sql");

        FileReader fr = null;
        FileWriter fw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            fr = new FileReader(file);
            fw = new FileWriter(outFile);

            br = new BufferedReader(fr);
            bw = new BufferedWriter(fw);

            String s;
            while ((s = br.readLine()) != null) {
                if (s.trim().endsWith("AND  pay_status = 0 ,")) {
                    s = s.replace("AND  pay_status = 0 ,", "AND  pay_status = 0;\n");
                    bw.write(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                bw.close();
            }
            if (br != null) {
                br.close();
            }
            if (fw != null) {
                fw.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    /**
     * 第三次清洗提取Key
     */
    private static void extractPrimaryKey () throws IOException {
        int count = 0;
        File file = new File(path + "/roll-two.sql");
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            StringBuilder builder = new StringBuilder();
            String s;
            while ((s = br.readLine()) != null) {
                // 提取ID
                String regex = "id = ([\\d]*)";
                String id = getSubUtilSimple(s, regex);
                builder.append(id).append(", ");

                count++;
            }
            System.out.println("count: " + count);
            System.out.println(builder);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                br.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    /**
     * 返回单个字符串，若匹配到多个的话就返回第一个
     */
    private static String getSubUtilSimple(String soap, String rgex) {
        Pattern pattern = Pattern.compile(rgex);
        Matcher m = pattern.matcher(soap);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }
}
