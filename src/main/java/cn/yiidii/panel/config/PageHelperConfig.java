package cn.yiidii.panel.config;


import com.github.pagehelper.PageHelper;

import java.util.Properties;

//@Configuration
public class PageHelperConfig {
    // @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();

        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
