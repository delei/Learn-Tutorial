package cn.delei.spring.cloud.remote.factory;

import cn.delei.spring.cloud.remote.RemoteTeacherService;
import cn.delei.spring.cloud.remote.fallback.RemoteTeacherServiceImpl;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteTeacherServiceFallbackFactory implements FallbackFactory<RemoteTeacherService> {
    @Override
    public RemoteTeacherService create(Throwable throwable) {
        RemoteTeacherServiceImpl fallback = new RemoteTeacherServiceImpl();
        fallback.setCause(throwable);
        return fallback;
    }
}
