package cn.yiidii.panel.quratz.controller;

import cn.yiidii.panel.quratz.util.JobUtil;
import cn.yiidii.panel.quratz.entity.QuartzTask;
import cn.yiidii.panel.quratz.service.impl.QuartzService;
import cn.yiidii.panel.core.vo.ResponseWarper;
import cn.yiidii.panel.shiro.entity.User;
import cn.yiidii.panel.ex.ServiceException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
@Slf4j
public class JobController {
    @Autowired
    private JobUtil jobUtil;
    @Autowired
    private QuartzService quartzService;

    @GetMapping(value = "/state")
    public String getjobstate(@RequestParam(name = "id", required = true) Integer id) throws Exception {
        QuartzTask quartzTask = quartzService.queryQuartTaskzById(id);
        String state = jobUtil.getJobState(quartzTask.getJobName(), quartzTask.getJobGroup());
        return ResponseWarper.success(state, false).toJSON();
    }

    @GetMapping(value = "/all")
    public String getJobs() throws Exception {
        User currUser = (User) SecurityUtils.getSubject().getPrincipal();
        List<QuartzTask> tasks = quartzService.queryALlQuartzTaskByUid(currUser.getId());
        JSONArray ja = JSONArray.parseArray(JSON.toJSONString(tasks));
        for (Object o : ja) {
            JSONObject jo = (JSONObject) o;
            String jobName = jo.getString("jobName");
            String jobNGroup = jo.getString("jobNGroup");
            jo.put("state", jobUtil.getJobState(jobName, jobNGroup));
        }
        return ResponseWarper.success(ja).toJSON();
    }

    //添加一个job
    @RequestMapping(value = "/addJob", method = RequestMethod.POST)
    public String addjob(QuartzTask quartzTask) throws Exception {
        quartzService.insertQuartzTask(quartzTask);
        log.debug("quartzTask: " + JSONObject.toJSON(quartzTask));
        String result = jobUtil.addJob(quartzTask);
        if(!StringUtils.equals("success", result)){
            throw new ServiceException(result);
        }
        return ResponseWarper.success("添加成功", true).toJSON();
    }

    //暂停job
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    public String pausejob(@RequestBody Integer[] quartzIds) throws Exception {
        QuartzTask quartzTask = null;
        if (quartzIds.length > 0) {
            for (Integer quartzId : quartzIds) {
                quartzTask = quartzService.queryQuartTaskzById(quartzId);
                jobUtil.pauseJob(quartzTask.getJobName(), quartzTask.getJobGroup());
            }
            return ResponseWarper.success("success pauseJob", true).toJSON();
        } else {
            return ResponseWarper.serviceError("fail pauseJob").toJSON();
        }
    }

    //恢复job
    @RequestMapping(value = "/resumeJob", method = RequestMethod.POST)
    public String resumejob(@RequestBody Integer[] quartzIds) throws Exception {
        QuartzTask quartzTask = null;
        if (quartzIds.length > 0) {
            for (Integer quartzId : quartzIds) {
                quartzTask = quartzService.queryQuartTaskzById(quartzId);
                jobUtil.resumeJob(quartzTask.getJobName(), quartzTask.getJobGroup());
            }
            return ResponseWarper.success("success resumeJob", true).toJSON();
        } else {
            return ResponseWarper.serviceError("fail resumeJob").toJSON();
        }
    }


    //删除job
    @RequestMapping(value = "/deletJob", method = RequestMethod.DELETE)
    public String deletjob(@RequestBody Integer[] quartzIds) throws Exception {
        QuartzTask quartzTask = null;
        for (Integer quartzId : quartzIds) {
            quartzTask = quartzService.queryQuartTaskzById(quartzId);
            String ret = jobUtil.deleteJob(quartzTask);
            if ("success".equals(ret)) {
                quartzService.deleteQuartzTaskById(quartzId);
            }
        }
        return ResponseWarper.success("success deleteJob", true).toJSON();
    }

    //修改
    @RequestMapping(value = "/updateJob", method = RequestMethod.PUT)
    public String modifyJob(@RequestBody QuartzTask quartzTask) throws Exception {
        String ret = jobUtil.modifyJob(quartzTask);
        if ("success".equals(ret)) {
            quartzService.updateQuartzTask(quartzTask);
            return ResponseWarper.success("success updateJob", true).toJSON();
        } else {
            return ResponseWarper.serviceError("fail updateJob").toJSON();
        }
    }

    //暂停所有
    @RequestMapping(value = "/pauseAll", method = RequestMethod.GET)
    public String pauseAllJob() throws Exception {
        jobUtil.pauseAllJob();
        return ResponseWarper.success("success pauseAll", true).toJSON();
    }

    //恢复所有
    @RequestMapping(value = "/repauseAll", method = RequestMethod.GET)
    public String repauseAllJob() throws Exception {
        jobUtil.resumeAllJob();
        return ResponseWarper.success("success repauseAll", true).toJSON();
    }

}