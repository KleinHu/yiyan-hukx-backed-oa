package com.cac.oa.service.duty;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cac.oa.convert.duty.DutyConvert;
import com.cac.oa.dao.duty.DutyGroupMapper;
import com.cac.oa.entity.duty.DutyGroupEntity;
import com.cac.oa.vo.duty.DutyGroupSaveReqVO;
import com.cac.oa.vo.duty.DutyGroupVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DutyGroupService {

    @Resource
    private DutyGroupMapper dutyGroupMapper;

    /**
     * 获取全部分组(树形)
     */
    public List<DutyGroupVO> getGroupTree() {
        List<DutyGroupEntity> entities = dutyGroupMapper.selectList(new LambdaQueryWrapper<DutyGroupEntity>()
                .eq(DutyGroupEntity::getStatus, 1)
                .orderByAsc(DutyGroupEntity::getName));
        
        List<DutyGroupVO> voList = DutyConvert.INSTANCE.convertList(entities);
        return buildTree(voList);
    }

    private List<DutyGroupVO> buildTree(List<DutyGroupVO> voList) {
        Map<Long, List<DutyGroupVO>> childrenMap = voList.stream()
                .filter(v -> v.getParentId() != null && v.getParentId() != 0L)
                .collect(Collectors.groupingBy(DutyGroupVO::getParentId));
        
        List<DutyGroupVO> roots = voList.stream()
                .filter(v -> v.getParentId() == null || v.getParentId() == 0L)
                .collect(Collectors.toList());
        
        for (DutyGroupVO root : roots) {
            setChildren(root, childrenMap);
        }
        return roots;
    }

    private void setChildren(DutyGroupVO parent, Map<Long, List<DutyGroupVO>> childrenMap) {
        List<DutyGroupVO> children = childrenMap.get(parent.getId());
        if (children != null) {
            parent.setChildren(children);
            for (DutyGroupVO child : children) {
                setChildren(child, childrenMap);
            }
        }
    }

    /**
     * 保存或更新分组
     */
    @Transactional(rollbackFor = Exception.class)
    public Long saveGroup(DutyGroupSaveReqVO vo) {
        DutyGroupEntity entity = DutyConvert.INSTANCE.convert(vo);
        if (entity.getId() != null) {
            dutyGroupMapper.updateById(entity);
        } else {
            dutyGroupMapper.insert(entity);
        }
        return entity.getId();
    }

    /**
     * 删除分组
     */
    public void deleteGroup(Long id) {
        // 其实这里可以增加逻辑判断是否有子级
        dutyGroupMapper.deleteById(id);
    }
}
