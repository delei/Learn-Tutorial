package cn.delei.java.feature;

import cn.delei.pojo.Person;
import cn.delei.util.PrintUtil;

import java.util.Optional;

/**
 * Optional类 使用
 *
 * @author deleiguo
 */
public class OptionalDemo {
    public static void main(String[] args) {
        String parameter = "deleiguo";
        String nullObj = null;
        String nullStr = "";
        // ==> of
        PrintUtil.printDivider("of");
        Optional<String> parameterOptional = Optional.of(parameter);
        Optional<String> nullObjOptional = Optional.of(nullStr);
//        Optional<String> nullStrOptional = Optional.of(nullObj); // throw NPE

        // ==> ofNullable
        PrintUtil.printDivider("ofNullable");
        parameterOptional = Optional.ofNullable(parameter);
        nullObjOptional = Optional.ofNullable(null);
        Optional<String> nullStrOptional = Optional.ofNullable(nullObj);

        // ==> isPresent
        PrintUtil.printDivider("isPresent");
        if (parameterOptional.isPresent()) {
            //在Optional实例内调用get()返回已存在的值
            System.out.println(parameterOptional.get());
        }

        // ==> orElse
        PrintUtil.printDivider("orElse");
        System.out.println(parameterOptional.orElse("There is nobody"));
        System.out.println(nullStrOptional.orElse("There is  nobody"));
        System.out.println(nullStrOptional.orElseGet(() -> "default person"));

        // ==> map
        PrintUtil.printDivider("map");
        Optional<String> optional = Optional.ofNullable(parameter).map(s -> s.toUpperCase());
        System.out.println(optional.orElse("No value found"));

        // ==> 高频使用方法
        Person delei = new Person("deleiguo", 28, "12345678");
        Person nullPerson = null;
        Optional.ofNullable(delei).map(Person::getName);
    }
}
