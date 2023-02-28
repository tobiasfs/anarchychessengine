# Dictionaries

The dictionaries contain the words, that the engine understands. The syntax is hardcoded
in the language parser. Maybe I can get aways with most roman languages by just adapting
the Universal Dependencies in the dictionaries. Maybe I get an idea on how to generalize
the syntax of more languages in a descriptive way. For now english and the other european
languages are in. Maybe the slavic ones or even arabic. But I am pretty sure, it stopps
with Chinese or Japanese.

## Universal Dependencies

POS tags, that a very universal. The dictionary files contain these and have the added
parameters of the UDs attached.

To get from syntax to semantics, the UDs are extended by properties, that are important for
the language parser to infere the semantics of the language processed.

## Automatic Translation?

It would be fun, if the rules can be translated into various languages. As they are only
a very limited, very formalized subset of the languages, it might be possible to naively
translate from one language to another.

I will give it a go ... later.
