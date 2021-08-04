package cn.delei.spring.boot.transaction.domain.user.service;

import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import cn.delei.spring.boot.transaction.infrastructure.TxBaseService;

public interface TxUserDomainService extends TxBaseService<UserEntity> {
}
