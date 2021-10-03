package cn.delei.distributed.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * RMI 客户端 Demo
 *
 * @author deleiguo
 */
public class RMIClient {

    private static final int PORT = 8888;
    private static final String HOST = "localhost";
    private static final String SERVICE_NAME = "RUserManager";

    public static void main(String[] args) {
        demo01();
        //demo02();
    }

    private static void getUserInfo(UserManager userManager) throws RemoteException {
        System.out.println(userManager.getUserInfo());
    }

    /**
     * 写法02
     */
    private static void demo01() {
        // 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略，下面两种写法都是正确的）
        String rmi = "rmi://" + HOST + ":" + PORT + "/" + SERVICE_NAME;
        try {
            UserManager userManager = (UserManager) Naming.lookup(rmi);
            getUserInfo(userManager);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写法02
     */
    private static void demo02() {
        try {
            // 获取注册表
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);
            // 查找对应的服务
            UserManager userManager = (UserManager) registry.lookup(SERVICE_NAME);
            getUserInfo(userManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
