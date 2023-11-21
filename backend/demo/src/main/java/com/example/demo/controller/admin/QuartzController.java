package com.example.demo.controller.admin;

import com.example.demo.controller.common.ApiResponse;
import com.example.demo.pojo.QuartzJobVO;
import com.example.demo.service.impl.admin.QuartzService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ToTryEveryThing
 * @date 2023/11/21 10:00
 * @Description
 */
@RestController
public class QuartzController {

    @Resource
    QuartzService quartzService;
//添加任务
    @PostMapping("/admin/quartz/job/add/")
    public ApiResponse<Void> addJob(@RequestParam("add_JobName") String jobName,
                                    @RequestParam("add_triggerName") String triggerName,
                                    @RequestParam("add_description") String description,
                                    @RequestParam("add_JobClass") String className,
                                    @RequestParam("add_cron") String cron) throws SchedulerException, ClassNotFoundException {

         return quartzService.addJob(jobName,triggerName,description,className,cron);
    }
//获取所有任务
    @GetMapping("/admin/quartz/job/list/")
    public ApiResponse<List<QuartzJobVO>> getList() throws SchedulerException {
        return quartzService.getList();
    }
// 开始任务
    @PostMapping("/admin/quartz/job/start")
    public ApiResponse<Void> startJob(String name, String group) throws SchedulerException {
        return quartzService.startOneJob(name,group);
    }
//暂停任务
    @PostMapping("/admin/quartz/job/pause/")
    public ApiResponse<Void> pauseJob(String name, String group) throws SchedulerException {
        return quartzService.pauseOneJob(name,group);
    }
//删除任务
    @DeleteMapping("/admin/quartz/job/delete/{name}/{group}/")
    public ApiResponse<Void> deleteOneJob(@PathVariable String name, @PathVariable String group) throws SchedulerException {
        return quartzService.deleteOneJob(name,group);
    }
//    修改任务
    @PutMapping("/admin/quartz/job/update/")
    public ApiResponse<Void> updateJob(String name, String group, String cron) throws SchedulerException {
        return quartzService.updateJob(name,group,cron);
    }


}
