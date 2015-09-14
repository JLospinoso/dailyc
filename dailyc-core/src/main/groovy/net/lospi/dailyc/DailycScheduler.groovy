package net.lospi.dailyc

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Slf4j
@Configuration
class DailycScheduler {
    @Autowired
    DailycManager manager

    @Scheduled(cron = '${heartbeatCron:0 * * * * *}')
    void update() {
        log.info("[ ] Heartbeat")
    }

    @Scheduled(cron = '${batchCron:0 7 * * * MON-FRI}')
    void batch() {
        log.info("[ ] Batch triggered.")
        manager.batch()
        log.info("[+] Batch complete.")
    }

    @Scheduled(cron = '${sendCron:0 7 * * * MON-FRI}')
    void sendDailyc() {
        log.info("[ ] Message send triggered.")
        manager.run()
        log.info("[+] Message send complete.")
    }
}
