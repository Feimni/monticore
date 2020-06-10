<!-- (c) https://github.com/MontiCore/monticore -->

# MontiCore Best Practices - A Guide For Small Solutions

[[_TOC_]]

[MontiCore](http://www.monticore.de) provides a number of options to design 
languages, access and modify the abstract syntax tree, and produce output files.

This (currently unsorted and evolving) list of practices discusses solutions 
that we identified and applied as well as alternatives and their specfic 
advantages and drawbacks. The list also mentions where the solutions have been
found and where they have been applied first.

This file is partially temporary and also contains compact (incomplete) solutions.
More detailed descriptions of best practices can be found in the 
[MontiCore reference manual](http://monticore.de/MontiCore_Reference-Manual.2017.pdf).
Some of the best practices here will also be incorporated in the next version
of the reference manual.

## **Handling Errors 0x.....** 

### How to use **Expressions** 

* `Expression` is a predefined nonterminal in the MontiCore basic grammars. 
  Because of the infix notation of some operators and similar challenges,
  it is usually not possible to use a subset of the expressions only. 
  For example use of `ConditionalExpression` may lead to a parser generation 
  error (i.e. `0xA0129`).
* Solutions:
  1. Use nonterminal `Expression` and forbid all unwanted alternatives through 
     context conditions.
  2. Think of allowing more general expressions?
  3. If especially the syntax of `if . then . else .` shall be reused, 
     why not defining this in a new nonterminal and ignoring that the same
     syntactic constructs were already available in another production.
* Defined by: CKi, BR.
  
 
    
## Further Information

* [Overview Best practices](BestPractices.md)
* [MontiCore project](../../README.md) - MontiCore
* see also [**MontiCore Reference Manual**](http://www.monticore.de/)
