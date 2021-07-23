package cn.delei.ddd.example.register;

/**
 * 注册服务实现类
 *
 * @author deleiguo
 */
public class RegistrationServiceImpl implements RegistrationService {
    @Override
    public void register(AccountVO accountVO, MobileVO mobileVO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccountVO(accountVO);
        userEntity.setMobileVO(mobileVO);

        System.out.println(userEntity.toString());

        // 其他逻辑：存入数据库等
    }
}
