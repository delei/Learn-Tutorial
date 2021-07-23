package cn.delei.ddd.example.register;

import cn.hutool.core.util.StrUtil;

/**
 * 账号 VO (Domain Primitive,DP)
 *
 * @author deleiguo
 */
public class AccountVO {
    /**
     * 昵称(Immutable)
     */
    private final String nicKName;

    public AccountVO(String nicKName) {
        if (!isValid(nicKName)) {
            throw new IllegalArgumentException("nicKName is null");
        }
        this.nicKName = nicKName;
    }

    public static boolean isValid(String nicKName) {
        return StrUtil.isNotBlank(nicKName);
    }

    @Override
    public String toString() {
        return "Account{" +
                "nicKName='" + nicKName + '\'' +
                '}';
    }
}
