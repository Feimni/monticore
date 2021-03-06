/* (c) https://github.com/MontiCore/monticore */

package mc.embedding;

import de.monticore.io.paths.ModelPath;
import mc.GeneratorIntegrationsTest;
import mc.embedding.embedded.EmbeddedMill;
import mc.embedding.embedded._symboltable.EmbeddedGlobalScope;
import mc.embedding.embedded._symboltable.IEmbeddedGlobalScope;
import mc.embedding.embedded._symboltable.TextSymbol;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmbeddedTest extends GeneratorIntegrationsTest {

  @Test
  public void test() {
    final ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/mc/embedding"));
    final IEmbeddedGlobalScope scope = EmbeddedMill
        .embeddedGlobalScopeBuilder()
        .setModelPath(modelPath)
        .setModelFileExtension("embedded")
        .build();

    final TextSymbol textSymbol = scope.resolveText("E").orElse(null);
    assertNotNull(textSymbol);
    assertEquals("E", textSymbol.getName());
  }

}
