package de.monticore.typescalculator;

import de.monticore.ast.ASTNode;
import de.monticore.expressions.assignmentexpressions._ast.*;
import de.monticore.expressions.assignmentexpressions._visitor.AssignmentExpressionsInheritanceVisitor;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.expressionsbasis._symboltable.EVariableSymbol;
import de.monticore.expressions.expressionsbasis._symboltable.ExpressionsBasisScope;
import de.monticore.types.mcbasictypes._ast.*;
import de.monticore.types.mcbasictypes._symboltable.MCTypeSymbol;

import java.util.*;

public class AssignmentExpressionTypesCalculator implements AssignmentExpressionsInheritanceVisitor {

  private LiteralTypeCalculator literalsVisitor;

  private Map<ASTNode, MCTypeSymbol> types;

  private ASTMCType result;

  private ExpressionsBasisScope scope;

  public AssignmentExpressionTypesCalculator(){
    types = new HashMap<>();
  }

  @Override
  public void endVisit(ASTIncSuffixExpression expr){
    ASTMCType result = null;
    if(types.containsKey(expr.getExpression())){
      if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }

    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTDecSuffixExpression expr){
    ASTMCType result = null;
    if(types.containsKey(expr.getExpression())){
      if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }

    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTIncPrefixExpression expr){
    ASTMCType result = null;
    if(types.containsKey(expr.getExpression())){
      if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }

    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTDecPrefixExpression expr){
    ASTMCType result = null;
    if(types.containsKey(expr.getExpression())){
      if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }

    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTPlusPrefixExpression expr){
    ASTMCType result = null;
    if(types.containsKey(expr.getExpression())){
      if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }

    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTMinusPrefixExpression expr){
    ASTMCType result = null;
    if(types.containsKey(expr.getExpression())){
      if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getExpression()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }

    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculatePlusAssignment(ASTRegularAssignmentExpression expr){
    ASTMCType result = calculateTypeArithmetic(expr.getLeft(),expr.getRight());
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())) {
      List<String> name = new ArrayList<>();
      name.add("String");
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(name)))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(name)))){
        result = new ASTMCQualifiedType(new ASTMCQualifiedName(name));
      }
      ArrayList<String> nameB = new ArrayList<>();
      nameB.add("String");
      nameB.add(0,"lang");
      nameB.add(0,"java");
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(nameB)))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(nameB)))){
        result = new ASTMCQualifiedType(new ASTMCQualifiedName(nameB));
      }
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(name)))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(nameB)))||types.get(expr.getLeft()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(nameB)))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCQualifiedType(new ASTMCQualifiedName(name)))){
        result = new ASTMCQualifiedType(new ASTMCQualifiedName(name));
      }
    }
    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateMinusAssignment(ASTRegularAssignmentExpression expr){
    ASTMCType result = calculateTypeArithmetic(expr.getLeft(),expr.getRight());
    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateMultAssignment(ASTRegularAssignmentExpression expr){
    ASTMCType result = calculateTypeArithmetic(expr.getLeft(),expr.getRight());
    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateDivideAssignment(ASTRegularAssignmentExpression expr){
    ASTMCType result = calculateTypeArithmetic(expr.getLeft(),expr.getRight());
    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTLiteralExpression expr){
    ASTMCType result=null;
    if(types.containsKey(expr.getEExtLiteral())){
      result=types.get(expr.getEExtLiteral()).getASTMCType();
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  @Override
  public void endVisit(ASTRegularAssignmentExpression expr){
    if(expr.getOperator()==ASTConstantsAssignmentExpressions.PLUSEQUALS){
      calculatePlusAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.MINUSEQUALS){
      calculateMinusAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.STAREQUALS){
      calculateMultAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.SLASHEQUALS){
      calculateDivideAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.ANDEQUALS){
      calculateAndAssigment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.PIPEEQUALS){
      calculateOrAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.GTGTEQUALS){
      calculateDoubleRightAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.LTLTEQUALS){
      calculateDoubleLeftAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.GTGTGTEQUALS){
      calculateLogicalRightAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.ROOFEQUALS){
      calculateBinaryXorAssignment(expr);
    }else if(expr.getOperator()==ASTConstantsAssignmentExpressions.PERCENTEQUALS){
      calculateModuloAssignment(expr);
    }else {
      ASTMCType result = null;
      Optional<EVariableSymbol> left = Optional.empty();
      Optional<EVariableSymbol> right = Optional.empty();
      if (types.get(expr.getLeft()).getEVariableSymbol() != null && types.get(expr.getRight()).getEVariableSymbol() != null) {
        left = scope.resolveEVariable(types.get(expr.getLeft()).getEVariableSymbol().getName());
        right = scope.resolveEVariable(types.get(expr.getRight()).getEVariableSymbol().getName());
      }

      if (types.containsKey(expr.getLeft()) && types.containsKey(expr.getRight())) {
        if (types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE)) && types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))) {
          result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
        }
        else if (types.get(expr.getLeft()).deepEquals(types.get(expr.getRight()))) {
          result = types.get(expr.getLeft()).getASTMCType().deepClone();
        }
        else if (left.isPresent() && right.isPresent()) {
          if (left.get().getMCTypeSymbol().getSubtypes().contains(right.get().getMCTypeSymbol())) {
            result = types.get(expr.getLeft()).getASTMCType().deepClone();
          }
        }
      }
      if (result != null) {
        this.result = result;
        MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
        res.setASTMCType(result);
        types.put(expr, res);
      }
      else {
        throw new RuntimeException("the resulting type cannot be resolved");
      }
    }
  }

  public void calculateAndAssigment(ASTRegularAssignmentExpression expr){
    //definiert auf boolean - boolean und ganzzahl - ganzzahl
    ASTMCType result = null;
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())){
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN);
      }
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol sym = new MCTypeSymbol(result.getBaseName());
      sym.setASTMCType(result);
      types.put(expr,sym);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateOrAssignment(ASTRegularAssignmentExpression expr){
    //definiert auf boolean - boolean und ganzzahl - ganzzahl
    ASTMCType result = null;
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())){
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN);
      }
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol sym = new MCTypeSymbol(result.getBaseName());
      sym.setASTMCType(result);
      types.put(expr,sym);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateBinaryXorAssignment(ASTRegularAssignmentExpression expr){
    //definiert auf boolean - boolean und ganzzahl - ganzzahl
    ASTMCType result = null;
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())){
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))){
        result=new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }else if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.BOOLEAN);
      }
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol sym = new MCTypeSymbol(result.getBaseName());
      sym.setASTMCType(result);
      types.put(expr,sym);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateDoubleRightAssignment(ASTRegularAssignmentExpression expr){
    //definiert auf Ganzzahl - Ganzzahl
    ASTMCType result = null;
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())){
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol sym = new MCTypeSymbol(result.getBaseName());
      sym.setASTMCType(result);
      types.put(expr,sym);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateDoubleLeftAssignment(ASTRegularAssignmentExpression expr){
    //definiert auf Ganzzahl - Ganzzahl
    ASTMCType result = null;
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())){
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol sym = new MCTypeSymbol(result.getBaseName());
      sym.setASTMCType(result);
      types.put(expr,sym);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateLogicalRightAssignment(ASTRegularAssignmentExpression expr){
    //definiert auf Ganzzahl - Ganzzahl
    ASTMCType result = null;
    if(types.containsKey(expr.getLeft())&&types.containsKey(expr.getRight())){
      if(types.get(expr.getLeft()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))&&types.get(expr.getRight()).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }
    }
    if(result!=null){
      this.result=result;
      MCTypeSymbol sym = new MCTypeSymbol(result.getBaseName());
      sym.setASTMCType(result);
      types.put(expr,sym);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public void calculateModuloAssignment(ASTRegularAssignmentExpression expr){
    ASTMCType result = calculateTypeArithmetic(expr.getLeft(),expr.getRight());
    if(result!=null){
      this.result = result;
      MCTypeSymbol res = new MCTypeSymbol(result.getBaseName());
      res.setASTMCType(result);
      types.put(expr,res);
    }else{
      throw new RuntimeException("the resulting type cannot be resolved");
    }
  }

  public Map<ASTNode, MCTypeSymbol> getTypes() {
    return types;
  }

  public ASTMCType getResult() {
    return result;
  }

  private ASTMCType calculateTypeArithmetic(ASTExpression left, ASTExpression right){
    ASTMCType result = null;
    if(types.containsKey(left)&&types.containsKey(right)) {
      if (types.get(left).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT)) && types.get(right).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT);
      }
      else if (types.get(left).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE)) && types.get(right).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
      else if (types.get(left).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE)) && types.get(right).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT)) || types.get(left).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.INT)) && types.get(right).deepEqualsWithType(new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE))) {
        result = new ASTMCPrimitiveType(ASTConstantsMCBasicTypes.DOUBLE);
      }
    }
    return result;
  }

  public void setScope(ExpressionsBasisScope scope){
    this.scope=scope;
  }

  public ExpressionsBasisScope getScope(){
    return scope;
  }

  public void setLiteralsVisitor(LiteralTypeCalculator literalsVisitor){
    this.literalsVisitor=literalsVisitor;
  }

  public LiteralTypeCalculator getLiteralsVisitor(){
    return literalsVisitor;
  }

  //TODO: bisher nur double und int behandelt, bei += auch String, es fehlen noch RegularAssignmentExpr, AndAssignmentExpr, OrAssignmentExpr, BinaryXorAssignmentExpr, RightShiftAssignmentExpr, LeftShiftAssignmentExpr, LogicalRightAssignmentExpr und die Tests
}
