package com.cyrus.final_job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {

    /**
     * checkInJob
     *
     * @return
     */
    @Bean
    MethodInvokingJobDetailFactoryBean checkInJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("checkInJob");
        bean.setTargetMethod("buildCheckIn");
        return bean;
    }

    /**
     * vacationJob
     *
     * @return
     */
    @Bean
    MethodInvokingJobDetailFactoryBean vacationJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
        bean.setTargetBeanName("vacationJob");
        bean.setTargetMethod("buildHoliday");
        return bean;
    }

    /**
     * checkInJob cron 表达式
     *
     * @return
     */
    @Bean
    CronTriggerFactoryBean checkInJobCron() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(checkInJobDetailFactoryBean().getObject());
        bean.setCronExpression("0 0 1 * * ? *"); //每天凌晨一点执行
//        bean.setCronExpression("* * * * * ?"); //每秒
//        bean.setCronExpression("0 48 09 * * ? *");
        return bean;
    }

    /**
     * vacationJob cron 表达式
     *
     * @return
     */
    @Bean
    CronTriggerFactoryBean vacationJobCron() {
        CronTriggerFactoryBean bean = new CronTriggerFactoryBean();
        bean.setJobDetail(vacationJobDetailFactoryBean().getObject());
        bean.setCronExpression("0 0 1 1 1 ? *"); //每年一月01点运行
        return bean;
    }


    // 添加触发器
    @Bean
    SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setTriggers(checkInJobCron().getObject(),
                vacationJobCron().getObject());
        return bean;
    }
}
