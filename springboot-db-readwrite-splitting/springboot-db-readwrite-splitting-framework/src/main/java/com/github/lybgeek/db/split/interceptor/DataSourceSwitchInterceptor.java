package com.github.lybgeek.db.split.interceptor;


import com.github.lybgeek.db.split.context.DataSourceContextHolder;
import com.github.lybgeek.db.split.enums.DataSourceTypeEnum;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class DataSourceSwitchInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        if(SqlCommandType.SELECT == ms.getSqlCommandType()){
            DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.SLAVE);
        }else{
            DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.MASTER);
        }

        try {
            // 执行 SQL
            return invocation.proceed();
        } finally {
            // 恢复数据源  考虑到写入后可能会反查，后续都走主库
             DataSourceContextHolder.setDataSourceType(DataSourceTypeEnum.MASTER);
        }
    }
}
