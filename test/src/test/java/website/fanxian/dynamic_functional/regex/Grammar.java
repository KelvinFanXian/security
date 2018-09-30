package website.fanxian.dynamic_functional.regex;

/**
 * 正则表达式语法
 * 1.单个字符
 * 2.字符组
 * 3.量词
 * 4.分组
 * 5.特殊边界匹配
 * 6.环视边界匹配
 * 7.转义与匹配模式
 */
public class Grammar {

    void _1单个字符(){/*
        特殊字符 \t \n \r
        八进制表示的字符 \0141
        十六进制表示的字符 \x6A
        Unicode编号表示的字符 \u9A6C
        斜杆本身 \
        元字符本身 . * ? + -
    */}

    void _2字符组(){/*
        任意字符
        多个指定字符之一
        字符区间
        排除型字符组
        预定义的字符组

        .   除了换行符意外的任意字符 a.f | 单行匹配模式中的任意字符 (?s)a.f
        []  匹配组中的任意一个字符     [abcd]
        -   连字符，表示连续的多个字符   [0-9]、[a-z]
            可以有多个连续空间，可以有其他普通字符     [0-9a-zA-Z_]
            如果要匹配 - 本身，可以加\，或者写在最前面     [-0-9]
        ^   字符组支持排除的概念， 在[后紧跟一个^    [^abcd]、[^0-9]
            只有在字符组开头才是元字符，否则匹配自身    [a^b]
        \d  digit=[0-9]
        \w  word=[a-zA-Z_0-9]
        \s  space=[\t\n\x0B\f\r]
        \D  [^\d]
        \W  [^\w]
        \S  [^\s]
    */}

    void _3量词(){/*
        指定出现次数的元字符

        +   表示前面字符的一次或者多次出现
        *   表示前面字符的0次或多次出现
        ?   表示前面字符可能出现或者不出现

        {m,n}  出现次数在区间[m,n]
                如果n没有限制则省略  {m,}
                如果与m相等则写为   {m}
        例：
            ab{1,10}c   b可能出现1到10次
            ab{3}c  b必须出现三次，即只能匹配abbbc
            ab{1,}c = ab+c
            ab{0,}c = ab*c
            ab{0,1}c = ab+c
        注意：
            一定严格{m,n}形式，逗号左右不能有空格
            量词出现在字符组中，不是元字符     [?*+{] 匹配的是字符自身
        默认匹配是贪婪的：
            <a>.*</a>   目的是处理<a>first</a><a>second</a>，获取到<a>first</a>和<a>second</a>
            但默认情况会只得到1个结果，匹配了所有内容。
            这是因为.*可以匹配到第一个<a>到最后一个</a>中间的所有字符，只要能往后匹配，它就会贪婪的往后匹配。
            如果希望碰到第一个匹配时就停止，应该使用
        懒惰量词
            在量词的后面加一个符号 ?
            <a>.*?</a>  就能得到期望的结果。
            所有量词都有对应的懒惰形式，比如：
                x?? x*? x+? x{m,n}?
    */}

    void _4分组(){/*
        ()  表示一个分组
            分组可以嵌套，如 a(de(fg))
            分组默认都有一个编号，按照括号出现的顺序，从1开始，从左到右一次递增
            例：
                a(bc)((de)(fg))    字符串abcdefg匹配这个表达式
                    分组1 bc
                    分组2 defg
                    分组3 de
                    分组4 fg
                    分组0 是一个特殊分组，内容是整个匹配的字符串，这里就是abcdefg
        a(bc))+d            可以对分组使用量词，表示分组出现次数，这里表示bc出现一次或多次
        []                  表示匹配其中的一个字符
        (http|ftp|file)     表示匹配其中的一个子表达式。其中，|在分组之外只是一个普通字符
        回溯引用        可以使用斜杠\加分组编号引用之前匹配的分组
            例：
                <(\w+)>(.*)</\1>    \1匹配之前的第一个分组(\w+)，这个表达式可以匹配类似如下字符串：
                                    <title>bc</title>
       (?<name>X)   给分组命名
       \k<name>     引用分组
            上面的例子可以写为
                <(?<tag>\w+)(.*)</\k<tag>>

            默认分组都称为捕获分组，即分组匹配的内容被捕获了，可以在后续被引用。实现捕获分组有一定的成本，
            为了提高性能，如果分组后续不需要被应用，可以改为
       (?:...)     非捕获分组。比如：(?:abc|def)
    */}

    void _5特殊边界匹配(){/*
        ^   $   \A  \Z  \z  \b
        ^   匹配整个字符从的开始  ^abc 表示整个字符串必须以abc开始
            在组中表示排除；在字符组外，匹配开始。 ^[^abc]表示一个不是a、b、c的字符开始。
        $   匹配整个字符串的结束。
            不过，如果整个字符串以换行符结束，$匹配的是换行符之前的边界。
            比如表达式abc$，表示整个表达式以abc结束，或者以abc\r\n或abc\n结束。

        (?m)    multi-line，表示多行匹配模式，如：(?m)^abc$ 对abc\nabc\r\n有两个匹配。

        单行模式    影响的字符是 . 的匹配规则， 使得 . 可以匹配到换行符
        多行模式    影响的是 ^ 和 $ 的匹配规则，是得他们可以匹配寒的开始和结束，两个模式可以一起使用。

        \A           与 ^ 类似，但不管什么模式，它匹配的总是整个字符串的开始边界
        \Z、\z       于 $ 类似，但不管什么模式，它匹配的总是整个字符串的结束边界
                     两者的区别： 如果字符串以换行符结束，\Z于$一样，匹配的是换行符之前的边界，而\z匹配的总是结束边界。
                     在进行输入验证的时候，为确保输入最后没有多余的换行符，可以使用\z进行匹配。

        \b           匹配的是单词边界。 如\bcat\b，匹配的是完整的单词cat，它不能匹配category
                     边界： 必须一边是字符(\w，java中还包括中文)，而另一边不是。否则是不存在边界的
    */}

    void _6环视边界匹配(){/*
        (?=...)     肯定顺序环视      abc(?=def)  右边必须以def结尾
        (?!...)     否定顺序环视      s(?!ing)    右边不能以ing结尾
        (?<=...)     肯定逆序环视     (?<=\s)abc  左边必须是空白字符
        (?<!...)     否定逆序环视     (?<!\w)cat  左边不能是单词字符

        当顺序环视位于左边，或者逆序环视位于右边时， 则是对边界的修饰。
    */}

    void _7转义与匹配模式(){/*
        把普通字符转义，使其具备特殊含义    \t \n \d \w \b \A
        把元字符转义，使其变为普通字符      \. \* \? \( \\

        \Q(.*+)\E        \Q和\E之间的所有字符都会被视为普通字符。

        \   Java中，因为\也是字符串语法中的元字符，
            所以要表达转义用 \\
            要匹配斜杠本身要用 \\\\

    */}
}
