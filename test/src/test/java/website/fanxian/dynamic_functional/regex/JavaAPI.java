package website.fanxian.dynamic_functional.regex;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            对比String方法的split:
                1)  Pattern接受的参数是CharSequence，更为通用
                2)  如果regex长度大于1或包含元字符， String的split需要： regex->Pattern; Pattern.split。 （为避免重复编译，应该优先采用Pattern的方法）
                3)  如果regex就一个字符且不是元字符， String的split会采用更简单高效的实现。

    */}

    @Test
    public void _3验证(){
        /*String的方法
            public boolean matches(String regex)
            其实现方式就是调用Pattern的matches方法
            就是先嗲用compile编译regex为Pattern对象， 再调用Pattern的matches方法生成一个匹配对象Matcher，
            Matcher的matches方法返回是否完整匹配
        */
        String regex = "\\d{8}";
        String string = "12345678";
        System.out.println(string.matches(regex));
    }

    @Test
    public void _4查找(){/*
        在文本中寻找匹配正则表达式的子字符串
        end()   子字符串在整个字符串中的结束位置+1
        group() 其实调用的是group(0)， 表示获取匹配的第0个分组的内容。分组0是一个特殊的分组，表示匹配的整个子字符串。
     */
        String regex = "(\\d{4})-(\\d{2})-(\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        String string  = "today is 2017-06-02, yesterday is 2017-06-01";
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            System.out.println(
                    "find " + matcher.group() +
                    " position: " + matcher.start() + "-" + matcher.end());

            System.out.println(
                    "year: " + matcher.group(1) +
                    ", month: " + matcher.group(2) +
                    ", day: " + matcher.group(3));
        }

    }

    @Test
    public void _5替换(){/*
        String有多个替换方法：
            public String replace(char oldChar, char newChar)
            public String replace(CharSequence target, CharSequence replacement)
            public String replaceAll(String regex, String replacement)
            public String replaceFirst(String regex, String replacement)
     */

        // 将多个空白，替换为一个空白
//        String regex = "\\s+";
//        String string = "hello      world     good";
//        System.out.println(string.replaceAll(regex, " "));

        // 可以用美元符号加数字的形式(比如$1)引用捕获分组：
//        String regex0 = "(\\d{4})-(\\d{2})-(\\d{2})";
//        String string0 = "today is 2017-06-02.";
//        System.out.println(string0.replaceFirst(regex0, "$1/$2/$3"));

        // 如果替换字符串是用户提供的， 为避免元字符的干扰，可以使用Matcher的如下静态方法，将其视为普通字符串：
        // public static String quoteReplacement(String s)
//        String regex1 = "#";
//        String string1 = "#this is a test";
//        String replacement = "$";
//        String replacement0 = Matcher.quoteReplacement(replacement);
//        System.out.println(string1.replaceAll(regex1, replacement0));

        // replaceAll和replaceFirst都定义在Matcher中，除了一次性的替换操作外，
        // Matcher还定义了 边查找、边替换的方法：
        // public Matcher appendReplacement(StringBuffer sb, String replacement)
        // public StringBuffer appendTail(StringBuffer sb)
        // 这两个方法和find一起使用：
        Pattern p = Pattern.compile("cat");
        Matcher m = p.matcher("one cat, two cat, three cat");
        StringBuffer sb = new StringBuffer();
        int foundNum = 0;
        while (m.find()) {
            m.appendReplacement(sb, "dog");
            foundNum++;
            if (foundNum == 2) {
                break;
            }
        }
        m.appendTail(sb);
        System.out.println(sb.toString());

        // Matcher内部除了有一个查找位置，还有一个append位置，初始为0， 当找到一个匹配的子字符串后，
        // appendReplacement() 做了3件事：
            // 1) 将append位置到当前匹配之前的子字符串append到sb中， 在第一次为“one”，第二次为“two”。
            // 2) 将替换字符串append到sb中。
            // 3）更新append位置为当前匹配之后的位置。
        // appendTail将append位置之后所有的字符append到sb中。
    }

}
