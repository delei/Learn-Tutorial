package cn.delei.ddd.example.register;

/**
 * 注册服务接口
 *
 * @author deleiguo
 */
public interface RegistrationService {
    /**
     * 注册
     *
     * @param accountVO 账号
     * @param mobileVO  号码
     */
    void register(AccountVO accountVO, MobileVO mobileVO);
}
