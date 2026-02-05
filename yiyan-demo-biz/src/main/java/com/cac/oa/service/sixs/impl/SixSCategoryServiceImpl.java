package com.cac.oa.service.sixs.impl;

import com.cac.oa.dao.sixs.SixSCategoryMapper;
import com.cac.oa.entity.sixs.SixSCategoryEntity;
import com.cac.oa.service.sixs.SixSCategoryService;
import com.cac.oa.vo.sixs.SixSCategoryQuery;
import com.cac.yiyan.common.exception.service.ServiceException;
import com.cac.yiyan.common.page.PageData;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 6S 标准化分类 Service 实现类
 *
 * @author
 * @since
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SixSCategoryServiceImpl extends ServiceImpl<SixSCategoryMapper, SixSCategoryEntity> implements SixSCategoryService {

    private final SixSCategoryMapper sixSCategoryMapper;

    @Override
    public List<SixSCategoryEntity> getSixSCategoryList(SixSCategoryQuery query) {
        try {
            return sixSCategoryMapper.selectSixSCategoryList(query);
        } catch (Exception e) {
            log.error("执行[selectSixSCategoryList]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public PageData<SixSCategoryEntity> getSixSCategoryPage(SixSCategoryQuery query) {
        try {
            return sixSCategoryMapper.selectSixSCategoryPage(query);
        } catch (Exception e) {
            log.error("执行[selectSixSCategoryPage]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public SixSCategoryEntity getSixSCategoryById(Long id) {
        try {
            return sixSCategoryMapper.selectById(id);
        } catch (Exception e) {
            log.error("执行[getSixSCategoryById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<SixSCategoryEntity> getSixSCategoryListByIds(Collection<Long> ids) {
        try {
            return sixSCategoryMapper.selectBatchIds(ids);
        } catch (Exception e) {
            log.error("执行[getSixSCategoryListByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveSixSCategory(SixSCategoryEntity entity) {
        try {
            if (entity.getDefaultScore() == null) {
                entity.setDefaultScore(5);
            }
            if (entity.getSort() == null) {
                entity.setSort(0);
            }
            if (entity.getStatus() == null) {
                entity.setStatus(1);
            }
            sixSCategoryMapper.insert(entity);
        } catch (Exception e) {
            log.error("执行[saveSixSCategory]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
        return entity.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSixSCategory(SixSCategoryEntity entity) {
        validateSixSCategoryExists(entity.getId());
        try {
            return updateById(entity);
        } catch (Exception e) {
            log.error("执行[updateSixSCategory]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSixSCategoryById(Long id) {
        validateSixSCategoryExists(id);
        try {
            return removeById(id);
        } catch (Exception e) {
            log.error("执行[deleteSixSCategoryById]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSixSCategoryByIds(Collection<Long> ids) {
        try {
            return removeByIds(ids);
        } catch (Exception e) {
            log.error("执行[deleteSixSCategoryByIds]方法发生异常:{}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private SixSCategoryEntity validateSixSCategoryExists(Long id) {
        SixSCategoryEntity exists = sixSCategoryMapper.selectById(id);
        if (exists == null) {
            throw new ServiceException("数据失效,请刷新");
        }
        return exists;
    }
}
