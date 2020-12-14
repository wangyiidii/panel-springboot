package cn.yiidii.panel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @desc 操作日志
 */
@Data
public class OptLog {

    private Integer id;
    private Integer uid;
    private String subject;
    private String ip;
    private String locationInfo;
    private String module;
    private String content;
    private Date createTime;

}
