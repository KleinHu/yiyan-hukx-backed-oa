package com.cac.oa.api.demo;

import com.cac.oa.ApiConstant;
import com.cac.oa.api.demo.dto.DemoDTO;
import com.cac.yiyan.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Copyright (c) 2023 成都飞机工业（集团）有限责任公司
 *
 * @author flame
 * @version 1.0
 * @date 2023/4/17 15:17
 * @description
 * @history
 */
@FeignClient(contextId = "demoApi", value = ApiConstant.SERVICE_NAME)
public interface DemoApi {

    @RequestMapping("/rpc-api/demo/test-rpc")
    public Result<DemoDTO> testRpc();

}
