package com.cyrus.final_job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    @Bean
    MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("checkInJob");
        bean.setTargetMethod("buildCheckIn");
        return bean;
    }
//
//    // 触发器
//    @Bean
//    SimpleTriggerFactoryBean simpleTriggerFactoryBean() {
//        SimpleTriggerFactoryBean bean = new SimpleTriggerFactoryBean();
//        bean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
//        // 定时任务开始时间
//        bean.setStartTime(new Date());
//        // 定时任务执行间隔
//        bean.setRepeatInterval(1000);
//        // 定时任务执行次数
//        bean.setRepeatCount(0);
//        return bean;
//    }
//
//    // 添加触发器
//    @Bean
//    SchedulerFactoryBean schedulerFactoryBean() {
//        SchedulerFactoryBean bean = new SchedulerFactoryBean();
//        bean.setTriggers(simpleTriggerFactoryBean().getObject());
//        return bean;
//    }

    @Bean
    CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        bean.setCronExpression("0 0 1 * * ? *"); //每天凌晨一点执行
//        bean.setCronExpression("0 29 21 * * ? *");
        return bean;
    }

    // 添加触发器
    @Bean
    SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(cronTriggerFactoryBean().getObject());
        return bean;
    }
}
