package cn.yiidii.panel.optlog.service;

import cn.yiidii.panel.pojo.OptLog;

import java.util.List;

public interface IOptLogService {

    Object queryLogWithoutUid(Integer page, Integer pageSize);

    List<OptLog> queryLogByUid(Integer uid, Integer page, Integer pageSize);

    Integer insert(OptLog optLog);

}
