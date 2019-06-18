/* generated by template symboltable.SymbolDelegateInterface*/

package de.monticore.grammar.grammar._symboltable;

import java.util.function.Predicate;
import java.util.Collection;

import de.monticore.symboltable.modifiers.AccessModifier;

public interface IMCProdAttributeSymbolDelegate {

  public Collection<MCProdAttributeSymbol> resolveAdaptedAdditionalAttributeSymbol(boolean foundSymbols,
                                                                                   String symbolName, AccessModifier modifier, Predicate<MCProdAttributeSymbol> predicate);
}

