Pronoun Annotator 
=================================

Pronoun Annotator creates user-defined annotations for English pronoun mentions and adds the following features, depending on context:

type				- 	anaphoric or pleonastic
case				-	nominative (I), objective (me), possessive (my), reflexive (myself), nominative-possessive (mine)
number				- 	singular or plural
class				- 	Person (personal pronouns), Thing (it, that, these, those etc), Location (here, there, where)
person				-	first, second, third
gender				-	male, female

The plugin creates temporary JAPE files from templates in the 'resources' folder, and creates a JAPE transducer from these. The temporary files are deleted when GATE exits. This transducer is used to generate the annotations. 

The plugin requires the following ANNIE components to be in your Corpus pipeline before Pronoun Annotator:

ANNIE Tokenizer
ANNIE POS Tagger
[or standard ANNIE application components plus the following]
GATE Morphological Analyzer
[followed by Pronoun Annotator]

These are available in GATE by choosing File->Manage CREOLE Plugins, scroll down to Tools and check the 'Load now' checkbox.

In addition, as of version 0.3, this plugin also performs pronominal coreference on user-defined annotations, including specification of annotations by feature value (e.g. Mention.mentionClass == Treatment) (regexes for feature value are not currently allowed but will be added in future). 

The approach to coreference is fairly novel and efficient, as it uses progressively pruned, double-linked lists to create in-document coreference chains, and makes use of centering theory to give preference to anaphors that grammatically agree with forward-looking centers (rather than searching backwards from the anaphor to possible antecedents).

If you use this plugin in any of your projects, please cite the following paper:

Gooch P, Roudsari A. Lexical patterns, features and knowledge resources for coreference resolution in clinical notes. Journal of Biomedical Informatics, 2012; DOI: 10.1016/j.jbi.2012.02.012.


Parameters
==========

- Init-time
---------------------------------

japeURL: 		Path to the main JAPE file that lists the rules files used by the transducer.
			Please ensure that the parent directory to this file is write-enabled, as the plugin
			creates JAPE rules files in this directory.

pronoun: 		Annotation name to use for the creation of pronoun annotations

relativePronoun: 	Annotation name to use for the creation of relative pronouns (which, that)


- Run-time
---------------------------------

inputASName: 		Input Annotation Set name. Leave blank for the default Annotation Set.
outputASName: 		Output Annotation Set name. Leave blank for the default Annotation Set.

personTypes:		List of annotation types that correspond to persons, e.g. Person, Mention.type == People. Defaults to Person
locationTypes:		List of annotation types that correspond to places, e.g. Location, Location.City, Mention.type == Location. Defaults to Location
otherTypes:		List of annotation types that correspond to any 'Thing', e.g. Procedure, Disease, Mention.type == Symptom. Defaults to Unknown
excludeIfWithin:	List of annotation types within which pronominal coreference should not occur.

personPronoun1st:	Coreference all first person pronouns against the chosen protagonist. Useful for coreferencing the signatory of a letter or a report against pronouns I, my, we etc, e.g. when set to LastPersonMentioned. Defaults to None.
personPronoun2nd:	Coreference all second person pronouns against the chosen protagonist. Useful for coreferencing the addressee of a letter or report against pronouns you, your etc, e.g. when set to FirstPersonMentioned. Defaults to None.

quotedPP3rd:		Set a default protagonist for resolution of third person pronouns in quoted text. In many news reports, the 3rd person pronoun in quoted text refers to the key protagonist - often the first person mentioned or the most frequently mentioned person (e.g. set to MostFrequentPersonMentioned). Defaults to PreviousPersonMentioned.

quotedTextName:		Annotation type that marks quoted text

corefSortalMentions:	Set to true if definite descriptors should be coreferenced to the relevant pronoun. Default is false.
corefIdFeature:		Feature that will hold the id of the anaphor on the antecedent
backrefIdFeature:	Feature that will hold the id of the antecedent on the anaphor
corefTextFeature:	Feature that will hold the anaphor text on the antecedent
backrefTextFeature:	Feature that will hold the antecedent text on the anaphor
maxSentenceDistance:	Maximum sentence distance between candidate antecedent-anaphor pairs. Default is 2.
tokenName:		Token annotation type. Default is Token.
sentenceName:		Sentence annotation type. Default is Sentence.
verbGroupName:		Verb group annotation type. Default is VG.