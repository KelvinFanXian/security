package website.fanxian;

import static website.fanxian.utils.KelvinUtils.consumer_genTestMethod;
import static website.fanxian.utils.KelvinUtils.handleString;

/**
 * @author Kelvin范显
 * @createDate 2018年10月16日
 */
public class GrammarCandy {
    public static void genTestMethod(String s, String splitString) {
        handleString(s,splitString,consumer_genTestMethod);
    }
}
