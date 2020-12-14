package cn.yiidii.panel.optlog.service.impl;

import cn.yiidii.panel.annotation.PagingQuery;
import cn.yiidii.panel.optlog.mapper.OptLogMapper;
import cn.yiidii.panel.pojo.OptLog;
import cn.yiidii.panel.optlog.service.IOptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptLogService implements IOptLogService {

    @Autowired
    private OptLogMapper optLogMapper;

    @Override
    @PagingQuery
    public Object queryLogWithoutUid(Integer page, Integer pageSize) {
        return optLogMapper.queryLogWithoutUid();
    }

    @Override
    @PagingQuery
    public List<OptLog> queryLogByUid(Integer uid, Integer page, Integer pageSize) {
        return optLogMapper.queryLogByUid(uid);
    }

    @Override
    public Integer insert(OptLog optLog) {
        return optLogMapper.insert(optLog);
    }
}
