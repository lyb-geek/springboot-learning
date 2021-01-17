package com.github.lybgeek.mybatisplus.tenant.parser;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;

import java.util.List;

/**
 * @description: 租户sql解析器
 * @author: lyb-geek
 * @date : 2020/08/19 12:00
 **/
public class CustomTenantSqlParser extends TenantSqlParser {

    /**
     * select 语句处理
     */
    @Override
    public void processSelectBody(SelectBody selectBody) {
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            if (withItem.getSelectBody() != null) {
                processSelectBody(withItem.getSelectBody());
            }
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            if (operationList.getSelects() != null && operationList.getSelects().size() > 0) {
                operationList.getSelects().forEach(this::processSelectBody);
            }
        }
    }

    /**
     * insert 语句处理
     */
    @Override
    public void processInsert(Insert insert) {
        if (getTenantHandler().doTableFilter(insert.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        for (Column column : insert.getColumns()) {
            if(column.getColumnName().equals(getTenantHandler().getTenantIdColumn())){
                return;
            }
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
     * update 语句处理
     */
    @Override
    public void processUpdate(Update update) {
        List<Table> tableList = update.getTables();
        Assert.isTrue(null != tableList && tableList.size() < 2,
                "Failed to process multiple-table update, please exclude the statementId");
        Table table = tableList.get(0);
        if (getTenantHandler().doTableFilter(table.getName())) {
            // 过滤退出执行
            return;
        }
        update.setWhere(this.andExpression(table, update.getWhere()));
    }

    /**
     * delete 语句处理
     */
    @Override
    public void processDelete(Delete delete) {
        if (getTenantHandler().doTableFilter(delete.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        delete.setWhere(this.andExpression(delete.getTable(), delete.getWhere()));
    }

    /**
     * delete update 语句 where 处理
     */
    @Override
    protected BinaryExpression andExpression(Table table, Expression where) {
        //获得where条件表达式
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(this.getAliasColumn(table));
        equalsTo.setRightExpression(getTenantHandler().getTenantId());
        if (null != where) {
            if (where instanceof OrExpression) {
                return new AndExpression(equalsTo, new Parenthesis(where));
            } else {
                return new AndExpression(equalsTo, where);
            }
        }
        return equalsTo;
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



    /**
     * 处理 PlainSelect
     *
     * @param plainSelect ignore
     * @param addColumn   是否添加租户列,insert into select语句中需要
     */
    @Override
    protected void processPlainSelect(PlainSelect plainSelect, boolean addColumn) {
        FromItem fromItem = plainSelect.getFromItem();
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            if (getTenantHandler().doTableFilter(fromTable.getName())) {
                // 过滤退出执行
                return;
            }
            plainSelect.setWhere(builderExpression(plainSelect.getWhere(), fromTable));
            if (addColumn) {
                plainSelect.getSelectItems().add(new SelectExpressionItem(new Column(getTenantHandler().getTenantIdColumn())));
            }
        } else {
            processFromItem(fromItem);
        }
        List<Join> joins = plainSelect.getJoins();
        if (joins != null && joins.size() > 0) {
            joins.forEach(j -> {
                processJoin(j);
                processFromItem(j.getRightItem());
            });
        }
    }

    /**
     * 处理子查询等
     */
    @Override
    protected void processFromItem(FromItem fromItem) {
        if (fromItem instanceof SubJoin) {
            SubJoin subJoin = (SubJoin) fromItem;
            if (subJoin.getJoinList() != null) {
                subJoin.getJoinList().forEach(this::processJoin);
            }
            if (subJoin.getLeft() != null) {
                processFromItem(subJoin.getLeft());
            }
        } else if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            logger.debug("Perform a subquery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * 处理联接语句
     */
    @Override
    protected void processJoin(Join join) {
        if (join.getRightItem() instanceof Table) {
            Table fromTable = (Table) join.getRightItem();
            if (this.getTenantHandler().doTableFilter(fromTable.getName())) {
                // 过滤退出执行
                return;
            }
            join.setOnExpression(builderExpression(join.getOnExpression(), fromTable));
        }
    }

    /**
     * 处理条件
     */
    @Override
    protected Expression builderExpression(Expression expression, Table table) {
        //生成字段名
        EqualsTo equalsTo = new EqualsTo();
        equalsTo.setLeftExpression(this.getAliasColumn(table));
        equalsTo.setRightExpression(getTenantHandler().getTenantId());
        //加入判断防止条件为空时生成 "and null" 导致查询结果为空
        if (expression == null) {
            return equalsTo;
        } else {
            if (expression instanceof BinaryExpression) {
                BinaryExpression binaryExpression = (BinaryExpression) expression;
                if (binaryExpression.getLeftExpression() instanceof FromItem) {
                    processFromItem((FromItem) binaryExpression.getLeftExpression());
                }
                if (binaryExpression.getRightExpression() instanceof FromItem) {
                    processFromItem((FromItem) binaryExpression.getRightExpression());
                }
            }
            if (expression instanceof OrExpression) {
                return new AndExpression(equalsTo, new Parenthesis(expression));
            } else {
                return new AndExpression(equalsTo, expression);
            }
        }
    }

    /**
     * 租户字段别名设置
     * <p>tableName.tenantId 或 tableAlias.tenantId</p>
     *
     * @param table 表对象
     * @return 字段
     */
    @Override
    protected Column getAliasColumn(Table table) {
        StringBuilder column = new StringBuilder();
        if (null == table.getAlias()) {
            column.append(table.getName());
        } else {
            column.append(table.getAlias().getName());
        }
        column.append(StringPool.DOT);
        column.append(getTenantHandler().getTenantIdColumn());
        return new Column(column.toString());
    }

}
