/* (c) https://github.com/MontiCore/monticore */

package de.monticore.grammar.transformation;

import de.monticore.grammar.grammar._ast.*;
import de.monticore.grammar.grammar_withconcepts._parser.Grammar_WithConceptsParser;
import de.monticore.utils.ASTTraverser;
import de.se_rwth.commons.StringTransformations;
import de.se_rwth.commons.logging.Log;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static de.monticore.codegen.mc2cd.TransformationHelper.AST_PREFIX;
import static de.monticore.codegen.mc2cd.TransformationHelper.simpleName;

/**
 * Static facade for the transformation of MC AST.
 */
public class GrammarTransformer {

  private GrammarTransformer() {
    // noninstantiable
  }

  public static void transform(ASTMCGrammar grammar) {
    removeNonTerminalSeparators(grammar);
    uncapitalizeMultivaluedAttributes(grammar);
  }

  /**
   * The shortcut NonTerminalSeparator is replaced by the detailed description.
   * Example: List(Element || ',')* ==> (List:Element (',' List:Element)+)
   */
  public static void removeNonTerminalSeparators(ASTMCGrammar grammar) {
    Map<ASTNonTerminalSeparator, ASTAlt> map = new HashMap<ASTNonTerminalSeparator, ASTAlt>();
    RuleComponentListFinder componentListTransformer = new RuleComponentListFinder(map);
    ASTTraverser traverser = new ASTTraverser(componentListTransformer);

    // execute the search
    traverser.traverse(grammar);

    // execute the transformation
    for (Entry<ASTNonTerminalSeparator, ASTAlt> entry : map.entrySet()) {
      Log.debug("Find NonTerminalSeparator", GrammarTransformer.class.getName());
      Optional<ASTBlock> block = transform(entry.getKey());
      if (block.isPresent()) {
        ASTAlt parent = entry.getValue();
        int ind = parent.getComponentList().indexOf(entry.getKey());
        if (ind >= 0) {
          Log.debug("Remove NonTerminalSeparator", GrammarTransformer.class.getName());
          parent.getComponentList().remove(ind);
          Log.debug("Added new generated block", GrammarTransformer.class.getName());
          parent.getComponentList().add(ind, block.get());
        } else {
          Log.error("0xA1009 Can't transform grammar");
        }
      }
    }
  }

  /**
   * Append suffix "List" to the names of multi-valued att * Append suffix "List" to
   * the names of multi-valued attributes (NonTerminals and attributesinAst) if
   * no usage names were set. Examples: Name ("." Name&)* ==> names:Name ("."
   * names:Name&)* (State | Transition)* ==> (states:State |
   * transitions:Transition)*
   */
  public static void uncapitalizeMultivaluedAttributes(ASTMCGrammar grammar) {
    grammar.getASTRuleList().forEach(c -> transformAttributesInAST(c));
  }

  private static void transformAttributesInAST(ASTASTRule astRule) {
    astRule.getAdditionalAttributeList().forEach(
            attributeInAST -> {
              if (!attributeInAST.isPresentName()) {
                String simpleName = simpleName(attributeInAST.getMCType());
                String typeName = simpleName.startsWith(AST_PREFIX) ?
                        simpleName.replaceFirst(AST_PREFIX, "") : simpleName;
                attributeInAST.setName(StringTransformations.uncapitalize(typeName));
                Log.debug("Change the name of ast-rule " + astRule.getType()
                                + " list-attribute: " + attributeInAST.getMCType(),
                        GrammarTransformer.class.getName());
              }
            });
  }

  /**
   * @param nonTerminalSep
   * @return
   */
  private static Optional<ASTBlock> transform(ASTNonTerminalSeparator nonTerminalSep) {
    String name = "";
    if (nonTerminalSep.isPresentUsageName()) {
      name = nonTerminalSep.getUsageName() + ":";
    }
    String referencedSymbol = nonTerminalSep.isPresentReferencedSymbol() ? "@" + nonTerminalSep.getReferencedSymbol() : "";
    String plusKeywords = (nonTerminalSep.isPlusKeywords()) ? "&" : "";
    String iteration = (nonTerminalSep.getIteration() == ASTConstantsGrammar.STAR) ? "?" : "";

    String extendedList = "(%usageName% %nonTerminal% %refSymbol% %plusKeywords% (\"%terminal%\" %usageName% %nonTerminal% %refSymbol% %plusKeywords%)*)%iterator%";
    extendedList = extendedList.replaceAll("%usageName%", name);
    extendedList = extendedList.replaceAll("%nonTerminal%", nonTerminalSep.getName());
    extendedList = extendedList.replaceAll("%refSymbol%", referencedSymbol);
    extendedList = extendedList.replaceAll("%plusKeywords%", plusKeywords);
    extendedList = extendedList.replaceAll("%terminal%", nonTerminalSep.getSeparator());
    extendedList = extendedList.replaceAll("%iterator%", iteration);

    Grammar_WithConceptsParser parser = new Grammar_WithConceptsParser();
    Optional<ASTBlock> block = null;
    try {
      Log.debug("Create ast for " + extendedList, GrammarTransformer.class.getName());
      block = parser.parseBlock(new StringReader(extendedList));
      if (parser.hasErrors()) {
        Log.error("0xA0362 RecognitionException during parsing " + extendedList);
      }
    } catch (IOException e) {
      Log.error("0xA0361 IOException during parsing " + extendedList);
    }
    return block;
  }

}
