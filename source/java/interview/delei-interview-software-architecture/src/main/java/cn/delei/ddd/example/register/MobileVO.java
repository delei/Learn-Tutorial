package cn.delei.ddd.example.register;

import java.util.Arrays;

/**
 * 手机号码 VO (Domain Primitive,DP)
 *
 * @author deleiguo
 */
public class MobileVO {

    /**
     * 号码(Immutable)
     */
    private final String number;

    public MobileVO(String number) {
        if (number == null || number.length() == 0) {
            throw new IllegalArgumentException("number is null");
        }
        if (!isValid(number)) {
            throw new IllegalArgumentException("number formate error");
        }
        this.number = number;
    }

    /**
     * 是否是区号
     *
     * @param prefix 前缀
     * @return boolean 是否
     */
    public static boolean isAreaCode(String prefix) {
        String[] areas = new String[]{"0571", "021", "010"};
        return Arrays.asList(areas).contains(prefix);
    }

    /**
     * 验证数据有效
     *
     * @param number 号码
     * @return boolean 是否
     */
    public static boolean isValid(String number) {
        String pattern = "^0?[1-9]{2,3}-?\\d{8}$";
        return number.matches(pattern);
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "Mobile{" +
                "number='" + number + '\'' +
                '}';
    }
}
