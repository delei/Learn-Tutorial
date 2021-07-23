package cn.delei.ddd.example.register;

/**
 * 用户Entity
 *
 * @author deleiguo
 */
public class UserEntity {
    /**
     * 号码 VO
     */
    private MobileVO mobileVO;
    /**
     * 昵称 VO
     */
    private AccountVO accountVO;

    public MobileVO getMobileVO() {
        return mobileVO;
    }

    public void setMobileVO(MobileVO mobileVO) {
        this.mobileVO = mobileVO;
    }

    public AccountVO getAccountVO() {
        return accountVO;
    }

    public void setAccountVO(AccountVO accountVO) {
        this.accountVO = accountVO;
    }

    @Override
    public String toString() {
        return "User{" +
                "mobile=" + mobileVO +
                ", account=" + accountVO +
                '}';
    }
}
