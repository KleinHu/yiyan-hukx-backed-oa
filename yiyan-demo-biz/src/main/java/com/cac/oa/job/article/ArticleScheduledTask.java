package com.cac.oa.job.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cac.oa.dao.article.ArticleMapper;
import com.cac.oa.entity.article.ArticleEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 文章定时任务
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleScheduledTask {

    private final ArticleMapper articleMapper;

    /**
     * 每分钟扫描一次：处理定时上线与定时下线
     */
    @Scheduled(cron = "0 * * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void processTimedArticles() {
        Date now = new Date();

        // 1. 处理定时上线 (仅处理状态为 6 且开启了定时发布的文章)
        List<ArticleEntity> toPublish = articleMapper.selectList(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, 6)
                .eq(ArticleEntity::getIsReleaseTimed, true)
                .le(ArticleEntity::getReleaseTime, now));
        
        if (!toPublish.isEmpty()) {
            for (ArticleEntity article : toPublish) {
                log.info("定时任务：文章[{}]已到达发布时间", article.getTitle());
                ArticleEntity update = new ArticleEntity();
                update.setId(article.getId());
                
                // 检查是否在发布时刻就已经到了下线时间
                if (Boolean.TRUE.equals(article.getIsOfflineTimed()) && article.getOfflineTime() != null && !article.getOfflineTime().after(now)) {
                    log.info("定时任务：文章[{}]发布时刻已过下线时间，直接转为下线状态", article.getTitle());
                    update.setStatus(4);
                } else {
                    update.setStatus(2);
                    if (article.getPublishTime() == null) {
                        update.setPublishTime(now);
                    }
                }
                articleMapper.updateById(update);
            }
        }

        // 2. 处理定时下线 (仅处理状态为 2 且开启了定时下线的文章)
        List<ArticleEntity> toOffline = articleMapper.selectList(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, 2)
                .eq(ArticleEntity::getIsOfflineTimed, true)
                .le(ArticleEntity::getOfflineTime, now));
        
        if (!toOffline.isEmpty()) {
            for (ArticleEntity article : toOffline) {
                log.info("定时任务：文章[{}]已到达下线时间，执行下线", article.getTitle());
                ArticleEntity update = new ArticleEntity();
                update.setId(article.getId());
                update.setStatus(4);
                update.setIsTop(0);
                update.setTopExpireTime(null);
                articleMapper.updateById(update);
            }
        }
    }
}
