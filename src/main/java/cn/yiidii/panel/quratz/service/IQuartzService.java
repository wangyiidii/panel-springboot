package cn.yiidii.panel.quratz.service;

import cn.yiidii.panel.quratz.entity.QuartzTask;

import java.util.List;

public interface IQuartzService {
    Integer insertQuartzTask(QuartzTask quartzTask);

    QuartzTask queryQuartTaskzById(Integer id);

    List<QuartzTask> queryALlQuartzTaskByUid(Integer uid);

    Integer updateQuartzTask(QuartzTask quartzTask);

    Integer deleteQuartzTaskById(Integer id);
}
