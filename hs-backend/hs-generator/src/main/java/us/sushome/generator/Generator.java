package us.sushome.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.sql.Types;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Generator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/home_service?serverTimezone=UTC&remarks=true&useInformationSchema=true", "root", "sussql123")
                .globalConfig(builder -> {
                    builder.author("sushome") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(Paths.get(System.getProperty("user.dir")) + "/hs-backend/hs-db/src/main/java"); // 指定输出目录
                })
                .dataSourceConfig(builder ->
                        builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                            if (typeCode == Types.SMALLINT) {
                                // 自定义类型转换
                                return DbColumnType.INTEGER;
                            }
                            return typeRegistry.getColumnType(metaInfo);
                        })
                )
                .packageConfig(builder ->
                        builder.parent("us.sushome.db") // 设置父包名
                                .entity("model") // 设置实体类包名
                                .mapper("dao") // 设置 Mapper 接口包名
                                .service("service") // 设置 Service 接口包名
                                .xml("mappers") // 设置 Mapper XML 文件包名
                                .controller("")
                )
                .strategyConfig(builder ->
                        builder.addInclude("hs_user","hs_roles","hs_permission") // 设置需要生成的表名
                                .entityBuilder()
                                .javaTemplate("/templates/model.java")
                                .enableFileOverride()
                                .enableLombok() // 启用 Lombok
                                .enableTableFieldAnnotation() // 启用字段注解
                                .serviceBuilder()
                                .enableFileOverride()
                                .disableServiceImpl()
                                .serviceTemplate("/templates/service.java")
                                .enableFileOverride()
                                .controllerBuilder()
                                .disable()
                )
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}