/* (c) https://github.com/MontiCore/monticore */
package de.monticore.codegen.cd2java._symboltable.scope;

import de.monticore.cd.cd4analysis._ast.ASTCDAttribute;
import de.monticore.cd.cd4analysis._ast.ASTCDClass;
import de.monticore.cd.cd4analysis._ast.ASTCDMethod;
import de.monticore.codegen.cd2java.AbstractCreator;
import de.monticore.codegen.cd2java._ast.builder.BuilderDecorator;
import de.monticore.codegen.cd2java._ast.builder.buildermethods.BuilderMutatorMethodDecorator;
import de.monticore.codegen.cd2java._symboltable.SymbolTableService;
import de.monticore.codegen.cd2java.methods.AccessorDecorator;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.generating.templateengine.StringHookPoint;
import de.monticore.generating.templateengine.TemplateHookPoint;
import de.monticore.types.mcbasictypes.MCBasicTypesMill;

import java.util.List;
import java.util.Optional;

import static de.monticore.cd.facade.CDModifier.PROTECTED;
import static de.monticore.codegen.cd2java.CoreTemplates.EMPTY_BODY;
import static de.monticore.codegen.cd2java.CoreTemplates.VALUE;
import static de.monticore.codegen.cd2java._ast.builder.BuilderConstants.BUILDER_SUFFIX;
import static de.monticore.codegen.cd2java._ast.builder.BuilderConstants.BUILD_METHOD;
import static de.monticore.codegen.cd2java._symboltable.SymbolTableConstants.ENCLOSING_SCOPE_VAR;


public class ArtifactScopeClassBuilderDecorator extends AbstractCreator<ASTCDClass, ASTCDClass> {

  protected BuilderDecorator builderDecorator;

  protected final SymbolTableService symbolTableService;

  protected final AccessorDecorator accessorDecorator;

  protected static final String TEMPLATE_PATH = "_symboltable.artifactscope.";

  public ArtifactScopeClassBuilderDecorator(final GlobalExtensionManagement glex,
                                       final SymbolTableService symbolTableService,
                                       final BuilderDecorator builderDecorator,
                                       final AccessorDecorator accessorDecorator) {
    super(glex);
    this.symbolTableService = symbolTableService;
    this.builderDecorator = builderDecorator;
    this.accessorDecorator = accessorDecorator;
  }

  @Override
  public ASTCDClass decorate(ASTCDClass scopeClass) {
    ASTCDClass decoratedScopeClass = scopeClass.deepClone();
    String scopeBuilderName = scopeClass.getName() + BUILDER_SUFFIX;

    decoratedScopeClass.getCDMethodList().clear();

    builderDecorator.setPrintBuildMethodTemplate(false);
    ASTCDClass scopeBuilder = builderDecorator.decorate(decoratedScopeClass);
    builderDecorator.setPrintBuildMethodTemplate(true);

    scopeBuilder.getCDAttributeList().forEach(a -> a.setModifier(PROTECTED.build()));
    scopeBuilder.setName(scopeBuilderName);

    // new build method template
    Optional<ASTCDMethod> buildMethod = scopeBuilder.getCDMethodList()
        .stream()
        .filter(m -> BUILD_METHOD.equals(m.getName()))
        .findFirst();
    if (buildMethod.isPresent()) {
      this.replaceTemplate(EMPTY_BODY, buildMethod.get(),
          new TemplateHookPoint(TEMPLATE_PATH + "BuildArtifactScope", scopeClass.getName()));
      buildMethod.get().setMCReturnType(MCBasicTypesMill.mCReturnTypeBuilder()
          .setMCType(getMCTypeFacade().createQualifiedType("I"+ buildMethod.get().printReturnType()))
          .build());
    }

    BuilderMutatorMethodDecorator builderMutatorMethodDecorator = new BuilderMutatorMethodDecorator(glex,
        getMCTypeFacade().createQualifiedType(scopeBuilderName));

    ASTCDAttribute enclosingScopeAttribute = createEnclosingScopeAttribute();
    List<ASTCDMethod> enclosingScopeMethods = builderMutatorMethodDecorator.decorate(enclosingScopeAttribute);
    enclosingScopeMethods.addAll(accessorDecorator.decorate(enclosingScopeAttribute));

    scopeBuilder.addCDAttribute(enclosingScopeAttribute);
    scopeBuilder.addAllCDMethods(enclosingScopeMethods);

    return scopeBuilder;
  }


  protected ASTCDAttribute createEnclosingScopeAttribute() {
    ASTCDAttribute enclosingScope = this.getCDAttributeFacade().createAttribute(PROTECTED,
        getMCTypeFacade().createOptionalTypeOf(symbolTableService.getScopeInterfaceType()), ENCLOSING_SCOPE_VAR);
    this.replaceTemplate(VALUE, enclosingScope, new StringHookPoint("= Optional.empty()"));
    return enclosingScope;
  }

}
