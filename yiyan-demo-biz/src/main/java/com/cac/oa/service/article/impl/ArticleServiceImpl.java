package com.cac.oa.service.article.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.convert.article.ArticleConvert;
import com.cac.oa.dao.article.ArticleMapper;
import com.cac.oa.dao.article.ArticleViewRecordMapper;
import com.cac.oa.dao.article.ArticleLikeRecordMapper;
import com.cac.oa.dao.article.ArticleFavoriteMapper;
import com.cac.oa.entity.article.ArticleEntity;
import com.cac.oa.entity.article.ArticleViewRecordEntity;
import com.cac.oa.entity.article.ArticleLikeRecordEntity;
import com.cac.oa.entity.article.ArticleFavoriteEntity;
import com.cac.oa.service.article.IArticleColumnService;
import com.cac.oa.service.article.IArticleService;
import com.cac.oa.vo.article.ArticleQuery;
import com.cac.oa.vo.article.ArticleVO;
import com.cac.yiyan.common.page.PageData;
import com.cac.yiyan.mybatis.query.LambdaQueryWrapperX;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Calendar;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 文章 Service 实现
 */
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, ArticleEntity> implements IArticleService {

    private final ArticleMapper articleMapper;
    private final ArticleViewRecordMapper viewRecordMapper;
    private final ArticleLikeRecordMapper likeRecordMapper;
    private final ArticleFavoriteMapper favoriteMapper;
    private final IArticleColumnService columnService;
    private final ArticleConvert converter = ArticleConvert.INSTANCE;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ArticleEntity entity) {
        setPublishTimeIfNecessary(entity);
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(ArticleEntity entity) {
        setPublishTimeIfNecessary(entity);
        return super.updateById(entity);
    }

    private void setPublishTimeIfNecessary(ArticleEntity entity) {
        if (entity.getStatus() != null && entity.getStatus() == 2 && entity.getPublishTime() == null) {
            entity.setPublishTime(new Date());
        }
    }

    @Override
    public PageData<ArticleVO> getPage(ArticleQuery query) {
        // 处理专栏ID：如果指定了专栏，则递归获取该专栏及其所有子孙专栏的 ID 列表
        List<Long> columnIds = null;
        if (query.getColumnId() != null) {
            columnIds = columnService.getChildIds(query.getColumnId());
        }

        PageData<ArticleEntity> page = articleMapper.selectPage(query, new LambdaQueryWrapperX<ArticleEntity>()
                .likeIfPresent(ArticleEntity::getTitle, query.getTitle())
                .likeIfPresent(ArticleEntity::getTags, query.getTag())
                .inIfPresent(ArticleEntity::getColumnId, columnIds)
                .eqIfPresent(ArticleEntity::getStatus, query.getStatus())
                .eqIfPresent(ArticleEntity::getAuthorCode, query.getAuthorCode())
                .betweenIfPresent(ArticleEntity::getPublishTime,
                        StringUtils.hasText(query.getBeginTime()) ? query.getBeginTime() : null,
                        StringUtils.hasText(query.getEndTime()) ? query.getEndTime() : null)
                .orderByDesc(ArticleEntity::getIsTop)
                .orderByDesc(query.getHotSort() != null && query.getHotSort() == 1, ArticleEntity::getViewCount)
                .orderByDesc(ArticleEntity::getPublishTime)
                .orderByDesc(ArticleEntity::getCreateTime));
        return converter.convertPage(page);
    }

    @Override
    public PageData<ArticleVO> getAuditPage(ArticleQuery query) {
        // 处理专栏ID
        List<Long> columnIds = null;
        if (query.getColumnId() != null) {
            columnIds = columnService.getChildIds(query.getColumnId());
        }

        PageData<ArticleEntity> page = articleMapper.selectPage(query, new LambdaQueryWrapperX<ArticleEntity>()
                .likeIfPresent(ArticleEntity::getTitle, query.getTitle())
                .inIfPresent(ArticleEntity::getColumnId, columnIds)
                .in(ArticleEntity::getStatus, Arrays.asList(1, 5)) // 强制：仅查询待审核(1)和下线确认中(5)
                .orderByDesc(ArticleEntity::getCreateTime));
        return converter.convertPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitAudit(Long id) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(id);
        entity.setStatus(1); // 1-待审核
        articleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withdraw(Long id) {
        ArticleEntity article = articleMapper.selectById(id);
        if (article == null) return;
        ArticleEntity entity = new ArticleEntity();
        entity.setId(id);
        if (article.getStatus() != null && article.getStatus() == 5) {
            entity.setStatus(2); // 取消下线申请，恢复已发布状态
        } else {
            entity.setStatus(0); // 0-草稿
        }
        articleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long id, boolean pass, String opinion, Integer isTop, Date topExpireTime,
                       Boolean isReleaseTimed, Date releaseTime, Boolean isOfflineTimed, Date offlineTime,
                       Integer isMustRead) {
        ArticleEntity article = articleMapper.selectById(id);
        if (article == null) return;

        ArticleEntity entity = new ArticleEntity();
        entity.setId(id);
        entity.setAuditOpinion(opinion);
        // 管理员审核时可覆盖或维持原必读状态
        entity.setIsMustRead(isMustRead != null ? isMustRead : article.getIsMustRead());

        if (article.getStatus() != null && article.getStatus() == 5) {
            // 是针对下线操作的审批
            if (pass) {
                entity.setStatus(4); // 同意下线 -> 状态变更为已下线
                entity.setIsTop(0);
                entity.setTopExpireTime(null);
                // 手动同意下线，清理定时下线标志
                entity.setIsOfflineTimed(false);
                entity.setOfflineTime(null);
            } else {
                entity.setStatus(2); // 驳回下线申请 -> 状态恢复为已发布
                // 驳回时如果管理员调整了定时下线时间，也可以保留 (可选)
            }
        } else {
            // 针对常规发布的审批
            if (pass) {
                Date now = new Date();

                // 合并参数：优先使用审批时传入的，若未传入则维持原状
                Boolean finalIsOffline = isOfflineTimed != null ? isOfflineTimed : article.getIsOfflineTimed();
                Date finalOfflineTime = offlineTime != null ? offlineTime : article.getOfflineTime();
                Boolean finalIsRelease = isReleaseTimed != null ? isReleaseTimed : article.getIsReleaseTimed();
                Date finalReleaseTime = releaseTime != null ? releaseTime : article.getReleaseTime();

                // 1. 优先检查是否需要立即下线 (熔断：开启了下线且时间已过)
                if (Boolean.TRUE.equals(finalIsOffline) && finalOfflineTime != null && !finalOfflineTime.after(now)) {
                    entity.setStatus(4);
                    if (article.getPublishTime() == null) {
                        entity.setPublishTime(now);
                    }
                    entity.setIsTop(0);
                    entity.setTopExpireTime(null);
                    entity.setIsOfflineTimed(false);
                    entity.setOfflineTime(null);
                    entity.setIsReleaseTimed(false);
                }
                // 2. 检查是否进入定时发布队列 (开启了发布且时间在未来)
                else if (Boolean.TRUE.equals(finalIsRelease) && finalReleaseTime != null && finalReleaseTime.after(now)) {
                    entity.setStatus(6);
                    entity.setIsReleaseTimed(true);
                    entity.setReleaseTime(finalReleaseTime);
                    entity.setIsOfflineTimed(finalIsOffline);
                    entity.setOfflineTime(finalOfflineTime);
                }
                // 3. 立即发布 (默认或明确要求)
                else {
                    entity.setStatus(2);
                    if (article.getPublishTime() == null) {
                        entity.setPublishTime(now);
                    }
                    // 立即上线则取消定时发布，但保留（或更新）定时下线
                    entity.setIsReleaseTimed(false);
                    entity.setIsOfflineTimed(finalIsOffline);
                    entity.setOfflineTime(finalOfflineTime);
                }
                entity.setIsTop(isTop != null ? isTop : 0);
                entity.setTopExpireTime(topExpireTime);
            } else {
                entity.setStatus(3); // 3-已驳回
            }
        }
        articleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyOffline(Long id) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(id);
        entity.setStatus(5); // 5-待下线审核
        articleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void offline(Long id) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(id);
        entity.setStatus(4); // 4-已下线
        entity.setIsTop(0);
        entity.setTopExpireTime(null);
        // 手动下线，清理定时下线设置
        entity.setIsOfflineTimed(false);
        entity.setOfflineTime(null);
        articleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Long id) {
        ArticleEntity article = articleMapper.selectById(id);
        if (article == null) return;

        Date now = new Date();
        ArticleEntity entity = new ArticleEntity();
        entity.setId(id);

        // 同样增加即时下线检测
        if (Boolean.TRUE.equals(article.getIsOfflineTimed()) && article.getOfflineTime() != null && !article.getOfflineTime().after(now)) {
            entity.setStatus(4);
            entity.setIsTop(0);
            entity.setTopExpireTime(null);
            entity.setIsOfflineTimed(false);
            entity.setOfflineTime(null);
            entity.setIsReleaseTimed(false);
        } else {
            entity.setStatus(2);
            if (article.getPublishTime() == null) {
                entity.setPublishTime(now);
            }
            // 手动发布覆盖定时发布设置
            entity.setIsReleaseTimed(false);
            entity.setReleaseTime(null);
        }
        articleMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toggleTop(Long id) {
        ArticleEntity article = articleMapper.selectById(id);
        if (article != null) {
            ArticleEntity updateBody = new ArticleEntity();
            updateBody.setId(id);
            if (article.getIsTop() != null && article.getIsTop() == 1) {
                updateBody.setIsTop(0);
                updateBody.setTopExpireTime(null);
            } else {
                updateBody.setIsTop(1);
            }
            articleMapper.updateById(updateBody);
        }
    }

    @Override
    public ArticleVO getDetail(Long id) {
        ArticleEntity entity = articleMapper.selectById(id);
        return converter.convert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cleanExpiredTop() {
        Date now = new Date();
        LambdaUpdateWrapper<ArticleEntity> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ArticleEntity::getIsTop, 1)
                .isNotNull(ArticleEntity::getTopExpireTime)
                .le(ArticleEntity::getTopExpireTime, now)
                .set(ArticleEntity::getIsTop, 0)
                .set(ArticleEntity::getTopExpireTime, null);
        this.update(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recordView(Long id, String userName, String userCode) {
        // 1. 获取文章并校验状态：只有“已发布”(status=2)的文章才允许计入阅读量
        ArticleEntity article = articleMapper.selectById(id);
        if (article == null || article.getStatus() == null || article.getStatus() != 2) {
            return;
        }

        // 2. 增加总浏览量
        ArticleEntity updateEntity = new ArticleEntity();
        updateEntity.setId(id);
        updateEntity.setViewCount((article.getViewCount() == null ? 0 : article.getViewCount()) + 1);
        articleMapper.updateById(updateEntity);

        // 2. 插入详细阅读流水
        ArticleViewRecordEntity record = new ArticleViewRecordEntity();
        record.setArticleId(id);
        record.setUserName(userName);
        record.setUserCode(userCode);
        record.setViewTime(new Date());
        viewRecordMapper.insert(record);
    }

    @Override
    public List<ArticleViewRecordEntity> getViewRecords(Long id) {
        return viewRecordMapper.selectList(new LambdaQueryWrapper<ArticleViewRecordEntity>()
                .eq(ArticleViewRecordEntity::getArticleId, id)
                .orderByDesc(ArticleViewRecordEntity::getViewTime));
    }

    @Override
    public Map<String, Object> getMyStats(String authorCode) {
        // 计算已发布的数量
        long published = articleMapper.selectCount(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getAuthorCode, authorCode)
                .in(ArticleEntity::getStatus, Arrays.asList(2, 4)));

        // 计算审核中的数量（含首次提交审核 1，和下线审核 5，以及定时发布 6）
        long auditing = articleMapper.selectCount(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getAuthorCode, authorCode)
                .in(ArticleEntity::getStatus, Arrays.asList(1, 5, 6)));

        // 计算草稿与驳回数量
        long draft = articleMapper.selectCount(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getAuthorCode, authorCode)
                .in(ArticleEntity::getStatus, Arrays.asList(0, 3)));

        // 依靠持久层能力聚合同工号所有文章的累计阅读量
        QueryWrapper<ArticleEntity> sumWrapper = new QueryWrapper<>();
        sumWrapper.select("IFNULL(SUM(view_count), 0) AS totalView")
                .eq("author_code", authorCode);
        List<Map<String, Object>> res = articleMapper.selectMaps(sumWrapper);
        long viewCount = 0;
        if (res != null && !res.isEmpty() && res.get(0) != null && res.get(0).get("totalView") != null) {
            viewCount = Long.parseLong(res.get(0).get("totalView").toString());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("published", published);
        map.put("auditing", auditing);
        map.put("draft", draft);
        map.put("viewCount", viewCount);
        return map;
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // 1. 线上总文章
        Long publishedCount = this.count(new LambdaQueryWrapper<ArticleEntity>().eq(ArticleEntity::getStatus, 2));
        publishedCount = publishedCount == null ? 0 : publishedCount;
        stats.put("publishedCount", publishedCount);

        // 计算总文章的本月新增与上月新增对比
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.MONTH, -1);
        Date lastMonth = cal1.getTime();
        cal1.add(Calendar.MONTH, -1);
        Date twoMonthsAgo = cal1.getTime();

        long currentMonthAdded = this.count(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, 2)
                .ge(ArticleEntity::getPublishTime, lastMonth));
        long previousMonthAdded = this.count(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, 2)
                .ge(ArticleEntity::getPublishTime, twoMonthsAgo)
                .lt(ArticleEntity::getPublishTime, lastMonth));
        stats.put("publishedTrend", calculateTrend(currentMonthAdded, previousMonthAdded));

        // 2. 最近一周上线文章数量
        Calendar calWeek = Calendar.getInstance();
        calWeek.add(Calendar.DAY_OF_YEAR, -7);
        Date lastWeek = calWeek.getTime();

        Long recentPublishedCount = this.count(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, 2)
                .ge(ArticleEntity::getPublishTime, lastWeek));
        recentPublishedCount = recentPublishedCount == null ? 0 : recentPublishedCount;
        stats.put("recentPublishedCount", recentPublishedCount);

        // 计算近一周与上一周的对比
        calWeek.add(Calendar.DAY_OF_YEAR, -7);
        Date twoWeeksAgo = calWeek.getTime();
        long previousWeekAdded = this.count(new LambdaQueryWrapper<ArticleEntity>()
                .eq(ArticleEntity::getStatus, 2)
                .ge(ArticleEntity::getPublishTime, twoWeeksAgo)
                .lt(ArticleEntity::getPublishTime, lastWeek));
        stats.put("recentPublishedTrend", calculateTrend(recentPublishedCount, previousWeekAdded));

        // 3. 热门活跃度(取前10名浏览量之和)
        Page<ArticleEntity> page = new Page<>(1, 10);
        this.page(page, new LambdaQueryWrapper<ArticleEntity>()
                .select(ArticleEntity::getViewCount)
                .eq(ArticleEntity::getStatus, 2)
                .orderByDesc(ArticleEntity::getViewCount));
        long hotViewCount = page.getRecords().stream()
                .mapToLong(a -> a.getViewCount() == null ? 0 : a.getViewCount())
                .sum();
        stats.put("hotViewCount", hotViewCount);
        // 热度趋势难以按严格时间段对比，可以用当周与上周整体文章的浏览记录比，暂用当周发布文章的热度与上周发布文章的热度比
        long currentWeekHot = this.list(new LambdaQueryWrapper<ArticleEntity>().eq(ArticleEntity::getStatus, 2).ge(ArticleEntity::getPublishTime, lastWeek))
                .stream().mapToLong(a -> a.getViewCount() == null ? 0 : a.getViewCount()).sum();
        long previousWeekHot = this.list(new LambdaQueryWrapper<ArticleEntity>().eq(ArticleEntity::getStatus, 2).ge(ArticleEntity::getPublishTime, twoWeeksAgo).lt(ArticleEntity::getPublishTime, lastWeek))
                .stream().mapToLong(a -> a.getViewCount() == null ? 0 : a.getViewCount()).sum();
        stats.put("hotViewTrend", calculateTrend(currentWeekHot, previousWeekHot));

        // 4. 活跃专栏数 (有线上文章的专栏数)
        QueryWrapper<ArticleEntity> wrapper = new QueryWrapper<>();
        wrapper.select("count(distinct column_id) as total").eq("status", 2);
        List<Map<String, Object>> mapList = this.listMaps(wrapper);
        long columnCount = 0;
        if (mapList != null && !mapList.isEmpty() && mapList.get(0) != null && mapList.get(0).get("total") != null) {
            columnCount = Long.parseLong(mapList.get(0).get("total").toString());
        }
        stats.put("columnCount", columnCount);
        // 专栏趋势通常平稳，暂返回0或者根据前一个月判断，这里直接用0.0表示稳定
        stats.put("columnTrend", 0.0);

        return stats;
    }

    private double calculateTrend(long current, long previous) {
        if (previous == 0) {
            return current > 0 ? 100.0 : 0.0;
        }
        double trend = (current - previous) * 100.0 / previous;
        return Math.round(trend * 10.0) / 10.0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeArticle(Long id, String userCode) {
        ArticleEntity article = articleMapper.selectById(id);
        if (article == null) return;

        LambdaQueryWrapper<ArticleLikeRecordEntity> queryWrapper = new LambdaQueryWrapper<ArticleLikeRecordEntity>()
                .eq(ArticleLikeRecordEntity::getArticleId, id)
                .eq(ArticleLikeRecordEntity::getUserCode, userCode);
        ArticleLikeRecordEntity record = likeRecordMapper.selectOne(queryWrapper);

        ArticleEntity updateEntity = new ArticleEntity();
        updateEntity.setId(id);

        if (record != null) {
            // 已点赞，取消点赞
            likeRecordMapper.deleteById(record.getId());
            updateEntity.setLikeCount(Math.max(0, (article.getLikeCount() == null ? 1 : article.getLikeCount()) - 1));
        } else {
            // 未点赞，新增点赞
            ArticleLikeRecordEntity newRecord = new ArticleLikeRecordEntity();
            newRecord.setArticleId(id);
            newRecord.setUserCode(userCode);
            likeRecordMapper.insert(newRecord);
            updateEntity.setLikeCount((article.getLikeCount() == null ? 0 : article.getLikeCount()) + 1);
        }
        articleMapper.updateById(updateEntity);
    }

    @Override
    public boolean hasLiked(Long id, String userCode) {
        return likeRecordMapper.selectCount(new LambdaQueryWrapper<ArticleLikeRecordEntity>()
                .eq(ArticleLikeRecordEntity::getArticleId, id)
                .eq(ArticleLikeRecordEntity::getUserCode, userCode)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void favoriteArticle(Long id, String userCode) {
        ArticleEntity article = articleMapper.selectById(id);
        if (article == null) return;

        LambdaQueryWrapper<ArticleFavoriteEntity> queryWrapper = new LambdaQueryWrapper<ArticleFavoriteEntity>()
                .eq(ArticleFavoriteEntity::getArticleId, id)
                .eq(ArticleFavoriteEntity::getUserCode, userCode);
        ArticleFavoriteEntity record = favoriteMapper.selectOne(queryWrapper);

        ArticleEntity updateEntity = new ArticleEntity();
        updateEntity.setId(id);

        if (record != null) {
            // 已收藏，取消收藏
            favoriteMapper.deleteById(record.getId());
            updateEntity.setFavoriteCount(Math.max(0, (article.getFavoriteCount() == null ? 1 : article.getFavoriteCount()) - 1));
        } else {
            // 未收藏，新增收藏
            ArticleFavoriteEntity newRecord = new ArticleFavoriteEntity();
            newRecord.setArticleId(id);
            newRecord.setUserCode(userCode);
            favoriteMapper.insert(newRecord);
            updateEntity.setFavoriteCount((article.getFavoriteCount() == null ? 0 : article.getFavoriteCount()) + 1);
        }
        articleMapper.updateById(updateEntity);
    }

    @Override
    public boolean hasFavorited(Long id, String userCode) {
        return favoriteMapper.selectCount(new LambdaQueryWrapper<ArticleFavoriteEntity>()
                .eq(ArticleFavoriteEntity::getArticleId, id)
                .eq(ArticleFavoriteEntity::getUserCode, userCode)) > 0;
    }

    @Override
    public PageData<ArticleVO> getFavoritePage(ArticleQuery query) {
        // 1. 先从收藏表分页查出文章 ID
        Page<ArticleFavoriteEntity> favoritePage = new Page<>(query.getPageNo(), query.getPageSize());
        favoriteMapper.selectPage(favoritePage, new LambdaQueryWrapper<ArticleFavoriteEntity>()
                .eq(ArticleFavoriteEntity::getUserCode, query.getAuthorCode()) // 用 AuthorCode 传当前用户工号
                .orderByDesc(ArticleFavoriteEntity::getCreateTime));

        if (favoritePage.getRecords().isEmpty()) {
            return new PageData<>(null, 0L);
        }

        List<Long> articleIds = favoritePage.getRecords().stream()
                .map(ArticleFavoriteEntity::getArticleId)
                .collect(Collectors.toList());

        // 2. 根据 ID 列表批量查询文章详情
        List<ArticleEntity> articles = articleMapper.selectBatchIds(articleIds);

        // 保持收藏时间顺序
        Map<Long, ArticleEntity> articleMap = articles.stream()
                .collect(Collectors.toMap(ArticleEntity::getId, a -> a));
        List<ArticleEntity> sortedArticles = articleIds.stream()
                .map(articleMap::get)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());

        PageData<ArticleVO> result = new PageData<>();
        result.setList(converter.convertList(sortedArticles));
        result.setTotal(favoritePage.getTotal());
        return result;
    }
}
