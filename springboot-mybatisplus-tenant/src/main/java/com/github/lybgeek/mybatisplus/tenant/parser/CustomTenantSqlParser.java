package com.github.lybgeek.mybatisplus.tenant.parser;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;

/**
 * @description: 租户sql解析器
 * @author: lyb-geek
 * @date : 2020/08/19 12:00
 **/
public class CustomTenantSqlParser extends TenantSqlParser {


    /**
     * insert 语句处理
     */
    @Override
    public void processInsert(Insert insert) {
        if (getTenantHandler().doTableFilter(insert.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        if (isAleadyExistTenantColumn(insert)) {
            return;
        }
        insert.getColumns().add(new Column(getTenantHandler().getTenantIdColumn()));
        if (insert.getSelect() != null) {
            processPlainSelect((PlainSelect) insert.getSelect().getSelectBody(), true);
        } else if (insert.getItemsList() != null) {
            // fixed github pull/295
            ItemsList itemsList = insert.getItemsList();
            if (itemsList instanceof MultiExpressionList) {
                ((MultiExpressionList) itemsList).getExprList().forEach(el -> el.getExpressions().add(getTenantHandler().getTenantId()));
            } else {
                ((ExpressionList) insert.getItemsList()).getExpressions().add(getTenantHandler().getTenantId());
            }
        } else {
            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or statementId");
        }
    }

    /**
     * 判断是否存在租户id列字段
     * @param insert
     * @return 如果已经存在，则绕过不执行
     */
    private boolean isAleadyExistTenantColumn(Insert insert) {
        List<Column> columns = insert.getColumns();
        if(CollectionUtils.isEmpty(columns)){
            return false;
        }
        String tenantIdColumn = getTenantHandler().getTenantIdColumn();
        return columns.stream().map(Column::getColumnName).anyMatch(tenantId -> tenantId.equals(tenantIdColumn));
    }


    /***
     * 因为mybatis-plus自带的功能只会拼接left 、from和where后面的表或子查询添加租户id。
     * 想要将selete部分的子查询拼接租户id，必须重写TenantSqlParser类的processPlainSelect(PlainSelect plainSelect)
     *
     * @param plainSelect
     */
    @Override
    protected void processPlainSelect(PlainSelect plainSelect) {
        // SELECT 至 FROM 中的嵌套查询
        List<SelectItem> selectItemList = plainSelect.getSelectItems();
        for (SelectItem selectItem : selectItemList) {
            if (selectItem instanceof SelectExpressionItem) {
                SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                if (!(selectExpressionItem.getExpression() instanceof Column)) {
                    // 处理 column select 嵌套部分
                    operateExpression(selectExpressionItem.getExpression());
                }
            }
        }

        processPlainSelect(plainSelect, false);
    }


    private void operateExpression(Expression expression){

        if (expression instanceof SubSelect){
            SubSelect subSelect = (SubSelect) expression;
            PlainSelect plainSelect = (PlainSelect) subSelect.getSelectBody();
            processSelectBody(plainSelect);
        } else if (expression instanceof Parenthesis){
            Parenthesis parenthesis= (Parenthesis) expression;
            operateExpression(parenthesis.getExpression());
        }else if (expression instanceof CaseExpression) { //处理case when
            CaseExpression caseExpression = (CaseExpression) expression;
            List<WhenClause> whenClauses = caseExpression.getWhenClauses();
            for (Expression e : whenClauses) {
                if (e instanceof WhenClause){
                    WhenClause whenClause = (WhenClause) e;
                    operateExpression(whenClause.getThenExpression());
                }
            }
        }else if (expression instanceof Function){//处理IFNULL
            Function function= (Function) expression;
            if ("IFNULL".equals(function.getName())){
                ExpressionList expressionList=function.getParameters();
                List<Expression> ifExpression=expressionList.getExpressions();
                for (Expression e:ifExpression){
                    operateExpression(e);
                }
            }
        }
    }





}
