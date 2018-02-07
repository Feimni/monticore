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

package mc.feature.ast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import de.monticore.MontiCoreScript;
import de.monticore.grammar.grammar._ast.ASTMCGrammar;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import mc.feature.delete.deletetest._ast.ASTChild;
import mc.feature.delete.deletetest._ast.ASTParent;
import mc.feature.delete.deletetest._ast.DeleteTestNodeFactory;
import mc.feature.featuredsl._ast.ASTA;
import mc.feature.featuredsl._ast.FeatureDSLNodeFactory;

public class ASTTest {
  
  @BeforeClass
  public static void setup() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Test
  public void testGet_ChildNodes1() {
    List<ASTA> aList = new ArrayList<>();
    ASTA a = FeatureDSLNodeFactory.createASTA();
    assertEquals(0, aList.size());
    aList.add(a);
    assertEquals(1, aList.size());
  }
  
  @Test
  public void testGet_ChildNodes2() {
    ASTParent p = DeleteTestNodeFactory.createASTParent();
    ASTChild s = DeleteTestNodeFactory.createASTChild();
    p.addChild(s);
    p.setSon(s);
    assertEquals(1, p.getChildList().size());
    assertTrue(p.containsChilds(s));
  }
  
  @Test
  public void testFileNameInSourcePosition() {
    String grammarToTest = "src/test/resources/mc/grammar/SimpleGrammarWithConcept.mc4";
    
    Path model = Paths.get(new File(
        grammarToTest).getAbsolutePath());
    
    MontiCoreScript mc = new MontiCoreScript();
    Optional<ASTMCGrammar> ast = mc.parseGrammar(model);
    assertTrue(ast.isPresent());
    ASTMCGrammar clonedAst = ast.get().deepClone();
    assertTrue(clonedAst.get_SourcePositionStart().getFileName().isPresent());
    assertEquals("SimpleGrammarWithConcept.mc4", FilenameUtils.getName(clonedAst.get_SourcePositionStart().getFileName().get()));
  }
  
}
