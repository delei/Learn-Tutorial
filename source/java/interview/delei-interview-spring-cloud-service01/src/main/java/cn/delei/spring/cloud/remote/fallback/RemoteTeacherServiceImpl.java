package cn.delei.spring.cloud.remote.fallback;

import cn.delei.core.R;
import cn.delei.spring.cloud.remote.RemoteTeacherService;
import cn.delei.spring.cloud.remote.model.RemoteTeacher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemoteTeacherServiceImpl implements RemoteTeacherService {

    private Throwable cause;

    @Override
    public R<RemoteTeacher> selectSingle() {
        // UnitedLogger.error("[DataDriver] --- [Remote Error] 获取人员列表数据失败，error：{}", cause);
        System.err.printf("[Remote Error] 获取人员列表数据失败：\n %s \n", cause);
        return null;
    }

    @Override
    public R<List<RemoteTeacher>> teacherList() {
        return null;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
