package com.cac.oa.job.article;

import com.cac.oa.service.article.IArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 文章置顶过期清理任务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleTopTask {

    private final IArticleService articleService;

    /**
     * 每分钟执行一次，检查是否有到期的置顶
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void cleanExpiredTop() {
        log.info("[ArticleTopTask] 开始清理过期置顶文章...");
        try {
            articleService.cleanExpiredTop();
        } catch (Exception e) {
            log.error("[ArticleTopTask] 清理失败", e);
        }
    }
}
