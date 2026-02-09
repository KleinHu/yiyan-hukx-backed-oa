package com.cac.oa.service.supplies;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.supplies.SuppliesCategoryEntity;

import java.util.List;
import com.cac.oa.vo.supplies.SuppliesCategoryVO;

public interface ISuppliesCategoryService extends IService<SuppliesCategoryEntity> {
    /**
     * 获取分类树
     */
    List<SuppliesCategoryVO> listTree();
}
