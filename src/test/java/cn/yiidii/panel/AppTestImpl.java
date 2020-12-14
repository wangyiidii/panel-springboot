package cn.yiidii.panel;

import cn.yiidii.panel.lovebook.mapper.LoveBookMapper;
import cn.yiidii.panel.pojo.LoveBook;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Slf4j
public class AppTestImpl extends AppTest {


    @Autowired
    private LoveBookMapper loveBookMapper;


    @Test
    public void insert() throws Exception {
        LoveBook loveBook = new LoveBook();
        loveBook.setId(1);
        loveBook.setAuthor("author");
        loveBook.setContent("bala bala ...");
        loveBook.setCreateTime(new Date());
        loveBook.setHost("192.168.0.1");
        loveBook.setUa("Apple Safari");

        Integer row = loveBookMapper.insert(loveBook);
        log.debug("row: " + row);
        if (row == 1) {
            log.debug("插入成功");
        } else {
            log.debug("插入失败");

        }
    }

}
