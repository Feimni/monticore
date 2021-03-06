/* (c) https://github.com/MontiCore/monticore */
package de.monticore.grammar.grammar_withconcepts._symboltable;

public class Grammar_WithConceptsSymbolTableCreatorDelegator extends Grammar_WithConceptsSymbolTableCreatorDelegatorTOP {
  public Grammar_WithConceptsSymbolTableCreatorDelegator(
      IGrammar_WithConceptsGlobalScope globalScope) {
    super(globalScope);
  }
  
  public Grammar_WithConceptsArtifactScope createFromAST(de.monticore.grammar.grammar._ast.ASTMCGrammar rootNode) {
    Grammar_WithConceptsArtifactScope as =  symbolTable.createFromAST(rootNode);
    if (!as.getPackageName().isEmpty()){
      globalScope.addLoadedFile(as.getPackageName() + "." + as.getName());
    } else {
      globalScope.addLoadedFile(as.getName());
    }
    return as;
  }
}
