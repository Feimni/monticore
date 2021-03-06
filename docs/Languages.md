<!-- (c) https://github.com/MontiCore/monticore -->

# MontiCore Languages - an Overview

[[_TOC_]]

[MontiCore](http://www.monticore.de) is a language workbench
with an explicit notion of language components. It uses 
grammars to describe textual DSLs. 
MontiCore uses an extended grammar format that allows to compose 
language components via inheritance, embedding and aggregation (see the 
[**reference manual**](http://monticore.de/MontiCore_Reference-Manual.2017.pdf)
for details).

A **language component** is mainly represented through 
(1) the grammar describing concrete and abstract syntax of the language, 
(2) Java-classes implementing specific functionalities, and 
(3) Freemarker-Templates helping to print a model to text.
However, language components are often identified with their main 
component grammar.

Language components are currently organized in two levels:
In this list you mainly find grammars for 
**complete (but also reusable and adaptable) languages**.
A list of
[**grammar components**](../monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
with individual reusable nonterminals is also available in
the MontiCore core project 
([development status](../00.org/Explanations/StatusOfGrammars.md)).

The following list contains the language grammars found in the
`MontiCore` projects, such as `cd4analysis/cd4analysis`.
They are usually contained in project folders like `src/main/grammars/` 
and organized in packages like `de.monticore.cd`.
MontiCore projects are hosted at

* [`https://git.rwth-aachen.de/monticore`](https://git.rwth-aachen.de/monticore), 
    and partially also at
* [`https://github.com/MontiCore/`](https://github.com/MontiCore/monticore)


## List of Languages 

<!--
### [Activity Diagrams](INSERT LINK HERE) (not adressed yet)
* TO be added
-->


### [Class Diagram For Analysis (CD4A)](https://git.rwth-aachen.de/monticore/cd4analysis/cd4analysis) (MontiCore stable)
* Responsible: SVa
* CD4A is the textual representation to describe **UML class diagrams** 
  (it uses the [UML/P](http://mbse.se-rwth.de/) variant).
* CD4A covers **classes, interfaces, inheritance, attributes with types,
  visibilities**,
  and all kinds of **associations** and **composition**, including **qualified**
  and **ordered associations**. Classes can be placed in different **packages**.
  An example:
  ```
  classdiagram MyLife { 
    abstract class Person {
      int age;
      Date birthday;
      List<String> nickNames;
    }
    package com.universityLib {
      <<myStereotype>> class Student extends Person {
        StudentStatus status;
      }
      enum StudentStatus { ENROLLED, FINISHED; }
    }
    
    composition Person -> Address [*]  {ordered};
    association [0..2] Person (parent) <-> (child) Person [*];
    association phonebook Person [String] -> PhoneNumber ;
  }
  ```
* CD4A focusses on the analysis phase in typical data-driven development 
  projects and is therefore mainly for data modelling.
  Consequently, it omits method signatures and complex generics.
  The primary use of the CD4A language is therefore **data modelling**. The
  CD4A language opens various possibilities for the development of data
  structures, database tables as well as data transport infrastructures in
  cloud and distributed systems.
* [Main grammar `de.monticore.cd.CD4Analysis`](https://git.rwth-aachen.de/monticore/cd4analysis/cd4analysis/blob/develop/src/main/grammars/de/monticore/CD4Analysis.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/cd4analysis/cd4analysis/-/blob/develop/src/main/grammars/de/monticore/cd4analysis.md)


### [Class Diagram for Code (CD4Code)](https://git.rwth-aachen.de/monticore/cd4analysis/cd4analysis) (MontiCore stable)
* Responsible: SVa
* CD4Code describes **UML class diagrams**.
* CD4Code is a conservative extension of **CD4A**, 
  which includes method signatures. An example:
  ```
  classdiagram MyLife2 {
    // like CD4A but also allows:
    class Person {
      protected List<Person> closestFriends(int n);
      void addFriend(Person friends...);
      <<myStereotype>> void relocate();
    }
  }
  ```
* CD4Code is often used as tool-internal AST that allows to
  map any kind of source models to a class/attribute/method/association based
  intermediate structure, before it is printed e.g. as Java code. 
  For example a transformation sequence could be: 
  * [MontiCoreCLI](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-generator/src/main/java/de/monticore/codegen/cd2java/_symboltable/SymbolTableCDDecorator.java): 
    Grammar -> 
    [Grammar AST encoded in CD4Code](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-generator/src/main/java/de/monticore/MontiCoreScript.java#L411) ->
    [Decoration for custom behavior](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-generator/src/main/java/de/monticore/codegen/cd2java/_symboltable/SymbolTableCDDecorator.java) -> 
    [Java code](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-generator/src/main/java/de/monticore/codegen/cd2java/_symboltable/SymbolTableCDDecorator.java)
  * Statechart -> State pattern encoded in CD4Code 
  -> Decoration by monitoring methods -> Java code.
* Main grammar [`de.monticore.cd.CD4Code`](https://git.rwth-aachen.de/monticore/cd4analysis/cd4analysis/blob/develop/src/main/grammars/de/monticore/CD4Code.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/cd4analysis/cd4analysis/-/blob/develop/src/main/grammars/de/monticore/cd4analysis.md) 
  (see Section *CD4Code*)


### [Feature Diagrams](https://git.rwth-aachen.de/monticore/languages/feature-diagram) (MontiCore stable)
* Caretaker: AB, DS
* Language for feature models and feature configurations.
* **Feature diagrams** are used to model (software) **product lines** and their **variants**.
* **Feature configurations** select a subset of features of a feature model 
  to describe a product of the product line. An example:
  ```
  featurediagram MyPhones {
    Phone -> Memory & OS & Camera? & Screen;
    Memory -> Internal & External?;
    Internal -> [1..2] of {Small, Medium, Large};
    OS -> iOS ^ Android;
    Screen -> Flexible | FullHD;

    Camera requires (iOS && External) || Android ;
  }
  ```
  Rules `F -> ...` have a parent feature (left-hand side) 
  and its child features (right-hand side). 
  Operators are: **optional** feature `?`, **and** `&`, **or** `|`, **xor** `^`,
  and **subset cardinality** constraints, like `[1..2] of ...`.
  Further, a feature model may define cross-tree constraints using logic 
  operators **and** `&&`, **or** `||`, **implication** `requires`, etc.
* Main grammar [`FeatureDiagram`](https://git.rwth-aachen.de/monticore/languages/feature-diagram/-/blob/master/fd-lang/src/main/grammars/FeatureDiagram.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/languages/feature-diagram/-/blob/master/fd-lang/src/main/grammars/FeatureDiagram.md)


### [GUI DSL](https://git.rwth-aachen.de/macoco/gui-dsl) (Alpha: Intention to become stable)
* Caretaker: LN, AGe
* Language for textual definition of Graphical User Interfaces of Web
Applications
* GUI DSL covers GUI elements and relevant configuration, which include
**layout elements, widgets**, their **style definition** and **references to
data sources**.
* Language is mainly used to describe **GUI of Web Applications**. The models of
the language represent graphical views or their parts, omitting smaller details
of style definition and simplifying connection between graphical elements and
data sources.
* Currently new version of the `GUIDSL` is being developed:
  * [Basis grammar `GUIBasis`](https://git.rwth-aachen.de/macoco/gui-dsl/-/blob/dev/src/main/grammars/de/monticore/guidsl/GUIBasis.mc4)
includes constructs for general visualization component definitions, control
statements and components for layout description.
  * [Example models](https://git.rwth-aachen.de/macoco/gui-dsl/-/tree/dev/src/test/resources/pages/room)
can be found in the same repository.
  * [Main grammar `GUIDSL`](https://git.rwth-aachen.de/macoco/gui-dsl/-/blob/dev/src/main/grammars/de/monticore/guidsl/GUIDSL.mc4)
includes basic concepts and more specific implementation of component
configuration .
* In projects legacy version is currently used:
  * Examples: [**MaCoCo**](https://git.rwth-aachen.de/macoco/implementation),
[**Ford**](https://git.rwth-aachen.de/ford/implementation/frontend/montigem)
  * [Main grammar `GUIDSL`](https://git.rwth-aachen.de/macoco/gui-dsl/-/blob/master/src/main/grammars/GUIDSL.mc4)
includes definitions of MontiGem visualisation components, which are based on
abstract concepts, described in
[core grammar `GUIDSLCore`](https://git.rwth-aachen.de/macoco/gui-dsl/-/blob/master/src/main/grammars/GUIDSLCore.mc4).
[*Detailed description*](https://git.rwth-aachen.de/macoco/gui-dsl/-/blob/master/src/main/grammars/GUIDSL.md)
and
[*documentation*](https://git.rwth-aachen.de/macoco/gui-dsl/wikis/home).


### [MontiCore Grammar](https://git.rwth-aachen.de/monticore/monticore/blob/dev/monticore-generator) (MontiCore Stable)
* Caretaker: MB 
* Language for MontiCore Grammars itself. It can be understood as 
  *meta language*, but also used as ordinary language.
* Its main use currently: A MontiCore grammar defines the 
  **concrete syntax** and the **abstract syntax** of a textual language.
  Examples: All languages on this page are defined using MontiCore grammars
  and thus conform to this Grammar.
* Main features: Define **nonterminals** and their **productions** in EBNF, 
  **lexical token** as regular expressions. 
* Most important extensions to standard grammars:
  * **Abstract**, **interface** and **external productions** allow to
    define extensible component grammars (object-oriented grammar style).
  * Inherited productions can be redefined (overwritten) as well
    as conservatively extended.
  * Symbol and scope infrastructure is defined by simple keywords.
  * **Symbols definition** places can be introduced and 
    **symbol referencing places** defined, such that for standard cases
    automatically symbol tables can be added.
  * Additional attributes and methods can be added to the abstract syntax only.
  * Various elements, such as **semantic predicates** and **actions**
    can be defined in the same style as the underlying ANTLR.
  * MontiCore grammars can be **left recursive** and even allow mutual recursion. 
    This is e.g. useful for expression hierarchies.
  * Additional elements, such as **enum productions** and comfortable 
    operations for grammar definitions exist.
* Main grammars 
  [`de.monticore.grammar.Grammar`](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-generator/src/main/grammars/de/monticore/grammar/Grammar.mc4)
  defines the language with some open parameters and
  [`de.monticore.grammar.Grammar_WithConcepts`](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-generator/src/main/grammars/de/monticore/grammar/Grammar_WithConcepts.mc4)
  binds the external, imported expressions, method bodies, etc.
* [*Detailed description*](http://monticore.de/MontiCore_Reference-Manual.2017.pdf)
  in the MontiCore Reference Manual.
  

### [JSON](https://git.rwth-aachen.de/monticore/languages/json) (MontiCore Stable)
* Responsible: NJ
* The MontiCore language for parsing JSON artifacts. An example:
  ```
  { "Alice": {
      "fullname": "Alice Anderson",
      "address": {
        "postal_code": 10459, 
        "street": "Beck Street",
        "number": 56              }  },
    "Bob": { ... },
    "Caroll": { ... }, ...
  }
  ```
* The JSON grammar adheres to the common **JSON standard** and allows parsing 
  arbitrary JSON artifacts for further processing.
* Actually the grammar represents a slight superset to the official JSON standard. 
  It is intended for parsing JSON-compliant artifacts. Further well-formedness
  checks are not included, because we assume to parse correctly produced JSON 
  documents only.
* Please note that JSON (like XML or ASCII) is primarily a carrier language.
  The concrete JSON dialect and the question, how to recreate the
  real objects / data structures, etc. behind the JSON tree structure
  is beyond this grammar, but can be applied to the AST defined here.
* Main grammar 
  [`de.monticore.lang.JSON`](https://git.rwth-aachen.de/monticore/languages/json/-/blob/master/src/main/grammars/de/monticore/lang/JSON.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/languages/json/-/blob/master/src/main/grammars/de/monticore/lang/json.md)


### [MontiArc](https://git.rwth-aachen.de/monticore/montiarc/core) (Beta: In Stabilization)
* Caretaker: DS 
* MontiArc is an architecture and behavior modeling language and framework 
    that provides an platform independent structure and behavior 
    modeling language with an extensible code generation framework.
* MontiArc covers **components** their **ports**, **connectors** between 
  components and  
  embedded **statecharts** for component behavior description. 
* Statecharts define states and transitions with conditions on 
  the incoming messages as well as transition actions. 
  An example:
```
component InteriorLight {                           // MontiArc language
  port in Boolean lightSignal,          // ports
       in Boolean doorSignal
       out OnOff status;
  ORGate or;                            // used subcomponents
  lightSignal -> or.a;                  // connectors
  doorSignal -> or.b;
  or.c -> cntr.signal;
  component LightController cntr {      // freshly defined subcomponent 
    port in OnOff signal,
         out OnOff status;
    statechart {                        // with behavior by a Statechart
      initial state Off / {status = OFF};
      state On;
      Off -> On [ signal == true ] / {status = ON}
      On -> Off [ signal == false ] / {status = OFF}
    }
  }
  cntr.status -> status;
}
```
* MontiArcs main goal is to provide a textual notation for Component&Connector 
  diagrams, which is used quite often in various variants in industry.
  E.g. SysML's BDD, UML's component composition diagrams use the same 
  paradigm. 
* MontiArc does not define data types for their signals, but assumes 
  that these types can be imported (e.g. from a class diagram).
* MontiArc itself also has no timing predefined, but for a complete 
  language a concrete timing, such as formally grounded by Focus, 
  should be added.
* Main grammar 
  [`MontiArc.mc4`](https://git.rwth-aachen.de/monticore/montiarc/core/-/blob/modularization/languages/montiarc-fe/src/main/grammars/MontiArc.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/montiarc/core/-/blob/modularization/languages/montiarc-fe/src/main/grammars/MontiArc.md)


### [OCL/P](https://git.rwth-aachen.de/monticore/languages/OCL) (Alpha: Intention to become stable)
* Caretaker: CKi, supported by SVa, SH, OKa
* OCL/P is the textual representation of the UML OCL standard, adapted 
  with Java-like syntax.
  It's main goal is the usage in combination with other languages like 
  CD4A or Object Diagrams as an integrated part of that languages.
* OCL/P allows to define **invariants** and **pre/post conditions** in 
  the known OCL style. Furthermore, it offers a large set **expressions**
  to model constraints. These expressions include **Java expressions**,
  **set operations**, **list operations** etc., completely covering the 
  OCL standard concepts, but extend it e.g. by **set comprehensions** 
  known from Haskell, a **typesafe cast** or a 
  **transitive closure operator**.
* OCL/P comes with an 
  [OCL to Java generator](https://git.rwth-aachen.de/monticore/languages/OCL2Java)
  and a second generator for OCL in combination with 
  [*Embedded MontiArc*](https://git.rwth-aachen.de/monticore/EmbeddedMontiArc/generators/OCL_EMA2Java).
* Main grammar 
  [`ocl.monticore.ocl.OCL`](https://git.rwth-aachen.de/monticore/languages/OCL/-/blob/develop/src/main/grammars/de/monticore/ocl/OCL.mc4), 
  [Expressions](https://git.rwth-aachen.de/monticore/languages/OCL/-/tree/develop/src/main/grammars/de/monticore/ocl/expressions),
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/languages/OCL/-/blob/master/src/main/grammars/ocl/monticoreocl/OCL.md)


### [Object Diagrams](https://git.rwth-aachen.de/monticore/languages/od) (MontiCore Stable)
* Caretaker: SH
* OD is a language for textual denotation of object diagrams. The OD language
  has several purposes (when combined with appropriate language extensions):
  1. specification language for object structures (as part of the [UML/P](http://mbse.se-rwth.de/))
  1. stage and transport of data sets (e.g. the artifact analysis toolchain), and
  1. as a report format for the MontiCore tool infrastructure. 
* OD covers **named and anonymous objects, links, attributes, attribute values, lists**, and
  **visibilities**. For a comfortable definition, objects may be nested. An example:
  ```
  objectdiagram MyFamily {
    alice:Person {
      age = 29;
      cars = [
        :BMW {
          color = BLUE;
        },
        tiger:Jaguar {
          color = RED;
          length = 5.3; 
        }
      ];
    };
    bob:Person {
      nicknames = ["Bob", "Bobby", "Robert"];
      cars = [tiger];
    };
    link married alice <-> bob;
  }
  ```
* If ODs are used as specification techniqe, e.g. for tests or unwanted 
  situations,
  a more expressive version of expressions can be used for values 
  (e.g. by composing ODs with JavaExpressions). Furthermore, only 
  interesting attributes need to be defined (underspecification) and conformity
  to a CD4A model can be checked.
* The ODs differ from JSON structures, e.g., in 
  the possibility to give the object a name as it is the case for `tiger`, or `alice` 
  enabaling the definition real graph structures.
* Main grammars:
    * [ODBasics](https://git.rwth-aachen.de/monticore/languages/od/-/blob/master/src/main/grammars/de/monticore/lang/ODBasics.mc4)
    * [OD4Report](https://git.rwth-aachen.de/monticore/languages/od/-/blob/master/src/main/grammars/de/monticore/lang/OD4Report.mc4)
    * [DateLiterals](https://git.rwth-aachen.de/monticore/languages/od/-/blob/master/src/main/grammars/de/monticore/lang/DateLiterals.mc4)
* [*Detailed description*](https://git.rwth-aachen.de/monticore/languages/od/-/blob/master/src/main/grammars/de/monticore/lang/ODBasics.md) 


### [Sequence Diagrams](https://git.rwth-aachen.de/monticore/statechart/sd-language)  (MontiCore stable) 
* Caretaker: OKa
* A textual sequence diagram (SD) language.
* The project includes grammars, a symbol table infrastructure, a PrettyPrinter, 
  and various CoCos for typechecking.
* The language is divided into the two grammars SDBasis and SD4Development.
* The grammar [SDBasis](https://git.rwth-aachen.de/monticore/statechart/sd-language/-/blob/dev/src/main/grammars/de/monticore/lang/SDBasis.mc4) is a component grammar providing basic SD language features.
* The grammar [SD4Development](https://git.rwth-aachen.de/monticore/statechart/sd-language/-/blob/dev/src/main/grammars/de/monticore/lang/SD4Development.mc4) extends the grammar SDBasis with concepts used in 
  UML/P SDs.
* SD4Development supports modeling *objects*, *method calls*, *returns*, exception 
  throws, *dynamic object instantiation*, various *match modifiers* for objects 
  (free, initial, visible, complete), *life lines* with *activation regions*,
  static method calls, intermediate 
  variable declarations by using OCL, and conditions by using OCL.
* The grammars can easily be extended by further interactions and object modifiers.
* The following depicts a simple SD in its textual syntax. 
```
sequencediagram AuctionTest {
  kupfer912: Auction;         // Interacting objects
  bidPol: BiddingPolicy;
  timePol: TimingPolicy;
                              // Interaction sequence
  kupfer912 -> bidPol  : validateBid(bid)
  bidPol -> kupfer912  : return BiddingPolicy.OK;
  kupfer912 -> timePol : newCurrentClosingTime(kupfer912, bid) 
  timePol -> kupfer912 : return t;
  assert t.timeSec == bid.time.timeSec + extensionTime;
}
```

### [SI Units](https://git.rwth-aachen.de/monticore/languages/siunits) (MontiCore Stable)
* Caretaker: EK
* The international system of units (SI units) is a physical unit system widely used in the entire world. 
  It is based on the basis units `s, m, kg, A, K, mol, cd`, 
  provides a variety of derived units, and can be refined using prefixes such 
  as `m`(milli), `k`(kilo), etc.
* The SI Unit project aims to deliver SI units to MontiCore-based languages with expressions. 
  It provides a grammar for all types of SI units and prefixes usable for type 
  definition.
* Second, it provides the SI Unit literals, such as "5 km" as expression values
  and a language for SI unit types, such as "km/h" or "km/h<long>". Some examples:
  ```
    km/h speed = 5 m / 27 s                         // variable definition using type km/h
    speed = (3 * 4m  +  17km/h * 10h) / 3.5h        // values with SI unit types
    °C/s<float> coolingSpeed;                       // types (°C/s) with precision (float)
    g/mm^2<int> pressure; 
    Map<Location,°C> temperatures;                  // nesting of types 
  ```
  The SI unit literals integrate with MontiCore's expressions and the
  SI Unit types integrate with MontiCore's type system. 
  The SI unit language remains *fully type safe*.
* The math version uses "km/h" as idealistic full precision real number, while the
  computing version allows to contrain  the precision with "km/h<long>". 
* Main grammar components:
    * [SI units](https://git.rwth-aachen.de/monticore/languages/siunits/-/blob/master/src/main/grammars/de/monticore/siunits/SIUnits.mc4)
    * [SI unit literals](https://git.rwth-aachen.de/monticore/languages/siunits/-/blob/master/src/main/grammars/de/monticore/siunits/SIUnitLiterals.mc4)
    * [SI unit types for math](https://git.rwth-aachen.de/monticore/languages/siunits/-/blob/master/src/main/grammars/de/monticore/siunits/SIUnitTypes4Math.mc4)
    * [SI unit types for computations](https://git.rwth-aachen.de/monticore/languages/siunits/-/blob/master/src/main/grammars/de/monticore/siunits/SIUnitTypes4Computing.mc4)
    *           (other alternatives are possible; SI has not standardized anything here)
* Example projects:
    * [SI Java](https://git.rwth-aachen.de/monticore/languages/siunits/-/blob/master/src/test/grammars/de/monticore/lang/testsijava/TestSIJava.mc4) 
* [*detailed description*](https://git.rwth-aachen.de/monticore/languages/siunits/-/blob/master/src/main/grammars/de/monticore/SIUnits.md)  


### [Statecharts](https://git.rwth-aachen.de/monticore/statechart/sc-language) (MontiCore stable)
* Caretaker: KH  
* A set language variants for Statecharts (UML-like or also embedded SysML-like)
* creates transformation language within SC and sc<->cd4a
* [*Detailed description*](https://git.rwth-aachen.de/monticore/statechart/sc-language/-/blob/develop/scgrammar/src/main/grammars/de/monticore/umlsc/Statechart.md) 
* A compact teaser for the (one variant of the) Statechart language:
    ```
    statechart Door {
      state Opened
      initial state Closed
      state Locked
    
      Opened -> Closed close() /
      Closed -> Opened open(1) / {ringTheDoorBell();}
      Closed -> Locked timeOut(n) / { lockDoor(); } [doorIsLocked]
      Locked -> Closed [isAuthorized() && doorIsLocked] unlock() /
    }
    ```
  This example models the different states of a door: `Opened`, `Closed`, and `Locked`.
  When the statechart is in state `Opened`, it is possible to close the door using `close()`.
  When the statechart is in state `Closed`, it is possible to open the door using `open()`. 
  In the latter case the action  `ringDoorBell()` is executed. 
  When the door is `Closed` it is automatically locked after some time due to a 
  `timeout()` event that triggers the `lockDoor()` action.
  Consequently, the post-condition `doorIsLocked` holds. In case the door is locked,
  it can be unlocked by using `unlock()` if the pre-condition `isAuthorized` is fulfilled.
* *State invariants* and *transition preconditions* are defined using `Expressions`
  and *entry/exit/transition actions* are defined using `Statements`.

### [SysML_2](https://git.rwth-aachen.de/monticore/languages/sysml2/sysml2official) (Alpha: Intention to become stable)
* Caretaker: NJ
* MontiCore languages for parsing artifacts of the SysML 2 language famlily. 
  Examples:
```
package 'Vehicles' {                      // a SysML block diagram
  private import ScalarValues::*; 
  block Vehicle; 
  block Truck is Vehicle; 
  value type Torque is ISQ::TorqueValue; 
}
```
```
package 'Coffee' {                      // a SysML activity diagram
  activity BrewCoffee (in beans : CoffeeBeans, in, water : Water, out coffee : Coffee) { 
    bind grind::beans = beans;
    action grind : Grind (in beans, out powder);
    flow grind::powder to brew::powder;
    bind brew::water = water;
    action brew : Brew (in powder, in water, out coffee); 
    bind brew::coffee = coffee;
  }
}
```
* The SysML 2 grammars adhere to the general upcoming SysML 2 specification 
  (which is still under improvement currently).
* Actually these grammars represents a slight superset to the official SysML 2
  standard. It is intended for parsing SysML 2-compliant models. 
  Well-formedness checks are kept to a minimum, because we assume to parse
  correctly produced SysML 2 models only.
* MontiCore's SysML 2 is a language familiy that comes with a textual 
  representation to describe SysML 2 diagrams with respect to the standard. 

* SysML 2 covers **ADs**, **BDDs**, **IBDs**, **PackageDiagrams**, 
  **ParametricDiagrams**, **RequirementDiagrams**, **SDs**, **SMDs**, 
  **UseCaseDiagrams**, and general **SysMLBasics**
* [Main grammars](https://git.rwth-aachen.de/monticore/languages/sysml2/sysml2official/-/tree/master/src%2Fmain%2Fgrammars%2Fde%2Fmonticore%2Flang%2Fsysml)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/languages/sysml2/sysml2official/-/blob/master/src/main/grammars/de/monticore/lang/sysml/sysml2.md)


### [Tagging](https://git.rwth-aachen.de/monticore/EmbeddedMontiArc/languages/Tagging) (Alpha: Intention to become stable)
* Caretaker: SVa
* **Tags** are known e.g. from the UML and SysML and mainly used to add
  extra information to a model element. 
  Normally tags (and **stereotypes**) are inserted within the models,
  which over time polutes the models, especially when different sets of
  tags are needed for different technical platforms.
* MontiCore offers a solution that **separates a model and its tags into
  distinct artifacts**. Several independent tagging artifacts
  can be added without any need to adapt the core model.
  This allows fo reuse even of fixed library models.
* The tagging artifacts are dependent on two factors:
  * First, **tags** can be added to named elements of the base model.
    It is of great help that we have an elegant symbol mechanism included 
    in the MontiCore generator.
  * Second, the set of allowed tags can be constrained, by an explicit
    definition of allowed **tag types** and **tag values** and an explicit 
    declaration on which **kinds of symbols** a tag may be attached to.
  * Consequently tagging is not a single language, but a method to 
  **automatically and schematically derive** languages:
    * A tagging schema language TSL (dependent on the available symbol types
      of the base grammar)
    * a tagging language TL (dependent on the tag schema models written in TSL)
* Because tagging models can e.g. be used as configuration techniques 
  in a code generator, appropriate infrastructure is generated as well.
* Some [**tagging language examples**](https://git.rwth-aachen.de/monticore/EmbeddedMontiArc/languages/Tagging-Examples)
* Although concrete languages (and their grammars) are themselves generated,
  there is a 
  [main grammar `ocl.monticore.lang.Tagging`](https://git.rwth-aachen.de/monticore/EmbeddedMontiArc/languages/Tagging/-/blob/master/src/main/grammars/de/monticore/lang/Tagging.mc4),
  where the tagging language is derived from.
  See also [*detailed description*](https://git.rwth-aachen.de/monticore/EmbeddedMontiArc/languages/Tagging/-/blob/master/src/main/grammars/de/monticore/lang/Tagging.md)


### [XML](https://git.rwth-aachen.de/monticore/languages/xml) (Alpha: Intention to become stable)
* Responsible: NJ
* The MontiCore language for parsing XML artifacts. An example:
  ```
  <Calendar>
    <Appointment name="lunch">
      <Date>24.04.2020</Date>
      <Time>11:30</Time>
      <Location>cafeteria</Location>
    </Appointment>
  </Calendar>
  ```
* The XML grammar adheres to the common **XML standard** and allows parsing 
  arbitrary XML artifacts for further processing.
* Actually the grammar represents a slight superset to the official XML standard. 
  It is intended for parsing XML-compliant artifacts. Further well-formedness
  checks are not included, because we assume to parse correctly produced XML 
  documents only.
* Please note that XML (like JSON or ASCII) is mainly a carrier language.
  The concrete XML dialect and the question, how to recreate the
  real objects / data structures, etc. behind the XML structure
  is beyond this grammar, but can be applied to the AST defined here.
* Main grammar 
  [`de.monticore.lang.XML`](https://git.rwth-aachen.de/monticore/languages/xml/-/blob/master/src/main/grammars/de/monticore/lang/XML.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/languages/xml/-/blob/master/src/main/grammars/de/monticore/lang/xml.md)


### [JavaLight](https://git.rwth-aachen.de/monticore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/JavaLight.mc4) (MontiCore Stable)
* Caretaker: MB
* This is a reduced version of the **Java language**.
  JavaLight is meant to be used to integrate simplified Java-like parts 
  in modeling languages but not to parse complete Java implementations.
* It provides Java's **attribute** and **method definitions**, 
  **statements** and **expressions**, but 
  does not provide class or interface definitions and
  also no wildcards in the type system.
* One main usage of JavaLight is in the Grammar-language to model e.g. 
  Java methods. An example:
  ```
  public void print(String name) {
    System.out.println("Hello " + name);
  }
  ```
* [Main grammar `de.monticore.JavaLight`]((https://git.rwth-aachen.de/monticore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/JavaLight.mc4)
  and 
  [*detailed description*](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/monticore-grammar/src/main/grammars/de/monticore/JavaLight.md).



### [Java](https://git.rwth-aachen.de/monticore/javaDSL) (Beta: In Stabilization) (30% to MC6)
* Caretaker: MB
* This is the full Java' Language (as Opposed to JavaLight).
* Main Grammar [`JavaDSL`](https://git.rwth-aachen.de/monticore/javaDSL/-/blob/dev/javaDSL/src/main/grammars/de/monticore/java/JavaDSL.mc4)
  and
  [*detailed description*](https://git.rwth-aachen.de/monticore/javaDSL/-/blob/dev/javaDSL/src/main/grammars/de/monticore/java/JavaDSL.md).


## Further Information

* [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
* [MontiCore documentation](http://www.monticore.de/)
* [**List of languages**](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/docs/Languages.md)
* [**MontiCore Core Grammar Library**](https://git.rwth-aachen.de/monticore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
* [Overview Best Practices](BestPractices.md)
* [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)

* [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)


