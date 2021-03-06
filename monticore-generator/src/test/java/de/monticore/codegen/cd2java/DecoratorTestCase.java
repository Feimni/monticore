/* (c) https://github.com/MontiCore/monticore */
package de.monticore.codegen.cd2java;

import de.monticore.cd.cd4analysis.CD4AnalysisMill;
import de.monticore.cd.cd4analysis._ast.ASTCDCompilationUnit;
import de.monticore.cd.cd4analysis._parser.CD4AnalysisParser;
import de.monticore.cd.cd4analysis._symboltable.CD4AnalysisGlobalScope;
import de.monticore.cd.cd4analysis._symboltable.CD4AnalysisSymbolTableCreatorDelegator;
import de.monticore.io.paths.ModelPath;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.fail;

public abstract class DecoratorTestCase {

  private static final String MODEL_PATH = "src/test/resources/";

  public ASTCDCompilationUnit parse(String... names) {
    String qualifiedName = String.join("/", names);
    ModelPath modelPath = new ModelPath(Paths.get(MODEL_PATH));
    CD4AnalysisGlobalScope globalScope = new CD4AnalysisGlobalScope(modelPath, "cd");
    CD4AnalysisParser parser = new CD4AnalysisParser();
    Optional<ASTCDCompilationUnit> ast = null;
    try {
      ast = parser.parse(MODEL_PATH + qualifiedName + ".cd");
    } catch (IOException e) {
      fail(String.format("Failed to load model '%s'", qualifiedName));
    }
    if (!ast.isPresent()) {
      fail(String.format("Failed to load model '%s'", qualifiedName));
    }
    CD4AnalysisSymbolTableCreatorDelegator creator = CD4AnalysisMill.cD4AnalysisSymbolTableCreatorDelegatorBuilder()
            .setGlobalScope(globalScope).build();
    creator.createFromAST(ast.get());
    return ast.get();
  }
}
