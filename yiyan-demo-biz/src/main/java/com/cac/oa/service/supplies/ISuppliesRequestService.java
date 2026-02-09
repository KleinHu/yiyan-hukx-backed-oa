package com.cac.oa.service.supplies;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cac.oa.entity.supplies.SuppliesRequestEntity;
import com.cac.oa.vo.supplies.*;
import com.cac.yiyan.common.page.PageData;

/**
 * 领用申请服务接口
 */
public interface ISuppliesRequestService extends IService<SuppliesRequestEntity> {

    /**
     * 提交申请
     */
    void submitRequest(SuppliesRequestVO vo);

    /**
     * 分页查询
     */
    PageData<SuppliesRequestVO> getPage(SuppliesRequestQuery query);

    /**
     * 审核申请
     */
    void auditRequest(AuditRequest request);

    /**
     * 根据单号查询
     */
    SuppliesRequestVO getByOrderNo(String orderNo);

    /**
     * 获取部门物品领用汇总
     */
    java.util.List<DeptItemConsumptionVO> getDeptItemConsumption(ConsumptionQuery query);

    /**
     * 获取人员领用明细
     */
    java.util.List<UserConsumptionDetailVO> getConsumptionDetails(ConsumptionQuery query, String deptName, Long itemId);
}
