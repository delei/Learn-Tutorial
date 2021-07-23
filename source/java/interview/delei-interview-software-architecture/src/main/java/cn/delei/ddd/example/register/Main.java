package cn.delei.ddd.example.register;

/**
 * main 方法测试执行入口
 *
 * @author deleiguo
 */
public class Main {
    public static void main(String[] args) {
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
//        registrationService.register(new Account(""), new Mobile(""));
        registrationService.register(new AccountVO("dg100"), new MobileVO("02167767676"));
    }
}
