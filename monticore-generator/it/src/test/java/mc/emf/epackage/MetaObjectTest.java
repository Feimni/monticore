/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package mc.emf.epackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Test;

import mc.GeneratorIntegrationsTest;
import mc.feature.fautomaton.action.expression._ast.ASTAssignment;
import mc.feature.fautomaton.action.expression._ast.ExpressionPackage;
import mc.feature.fautomaton.automaton.flatautomaton._ast.ASTAutomaton;
import mc.feature.fautomaton.automaton.flatautomaton._ast.FlatAutomatonPackage;

public class MetaObjectTest extends GeneratorIntegrationsTest {
  
  @Test
  public void testSuperTypes() {
    EClass compAssig = ExpressionPackage.eINSTANCE.getComplexAssigment();
    
    EList<EClass> supertypes = compAssig.getEAllSuperTypes();
    
    assertTrue(supertypes.contains(ExpressionPackage.eINSTANCE.getAssignment()));
    assertTrue(supertypes.contains(ExpressionPackage.eINSTANCE.getValue()));
    assertTrue(supertypes.contains(ExpressionPackage.eINSTANCE.getExpression()));
  }
  
  @Test
  public void testEClass() {
    EClass exp = ExpressionPackage.eINSTANCE.getExpression();
    EClass incExp = (EClass) ExpressionPackage.eINSTANCE
        .getEClassifier("ASTIncreaseExpression");
    EClass assig = (EClass) ExpressionPackage.eINSTANCE.getEClassifiers()
        .get(ExpressionPackage.ASTAssignment);
        
    assertEquals(ExpressionPackage.eINSTANCE.getExpression(), exp);
    assertEquals(ExpressionPackage.eINSTANCE.getIncreaseExpression(), incExp);
    assertEquals(ExpressionPackage.eINSTANCE.getAssignment(), assig);
    
    assertTrue(exp.isInterface());
    assertFalse(incExp.isInterface());
    
    assertEquals(ASTAssignment.class, assig.getInstanceClass());
    assertEquals(3, assig
        .getFeatureCount());
        
  }
  
  @Test
  public void testEDataType() {
    EDataType eVector = ExpressionPackage.eINSTANCE.getEVector();
    
    assertEquals(java.util.Vector.class, eVector.getInstanceClass());
  }
  
  @Test
  public void testEAttribute() {
    EAttribute varName = ExpressionPackage.eINSTANCE.getASTExpression_Varname();
    
    assertEquals(varName.getFeatureID(),
        ExpressionPackage.ASTDecreaseExpression_Varname);
    assertEquals(EcorePackage.Literals.ESTRING, varName.getEType());
    assertEquals("Varname", varName.getName());
  }
  
  @Test
  public void testEReference() {
    EReference state = FlatAutomatonPackage.eINSTANCE.getASTAutomaton_StateList();
    
    // check feature ids
    assertEquals(state.getFeatureID(),
        FlatAutomatonPackage.ASTAutomaton_StateList);
        
    assertEquals(FlatAutomatonPackage.eINSTANCE.getState(), state
        .getEReferenceType());
    assertTrue(state.isMany());
    assertTrue(state.isContainment());
    
    assertEquals(ASTAutomaton.class, state.getContainerClass());
    assertEquals(-1, state.getUpperBound());
    
  }
  
}
