package com.example.demo.pojo;

import lombok.Data;

/**
 * @author ToTryEveryThing
 * @date 2023/11/21 9:54
 * @Description
 */
@Data
public class QuartzJobVO {

    private String jobName;
    private String jobGroup;
    private String jobClassName;
    private String description;
    private String triggerName;
    private String triggerGroup;
    private String triggerState;
    private String cronExpression;
    private String timeZone;

}
