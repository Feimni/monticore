/* (c) https://github.com/MontiCore/monticore */
package de.monticore.codegen.cd2java._symboltable.scope;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import de.monticore.cd.cd4analysis._ast.*;
import de.monticore.cd.prettyprint.CD4CodePrinter;
import de.monticore.codegen.cd2java.AbstractService;
import de.monticore.codegen.cd2java.CoreTemplates;
import de.monticore.codegen.cd2java.DecorationHelper;
import de.monticore.codegen.cd2java.DecoratorTestCase;
import de.monticore.codegen.cd2java._symboltable.SymbolTableService;
import de.monticore.codegen.cd2java.methods.MethodDecorator;
import de.monticore.generating.GeneratorEngine;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.types.MCTypeFacade;
import de.se_rwth.commons.logging.*;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static de.monticore.cd.facade.CDModifier.*;
import static de.monticore.codegen.cd2java.DecoratorAssert.*;
import static de.monticore.codegen.cd2java.DecoratorTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GlobalScopeClassDecoratorTest extends DecoratorTestCase {

  private ASTCDClass scopeClass;

  private GlobalExtensionManagement glex;

  private ASTCDCompilationUnit decoratedCompilationUnit;

  private ASTCDCompilationUnit originalCompilationUnit;

  private MCTypeFacade mcTypeFacade;

  private static final String MODEL_PATH = "de.monticore.io.paths.ModelPath";

  private static final String AUTOMATON_SCOPE = "de.monticore.codegen.ast.automaton._symboltable.AutomatonScope";

  private static final String I_AUTOMATON_SCOPE = "de.monticore.codegen.ast.automaton._symboltable.IAutomatonScope";

  private static final String AUTOMATON_SYMBOL = "de.monticore.codegen.ast.automaton._symboltable.AutomatonSymbol";

  private static final String ACCESS_MODIFIER = "de.monticore.symboltable.modifiers.AccessModifier";

  private static final String PREDICATE_AUTOMATON = "java.util.function.Predicate<de.monticore.codegen.ast.automaton._symboltable.AutomatonSymbol>";

  private static final String QUALIFIED_NAME_SYMBOL = "de.monticore.codegen.ast.lexicals._symboltable.QualifiedNameSymbol";

  private static final String PREDICATE_QUALIFIED_NAME = "java.util.function.Predicate<de.monticore.codegen.ast.lexicals._symboltable.QualifiedNameSymbol>";

  @Before
  public void setUp() {
    LogStub.init();         // replace log by a sideffect free variant
        // LogStub.initPlusLog();  // for manual testing purpose only
    this.glex = new GlobalExtensionManagement();
    this.glex.setGlobalValue("astHelper", DecorationHelper.getInstance());
    this.glex.setGlobalValue("cdPrinter", new CD4CodePrinter());

    this.mcTypeFacade = MCTypeFacade.getInstance();
    decoratedCompilationUnit = this.parse("de", "monticore", "codegen", "ast", "Automaton");
    originalCompilationUnit = decoratedCompilationUnit.deepClone();
    this.glex.setGlobalValue("service", new AbstractService(decoratedCompilationUnit));

    GlobalScopeClassDecorator decorator = new GlobalScopeClassDecorator(this.glex,
        new SymbolTableService(decoratedCompilationUnit),
        new MethodDecorator(glex, new SymbolTableService(decoratedCompilationUnit)));

    this.scopeClass = decorator.decorate(decoratedCompilationUnit);
  }

  @Test
  public void testCompilationUnitNotChanged() {
    assertDeepEquals(originalCompilationUnit, decoratedCompilationUnit);
  }

  @Test
  public void testClassName() {
    assertEquals("AutomatonGlobalScope", scopeClass.getName());
  }

  @Test
  public void testSuperInterfacesCount() {
    assertEquals(1, scopeClass.sizeInterface());
  }

  @Test
  public void testSuperInterfaces() {
    assertDeepEquals("de.monticore.codegen.ast.automaton._symboltable.IAutomatonGlobalScope",
        scopeClass.getInterface(0));
  }

  @Test
  public void testSuperClass() {
    assertDeepEquals(AUTOMATON_SCOPE, scopeClass.getSuperclass());
  }

  @Test
  public void testConstructorCount() {
    assertEquals(1, scopeClass.sizeCDConstructors());
  }

  @Test
  public void testConstructors() {
    // this(modelPath, modelFileExtension)
    ASTCDConstructor cdConstructor = scopeClass.getCDConstructor(0);
    assertDeepEquals(PUBLIC, cdConstructor.getModifier());
    assertEquals("AutomatonGlobalScope", cdConstructor.getName());

    assertEquals(2, cdConstructor.sizeCDParameters());
    assertDeepEquals(MODEL_PATH, cdConstructor.getCDParameter(0).getMCType());
    assertEquals("modelPath", cdConstructor.getCDParameter(0).getName());

    assertDeepEquals("String", cdConstructor.getCDParameter(1).getMCType());
    assertEquals("modelFileExtension", cdConstructor.getCDParameter(1).getName());

    assertTrue(cdConstructor.isEmptyException());
  }

  @Test
  public void testAttributeSize() {
    assertEquals(8, scopeClass.sizeCDAttributes());
  }

  @Test
  public void testModelPathAttribute() {
    ASTCDAttribute astcdAttribute = getAttributeBy("modelPath", scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals(MODEL_PATH, astcdAttribute.getMCType());
  }

  @Test
  public void testModelLoaderAttribute() {
    ASTCDAttribute astcdAttribute = getAttributeBy("modelLoader", scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals("Optional<AutomatonModelLoader>", astcdAttribute.getMCType());
  }

  @Test
  public void testFileExtensionAttribute() {
    ASTCDAttribute astcdAttribute = getAttributeBy("modelFileExtension", scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals("String", astcdAttribute.getMCType());
  }

  @Test
  public void testLoadModelsForMethod() {
    ASTCDMethod method = getMethodBy("loadModelsForAutomaton", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());
    assertEquals(1, method.sizeCDParameters());
    assertDeepEquals(String.class, method.getCDParameter(0).getMCType());
    assertEquals("name", method.getCDParameter(0).getName());
  }



  @Test
  public void testLoadModelsForSuperProdMethod() {
    ASTCDMethod method = getMethodBy("loadModelsForQualifiedName", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());
    assertEquals(1, method.sizeCDParameters());
    assertDeepEquals(String.class, method.getCDParameter(0).getMCType());
    assertEquals("name", method.getCDParameter(0).getName());
  }

  @Test
  public void testCacheAttribute(){
    ASTCDAttribute astcdAttribute = getAttributeBy("cache", scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals("Set<String>", astcdAttribute.getMCType());
  }

  @Test
  public void testScopeDeSerAttribute(){
    ASTCDAttribute astcdAttribute = getAttributeBy("scopeDeSer", scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals("AutomatonScopeDeSer", astcdAttribute.getMCType());
  }

  @Test
  public void testAdaptedAutomatonSymbolResolvingDelegateListAttribute() {
    ASTCDAttribute astcdAttribute = getAttributeBy("adaptedAutomatonSymbolResolvingDelegate",
        scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals(
        "List<de.monticore.codegen.ast.automaton._symboltable.IAutomatonSymbolResolvingDelegate>",
        astcdAttribute.getMCType());
  }

  @Test
  public void testAdaptedStateSymbolResolvingDelegateListAttribute() {
    ASTCDAttribute astcdAttribute = getAttributeBy("adaptedStateSymbolResolvingDelegate",
        scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals(
        "List<de.monticore.codegen.ast.automaton._symboltable.IStateSymbolResolvingDelegate>",
        astcdAttribute.getMCType());
  }

  @Test
  public void testAdaptedQualifiedNameSymbolResolvingDelegateListAttribute() {
    ASTCDAttribute astcdAttribute = getAttributeBy("adaptedQualifiedNameSymbolResolvingDelegate",
        scopeClass);
    assertDeepEquals(PROTECTED, astcdAttribute.getModifier());
    assertDeepEquals(
        "List<de.monticore.codegen.ast.lexicals._symboltable.IQualifiedNameSymbolResolvingDelegate>",
        astcdAttribute.getMCType());
  }

  @Test
  public void testMethodCount() {
    assertEquals(30, scopeClass.getCDMethodList().size());
  }

  @Test
  public void testGetModelPathMethod() {
    ASTCDMethod method = getMethodBy("getModelPath", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertDeepEquals(MODEL_PATH, method.getMCReturnType().getMCType());

    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testGetModelLoaderMethod() {
    ASTCDMethod method = getMethodBy("getModelLoader", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertDeepEquals("AutomatonModelLoader", method.getMCReturnType().getMCType());

    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testSetModelLoaderMethod() {
    ASTCDMethod method = getMethodBy("setModelLoader", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());

    assertEquals(1, method.sizeCDParameters());
    assertDeepEquals("AutomatonModelLoader", method.getCDParameter(0).getMCType());
    assertEquals("modelLoader", method.getCDParameter(0).getName());
  }

  @Test
  public void testSetModelLoaderAbsentMethod() {
    ASTCDMethod method = getMethodBy("setModelLoaderAbsent", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());
    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testIsPresentModelLoaderMethod() {
    ASTCDMethod method = getMethodBy("isPresentModelLoader", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertDeepEquals("boolean", method.getMCReturnType().getMCType());

    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testAddLoadedFileMethod() {
    ASTCDMethod method = getMethodBy("addLoadedFile", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());

    assertEquals(1, method.sizeCDParameters());
    assertDeepEquals(String.class, method.getCDParameter(0).getMCType());
    assertEquals("name", method.getCDParameter(0).getName());
  }

  @Test
  public void testSetAdaptedAutomatonSymbolResolvingDelegateListMethod() {
    ASTCDMethod method = getMethodBy("setAdaptedAutomatonSymbolResolvingDelegateList", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());

    assertEquals(1, method.sizeCDParameters());
    assertDeepEquals(
        "List<de.monticore.codegen.ast.automaton._symboltable.IAutomatonSymbolResolvingDelegate>",
        method.getCDParameter(0).getMCType());
    assertEquals("adaptedAutomatonSymbolResolvingDelegate", method.getCDParameter(0).getName());
  }

  @Test
  public void testGetAdaptedAutomatonSymbolResolvingDelegateListMethod() {
    ASTCDMethod method = getMethodBy("getAdaptedAutomatonSymbolResolvingDelegateList", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertDeepEquals(
        "List<de.monticore.codegen.ast.automaton._symboltable.IAutomatonSymbolResolvingDelegate>",
        method.getMCReturnType().getMCType());
    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testIsStateSymbolsAlreadyResolvedMethod() {
    ASTCDMethod method = getMethodBy("isStateSymbolsAlreadyResolved", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertBoolean(method.getMCReturnType().getMCType());

    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testIsAutomatonSymbolsAlreadyResolvedMethod() {
    ASTCDMethod method = getMethodBy("isAutomatonSymbolsAlreadyResolved", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertBoolean(method.getMCReturnType().getMCType());

    assertTrue(method.isEmptyCDParameters());
  }

  @Test
  public void testSetAutomatonSymbolsAlreadyResolvedMethod() {
    ASTCDMethod method = getMethodBy("setAutomatonSymbolsAlreadyResolved", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());
    assertEquals(1, method.sizeCDParameters());
    assertBoolean(method.getCDParameter(0).getMCType());
    assertEquals("automatonSymbolsAlreadyResolved", method.getCDParameter(0).getName());
  }

  @Test
  public void testSetStateSymbolsAlreadyResolvedMethod() {
    ASTCDMethod method = getMethodBy("setStateSymbolsAlreadyResolved", scopeClass);

    assertDeepEquals(PUBLIC, method.getModifier());
    assertTrue(method.getMCReturnType().isPresentMCVoidType());
    assertEquals(1, method.sizeCDParameters());
    assertBoolean(method.getCDParameter(0).getMCType());
    assertEquals("stateSymbolsAlreadyResolved", method.getCDParameter(0).getName());
  }

  @Test
  public void testGeneratedCode() {
    GeneratorSetup generatorSetup = new GeneratorSetup();
    generatorSetup.setGlex(glex);
    GeneratorEngine generatorEngine = new GeneratorEngine(generatorSetup);
    StringBuilder sb = generatorEngine.generate(CoreTemplates.CLASS, scopeClass, scopeClass);
    // test parsing
    ParserConfiguration configuration = new ParserConfiguration();
    JavaParser parser = new JavaParser(configuration);
    ParseResult parseResult = parser.parse(sb.toString());
    assertTrue(parseResult.isSuccessful());
  }

}
