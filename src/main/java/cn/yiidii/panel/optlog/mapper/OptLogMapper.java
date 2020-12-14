package cn.yiidii.panel.optlog.mapper;

import cn.yiidii.panel.pojo.OptLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OptLogMapper {

    List<OptLog> queryLogWithoutUid();

    List<OptLog> queryLogByUid(Integer uid);

    Integer insert(OptLog optLog);

    //Integer update(LoveBook loveBook);

    //Integer delete(Integer quartzId);
}
