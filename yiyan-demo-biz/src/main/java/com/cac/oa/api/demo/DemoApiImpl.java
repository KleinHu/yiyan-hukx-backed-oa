package com.cac.oa.api.demo;

import com.cac.oa.api.demo.dto.DemoDTO;
import com.cac.yiyan.common.vo.Result;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (c) 2023 成都飞机工业（集团）有限责任公司
 *
 * @author flame
 * @version 1.0
 * @date 2023/5/19 17:26
 * @description
 * @history
 */
@RestController
public class DemoApiImpl implements DemoApi{
    @Override
    public Result<DemoDTO> testRpc() {
        DemoDTO demoDTO = new DemoDTO();
        demoDTO.setTest1("1");
        demoDTO.setTest2(2);
        return Result.ok(demoDTO);
    }
}
