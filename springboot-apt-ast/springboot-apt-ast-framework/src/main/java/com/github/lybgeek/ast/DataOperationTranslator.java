package com.github.lybgeek.ast;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import com.sun.tools.javac.util.Names;
import com.sun.tools.javadoc.Messager;

public class DataOperationTranslator extends TreeTranslator {

    private Messager mMessager;
    private TreeMaker treeMaker;
    private Names names;

    public DataOperationTranslator(Messager mMessager, TreeMaker treeMaker, Names names) {
        this.mMessager = mMessager;
        this.treeMaker = treeMaker;
        this.names = names;
    }


    @Override
    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
        super.visitClassDef(jcClassDecl);
        for (JCTree jcTree : jcClassDecl.defs) {
            if (jcTree instanceof JCTree.JCVariableDecl){
                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                jcClassDecl.defs = jcClassDecl.defs.append(makeGetterMethod(jcVariableDecl));
                jcClassDecl.defs = jcClassDecl.defs.append(makeSetterMethod(jcVariableDecl));
            }
        }
        JCTree.JCMethodDecl toString = makeToStringMethod(jcClassDecl);
        jcClassDecl.defs = jcClassDecl.defs.append(toString);
        JCTree.JCMethodDecl hashCodeMethod = makeHashCodeMethod(jcClassDecl);
        jcClassDecl.defs = jcClassDecl.defs.append(hashCodeMethod);
        JCTree.JCMethodDecl equalsMethod = makeEqualsMethod(jcClassDecl);
        jcClassDecl.defs = jcClassDecl.defs.append(equalsMethod);
    }

    private JCTree.JCMethodDecl makeGetterMethod(JCTree.JCVariableDecl jcVariableDecl){
        JCTree.JCModifiers jcModifiers = treeMaker.Modifiers(Flags.PUBLIC);//public
        JCTree.JCExpression retrunType = jcVariableDecl.vartype;//xxx
        Name name = getterMethodName(jcVariableDecl);// getXxx
        JCTree.JCStatement jcStatement = // retrun this.xxx
                treeMaker.Return(treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.name));
        List<JCTree.JCStatement> jcStatementList = List.nil();
        jcStatementList = jcStatementList.append(jcStatement);
        JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatementList);//构建代码块
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();//泛型参数列表
        List<JCTree.JCVariableDecl> parameters = List.nil();//参数列表
        List<JCTree.JCExpression> throwsClauses = List.nil();//异常抛出列表
        JCTree.JCExpression defaultValue = null;
        JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(jcModifiers, name, retrunType, methodGenericParams, parameters, throwsClauses, jcBlock, defaultValue);
        return jcMethodDecl;
    }


    private JCTree.JCMethodDecl makeSetterMethod(JCTree.JCVariableDecl jcVariableDecl){
        JCTree.JCModifiers jcModifiers = treeMaker.Modifiers(Flags.PUBLIC);//public
        JCTree.JCExpression retrunType = treeMaker.TypeIdent(TypeTag.VOID);//或 treeMaker.Type(new Type.JCVoidType())
        Name name = setterMethodName(jcVariableDecl);// setXxx()
        List<JCTree.JCVariableDecl> parameters = List.nil();//参数列表
        JCTree.JCVariableDecl param = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER), jcVariableDecl.name, jcVariableDecl.vartype, null);
        param.pos = jcVariableDecl.pos;//设置形参这一句不能少，不然会编译报错(java.lang.AssertionError: Value of x -1)
        parameters = parameters.append(param);//添加参数；例如 int age
        JCTree.JCStatement jcStatement = treeMaker.Exec(treeMaker.Assign(
                treeMaker.Select(treeMaker.Ident(names.fromString("this")), jcVariableDecl.name),
                treeMaker.Ident(jcVariableDecl.name)));
        List<JCTree.JCStatement> jcStatementList = List.nil();
        jcStatementList = jcStatementList.append(jcStatement);
        JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatementList);
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();//泛型参数列表
        List<JCTree.JCExpression> throwsClauses = List.nil();//异常抛出列表
        JCTree.JCExpression defaultValue = null;
        JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(jcModifiers, name, retrunType, methodGenericParams, parameters, throwsClauses, jcBlock, defaultValue);
        return jcMethodDecl;
    }


    private JCTree.JCMethodDecl makeToStringMethod(JCTree.JCClassDecl jcClassDecl){
        List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();
        for (JCTree jcTree : jcClassDecl.defs) {
            if (jcTree instanceof JCTree.JCVariableDecl){
                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
            }
        }

        List<JCTree.JCAnnotation> jcAnnotationList = List.nil();
        JCTree.JCAnnotation jcAnnotation = treeMaker.Annotation(memberAccess("java.lang.Override"), List.nil());
        jcAnnotationList = jcAnnotationList.append(jcAnnotation);
        JCTree.JCModifiers jcModifiers = treeMaker.Modifiers(Flags.PUBLIC, jcAnnotationList);
        JCTree.JCExpression retrunType = memberAccess("java.lang.String");
        Name name = names.fromString("toString");
        JCTree.JCExpression jcExpression = treeMaker.Literal(jcClassDecl.name + "{");
        for (int i = 0; i < jcVariableDeclList.size(); i++) {
            JCTree.JCVariableDecl jcVariableDecl = jcVariableDeclList.get(i);
            if (i != 0){
                jcExpression = treeMaker.Binary(JCTree.Tag.PLUS, jcExpression, treeMaker.Literal("," + jcVariableDecl.name.toString() + "="));
            }else{
                jcExpression = treeMaker.Binary(JCTree.Tag.PLUS, jcExpression, treeMaker.Literal(jcVariableDecl.name.toString() + "="));
            }
            if (jcVariableDecl.vartype.toString().contains("String")){
                jcExpression = treeMaker.Binary(JCTree.Tag.PLUS, jcExpression, treeMaker.Literal("'"));
            }
            jcExpression = treeMaker.Binary(JCTree.Tag.PLUS, jcExpression, treeMaker.Ident(jcVariableDecl.name));
            if (jcVariableDecl.vartype.toString().contains("String")){
                jcExpression = treeMaker.Binary(JCTree.Tag.PLUS, jcExpression, treeMaker.Literal("'"));
            }
        }

        jcExpression = treeMaker.Binary(JCTree.Tag.PLUS, jcExpression, treeMaker.Literal("}"));
        JCTree.JCStatement jcStatement = treeMaker.Return(jcExpression);
        List<JCTree.JCStatement> jcStatementList = List.nil();
        jcStatementList = jcStatementList.append(jcStatement);
        JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatementList);
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();//泛型参数列表
        List<JCTree.JCVariableDecl> parameters = List.nil();//参数列表
        List<JCTree.JCExpression> throwsClauses = List.nil();//异常抛出列表
        JCTree.JCExpression defaultValue = null;
        JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(jcModifiers, name, retrunType, methodGenericParams, parameters, throwsClauses, jcBlock, defaultValue);
        return jcMethodDecl;
    }

    private JCTree.JCMethodDecl makeHashCodeMethod(JCTree.JCClassDecl jcClassDecl){
        List<JCTree.JCVariableDecl> jcVariableDeclList = List.nil();
        for (JCTree jcTree : jcClassDecl.defs) {
            if (jcTree instanceof JCTree.JCVariableDecl){
                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                jcVariableDeclList = jcVariableDeclList.append(jcVariableDecl);
            }
        }
        List<JCTree.JCAnnotation> jcAnnotationList = List.nil();
        JCTree.JCAnnotation jcAnnotation = treeMaker.Annotation(memberAccess("java.lang.Override"), List.nil());
        jcAnnotationList = jcAnnotationList.append(jcAnnotation);
        JCTree.JCModifiers jcModifiers = treeMaker.Modifiers(Flags.PUBLIC, jcAnnotationList);
        Name name = names.fromString("hashCode");
        JCTree.JCExpression retrunType = treeMaker.TypeIdent(TypeTag.INT);

        List<JCTree.JCExpression> var1 = List.nil();
        List<JCTree.JCExpression> var2 = List.nil();
        for (JCTree.JCVariableDecl variableDecl:jcVariableDeclList) {
            var1 = var1.append(typeTranslator(variableDecl.vartype));
            var2 = var2.append(treeMaker.Ident(variableDecl.name));
        }
        JCTree.JCStatement jcStatement =
                treeMaker.Return(treeMaker.Apply(var1, memberAccess("java.util.Objects.hash"), var2));
        List<JCTree.JCStatement> jcStatementList = List.nil();
        jcStatementList = jcStatementList.append(jcStatement);
        JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatementList);
        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();//泛型参数列表
        List<JCTree.JCVariableDecl> parameters = List.nil();//参数列表
        List<JCTree.JCExpression> throwsClauses = List.nil();//异常抛出列表
        JCTree.JCExpression defaultValue = null;
        JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(jcModifiers, name, retrunType, methodGenericParams, parameters, throwsClauses, jcBlock, defaultValue);
        return jcMethodDecl;
    }

    private JCTree.JCMethodDecl makeEqualsMethod(JCTree.JCClassDecl jcClassDecl){
        List<JCTree.JCVariableDecl> notStringJcVariableDeclList = List.nil();
        List<JCTree.JCVariableDecl> stringJcVariableDeclList = List.nil();
        for (JCTree jcTree : jcClassDecl.defs) {
            if (jcTree instanceof JCTree.JCVariableDecl){
                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcTree;
                if (jcVariableDecl.vartype.toString().equals("String")){
                    stringJcVariableDeclList = stringJcVariableDeclList.append(jcVariableDecl);
                }else{
                    notStringJcVariableDeclList = notStringJcVariableDeclList.append(jcVariableDecl);
                }
            }
        }

        List<JCTree.JCAnnotation> jcAnnotationList = List.nil();
        JCTree.JCAnnotation jcAnnotation = treeMaker.Annotation(memberAccess("java.lang.Override"), List.nil());
        jcAnnotationList = jcAnnotationList.append(jcAnnotation);
        JCTree.JCModifiers jcModifiers = treeMaker.Modifiers(Flags.PUBLIC, jcAnnotationList);
        Name name = names.fromString("equals");
        JCTree.JCExpression retrunType = treeMaker.TypeIdent(TypeTag.BOOLEAN);


        List<JCTree.JCStatement> jcStatementList = List.nil();
        // if (this == o) return false;
        JCTree.JCStatement zeroth = treeMaker.If(treeMaker.Binary(JCTree.Tag.EQ, treeMaker.Ident(names.fromString("this")), treeMaker.Ident(names.fromString("o"))),
                treeMaker.Return(treeMaker.Literal(false)), null);
        jcStatementList = jcStatementList.append(zeroth);
        //if (!(o instanceof TestBean)) return false;
        JCTree.JCStatement first = treeMaker.If(treeMaker.Unary(JCTree.Tag.NOT, treeMaker.TypeTest(treeMaker.Ident(names.fromString("o")), memberAccess(jcClassDecl.sym.toString()))),
                treeMaker.Return(treeMaker.Literal(false)), null);
        jcStatementList = jcStatementList.append(first);
        //TestBean testBean = (TestBean)o;
        JCTree.JCVariableDecl second = treeMaker.VarDef(
                treeMaker.Modifiers(0), names.fromString(toLowerCaseFirstOne(jcClassDecl.name.toString())), memberAccess(jcClassDecl.sym.toString()),
                treeMaker.TypeCast(memberAccess(jcClassDecl.sym.toString()), treeMaker.Ident(names.fromString("o"))));
        jcStatementList = jcStatementList.append(second);

        JCTree.JCExpression jcExpression = null;
        for (int i = 0; i < notStringJcVariableDeclList.size(); i++) {
            JCTree.JCExpression isEq = treeMaker.Binary(JCTree.Tag.EQ,
                    treeMaker.Ident(notStringJcVariableDeclList.get(i).name),
                    treeMaker.Select(treeMaker.Ident(names.fromString(toLowerCaseFirstOne(jcClassDecl.name.toString()))),
                            notStringJcVariableDeclList.get(i).name));
            if (jcExpression != null){
                //&& this.age == testBean.age
                jcExpression = treeMaker.Binary(JCTree.Tag.AND, jcExpression, isEq);
            }else{
                jcExpression = isEq;
            }
        }

        for (int i = 0; i < stringJcVariableDeclList.size(); i++) {
            List<JCTree.JCExpression> var1 = List.nil();
            var1 = var1.append(memberAccess("java.lang.String"));
            var1 = var1.append(memberAccess("java.lang.String"));
            List<JCTree.JCExpression> var2 = List.nil();
            var2 = var2.append(treeMaker.Ident(stringJcVariableDeclList.get(i).name));
            var2 = var2.append(treeMaker.Select(treeMaker.Ident(names.fromString(toLowerCaseFirstOne(jcClassDecl.name.toString()))),
                    stringJcVariableDeclList.get(i).name));
            JCTree.JCExpression isEq = treeMaker.Apply(var1, memberAccess("java.util.Objects.equals"), var2);
            if (jcExpression != null){
                //&& Objects.equals(this.nickName, testBean.nickName);
                jcExpression = treeMaker.Binary(JCTree.Tag.AND, jcExpression, isEq);
            }else{
                jcExpression = isEq;
            }
        }
        JCTree.JCStatement fourth = treeMaker.Return(jcExpression);//return语句
        jcStatementList = jcStatementList.append(fourth);
        JCTree.JCBlock jcBlock = treeMaker.Block(0, jcStatementList);

        List<JCTree.JCTypeParameter> methodGenericParams = List.nil();//泛型参数列表
        List<JCTree.JCVariableDecl> parameters = List.nil();//参数列表
        JCTree.JCVariableDecl param = treeMaker.VarDef(
                treeMaker.Modifiers(Flags.PARAMETER), names.fromString("o"), memberAccess("java.lang.Object"), null);
        param.pos = jcClassDecl.pos;
        parameters = parameters.append(param);//添加参数 Object o
        List<JCTree.JCExpression> throwsClauses = List.nil();//异常抛出列表
        JCTree.JCExpression defaultValue = null;
        JCTree.JCMethodDecl jcMethodDecl = treeMaker.MethodDef(jcModifiers, name, retrunType, methodGenericParams, parameters, throwsClauses, jcBlock, defaultValue);
        return jcMethodDecl;
    }

    private Name getterMethodName(JCTree.JCVariableDecl jcVariableDecl){
        String varName = jcVariableDecl.name.toString();
        Name name = names.fromString("get" + varName.substring(0, 1).toUpperCase() + varName.substring(1, varName.length()));
        return name;
    }

    private Name setterMethodName(JCTree.JCVariableDecl jcVariableDecl){
        String varName = jcVariableDecl.name.toString();
        Name name = names.fromString("set" + varName.substring(0, 1).toUpperCase() + varName.substring(1, varName.length()));
        return name;
    }

    //传入一个类的全路径名，获取对应类的JCIdent
    private JCTree.JCExpression memberAccess(String components) {
        String[] componentArray = components.split("\\.");
        JCTree.JCExpression expr = treeMaker.Ident(names.fromString(componentArray[0]));
        for (int i = 1; i < componentArray.length; i++) {
            expr = treeMaker.Select(expr, names.fromString(componentArray[i]));
        }
        return expr;
    }

    //装箱
    private JCTree.JCExpression typeTranslator(JCTree.JCExpression type){
        if (type.toString().equals("int")){
            type = memberAccess("java.lang.Integer");
        }else if (type.toString().equals("long")){
            type = memberAccess("java.lang.Long");
        }else if(type.toString().equals("short")){
            type = memberAccess("java.lang.Short");
        }else if(type.toString().equals("double")){
            type = memberAccess("java.lang.Double");
        }else if(type.toString().equals("boolean")){
            type = memberAccess("java.lang.Boolean");
        }else if(type.toString().equals("float")){
            type = memberAccess("java.lang.Float");
        }else if(type.toString().equals("char")){
            type = memberAccess("java.lang.Character");
        }else if(type.toString().equals("byte")){
            type = memberAccess("java.lang.Byte");
        }
        return type;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s){
        if(Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }


}
