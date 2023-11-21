package com.example.demo.service.impl.admin;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.QuartzJobVO;
import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @author ToTryEveryThing
 * @date 2023/11/21 9:54
 * @Description
 */
@Service
@Configuration
public class QuartzService {

    @Resource
    Scheduler scheduler;

    /**
     * 获取任务列表
     */
    public ApiResponse<List<QuartzJobVO>> getList() throws SchedulerException, SchedulerException {
        List<QuartzJobVO> list = new ArrayList<>();
        // 再获取Scheduler下的所有group
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String groupName : triggerGroupNames) {
            // 组装group的匹配，为了模糊获取所有的triggerKey或者jobKey
            GroupMatcher groupMatcher = GroupMatcher.groupEquals(groupName);
            // 获取所有的triggerKey
            Set<TriggerKey> triggerKeySet = scheduler.getTriggerKeys(groupMatcher);
            for (TriggerKey triggerKey : triggerKeySet) {
                // 通过triggerKey在scheduler中获取trigger对象
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                // 获取trigger拥有的Job
                JobKey jobKey = trigger.getJobKey();
                JobDetailImpl jobDetail = (JobDetailImpl) scheduler.getJobDetail(jobKey);
                // 组装页面需要显示的数据
                QuartzJobVO quartzJobsVO = new QuartzJobVO();
                quartzJobsVO.setJobName(jobDetail.getName());
                quartzJobsVO.setJobGroup(groupName);
                quartzJobsVO.setDescription(jobDetail.getDescription());
                // 获取全类名 也可以获取包名
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                quartzJobsVO.setJobClassName(jobClass.getName());
                quartzJobsVO.setTriggerName(triggerKey.getName());
                quartzJobsVO.setTriggerGroup(triggerKey.getGroup());
                // 任务当前状态
                quartzJobsVO.setTriggerState(scheduler.getTriggerState(triggerKey).toString());
                quartzJobsVO.setCronExpression(trigger.getCronExpression());
                quartzJobsVO.setTimeZone(trigger.getTimeZone().getID());
                list.add(quartzJobsVO);
            }
        }
        return ApiResponse.success(list);
    }

    /**
     * 动态添加一个定时任务 类名一定要写全
     * @param className 根据类名得到类 比如：bml.job.job.OneJob
     */
    @Transactional
    public ApiResponse<Void> addJob(String jobName, String triggerName, String description, String className, String cron) throws SchedulerException, ClassNotFoundException {
        JobDetail jobDetail = newJob((Class<? extends Job>) Class.forName(className))
                .withIdentity(jobName, "group1")
                .withDescription(description)
                .build();

        CronTrigger cronTrigger = newTrigger()
                .withIdentity(triggerName, "group1")
                .startNow()
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cron)
                                .withMisfireHandlingInstructionDoNothing())
                .build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
        return ApiResponse.success();
    }

    /**
     *  shutdown(true)表示等待所有正在执行的任务执行完毕后关闭Scheduler
     *  shutdown(false),即shutdown()表示直接关闭Scheduler
     */
    public void shutdown() throws SchedulerException {
        scheduler.shutdown(true);
    }

    /**
     * 02-暂停某个任务
     *
     * @return
     */
    public ApiResponse<Void> pauseOneJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return null;
        }
        scheduler.pauseJob(jobKey);
        return ApiResponse.success();
    }

    /**
     * 03-暂停所有任务
     * 区别在于触发失火指令应用行为.
     * 在standby()后调用start()时,任何在待机状态下出现的失火将被忽略.
     * 当您在pauseAll()之后调用resumeAll()时,将会应用在调度程序暂停时出现的所有失火.
     * standby之后就不能再条用了？
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 04-恢复一个任务
     */
    public ApiResponse<Void> startOneJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return ApiResponse.success();
        }
        scheduler.resumeJob(jobKey);
        return ApiResponse.success();
    }

    /**
     * 05-恢复所有任务
     */
    public void resumeAllJob() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 06-修改某个任务的执行时间
     */
    public ApiResponse<Void> updateJob(String name, String group, String cron) throws SchedulerException {
        Date date = null;
        TriggerKey triggerKey = new TriggerKey(name, group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime = cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)) {
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(cronScheduleBuilder).build();
            date = scheduler.rescheduleJob(triggerKey, trigger);
        }
        return ApiResponse.success();
    }

    /**
     * 07-删除某个任务 删除后该任务将不再执行
     */
    public ApiResponse<Void> deleteOneJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return ApiResponse.success();
        }
        scheduler.deleteJob(jobKey);
        return ApiResponse.success();
    }


}
