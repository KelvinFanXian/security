package website.fanxian.dynamic_functional.regex;

/**
 * 正则表达式JavaAPI
 * @author Kelvin范显
 * @createDate 2018年10月08日
 */
public class JavaAPI {/*
    切分
    验证
    查找
    替换
*/

    void Pattern() {/*表示正则表达式对象

    */}

    void Matcher() {/*表示一个匹配

    */}

    void _1表示正则表达式() {/*
        Java中主要是处理斜杠\，就是在字符串中，一个\要替换为两个\：
        <(\w+)>(.*)</\1>    "<(\\w+)>(.*)</\\1>"

        字符串表示的正则表达式可以被编译为一个Pattern对象，比如：
            String regex = "<(\\w+)>(.*)</\\1>";
            Pattern pattern = Pattern.compile(regex);

        三种匹配模式：
            单行模式（点号模式）      Pattern.DOTALL
            多行模式                 Pattern.MULTILINE
            大小写模式               Pattern.CASE_INSENSI-TIVE

            多个模式可以用|分隔
                Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL)

        还有一个模式 Pattern.LITERAL, 此模式下，正则表达式字符串中的元字符将失去特殊含义，被看作普通字符。
            Pattern的静态方法：  public static String quote(String s) 有类似意义。
            quote("\\d{6})"  =  "\\Q\\d{6}\\E"
    */}

    void _2切分(){/*
        String类有方法
            public String[] split(String regex)
            如果分隔符是元字符：. $ | ( ) [ { ^ ? * + \ 就需要转义。比如按点号 . 分隔：
                String[] fields = str.split("\\.");
            尾部的空白字符串不会包含在返回的结果数组中，但头部和中间的空白字符串会被包含在内。
                " ,abc,,def,, "  逗号分隔后-->   [, abc, ,def]

        Pattern类也有类似方法
            public String[] split(CharSequence input)
            对比String方法的

    */}

    void _3验证(){/*

     */}

    void _4查找(){/*

     */}

    void _5替换(){/*

     */}

}
