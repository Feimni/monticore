<#-- (c) https://github.com/MontiCore/monticore -->
${tc.signature("kinds", "symbolProdName", "attrList")}
<#assign genHelper = glex.getGlobalVar("astHelper")>
  printer.beginObject();
  // Name and kind are part of every serialized symbol

  printer.beginArray(de.monticore.symboltable.serialization.JsonDeSers.KIND);
<#list kinds as kind>
  printer.value("${kind}");
</#list>
  printer.endArray();
  printer.member(de.monticore.symboltable.serialization.JsonDeSers.NAME, node.getFullName());
  // Serialize all relevant additional attributes (introduced by symbolRules)
<#list attrList as attr>
  <#if genHelper.isOptional(attr.getMCType())>
  if (node.isPresent${attr.getName()?cap_first}()) {
    serialize${symbolProdName}${attr.getName()?cap_first}(Optional.of(node.${genHelper.getPlainGetter(attr)}()));
  }
  <#else>
  serialize${symbolProdName}${attr.getName()?cap_first}(node.${genHelper.getPlainGetter(attr)}());
  </#if>
</#list>
serializeAdditional${symbolProdName}SymbolAttributes(node);