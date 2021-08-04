package cn.delei.spring.boot.transaction.application;

import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import cn.delei.spring.boot.transaction.domain.user.service.TxUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userAppService")
public class UserAppService {

    @Autowired
    private TxUserDomainService txUserDomainService;

    @Transactional
    public void addTxDefault(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
        throw new RuntimeException("==> addRequired Exception");
    }

}
