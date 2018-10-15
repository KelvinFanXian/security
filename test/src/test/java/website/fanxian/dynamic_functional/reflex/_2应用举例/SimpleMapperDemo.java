package website.fanxian.dynamic_functional.reflex._2应用举例;

/**
 * @author Kelvin范显
 * @createDate 2018年10月15日
 */
public class SimpleMapperDemo {

    static class Student {
        String name;
        int age;
        Double score;

        public Student(String name, int age, Double score) {
            this.name = name;
            this.age = age;
            this.score = score;
        }
//
    }
    public static void main(String[] args) {
        Student zhangsan = new Student("张三", 18, 89d);
                String str = SimpleMapper.toString(zhangsan);
        Student zhangsan2 = (Student) SimpleMapper.fromString(str);
        System.out.println(zhangsan2);
    }
}