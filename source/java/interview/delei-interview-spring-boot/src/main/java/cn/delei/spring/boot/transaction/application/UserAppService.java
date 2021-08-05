package cn.delei.spring.boot.transaction.application;

import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import cn.delei.spring.boot.transaction.domain.user.service.TxUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
        throw new RuntimeException("==> addRequired Exception");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiredNew(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiredNewException(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
        throw new RuntimeException("==> addRequiredNew Exception");
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addRequiredNested(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void addRequiredNestedException(UserEntity userEntity) {
        txUserDomainService.add(userEntity);
        throw new RuntimeException("==> addRequiredNested Exception");
    }
}
