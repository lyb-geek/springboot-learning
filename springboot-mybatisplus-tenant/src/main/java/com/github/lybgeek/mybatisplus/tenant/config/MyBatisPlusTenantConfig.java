package com.github.lybgeek.mybatisplus.tenant.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.github.lybgeek.mybatisplus.tenant.parser.CustomTenantSqlParser;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
@EnableConfigurationProperties(MyBatisPlusTenantProperties.class)
public class MyBatisPlusTenantConfig {
    @Autowired
    private MyBatisPlusTenantProperties myBatisPlusTenantProperties;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        if(myBatisPlusTenantProperties.isEnabled()){
            tenantHandler(paginationInterceptor);
        }


        return paginationInterceptor;
    }

    /**
     * 租户处理
     * @param paginationInterceptor
     */
    private void tenantHandler(PaginationInterceptor paginationInterceptor) {
        // 创建SQL解析器集合
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 创建租户SQL解析器
        TenantSqlParser tenantSqlParser = new CustomTenantSqlParser();
        // 设置租户处理器
        tenantSqlParser.setTenantHandler(new TenantHandler() {

            @Override
            public Expression getTenantId() {
               return new LongValue(100);
            }

            @Override
            public String getTenantIdColumn() {
                // 对应数据库租户ID的列名
                return "tenant_id";
            }

            @Override
            public boolean doTableFilter(String tableName) {
                List<String> skipFillTenantIdTables = myBatisPlusTenantProperties.getSkipFillTenantIdTables();
                return skipFillTenantIdTables.contains(tableName);
            }
        });

        sqlParserList.add(tenantSqlParser);

        paginationInterceptor.setSqlParserList(sqlParserList);
    }

    // 乐观锁
    @Bean
    @ConditionalOnMissingBean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}