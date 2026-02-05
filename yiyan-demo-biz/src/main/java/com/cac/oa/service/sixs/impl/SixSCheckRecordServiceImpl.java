package com.cac.oa.service.sixs.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cac.oa.dao.sixs.SixSAccountMapper;
import com.cac.oa.dao.sixs.SixSCategoryMapper;
import com.cac.oa.dao.sixs.SixSCheckRecordMapper;
import com.cac.oa.entity.sixs.SixSAccountEntity;
import com.cac.oa.entity.sixs.SixSCategoryEntity;
import com.cac.oa.entity.sixs.SixSCheckRecordEntity;
import com.cac.oa.service.sixs.SixSCheckRecordService;
import com.cac.oa.vo.sixs.SixSCheckRecordQuery;
import com.cac.oa.vo.sixs.SixSCheckRecordVO;
import com.cac.yiyan.common.exception.service.ServiceException;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 6S 检查记录 Service 实现类（创建时自动扣分并更新台账总积分）
 *
 * @author
 * @since
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SixSCheckRecordServiceImpl extends ServiceImpl<SixSCheckRecordMapper, SixSCheckRecordEntity> implements SixSCheckRecordService {

    private final SixSCheckRecordMapper sixSCheckRecordMapper;
    private final SixSAccountMapper sixSAccountMapper;
    private final SixSCategoryMapper sixSCategoryMapper;

    @Override
    public List<SixSCheckRecordEntity> getSixSCheckRecordList(SixSCheckRecordQuery query) {
        try {
            return sixSCheckRecordMapper.selectSixSCheckRecordList(query);
        } catch (Exception e) {
            log.error("执行[selectSixSCheckRecordList]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageData<SixSCheckRecordEntity> getSixSCheckRecordPage(SixSCheckRecordQuery query) {
        try {
            return sixSCheckRecordMapper.selectSixSCheckRecordPage(query);
        } catch (Exception e) {
            log.error("执行[selectSixSCheckRecordPage]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SixSCheckRecordEntity getSixSCheckRecordById(Long id) {
        try {
            return sixSCheckRecordMapper.selectById(id);
        } catch (Exception e) {
            log.error("执行[getSixSCheckRecordById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<SixSCheckRecordEntity> getSixSCheckRecordListByIds(Collection<Long> ids) {
        try {
            return sixSCheckRecordMapper.selectBatchIds(ids);
        } catch (Exception e) {
            log.error("执行[getSixSCheckRecordListByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveSixSCheckRecord(SixSCheckRecordEntity entity) {
        SixSAccountEntity account = sixSAccountMapper.selectById(entity.getAccountId());
        if (account == null) {
            throw new ServiceException("台账不存在");
        }
        SixSCategoryEntity category = sixSCategoryMapper.selectById(entity.getCategoryId());
        if (category == null) {
            throw new ServiceException("分类不存在");
        }

        // 统一分值逻辑：scoreDeducted 存储为“净增量”
        // 加分(1) -> 存储为正数；扣分(2) -> 存储为负数
        int score = entity.getScoreDeducted() != null ? Math.abs(entity.getScoreDeducted()) : (category.getDefaultScore() != null ? Math.abs(category.getDefaultScore()) : 5);
        if (Objects.equals(category.getOperationType(), 2)) {
            score = -score; // 扣分为负
        }
        entity.setScoreDeducted(score);
        
        // 设置初始占位分值
        entity.setBeforeScore(0);
        entity.setAfterScore(0);

        try {
            sixSCheckRecordMapper.insert(entity);
            // 重新计算并刷新积分
            syncAccountScore(entity.getAccountId());
        } catch (Exception e) {
            log.error("执行[saveSixSCheckRecord]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSixSCheckRecord(SixSCheckRecordEntity entity) {
        SixSCheckRecordEntity exists = validateSixSCheckRecordExists(entity.getId());
        SixSCategoryEntity category = sixSCategoryMapper.selectById(entity.getCategoryId());
        if (category == null) {
            throw new ServiceException("分类不存在");
        }

        // 统一分值逻辑
        int score = entity.getScoreDeducted() != null ? Math.abs(entity.getScoreDeducted()) : (category.getDefaultScore() != null ? Math.abs(category.getDefaultScore()) : 5);
        if (Objects.equals(category.getOperationType(), 2)) {
            score = -score; 
        }
        entity.setScoreDeducted(score);

        try {
            boolean updated = updateById(entity);
            if (!updated) {
                throw new ServiceException("更新失败");
            }
            // 刷新原台账积分
            syncAccountScore(exists.getAccountId());
            // 如果更换了台账，刷新新台账积分
            if (!Objects.equals(exists.getAccountId(), entity.getAccountId())) {
                syncAccountScore(entity.getAccountId());
            }
        } catch (Exception e) {
            log.error("执行[updateSixSCheckRecord]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSixSCheckRecordById(Long id) {
        SixSCheckRecordEntity exists = validateSixSCheckRecordExists(id);
        try {
            boolean removed = removeById(id);
            if (!removed) {
                throw new ServiceException("删除失败");
            }
            // 刷新台账积分
            syncAccountScore(exists.getAccountId());
        } catch (Exception e) {
            log.error("执行[deleteSixSCheckRecordById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSixSCheckRecordByIds(Collection<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Boolean.TRUE;
        }
        List<SixSCheckRecordEntity> existsList = sixSCheckRecordMapper.selectBatchIds(ids);
        if (existsList == null || existsList.isEmpty()) {
            return Boolean.TRUE;
        }
        List<Long> accountIds = existsList.stream()
            .map(SixSCheckRecordEntity::getAccountId)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());

        try {
            boolean removed = removeByIds(ids);
            if (!removed) {
                throw new ServiceException("批量删除失败");
            }
            // 刷新所有受影响的台账
            for (Long accountId : accountIds) {
                syncAccountScore(accountId);
            }
        } catch (Exception e) {
            log.error("执行[deleteSixSCheckRecordByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return Boolean.TRUE;
    }

    /**
     * 核心评分同步逻辑：按时间顺序重排所有记录，刷新记录本身的 before/after 分数并同步至台账总分
     */
    private void syncAccountScore(Long accountId) {
        if (accountId == null) return;
        SixSAccountEntity account = sixSAccountMapper.selectById(accountId);
        if (account == null) return;

        // 获取全部有效检查记录，按日期和ID排序以保证分数链逻辑稳定
        List<SixSCheckRecordEntity> records = sixSCheckRecordMapper.selectList(
            new LambdaQueryWrapper<SixSCheckRecordEntity>()
                .eq(SixSCheckRecordEntity::getAccountId, accountId)
                .orderByAsc(SixSCheckRecordEntity::getCheckDate)
                .orderByAsc(SixSCheckRecordEntity::getId)
        );

        int currentScore = 100; // 初始分值为100
        for (SixSCheckRecordEntity record : records) {
            record.setBeforeScore(currentScore);
            int offset = record.getScoreDeducted() != null ? record.getScoreDeducted() : 0;
            currentScore = currentScore + offset; // 直接累加偏移量
            record.setAfterScore(currentScore);
            sixSCheckRecordMapper.updateById(record);
        }

        // 更新台账总分（台账总分通常不设负数，至少为0）
        account.setTotalScore(Math.max(0, currentScore));
        sixSAccountMapper.updateById(account);
    }

    @Override
    public void fillCategoryNames(List<SixSCheckRecordVO> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<Long> categoryIds = list.stream()
            .map(SixSCheckRecordVO::getCategoryId)
            .filter(Objects::nonNull)
            .distinct()
            .collect(Collectors.toList());
        if (categoryIds.isEmpty()) {
            return;
        }
        List<SixSCategoryEntity> categories = sixSCategoryMapper.selectBatchIds(categoryIds);
        Map<Long, String> categoryNameMap = categories.stream()
            .collect(Collectors.toMap(SixSCategoryEntity::getId, SixSCategoryEntity::getName, (a, b) -> a));
        for (SixSCheckRecordVO vo : list) {
            if (vo.getCategoryId() != null) {
                vo.setCategoryName(categoryNameMap.get(vo.getCategoryId()));
            }
        }
    }

    private SixSCheckRecordEntity validateSixSCheckRecordExists(Long id) {
        SixSCheckRecordEntity exists = sixSCheckRecordMapper.selectById(id);
        if (exists == null) {
            throw new ServiceException("数据失效,请刷新");
        }
        return exists;
    }
}
