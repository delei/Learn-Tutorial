package cn.delei.distributed.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserManagerImpl extends UnicastRemoteObject implements UserManager {
    private static final long serialVersionUID = 3737887544119799761L;

    protected UserManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public String getUserInfo() throws RemoteException {
        return UserManagerImpl.class.getName() + ":getUserInfo()";
    }
}
