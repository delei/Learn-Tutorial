package cn.delei.distributed.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 用户管理远程对象接口
 * <p>Server端和Client都需要依赖该接口类</p>
 *
 * @author deleiguo
 */
public interface UserManager extends Remote {
    /**
     * 获取用户信息
     *
     * @return String 信息数据
     * @throws RemoteException 远程错误
     */
    String getUserInfo() throws RemoteException;
}
