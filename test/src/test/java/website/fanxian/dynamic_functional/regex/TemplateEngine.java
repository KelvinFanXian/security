package website.fanxian.dynamic_functional.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 25.3 模板引擎
 * @author Kelvin范显
 * @createDate 2018年10月11日
 */
public class TemplateEngine {
    private static Pattern templatePattern = Pattern.compile("\\{(\\w+)\\}");
    public static String templateEngine(String template, Map<String, Object> params){
        StringBuffer sb = new StringBuffer();
        Matcher matcher = templatePattern.matcher(template);
        while (matcher.find()) {
            String key = matcher.group(1);
            Object value = params.get(key);
            matcher.appendReplacement(sb, value != null ? Matcher.quoteReplacement(value.toString()) : "");
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        String template = "Hi {name}, your code is {code}.";
        Map<String, Object> params = new HashMap();
        params.put("name", "老马");
        params.put("code", "1234");
        System.out.println(templateEngine(template, params));
    }
}
