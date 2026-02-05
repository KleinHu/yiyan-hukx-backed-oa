package com.cac.oa.service.sixs.impl;

import com.cac.oa.dao.sixs.SixSAccountMapper;
import com.cac.oa.entity.sixs.SixSAccountEntity;
import com.cac.oa.service.sixs.SixSAccountService;
import com.cac.oa.vo.sixs.SixSAccountQuery;
import com.cac.oa.vo.sixs.SixSAccountStatisticsVO;
import com.cac.oa.vo.sixs.SixSAccountTrendVO;
import com.cac.oa.dao.sixs.SixSCheckRecordMapper;
import com.cac.oa.entity.sixs.SixSCheckRecordEntity;
import com.cac.yiyan.common.exception.service.ServiceException;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 6S 积分台账 Service 实现类
 *
 * @author
 * @since
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SixSAccountServiceImpl extends ServiceImpl<SixSAccountMapper, SixSAccountEntity> implements SixSAccountService {

    private final SixSAccountMapper sixSAccountMapper;
    private final SixSCheckRecordMapper sixSCheckRecordMapper;

    @Override
    public SixSAccountStatisticsVO getStatistics(Integer year) {
        if (year == null) {
            year = Year.now().getValue();
        }
        
        // 员工总数
        Long totalCount = sixSAccountMapper.selectCount(new LambdaQueryWrapper<SixSAccountEntity>()
                .eq(SixSAccountEntity::getYear, year));

        // 优秀人数 (>=100)
        Long excellentCount = sixSAccountMapper.selectCount(new LambdaQueryWrapper<SixSAccountEntity>()
                .eq(SixSAccountEntity::getYear, year)
                .ge(SixSAccountEntity::getTotalScore, 100));

        // 警告人数 (85-99)
        Long warningCount = sixSAccountMapper.selectCount(new LambdaQueryWrapper<SixSAccountEntity>()
                .eq(SixSAccountEntity::getYear, year)
                .ge(SixSAccountEntity::getTotalScore, 85)
                .lt(SixSAccountEntity::getTotalScore, 100));

        // 严重人数 (<85)
        Long seriousCount = sixSAccountMapper.selectCount(new LambdaQueryWrapper<SixSAccountEntity>()
                .eq(SixSAccountEntity::getYear, year)
                .lt(SixSAccountEntity::getTotalScore, 85));

        return SixSAccountStatisticsVO.builder()
                .totalCount(totalCount)
                .excellentCount(excellentCount)
                .warningCount(warningCount)
                .seriousCount(seriousCount)
                .build();
    }

    @Override
    public List<SixSAccountTrendVO> getTrend(Long accountId) {
        // 获取该台账的所有检查记录，按日期升序
        List<SixSCheckRecordEntity> records = sixSCheckRecordMapper.selectList(
                new LambdaQueryWrapper<SixSCheckRecordEntity>()
                        .eq(SixSCheckRecordEntity::getAccountId, accountId)
                        .orderByAsc(SixSCheckRecordEntity::getCheckDate)
                        .orderByAsc(SixSCheckRecordEntity::getId)
        );

        if (records.isEmpty()) {
            return new ArrayList<>();
        }

        // 按日期分组，取每组中 ID 最大的记录（即当天的最终积分）
        Map<java.time.LocalDate, SixSCheckRecordEntity> latestByDate = records.stream()
                .collect(Collectors.toMap(
                        SixSCheckRecordEntity::getCheckDate,
                        r -> r,
                        (r1, r2) -> r1.getId() > r2.getId() ? r1 : r2
                ));

        return latestByDate.entrySet().stream()
                .map(entry -> SixSAccountTrendVO.builder()
                        .checkDate(entry.getKey())
                        .score(entry.getValue().getAfterScore())
                        .build())
                .sorted(Comparator.comparing(SixSAccountTrendVO::getCheckDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<SixSAccountEntity> getSixSAccountList(SixSAccountQuery query) {
        try {
            return sixSAccountMapper.selectSixSAccountList(query);
        } catch (Exception e) {
            log.error("执行[selectSixSAccountList]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageData<SixSAccountEntity> getSixSAccountPage(SixSAccountQuery query) {
        try {
            return sixSAccountMapper.selectSixSAccountPage(query);
        } catch (Exception e) {
            log.error("执行[selectSixSAccountPage]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SixSAccountEntity getSixSAccountById(Long id) {
        try {
            return sixSAccountMapper.selectById(id);
        } catch (Exception e) {
            log.error("执行[getSixSAccountById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<SixSAccountEntity> getSixSAccountListByIds(Collection<Long> ids) {
        try {
            return sixSAccountMapper.selectBatchIds(ids);
        } catch (Exception e) {
            log.error("执行[getSixSAccountListByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveSixSAccount(SixSAccountEntity entity) {
        if (entity.getUserCode() == null || entity.getUserCode().isEmpty()) {
            throw new ServiceException("工号不能为空");
        }
        if (entity.getYear() == null) {
            entity.setYear(Year.now().getValue());
        }
        if (entity.getTotalScore() == null) {
            entity.setTotalScore(100);
        }
        if (entity.getStatus() == null) {
            entity.setStatus(1);
        }

        // 仅判断未删除的记录（is_deleted=0）：同一 userCode + year 已存在则跳过，不覆盖
        SixSAccountEntity existing = sixSAccountMapper.selectOne(
            new LambdaQueryWrapper<SixSAccountEntity>()
                .eq(SixSAccountEntity::getUserCode, entity.getUserCode())
                .eq(SixSAccountEntity::getYear, entity.getYear())
                .apply("is_deleted = 0")
        );
        if (existing != null) {
            log.info("6S积分台账已存在相同工号与年度，跳过保存: userCode={}, year={}", entity.getUserCode(), entity.getYear());
            return existing.getId();
        }

        try {
            sixSAccountMapper.insert(entity);
        } catch (Exception e) {
            log.error("执行[saveSixSAccount]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveSixSAccountBatch(List<SixSAccountEntity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return true;
        }

        // 1. 预处理数据 & 收集查询条件
        List<String> userCodes = new ArrayList<>();
        List<Integer> years = new ArrayList<>();
        
        for (SixSAccountEntity entity : entityList) {
            if (entity.getUserCode() == null || entity.getUserCode().isEmpty()) {
                continue; // Skip invalid
            }
            if (entity.getYear() == null) {
                entity.setYear(Year.now().getValue());
            }
            if (entity.getTotalScore() == null) {
                entity.setTotalScore(100);
            }
            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }
            userCodes.add(entity.getUserCode());
            years.add(entity.getYear());
        }

        if (userCodes.isEmpty()) {
            return true;
        }

        // 2. 查询已存在的记录 (UserCode IN (...) AND Year IN (...))
        // 注意：这种查询可能会查出交叉组合，需要在内存中精确匹配
        List<SixSAccountEntity> existingList = sixSAccountMapper.selectList(
            new LambdaQueryWrapper<SixSAccountEntity>()
                .in(SixSAccountEntity::getUserCode, userCodes)
                .in(SixSAccountEntity::getYear, years)
                .apply("is_deleted = 0")
        );
        
        // 构建已存在 Key 集合: "UserCode_Year"
        List<String> existingKeys = existingList.stream()
            .map(e -> e.getUserCode() + "_" + e.getYear())
            .collect(Collectors.toList());

        // 3. 过滤出不存在的记录进行插入
        List<SixSAccountEntity> insertList = entityList.stream()
            .filter(e -> e.getUserCode() != null && !existingKeys.contains(e.getUserCode() + "_" + e.getYear()))
            .collect(Collectors.toList());

        if (insertList.isEmpty()) {
            log.info("所有6S积分台账记录均已存在，全部跳过");
            return true;
        }

        try {
            return saveBatch(insertList);
        } catch (Exception e) {
            log.error("执行[saveSixSAccountBatch]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSixSAccount(SixSAccountEntity entity) {
        validateSixSAccountExists(entity.getId());
        try {
            return updateById(entity);
        } catch (Exception e) {
            log.error("执行[updateSixSAccount]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSixSAccountById(Long id) {
        validateSixSAccountExists(id);
        try {
            // 关联删除检查记录
            sixSCheckRecordMapper.delete(
                new LambdaQueryWrapper<SixSCheckRecordEntity>()
                    .eq(SixSCheckRecordEntity::getAccountId, id)
            );
            return removeById(id);
        } catch (Exception e) {
            log.error("执行[deleteSixSAccountById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSixSAccountByIds(Collection<Long> ids) {
        try {
            // 关联删除检查记录
            if (ids != null && !ids.isEmpty()) {
                sixSCheckRecordMapper.delete(
                    new LambdaQueryWrapper<SixSCheckRecordEntity>()
                        .in(SixSCheckRecordEntity::getAccountId, ids)
                );
            }
            return removeByIds(ids);
        } catch (Exception e) {
            log.error("执行[deleteSixSAccountByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private SixSAccountEntity validateSixSAccountExists(Long id) {
        SixSAccountEntity exists = sixSAccountMapper.selectById(id);
        if (exists == null) {
            throw new ServiceException("数据失效,请刷新");
        }
        return exists;
    }
}
