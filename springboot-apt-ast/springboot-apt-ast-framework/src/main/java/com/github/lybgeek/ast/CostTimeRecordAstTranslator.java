package com.github.lybgeek.ast;


import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

public class CostTimeRecordAstTranslator extends AbstractTreeTranslator{



    /**
     * 有标注CostTimeRecord的方法
     */
    private JCTree.JCMethodDecl methodDecl;

    private JCTree.JCClassDecl classDecl;


    public CostTimeRecordAstTranslator(TreeMaker treeMaker, Names names, Messager meessager,JCTree.JCClassDecl classDecl, JCTree.JCMethodDecl methodDecl) {
        super(treeMaker, names, meessager);
        this.methodDecl = methodDecl;
        this.classDecl = classDecl;
    }

    @Override
    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
        jcClassDecl.defs = jcClassDecl.defs.append(createSaveLogMethod());
        super.visitClassDef(jcClassDecl);
    }

    // 创建private void saveLog(long costTime,List<Object> args)方法
    private JCTree.JCMethodDecl createSaveLogMethod(){
        //访问修饰符
        JCTree.JCModifiers jcModifiers = treeMaker.Modifiers(Flags.PRIVATE);
        //返回值类型
        JCTree.JCExpression retrunType = treeMaker.TypeIdent(TypeTag.VOID);
        //方法名称
        Name methodName = getNameFromString("saveLog");
        //参数列表
        List<JCTree.JCVariableDecl> parameters = List.nil();
        JCTree.JCVariableDecl costTime = makeVarDef(treeMaker.Modifiers(Flags.PARAMETER),
                memberAccess("java.lang.Long"),
                "costTime",
                null
                );
        JCTree.JCVariableDecl args = makeVarDef(treeMaker.Modifiers(Flags.PARAMETER),
                memberAccess("java.util.List"),
                "args",
                null
        );
        parameters = parameters.append(costTime).append(args);
        // 方法体
        List<JCTree.JCStatement> jcStatementList = List.nil();


        // 构造 LogDTO logDTO = new LogDTO();
        // logDTO.setMethodName()
        // logDTO.setClassName();
        // logDTO.setCostTime();
        List<JCTree.JCStatement> logDTOExpressionStatements = createLogDTO();

        // 构造 LogService logService = LogFactory.getLogger();
        JCTree.JCVariableDecl logService = makeVarDef(treeMaker.Modifiers(0),
                memberAccess("com.github.lybgeek.log.service.LogService"),
                "logService",
                treeMaker.Apply(List.nil(),memberAccess("LogFactory.getLogger"),List.nil())

        );

        // 构造 logService.save(logDto);
        JCTree.JCExpressionStatement saveLogExpression = treeMaker.Exec(treeMaker.Apply(
                List.of(memberAccess("LogDTO")),//参数类型
                treeMaker.Select(treeMaker.Ident(getNameFromString("logService")),//左边的内容
                        getNameFromString("save")//右边的内容
                ),
                List.of(treeMaker.Ident(getNameFromString("logDTO")))));

        jcStatementList = jcStatementList.appendList(logDTOExpressionStatements)
                .append(logService).append(saveLogExpression);

        JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatementList);
        //泛型参数列表
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();
        //异常抛出列表
        List<JCTree.JCExpression> throwsClauses = List.nil();
        JCTree.JCExpression defaultValue = null;

        JCTree.JCMethodDecl saveLogMethodDecl = treeMaker.MethodDef(jcModifiers, methodName, retrunType, methodGenericParams, parameters, throwsClauses, jcBlock, defaultValue);
        return saveLogMethodDecl;

    }


    private List<JCTree.JCStatement> createLogDTO(){

        JCTree.JCNewClass logDTOclass = treeMaker.NewClass(null, null, treeMaker.Ident(getNameFromString("LogDTO")), List.nil(), null);

        //构造 LogDTO logDTO = new LogDTO();
        JCTree.JCVariableDecl logDTO = makeVarDef(treeMaker.Modifiers(0),
                treeMaker.Ident(getNameFromString("LogDTO")),
                "logDTO",
                logDTOclass
        );

        //构造 logDTO.setMethodName("xxxx")
        JCTree.JCExpressionStatement setMethodName = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //构造方法 logDTO.setMethodName
                treeMaker.Select(treeMaker.Ident(getNameFromString("logDTO")),//左边的内容
                        getNameFromString("setMethodName")//右边的内容
                ),
                List.of(treeMaker.Literal(methodDecl.name.toString()))
        ));


        //构造 logDTO.setClassName("xxxx")
        JCTree.JCExpressionStatement setClassName = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //构造方法 logDTO.setClassName
                treeMaker.Select(treeMaker.Ident(getNameFromString("logDTO")),//左边的内容
                        getNameFromString("setClassName")//右边的内容
                ),
                List.of(treeMaker.Literal(classDecl.sym.fullname.toString()))
        ));


        //构造 logDTO.setCostTime("xxxx")
        JCTree.JCExpressionStatement setCostTime = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //构造方法 logDTO.setClassName
                treeMaker.Select(treeMaker.Ident(getNameFromString("logDTO")),//左边的内容
                        getNameFromString("setCostTime")//右边的内容
                ),
                List.of(treeMaker.Ident(getNameFromString("costTime")))
        ));



        //构造 logDTO.setArgs("xxxx")
        JCTree.JCExpressionStatement setArgs = treeMaker.Exec(treeMaker.Apply(
                List.nil(),
                //构造方法 logDTO.setArgs
                treeMaker.Select(treeMaker.Ident(getNameFromString("logDTO")),//左边的内容
                        getNameFromString("setArgs")//右边的内容
                ),
                List.of(treeMaker.Ident(getNameFromString("args")))
        ));

        List<JCTree.JCStatement> logDTOStaments = List.of(logDTO, setMethodName, setClassName, setCostTime);
        logDTOStaments = logDTOStaments.append(setArgs);
        return logDTOStaments;

    }

    private List<JCTree.JCStatement> addArgs(){

        JCTree.JCNewClass argsListclass = treeMaker.NewClass(null, null, memberAccess("java.util.ArrayList"), List.nil(), null);

        //构造 List args = new Arraylist();
        JCTree.JCVariableDecl args = makeVarDef(treeMaker.Modifiers(0),
                memberAccess("java.util.List"),
                "args",
                argsListclass
        );

        List<JCTree.JCStatement> parameters = List.of(args);
        for (JCTree.JCVariableDecl parameter : methodDecl.getParameters()) {
            JCTree.JCStatement add = treeMaker.Exec(treeMaker.Apply(
                    List.nil(),
                    //构造方法 arrayList.add
                    treeMaker.Select(treeMaker.Ident(getNameFromString("args")),//左边的内容
                            getNameFromString("add")//右边的内容
                    ),
                    List.of(treeMaker.Ident(getNameFromString(parameter.name.toString())))
            ));

            parameters = parameters.append(add);

        }



        return parameters;

    }



    @Override
    public void visitMethodDef(JCTree.JCMethodDecl jcMethodDecl) {
        if(methodDecl == jcMethodDecl){
            List<JCTree.JCStatement> statements = methodDecl.body.getStatements();

            // 构造long startTime = System.currentTimeMillis();
            JCTree.JCVariableDecl startTime = makeVarDef(treeMaker.Modifiers(0),
                    memberAccess("java.lang.Long"),
                    "startTime",
                    // 第一个值是参数类型、第二个是执行方法、第三个是参数值
                    treeMaker.Apply(List.nil(),memberAccess("java.lang.System.currentTimeMillis"),List.nil())
                    );

                // 构造long endTime = System.currentTimeMillis();
                JCTree.JCVariableDecl endTime = makeVarDef(treeMaker.Modifiers(0),
                        memberAccess("java.lang.Long"),
                        "endTime",
                        // 第一个值是参数类型、第二个是执行方法、第三个是参数值
                        treeMaker.Apply(List.nil(),memberAccess("java.lang.System.currentTimeMillis"),List.nil())
                );

                // 构造long costTime = endTime - startTime;
                JCTree.JCVariableDecl costTime = makeVarDef(treeMaker.Modifiers(0),
                        memberAccess("java.lang.Long"),
                        "costTime",
                        //构造减法
                        treeMaker.Binary(JCTree.Tag.MINUS,
                                treeMaker.Ident(getNameFromString("endTime")),
                                treeMaker.Ident(getNameFromString("startTime"))
                                )
                );
            // 构造String msg = "costTime = "+costTime+ "ms"
            JCTree.JCVariableDecl message = treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER),
                    names.fromString("msg"),
                    memberAccess("java.lang.String"),
                    treeMaker.Apply(
                            List.of(memberAccess("java.lang.String"),memberAccess("java.lang.Object")),
                            memberAccess("java.lang.String.format"),
                            List.of(treeMaker.Literal("costTime = %s(ms)"),treeMaker.Ident(names.fromString("costTime")))
                    )
            );

             // 构造 System.out.println(msg);
            JCTree.JCExpressionStatement printExpression = treeMaker.Exec(treeMaker.Apply(
                    List.of(memberAccess("java.lang.String")),//参数类型
                    memberAccess("java.lang.System.out.println"),
                    List.of(treeMaker.Ident(getNameFromString("msg")))));

            List<JCTree.JCStatement> args = addArgs();


            JCTree.JCStatement saveLog = treeMaker.Exec(treeMaker.Apply(
                    List.nil(),
                    //构造方法 this.saveLog(costTime,args)
                    treeMaker.Select(treeMaker.Ident(getNameFromString("this")),//左边的内容
                            getNameFromString("saveLog")//右边的内容
                    ),
                   List.of(treeMaker.Ident(getNameFromString("costTime")),
                           treeMaker.Ident(getNameFromString("args")))
            ));


            if(jcMethodDecl.getReturnType().type.getTag() == TypeTag.VOID){
                   statements = statements.prepend(startTime)
                           .append(endTime).append(costTime)
                           .append(message)
                           .append(printExpression)
                           .appendList(args)
                           .append(saveLog);

               }else{
                   List<JCTree.JCStatement> newStatements = List.of(startTime);
                   for (int i = 0; i < statements.size() - 1; i++) {
                       newStatements = newStatements.append(statements.get(i));
                   }
                   JCTree.JCStatement returnStatement = statements.last();
                   newStatements = newStatements.append(endTime).append(costTime)
                           .append(message)
                           .append(printExpression)
                           .appendList(args)
                           .append(saveLog)
                           .append(returnStatement);
                   statements = newStatements;
               }


            JCTree.JCBlock jcBlock = treeMaker.Block(0, statements);
            jcMethodDecl = treeMaker.MethodDef(jcMethodDecl.sym,jcBlock);

            meessager.printMessage(Diagnostic.Kind.NOTE,"method:" + jcMethodDecl);

        }
        super.visitMethodDef(jcMethodDecl);
    }





}