package cn.yiidii.panel.quratz.util;

import cn.yiidii.panel.quratz.job.LTSign;
import cn.yiidii.panel.quratz.entity.QuartzTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class JobUtil {

    @Autowired
//    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * 新建一个任务
     */
    public String addJob(QuartzTask QuartzTask) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse(QuartzTask.getStartTime());

        if (!CronExpression.isValidExpression(QuartzTask.getCronExpression())) {
            return "Illegal cron expression";   //表达式格式不正确
        }
        JobDetail jobDetail = null;
        //构建job信息
        if (1 == QuartzTask.getType()) {
            Class<Job> clazz = (Class<Job>) Class.forName("cn.yiidii.panel.quratz.job.LTSign");
            jobDetail = JobBuilder.newJob(LTSign.class).withIdentity(QuartzTask.getJobName(), QuartzTask.getJobGroup()).build();
        } else if (1 == QuartzTask.getType()) {

        }

        //表达式调度构建器(即任务执行的时间,不立即执行)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzTask.getCronExpression()).withMisfireHandlingInstructionFireAndProceed();

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(QuartzTask.getJobName(), QuartzTask.getJobGroup()).startAt(date)
                .withSchedule(scheduleBuilder).build();

        //传递参数
        if (QuartzTask.getInvokeParam() != null && !"".equals(QuartzTask.getInvokeParam())) {
            trigger.getJobDataMap().put("invokeParam", QuartzTask.getInvokeParam());
        }
        scheduler.scheduleJob(jobDetail, trigger);
        //pauseJob(QuartzTask.getJobName(), QuartzTask.getJobGroup());
        return "success";
    }

    /**
     * 获取Job状态
     *
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return scheduler.getTriggerState(triggerKey).name();
    }

    //暂停所有任务
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    //暂停任务
    public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.pauseJob(jobKey);
            return "success";
        }

    }

    //恢复所有任务
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    // 恢复某个任务
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        } else {
            scheduler.resumeJob(jobKey);
            return "success";
        }
    }

    //删除某个任务
    public String deleteJob(QuartzTask QuartzTask) throws SchedulerException {
        JobKey jobKey = new JobKey(QuartzTask.getJobName(), QuartzTask.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return "jobDetail is null";
        } else if (!scheduler.checkExists(jobKey)) {
            return "jobKey is not exists";
        } else {
            scheduler.deleteJob(jobKey);
            return "success";
        }

    }

    //修改任务
    public String modifyJob(QuartzTask QuartzTask) throws SchedulerException {
        if (!CronExpression.isValidExpression(QuartzTask.getCronExpression())) {
            return "Illegal cron expression";
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(QuartzTask.getJobName(), QuartzTask.getJobGroup());
        JobKey jobKey = new JobKey(QuartzTask.getJobName(), QuartzTask.getJobGroup());
        if (scheduler.checkExists(jobKey) && scheduler.checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //表达式调度构建器,不立即执行
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(QuartzTask.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            //修改参数
            if (!trigger.getJobDataMap().get("invokeParam").equals(QuartzTask.getInvokeParam())) {
                trigger.getJobDataMap().put("invokeParam", QuartzTask.getInvokeParam());
            }
            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
            return "success";
        } else {
            return "job or trigger not exists";
        }

    }

}