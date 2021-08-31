package cn.delei.spring.starter;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * DeleiServiceImpl
 *
 * @author deleiguo
 */
public class DeleiServiceImpl implements DeleiService {

    @Autowired
    private DeleiProperties deleiProperties;

    @Override
    public void loadDelei() {
        System.out.println("DeleiServiceImpl loadDelei()");
        System.out.println(deleiProperties.toString());
    }
}
