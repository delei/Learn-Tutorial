package cn.delei.spring.cloud.remote;

import cn.delei.core.R;
import cn.delei.spring.cloud.remote.factory.RemoteTeacherServiceFallbackFactory;
import cn.delei.spring.cloud.remote.model.RemoteTeacher;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "interview-service02", path = "/teacher", fallbackFactory =
        RemoteTeacherServiceFallbackFactory.class)
public interface RemoteTeacherService {

    @GetMapping("/{id}")
    R<RemoteTeacher> selectSingle();

    @GetMapping("/list")
    R<List<RemoteTeacher>> teacherList();
}
