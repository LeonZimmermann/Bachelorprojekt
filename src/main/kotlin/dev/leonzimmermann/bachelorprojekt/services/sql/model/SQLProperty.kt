package dev.leonzimmermann.bachelorprojekt.services.sql.model

import simplenlg.features.Feature
import simplenlg.features.LexicalFeature
import simplenlg.framework.LexicalCategory
import simplenlg.framework.NLGElement
import simplenlg.framework.NLGFactory

/**
 * The SQLProperty class is a representation of a table's property (column)
 */
open class SQLProperty(
  private val propertyName: String,
  private val propertyStem: String? = null,
  private val plural: Boolean = false,
  private val withSpecifier: Boolean = true,
  private val possessor: SQLElement? = null
) :
  SQLElement {
  override fun toSQLString(): String = propertyName

  /**
   * In the simplest form, the stem text of an SQL property is its name. With propertyStem an alias
   * can be supplied, that can be used instead, so that the name of the property in the stem can differ
   * from its technical name. If plural is true, then the plural form of the word will be used. When withSpecifier
   * is true, then the word will be prefixed with the word "the". If a possessor is supplied, then a phrase will be
   * created that makes it clear, that the property belongs to the supplied possessor. For example in "The person's address"
   * the possessor is the person and the address is the property.
   */
  override fun toStemText(nlgFactory: NLGFactory): NLGElement {
    val word = nlgFactory.createWord(propertyStem ?: propertyName, LexicalCategory.NOUN)
    var phrase = nlgFactory.createNounPhrase(word)
    if (possessor == null) {
      phrase.isPlural = plural
    } else {
      val tableStemText = possessor.toStemText(nlgFactory)
      tableStemText.setFeature(LexicalFeature.PLURAL, plural)
      val tablePhrase = nlgFactory.createNounPhrase(tableStemText)
      tablePhrase.setFeature(Feature.POSSESSIVE, true)
      tablePhrase.addComplement(phrase)
      phrase = tablePhrase
    }
    if (withSpecifier) {
      phrase.setSpecifier("the")
    }
    return phrase
  }
}