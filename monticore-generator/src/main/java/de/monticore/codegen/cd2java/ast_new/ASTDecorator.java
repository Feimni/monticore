package de.monticore.codegen.cd2java.ast_new;

import de.monticore.ast.ASTCNode;
import de.monticore.codegen.cd2java.AbstractDecorator;
import de.monticore.codegen.cd2java.factories.*;
import de.monticore.codegen.cd2java.factory.NodeFactoryService;
import de.monticore.codegen.cd2java.visitor_new.VisitorService;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.generating.templateengine.StringHookPoint;
import de.monticore.generating.templateengine.TemplateHookPoint;
import de.monticore.types.TypesPrinter;
import de.monticore.types.types._ast.ASTType;
import de.monticore.umlcd4a.cd4analysis._ast.*;

import java.util.ArrayList;
import java.util.List;

import static de.monticore.codegen.cd2java.CoreTemplates.EMPTY_BODY;
import static de.monticore.codegen.cd2java.ast_new.ASTConstants.ACCEPT_METHOD;
import static de.monticore.codegen.cd2java.ast_new.ASTConstants.CONSTRUCT_METHOD;
import static de.monticore.codegen.cd2java.factories.CDModifier.PROTECTED;
import static de.monticore.codegen.cd2java.factories.CDModifier.PUBLIC;


public class ASTDecorator extends AbstractDecorator<ASTCDClass, ASTCDClass> {

  private static final String VISITOR = "visitor";

  private final ASTService astService;

  private final VisitorService visitorService;

  private final NodeFactoryService nodeFactoryService;

  public ASTDecorator(final GlobalExtensionManagement glex,
      final ASTService astService,
      final VisitorService visitorService,
      final NodeFactoryService nodeFactoryService) {
    super(glex);
    this.astService = astService;
    this.visitorService = visitorService;
    this.nodeFactoryService = nodeFactoryService;
  }

  @Override
  public ASTCDClass decorate(ASTCDClass clazz) {
    if (!clazz.isPresentSuperclass()) {
      clazz.setSuperclass(this.getCDTypeFactory().createSimpleReferenceType(ASTCNode.class));
    }

    clazz.addInterface(this.astService.getASTBaseInterface());

    clazz.addCDMethod(createAcceptMethod(clazz));
    clazz.addAllCDMethods(createAcceptSuperMethods(clazz));
    clazz.addCDMethod(getConstructMethod(clazz));

    return clazz;
  }

  protected ASTCDMethod createAcceptMethod(ASTCDClass astClass) {
    ASTCDParameter visitorParameter = this.getCDParameterFactory().createParameter(this.visitorService.getVisitorType(), VISITOR);
    ASTCDMethod acceptMethod = this.getCDMethodFactory().createMethod(PUBLIC, ACCEPT_METHOD, visitorParameter);
    this.replaceTemplate(EMPTY_BODY, acceptMethod, new TemplateHookPoint("ast_new.Accept", astClass));
    return acceptMethod;
  }

  protected List<ASTCDMethod> createAcceptSuperMethods(ASTCDClass astClass) {
    List<ASTCDMethod> result = new ArrayList<>();
    //accept methods for super visitors
    for (ASTType superVisitorType : this.visitorService.getAllVisitorTypesInHierarchy()) {
      ASTCDParameter superVisitorParameter = this.getCDParameterFactory().createParameter(superVisitorType, VISITOR);

      ASTCDMethod superAccept = this.getCDMethodFactory().createMethod(PUBLIC, ACCEPT_METHOD, superVisitorParameter);
      String errorCode = DecorationHelper.getGeneratedErrorCode(astClass);
      this.replaceTemplate(EMPTY_BODY, superAccept, new TemplateHookPoint("ast_new.AcceptSuper",
          this.visitorService.getVisitorFullTypeName(), errorCode, astClass.getName(), TypesPrinter.printType(superVisitorType)));
      result.add(superAccept);
    }
    return result;
  }

  protected ASTCDMethod getConstructMethod(ASTCDClass astClass) {
    ASTType classType = this.getCDTypeFactory().createSimpleReferenceType(astClass.getName());
    ASTCDMethod constructMethod = this.getCDMethodFactory().createMethod(PROTECTED, classType, CONSTRUCT_METHOD);
    this.replaceTemplate(EMPTY_BODY, constructMethod, new StringHookPoint(this.nodeFactoryService.getCreateInvocation(astClass)));
    return constructMethod;
  }
}
