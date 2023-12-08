package com.example.quartz.controller;

import com.example.common.constants.response.ApiResponse;
import com.example.quartz.pojo.QuartzJobVO;
import com.example.quartz.service.QuartzService;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ToTryEveryThing
 * @date 2023/11/21 10:00
 * @Description
 */
@RestController
@RequestMapping("/admin/quartz")
public class QuartzController {

    @Resource
    QuartzService quartzService;
//添加任务
    @PostMapping("/job/add/")
    @ApiOperation("添加任务")
    public ApiResponse<Void> addJob(@RequestParam("add_JobName") String jobName,
                                    @RequestParam("add_triggerName") String triggerName,
                                    @RequestParam("add_description") String description,
                                    @RequestParam("add_JobClass") String className,
                                    @RequestParam("add_cron") String cron) throws SchedulerException, ClassNotFoundException {

         return quartzService.addJob(jobName,triggerName,description,className,cron);
    }
//获取所有任务
    @GetMapping("/job/list/")
    @ApiOperation("任务列表")
    public ApiResponse<List<QuartzJobVO>> getList() throws SchedulerException {
        return quartzService.getList();
    }
// 开始任务
    @PostMapping("/job/start")
    @ApiOperation("任务开始")
    public ApiResponse<Void> startJob(String name, String group) throws SchedulerException {
        return quartzService.startOneJob(name,group);
    }
//暂停任务
    @PostMapping("/job/pause/")
    @ApiOperation("任务暂停")
    public ApiResponse<Void> pauseJob(String name, String group) throws SchedulerException {
        return quartzService.pauseOneJob(name,group);
    }
//删除任务
    @DeleteMapping("/job/delete/{name}/{group}/")
    @ApiOperation("删除任务")
    public ApiResponse<Void> deleteOneJob(@PathVariable String name, @PathVariable String group) throws SchedulerException {
        return quartzService.deleteOneJob(name,group);
    }
//    修改任务
    @PutMapping("/job/update/")
    @ApiOperation("更新任务")
    public ApiResponse<Void> updateJob(String name, String group, String cron) throws SchedulerException {
        return quartzService.updateJob(name,group,cron);
    }


}
