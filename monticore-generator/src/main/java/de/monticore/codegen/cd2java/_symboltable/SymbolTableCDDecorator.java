package de.monticore.codegen.cd2java._symboltable;

import de.monticore.cd.cd4analysis._ast.*;
import de.monticore.codegen.cd2java.AbstractCreator;
import de.monticore.codegen.cd2java.CoreTemplates;
import de.monticore.codegen.cd2java._symboltable.scope.ScopeClassDecorator;
import de.monticore.codegen.cd2java._symboltable.symbol.SymbolBuilderDecorator;
import de.monticore.codegen.cd2java._symboltable.symbol.SymbolDecorator;
import de.monticore.generating.templateengine.GlobalExtensionManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static de.monticore.codegen.cd2java.CoreTemplates.PACKAGE;
import static de.monticore.codegen.cd2java.CoreTemplates.createPackageHookPoint;
import static de.monticore.codegen.cd2java._symboltable.SymbolTableConstants.SYMBOL_TABLE_PACKAGE;

public class SymbolTableCDDecorator extends AbstractCreator<ASTCDCompilationUnit, ASTCDCompilationUnit> {

  protected final SymbolDecorator symbolDecorator;

  protected final SymbolBuilderDecorator symbolBuilderDecorator;

  protected final SymbolTableService symbolTableService;

  protected final ScopeClassDecorator scopeClassDecorator;

  public SymbolTableCDDecorator(final GlobalExtensionManagement glex,
                                final SymbolTableService symbolTableService,
                                final SymbolDecorator symbolDecorator,
                                final SymbolBuilderDecorator symbolBuilderDecorator,
                                final ScopeClassDecorator scopeClassDecorator) {
    super(glex);
    this.symbolDecorator = symbolDecorator;
    this.symbolBuilderDecorator = symbolBuilderDecorator;
    this.symbolTableService = symbolTableService;
    this.scopeClassDecorator = scopeClassDecorator;
  }

  @Override
  public ASTCDCompilationUnit decorate(ASTCDCompilationUnit ast) {
    List<String> symbolTablePackage = new ArrayList<>(ast.getPackageList());
    symbolTablePackage.addAll(Arrays.asList(ast.getCDDefinition().getName().toLowerCase(), SYMBOL_TABLE_PACKAGE));

    List<ASTCDClass> symbolClasses = symbolTableService.getSymbolClasses(ast.getCDDefinition().getCDClassList());
    List<ASTCDInterface> symbolInterfaces = symbolTableService.getSymbolInterfaces(ast.getCDDefinition().getCDInterfaceList());
    List<ASTCDClass> scopeClasses = symbolTableService.getScopeClasses(ast.getCDDefinition().getCDClassList());
    List<ASTCDInterface> scopeInterfaces = symbolTableService.getScopeInterfaces(ast.getCDDefinition().getCDInterfaceList());

    List<ASTCDClass> decoratedSymbolClasses = createSymbolClasses(symbolClasses);
    List<ASTCDClass> decoratedSymbolInterfaces = createSymbolClasses(symbolInterfaces);

    ASTCDDefinition astCD = CD4AnalysisMill.cDDefinitionBuilder()
        .setName(ast.getCDDefinition().getName())
        .addAllCDClasss(decoratedSymbolClasses)
        .addAllCDClasss(createSymbolBuilderClasses(decoratedSymbolClasses))
        .addAllCDClasss(decoratedSymbolInterfaces)
        .addAllCDClasss(createSymbolBuilderClasses(decoratedSymbolInterfaces))
        .addCDClass(createScopeClass(ast))
        .build();

    for (ASTCDClass cdClass : astCD.getCDClassList()) {
      this.replaceTemplate(PACKAGE, cdClass, createPackageHookPoint(symbolTablePackage));
    }

    for (ASTCDInterface cdInterface : astCD.getCDInterfaceList()) {
      this.replaceTemplate(CoreTemplates.PACKAGE, cdInterface, createPackageHookPoint(symbolTablePackage));
    }

    for (ASTCDEnum cdEnum : astCD.getCDEnumList()) {
      this.replaceTemplate(CoreTemplates.PACKAGE, cdEnum, createPackageHookPoint(symbolTablePackage));
    }

    return CD4AnalysisMill.cDCompilationUnitBuilder()
        .setPackageList(symbolTablePackage)
        .setCDDefinition(astCD)
        .build();
  }

  protected List<ASTCDClass> createSymbolClasses(List<? extends ASTCDType> astcdTypeList) {
    return astcdTypeList
        .stream()
        .map(symbolDecorator::decorate)
        .collect(Collectors.toList());
  }

  protected List<ASTCDClass> createSymbolBuilderClasses(List<ASTCDClass> symbolASTClasses) {
    return symbolASTClasses
        .stream()
        .map(symbolBuilderDecorator::decorate)
        .collect(Collectors.toList());
  }

  protected ASTCDClass createScopeClass(ASTCDCompilationUnit astcdCompilationUnit) {
    return scopeClassDecorator.decorate(astcdCompilationUnit);
  }
}
