package com.cac.oa.service.documents.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cac.oa.dao.documents.DocumentCategoryMapper;
import com.cac.oa.entity.documents.DocumentCategoryEntity;
import com.cac.oa.service.documents.DocumentCategoryService;
import com.cac.oa.vo.documents.DocumentCategoryTreeVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 文档分类 Service 实现类
 */
@Service
public class DocumentCategoryServiceImpl extends ServiceImpl<DocumentCategoryMapper, DocumentCategoryEntity> implements DocumentCategoryService {

    @Override
    public List<DocumentCategoryTreeVO> getCategoryTree() {
        // 1. 查询所有未删除分类
        List<DocumentCategoryEntity> allCategories = this.list();

        // 2. 转换为 VO
        List<DocumentCategoryTreeVO> allVOs = new ArrayList<>();
        for (DocumentCategoryEntity entity : allCategories) {
            DocumentCategoryTreeVO vo = new DocumentCategoryTreeVO();
            BeanUtils.copyProperties(entity, vo);
            allVOs.add(vo);
        }

        // 3. 构建树形结构
        Map<Long, List<DocumentCategoryTreeVO>> parentMap = allVOs.stream()
                .collect(Collectors.groupingBy(DocumentCategoryTreeVO::getParentId));

        List<DocumentCategoryTreeVO> rootNodes = parentMap.getOrDefault(0L, new ArrayList<>());
        buildTree(rootNodes, parentMap);

        return rootNodes;
    }

    private void buildTree(List<DocumentCategoryTreeVO> currentNodes, Map<Long, List<DocumentCategoryTreeVO>> parentMap) {
        if (currentNodes == null || currentNodes.isEmpty()) {
            return;
        }
        for (DocumentCategoryTreeVO node : currentNodes) {
            List<DocumentCategoryTreeVO> children = parentMap.get(node.getId());
            if (children != null && !children.isEmpty()) {
                // 根据 sort 排序
                children.sort((a, b) -> {
                    int sortA = a.getSort() == null ? 0 : a.getSort();
                    int sortB = b.getSort() == null ? 0 : b.getSort();
                    return Integer.compare(sortA, sortB);
                });
                node.setChildren(children);
                buildTree(children, parentMap);
            }
        }
    }
}
