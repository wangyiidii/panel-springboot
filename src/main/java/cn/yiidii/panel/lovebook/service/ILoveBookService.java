package cn.yiidii.panel.lovebook.service;

import cn.yiidii.panel.pojo.LoveBook;

import java.util.List;

/**
 * @author yiidii
 * @date 2020/3/26 23:23:11
 * @desc This class is used to ...
 */
public interface ILoveBookService {
    List<LoveBook> queryAllLoveBook();

    Integer insert(LoveBook loveBook);
}
