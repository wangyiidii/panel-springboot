package cn.yiidii.panel.lovebook.mapper;

import cn.yiidii.panel.pojo.LoveBook;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yiidii
 * @date 2020/3/26 23:15:27
 * @desc This class is used to ...
 */
@Mapper
public interface LoveBookMapper {



    List<LoveBook> queryAllLoveBook();

    Integer insert(LoveBook loveBook);

    //Integer update(LoveBook loveBook);

    //Integer delete(Integer quartzId);
}
