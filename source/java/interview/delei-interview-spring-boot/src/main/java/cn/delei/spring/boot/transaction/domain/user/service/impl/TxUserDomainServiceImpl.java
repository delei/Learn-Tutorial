package cn.delei.spring.boot.transaction.domain.user.service.impl;

import cn.delei.spring.boot.transaction.domain.user.entity.UserEntity;
import cn.delei.spring.boot.transaction.domain.user.repository.UserRepository;
import cn.delei.spring.boot.transaction.domain.user.service.TxUserDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service("txUserDomainServiceImpl")
public class TxUserDomainServiceImpl implements TxUserDomainService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void add(UserEntity entity) {
        userRepository.add(entity);
    }

    @Override
    public void update(UserEntity entity) {
        userRepository.update(entity);
    }

    @Override
    public UserEntity selectByPrimaryKey(@NotNull Long key) {
        return userRepository.selectByPrimaryKey(key);
    }

    @Override
    public void deleteByPrimaryKey(@NotNull Long key) {
        userRepository.deleteByPrimaryKey(key);
    }
}
