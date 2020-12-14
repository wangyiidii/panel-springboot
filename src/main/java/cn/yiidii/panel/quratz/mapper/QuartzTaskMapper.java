package cn.yiidii.panel.quratz.mapper;

import cn.yiidii.panel.quratz.entity.QuartzTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuartzTaskMapper {

    QuartzTask queryQuartzTaskById(Integer quartzId);

    QuartzTask queryQuartzTaskByJobName(String jobName);

    List<QuartzTask> queryALlQuartzTaskByUid(Integer uid);

    Integer insert(QuartzTask quartzTask);

    Integer update(QuartzTask quartzTask);

    Integer delete(Integer quartzId);

}
