package cn.delei.distributed.rmi;

import cn.delei.util.PrintUtil;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI 服务端 Demo
 *
 * @author deleiguo
 */
public class RMIServer {
    private static final int PORT = 8888;
    private static final String HOST = "localhost";
    private static final String SERVICE_NAME = "RUserManager";

    public static void main(String[] args) {
        demo01();
        //demo02();
    }

    private static void demo01() {
        try {
            UserManager userManager = new UserManagerImpl();
            // 本地主机上的远程对象注册表Registry的实例，并指定端口为8888，默认端口为1099
            Registry registry = LocateRegistry.createRegistry(PORT);
            // 把远程对象注册到RMI注册服务器上，并命名为RUserManager
            registry.rebind(SERVICE_NAME, userManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintUtil.printTitle("远程对象注册成功，等待客户端调用");
    }

    private static void demo02() {
        try {
            UserManager userManager = new UserManagerImpl();
            // 本地主机上的远程对象注册表Registry的实例，并指定端口为8888，默认端口为1099
            LocateRegistry.createRegistry(PORT);

            // 把实例注册到远程RMI注册服务上
            // 绑定的URL标准格式为：rmi://host:port/name(其中协议名可以省略）
            // Naming.rebind("//192.168.1.105:1099/Hello",hello);
            Naming.rebind(SERVICE_NAME, userManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintUtil.printTitle("远程对象注册成功，等待客户端调用");
    }
}
