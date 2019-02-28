package de.monticore.codegen.cd2java.builder;

import de.monticore.codegen.cd2java.CoreTemplates;
import de.monticore.codegen.cd2java.DecoratorTestCase;
import de.monticore.generating.GeneratorEngine;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.umlcd4a.cd4analysis._ast.*;
import de.monticore.umlcd4a.cd4analysis._parser.CD4AnalysisParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static de.monticore.codegen.cd2java.DecoratorTestUtil.getClassBy;
import static org.junit.Assert.assertEquals;

public class BuilderASTNodeDecoratorTest extends DecoratorTestCase {

  private final GlobalExtensionManagement glex = new GlobalExtensionManagement();

  private ASTCDClass builderClass;

  @Before
  public void setup() throws IOException {
    LogStub.init();
    ASTCDCompilationUnit ast = parse("de", "monticore", "codegen", "builder", "ASTNodeBuilder");
    ASTCDClass cdClass = getClassBy("A", ast);

    BuilderDecorator builderDecorator = new BuilderDecorator(glex);
    ASTNodeBuilderDecorator builderASTNodeDecorator = new ASTNodeBuilderDecorator(glex, builderDecorator);
    this.builderClass = builderASTNodeDecorator.decorate(cdClass);
  }

  @Test
  public void testClassName() {
    assertEquals("ABuilder", builderClass.getName());
  }

  @Test
  public void testSuperClassName() {
    assertEquals("de.monticore.ast.ASTNodeBuilder<ABuilder>", builderClass.printSuperClass());
  }

  @Test
  public void testMethods() {
    assertEquals(47, builderClass.getCDMethodList().size());
  }

  @Test
  public void testGeneratedCode() {
    GeneratorSetup generatorSetup = new GeneratorSetup();
    generatorSetup.setGlex(glex);
    GeneratorEngine generatorEngine = new GeneratorEngine(generatorSetup);
    StringBuilder sb = generatorEngine.generate(CoreTemplates.CLASS, builderClass, builderClass);
    System.out.println(sb.toString());
  }
}
