/* (c) https://github.com/MontiCore/monticore */
package de.monticore.typescalculator;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.io.paths.ModelPath;
import de.monticore.types2.SymObjectType;
import de.monticore.types2.SymTypeConstant;
import de.monticore.types2.SymTypeExpression;
import de.monticore.typescalculator.combineexpressionswithliterals._ast.ASTFoo;
import de.monticore.typescalculator.combineexpressionswithliterals._ast.CombineExpressionsWithLiteralsMill;
import de.monticore.typescalculator.combineexpressionswithliterals._parser.CombineExpressionsWithLiteralsParser;
import de.monticore.typescalculator.combineexpressionswithliterals._symboltable.*;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AssignmentExpressionsTest {

  private CombineExpressionsWithLiteralsGlobalScope globalScope;

  private CombineExpressionsWithLiteralsArtifactScope artifactScope;

  @Before
  public void setup() throws IOException{
    Log.enableFailQuick(false);
    ASTExpression expression = new CombineExpressionsWithLiteralsParser().parse_StringExpression("A").get();
    ASTFoo ast = CombineExpressionsWithLiteralsMill.fooBuilder().setExpression(expression).build();
    CombineExpressionsWithLiteralsLanguage language = CombineExpressionsWithLiteralsSymTabMill.combineExpressionsWithLiteralsLanguageBuilder().build();
    globalScope = CombineExpressionsWithLiteralsSymTabMill.combineExpressionsWithLiteralsGlobalScopeBuilder().setLanguage(language).setModelPath(new ModelPath()).build();
    CombineExpressionsWithLiteralsSymbolTableCreatorDelegator stc = language.getSymbolTableCreator(globalScope);
    artifactScope = stc.createFromAST(ast);
    globalScope.addAdaptedEMethodSymbolResolvingDelegate(new DummyAdapter(artifactScope));
    globalScope.addAdaptedETypeSymbolResolvingDelegate(new DummyAdapter(artifactScope));
    globalScope.addAdaptedEVariableSymbolResolvingDelegate(new DummyAdapter(artifactScope));
  }

  @Test
  public void incSuffixTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);
    Optional<ASTExpression> o = p.parse_StringExpression("4++");
    Optional<ASTExpression> r = p.parse_StringExpression("7.3++");
    Optional<ASTExpression> q = p.parse_StringExpression("8l++");
    Optional<ASTExpression> s = p.parse_StringExpression("\'a\'++");
    Optional<ASTExpression> t = p.parse_StringExpression("4.5f++");

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("long");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("float");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void decSuffixTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);
    Optional<ASTExpression> o = p.parse_StringExpression("4--");
    Optional<ASTExpression> r = p.parse_StringExpression("7.3--");
    Optional<ASTExpression> q = p.parse_StringExpression("8l--");
    Optional<ASTExpression> s = p.parse_StringExpression("\'a\'--");
    Optional<ASTExpression> t = p.parse_StringExpression("4.5f--");

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("long");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("float");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void incPrefixTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);
    Optional<ASTExpression> o = p.parse_StringExpression("++4");
    Optional<ASTExpression> r = p.parse_StringExpression("++7.3");
    Optional<ASTExpression> q = p.parse_StringExpression("++8l");
    Optional<ASTExpression> s = p.parse_StringExpression("++\'a\'");
    Optional<ASTExpression> t = p.parse_StringExpression("++4.5f");

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("long");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("float");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void decPrefixTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);
    Optional<ASTExpression> o = p.parse_StringExpression("--4");
    Optional<ASTExpression> r = p.parse_StringExpression("--7.3");
    Optional<ASTExpression> q = p.parse_StringExpression("--8l");
    Optional<ASTExpression> s = p.parse_StringExpression("--\'a\'");
    Optional<ASTExpression> t = p.parse_StringExpression("--4.5f");

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("long");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("float");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void plusPrefixTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);
    Optional<ASTExpression> o = p.parse_StringExpression("+4");
    Optional<ASTExpression> r = p.parse_StringExpression("+7.3");
    Optional<ASTExpression> q = p.parse_StringExpression("+8l");
    Optional<ASTExpression> s = p.parse_StringExpression("+\'a\'");
    Optional<ASTExpression> t = p.parse_StringExpression("+4.5f");

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("long");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("float");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void minusPrefixTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);
    Optional<ASTExpression> o = p.parse_StringExpression("-varint");
    Optional<ASTExpression> r = p.parse_StringExpression("-7.3");
    Optional<ASTExpression> q = p.parse_StringExpression("-8l");
    Optional<ASTExpression> s = p.parse_StringExpression("-\'a\'");
    Optional<ASTExpression> t = p.parse_StringExpression("-4.5f");

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("long");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("float");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void plusAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint+=3");
    Optional<ASTExpression> r = p.parse_StringExpression("vardouble+=5");
    Optional<ASTExpression> s = p.parse_StringExpression("vardouble+=12.7");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar+=12");
    Optional<ASTExpression> u = p.parse_StringExpression("varString+=varString");
    Optional<ASTExpression> v = p.parse_StringExpression("varchar+=13.3f");
    Optional<ASTExpression> w = p.parse_StringExpression("vardouble+=3L");
    Optional<ASTExpression> x = p.parse_StringExpression("varString+=13.2");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    SymTypeExpression exp2 = new SymObjectType();
    exp2.setName("String");
    assertTrue(u.isPresent());
    assertTrue(exp2.deepEquals(calc.calculateType(u.get())));

    exp.setName("char");
    assertTrue(v.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(v.get())));

    exp.setName("double");
    assertTrue(w.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(w.get())));

    exp2.setName("String");
    assertTrue(x.isPresent());
    assertTrue(exp2.deepEquals(calc.calculateType(x.get())));

  }

  @Test
  public void minusAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint-=3");
    Optional<ASTExpression> r = p.parse_StringExpression("vardouble-=5");
    Optional<ASTExpression> s = p.parse_StringExpression("vardouble-=12.7");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar-=12");
    Optional<ASTExpression> v = p.parse_StringExpression("varchar-=13.3f");
    Optional<ASTExpression> w = p.parse_StringExpression("vardouble-=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    assertTrue(v.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(v.get())));

    exp.setName("double");
    assertTrue(w.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(w.get())));
  }

  @Test
  public void multAssignmentTest() throws IOException {
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint*=3");
    Optional<ASTExpression> r = p.parse_StringExpression("vardouble*=5");
    Optional<ASTExpression> s = p.parse_StringExpression("vardouble*=12.7");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar*=12.7");
    Optional<ASTExpression> v = p.parse_StringExpression("varchar*=13.3f");
    Optional<ASTExpression> w = p.parse_StringExpression("vardouble*=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    assertTrue(v.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(v.get())));

    exp.setName("double");
    assertTrue(w.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(w.get())));
  }

  @Test
  public void divideAssignmentTest() throws IOException {
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint/=3");
    Optional<ASTExpression> r = p.parse_StringExpression("vardouble/=5");
    Optional<ASTExpression> s = p.parse_StringExpression("vardouble/=12.7");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar/=12.7");
    Optional<ASTExpression> v = p.parse_StringExpression("varchar/=13.3f");
    Optional<ASTExpression> w = p.parse_StringExpression("vardouble/=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    assertTrue(v.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(v.get())));

    exp.setName("double");
    assertTrue(w.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(w.get())));
  }

  @Test
  public void regularAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint=3.5f");
    Optional<ASTExpression> r = p.parse_StringExpression("vardouble=varint");
    Optional<ASTExpression> s = p.parse_StringExpression("vardouble=12.7");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar=12");
    Optional<ASTExpression> u = p.parse_StringExpression("varString=varString");
    Optional<ASTExpression> v = p.parse_StringExpression("varA=varB");
    Optional<ASTExpression> w = p.parse_StringExpression("varchar=\'a\'");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    SymTypeExpression exp2 = new SymObjectType();
    exp2.setName("String");
    assertTrue(u.isPresent());
    assertTrue(exp2.deepEquals(calc.calculateType(u.get())));

    exp2.setName("A");
    assertTrue(v.isPresent());
    assertTrue(exp2.deepEquals(calc.calculateType(v.get())));

    exp.setName("char");
    assertTrue(w.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(w.get())));
  }

  @Test
  public void andAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint&=3");
    Optional<ASTExpression> r = p.parse_StringExpression("varint&=varint");
    Optional<ASTExpression> q = p.parse_StringExpression("varboolean&=false");
    Optional<ASTExpression> s = p.parse_StringExpression("varint&=7L");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar&=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("boolean");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

  }

  @Test
  public void orAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint|=3");
    Optional<ASTExpression> r = p.parse_StringExpression("varint|=varint");
    Optional<ASTExpression> q = p.parse_StringExpression("varboolean|=false");
    Optional<ASTExpression> s = p.parse_StringExpression("varint|=7L");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar|=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("boolean");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void binaryXorAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint^=3");
    Optional<ASTExpression> r = p.parse_StringExpression("varint^=varint");
    Optional<ASTExpression> q = p.parse_StringExpression("varboolean^=varboolean");
    Optional<ASTExpression> s = p.parse_StringExpression("varint^=7L");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar^=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    exp.setName("boolean");
    assertTrue(q.isPresent());
    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    exp.setName("int");
    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void rightShiftAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint>>=3");
    Optional<ASTExpression> r = p.parse_StringExpression("varint>>=varint");
    Optional<ASTExpression> s = p.parse_StringExpression("varint>>=7L");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar>>=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void leftShiftAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint<<=3");
    Optional<ASTExpression> r = p.parse_StringExpression("varint<<=varint");
    Optional<ASTExpression> s = p.parse_StringExpression("varint<<=7L");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar<<=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void logicalRightAssignmentTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint>>>=3");
    Optional<ASTExpression> r = p.parse_StringExpression("varint>>>=varint");
    Optional<ASTExpression> s = p.parse_StringExpression("varint>>>=7L");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar>>>=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());
  }

  @Test
  public void moduloAssignmentTest() throws IOException {
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint%=3");
    Optional<ASTExpression> r = p.parse_StringExpression("vardouble%=5");
    Optional<ASTExpression> s = p.parse_StringExpression("vardouble%=12.7");
    Optional<ASTExpression> t = p.parse_StringExpression("varchar%=12.7");
    Optional<ASTExpression> v = p.parse_StringExpression("varchar%=13.3f");
    Optional<ASTExpression> w = p.parse_StringExpression("vardouble%=3L");
    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertTrue(o.isPresent());
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    exp.setName("double");
    assertTrue(r.isPresent());
    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertTrue(s.isPresent());
    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    exp.setName("char");
    assertTrue(t.isPresent());
    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    assertTrue(v.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(v.get())));

    exp.setName("double");
    assertTrue(w.isPresent());
    assertTrue(exp.deepEquals(calc.calculateType(w.get())));



  }


  @Test
  public void combineOperatorsTest() throws IOException{
    CombineExpressionsWithLiteralsParser p = new CombineExpressionsWithLiteralsParser();
    Optional<ASTExpression> o = p.parse_StringExpression("varint+=vardouble-=7");
    Optional<ASTExpression> q = p.parse_StringExpression("varint<<=varint*=9");
    Optional<ASTExpression> r = p.parse_StringExpression("varint>>>=varint+=4");
    Optional<ASTExpression> s = p.parse_StringExpression("varint%=vardouble-=3");
    Optional<ASTExpression> t = p.parse_StringExpression("varint&=varint|=3");
    Optional<ASTExpression> u = p.parse_StringExpression("vardouble=varint^=8");
    Optional<ASTExpression> v = p.parse_StringExpression("varint+=vardouble*=9.6");

    assertTrue(o.isPresent());
    assertTrue(q.isPresent());
    assertTrue(r.isPresent());
    assertTrue(s.isPresent());
    assertTrue(t.isPresent());
    assertTrue(u.isPresent());
    assertTrue(v.isPresent());

    CombineExpressionsWithLiteralsTypesCalculator calc = new CombineExpressionsWithLiteralsTypesCalculator(artifactScope);

    SymTypeExpression exp = new SymTypeConstant();
    exp.setName("int");
    assertEquals(exp.print(), calc.calculateType(o.get()).print());

    assertEquals(exp.print(), calc.calculateType(q.get()).print());

    assertEquals(exp.print(), calc.calculateType(r.get()).print());

    assertEquals(exp.print(), calc.calculateType(s.get()).print());

    assertEquals(exp.print(), calc.calculateType(t.get()).print());

    exp.setName("double");
    assertTrue(exp.deepEquals(calc.calculateType(u.get())));

    exp.setName("int");
    assertTrue(exp.deepEquals(calc.calculateType(v.get())));

  }

}
