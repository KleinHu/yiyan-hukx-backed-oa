package com.cac.oa.service.supplies.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.convert.supplies.SuppliesCategoryConvert;
import com.cac.oa.dao.supplies.SuppliesCategoryMapper;
import com.cac.oa.entity.supplies.SuppliesCategoryEntity;
import com.cac.oa.service.supplies.ISuppliesCategoryService;
import com.cac.oa.vo.supplies.SuppliesCategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SuppliesCategoryServiceImpl extends ServiceImpl<SuppliesCategoryMapper, SuppliesCategoryEntity> implements ISuppliesCategoryService {

    private final SuppliesCategoryConvert converter = SuppliesCategoryConvert.INSTANCE;

    @Override
    public List<SuppliesCategoryVO> listTree() {
        List<SuppliesCategoryEntity> list = this.list(new LambdaQueryWrapper<SuppliesCategoryEntity>()
                .orderByDesc(SuppliesCategoryEntity::getSort));
        
        List<SuppliesCategoryVO> voList = converter.convertList(list);

        Map<Long, List<SuppliesCategoryVO>> childrenMap = voList.stream()
                .filter(vo -> vo.getParentId() != null && vo.getParentId() != 0)
                .collect(Collectors.groupingBy(SuppliesCategoryVO::getParentId));

        List<SuppliesCategoryVO> rootList = voList.stream()
                .filter(vo -> vo.getParentId() == null || vo.getParentId() == 0)
                .collect(Collectors.toList());

        rootList.forEach(root -> fillChildren(root, childrenMap));
        
        return rootList;
    }

    private void fillChildren(SuppliesCategoryVO parent, Map<Long, List<SuppliesCategoryVO>> childrenMap) {
        List<SuppliesCategoryVO> children = childrenMap.get(parent.getId());
        if (children != null) {
            parent.setChildren(children);
            children.forEach(child -> fillChildren(child, childrenMap));
        }
    }
}
