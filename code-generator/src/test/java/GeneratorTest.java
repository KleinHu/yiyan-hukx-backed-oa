import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.util.RuntimeUtils;
import com.cac.yiyan.module.codegen.config.GeneratorProperties;
import com.cac.yiyan.module.codegen.entity.DataSourceConfigDO;
import com.cac.yiyan.module.codegen.generator.CodegenEngine;
import com.cac.yiyan.module.codegen.generator.Generator;
import com.cac.yiyan.module.codegen.generator.LocalGenerator;
import com.cac.yiyan.module.codegen.util.GeneratorUtils;
import java.io.IOException;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Copyright (c) 2023 成都飞机工业（集团）有限责任公司
 *
 * @author flame
 * @version 1.0
 * @date 2023/4/6 18:21
 * @description
 * @history
 */
public class GeneratorTest {

    /**
     * 生成代码的路径
     */
//    private static final String filePath = "D:\\test";
    private static final String filePath = "D:\\IdeaProjects\\shangwang";

    /**
     * com.cac.demo 生成代码基础包名
     * yiyan-demo  项目名，会以易研的目录结构生成拼接 biz等作为子maven工程
     * filePath 生成代码放在本地哪个路径
     * mp 模板类型，生成mybatis-plus模板代码
     */
    private static final GeneratorProperties MPSystemProperties =
        new GeneratorProperties("com.cac.demo", "yiyan-demo", filePath, "mp");

    // 代码生成时，连接数据库配置
    private static DataSourceConfigDO getDataSourceConfig() {
        DataSourceConfigDO configDO = new DataSourceConfigDO();
        configDO.setUrl("jdbc:mysql://10.1.1.123:3306/platform?serverTimezone=Asia/Shanghai");
        configDO.setUsername("root");
        configDO.setPassword("cacjszx.132");
        return configDO;
    }

    @Test
    public void testLocalFile() {
        final GeneratorProperties properties = MPSystemProperties;
//        List<String> templates = Lists.newArrayList(CodegenTemplate.VO).stream().map(CodegenTemplate::getName).collect(Collectors.toList());
//        properties.setCodegenTemplates(templates);
        //properties.setCustomBusinessName("permission");
        Generator generator = new LocalGenerator();
        // 包是否需要带上module路径
        CodegenEngine.useModuleName = false;
        // 包是否使用简化类名，移除ClassName前缀
        CodegenEngine.useSimpleClassName = true;
        // 使用mybatis-plus的默认mapper文件路径，关闭时使用mapper文件路径
        CodegenEngine.useMPMapperLocation = true;
        // 指定日期类型
//        CodegenEngine.dateType = DateType.TIME_PACK;
        CodegenEngine.dateType = DateType.ONLY_DATE;
        // 重新指定entity基类
//        CodegenBuilder.refreshBaseEntity(BaseEntityX.class);
        // 是否生成PageOr方法
        properties.setPageOr(false);
        // SOURCE_CONFIG_UF为数据源配置，system_user为表
        // 3种生成代码　标准 树　一对多
//        Map<String, String> codes = generator.generationCodesWithTree(SOURCE_CONFIG_DO, "system_dept", properties, "id", "parentId");
        Map<String, String> codes = generator.generationCodes(getDataSourceConfig(), "system_operate_log", properties);
//        Map<String, String> codes = generator.generationCodesWithSubTable(SOURCE_CONFIG_DO, properties,
//            "system_role", "system_role_menu",
//            "id", "roleId","role_id");
        GeneratorUtils.outputLocalFile(properties.getBasePath(), codes);
        try {
            // 打开文件夹
            RuntimeUtils.openDir(properties.getBasePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
