package com.cac.demo.config;

import com.cac.yiyan.excel.core.function.ExcelColumnSelectFunction;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author flame
 * @date 2025/4/7 10:36
 */
@Configuration
public class ExcelConfig {

    @Bean
    public ExcelColumnSelectFunction selectFunction() {
        return new ExcelColumnSelectFunction() {
            @Override
            public String getName() {
                return "test1";
            }

            @Override
            public List<String> getOptions() {
                return Lists.newArrayList("查询", "删除", "更新");
            }
        };
    }

}
