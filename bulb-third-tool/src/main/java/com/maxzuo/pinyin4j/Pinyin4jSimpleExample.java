package com.maxzuo.pinyin4j;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * pinyin4j的使用
 * <pre>
 *   pinyin4j的主页：http://pinyin4j.sourceforge.net/
 *   pinyin4j能够根据中文字符获取其对应的拼音，而且拼音的格式可以定制
 *   pinyin4j是一个支持将中文转换到拼音的Java开源类库
 * 　　1.支持简体中文和繁体中文字符
 * 　　2.支持转换到汉语拼音，通用拼音, 威妥玛拼音（威玛拼法）, 注音符号第二式, 耶鲁拼法和国语罗马字
 * 　　3.支持多音字，即可以获取一个中文字符的多种发音
 * 　　4.支持多种字符串输出格式，比如支持Unicode格式的字符ü和声调符号(阴平 "ˉ",阳平"ˊ",上声"ˇ",去声"ˋ")的输出
 * </pre>
 * <p>
 * Created by zfh on 2019/08/26
 */
public class Pinyin4jSimpleExample {

    public static void main(String[] args) {
        System.out.println(cn2FirstSpell("中国"));
        System.out.println(cn2Spell("中国"));
    }

    /**
     * 获取汉字串拼音首字母，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音首字母
     */
    private static String cn2FirstSpell(String chinese) {
        StringBuilder pybf = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char c : arr) {
            if (c > 128) {
                try {
                    String[] _t = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
                    if (_t != null) {
                        pybf.append(_t[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(c);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }

    /**
     * 获取汉字串拼音，英文字符不变
     *
     * @param chinese 汉字串
     * @return 汉语拼音
     */
    private static String cn2Spell(String chinese) {
        StringBuilder pybf = new StringBuilder();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }
}
