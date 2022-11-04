package dev.leonzimmermann.bachelorprojekt.services.sql.model

import simplenlg.framework.NLGFactory
import simplenlg.lexicon.Lexicon
import simplenlg.realiser.english.Realiser

abstract class AbstractSQLUnitTest {
  protected val lexicon = Lexicon.getDefaultLexicon()
  protected val nlgFactory = NLGFactory(lexicon)
  protected val realiser = Realiser(lexicon)
}