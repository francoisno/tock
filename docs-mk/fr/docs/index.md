# Bienvenue sur Tock : une plateforme conversationnelle ouverte

**Tock** (*The Open Conversation Kit*) est une plateforme complète pour construire des agents conversationnels - souvent appelés _bots_. 

Contrairement à la plupart des solutions conversationnelles, Tock ne dépend pas d'API tierces, bien qu'il soit possible d'en intégrer.
L'utilisateur choisit les composants qu'il embarque et peut ainsi conserver la maîtrise de ses modèles et données conversationnelles.

> Tock est utilisé en production depuis plusieurs années par [OUI.sncf](https://www.oui.sncf/services/assistant) pour
> proposer des assistants sur des canaux propres (Web, mobile), réseaux sociaux et enceintes connectées.

> L'ensemble du code source est disponible sur 
> [https://github.com/voyages-sncf-technologies/tock](https://github.com/voyages-sncf-technologies/tock) 
> sous une [licence Apache 2](https://github.com/voyages-sncf-technologies/tock/blob/master/LICENSE). 

## Fonctionnalités

* Bots autonomes ou intégrés à des canaux externes comme des sites Web, applications mobiles, réseaux sociaux
* Plateforme _NLU (Natural Language Understanding)_ complète :
    * Compatible avec des bibliothèques d'algorithmes comme 
[Apache OpenNLP](https://opennlp.apache.org/), [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/), [Duckling](https://github.com/facebook/duckling).
    * Déployable seule, pour des cas d'usage comme l'[Internet des objets](https://fr.wikipedia.org/wiki/Internet_des_objets) par exemple
* Interfaces _Tock Studio_ :
    * Qualifier les phrases, gérer les modèles et l'apprentissage du bot, 
créer des parcours conversationnels sans code ni connaissance poussée des algorithmes
    * Support de l'internationalisation (_i18n_) pour les bots multilingues
    * Suivi des conversations, performances et erreurs (amélioration continue des modèles)
    * Analyse et visualisation interactive des parcours possibles et réellement suivis par les utilisateurs (_Bot Flow_)
* Possibilité de développer des parcours complexes et intégrer des API tierces grâce à plusieurs frameworks 
disponibles : clients et _DSL (Domain Specific Language)_ en [Kotlin](https://kotlinlang.org/) ou _Bot API_ pour tout langage
* Nombreux connecteurs pour intégrer un bot à des canaux comme [Messenger](https://www.messenger.com/), [WhatsApp](https://www.whatsapp.com/), 
[Google Assistant](https://assistant.google.com/) et [Google Home](https://store.google.com/fr/product/google_home), 
[Twitter](https://twitter.com/), [Alexa](https://alexa.amazon.com/), [Business Chat](https://www.apple.com/fr/ios/business-chat/), 
[Teams](https://products.office.com/fr-fr/microsoft-teams/), [Slack](https://slack.com/), 
 [Rocket.Chat](https://rocket.chat/)
* Déploiement : local, _cloud_, _on-premise_, avec ou sans [Docker](https://www.docker.com/), 
voire sans connexion Internet (bot _"embarqué"_) 

![Interface d'admin NLP - qualification de phrase](img/tock-nlp-admin.png "Exemple de qualification de phrase")

## Technologies

La plateforme applicative est la [JVM](https://fr.wikipedia.org/wiki/Machine_virtuelle_Java).

Le langage de référence est [Kotlin](https://kotlinlang.org/) mais d'autres langages de programmation peuvent être utilisés via les API mises à disposition.
 
Tock utilise [Vert.x](http://vertx.io/) et [MongoDB](https://www.mongodb.com ). 
Des bibliothèques et algorithmes _NLU_ comme [Apache OpenNLP](https://opennlp.apache.org/) ou [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/)
peuvent être utilisées, mais Tock n'en dépend pas directement.

Les interfaces graphiques _(Tock Studio)_ sont écrites avec [Angular](https://angular.io/) en [Typescript](https://www.typescriptlang.org/).

## Démarrer...

* _[Guides](getting-started/start-studio.md)_ et [plateforme de démonstration](https://demotock-production-admin.vsct-prod.aws.vsct.fr/)
* _[Manuel utilisateur](user-manual/toc.md)_ (fonctionnalités, architecture et déploiement)
* _[Etudes de cas](examples/users.md)_
* _[Présentations et video](examples/presentations.md)_
* _[Exemples de code](examples/samples.md)_

## Pourquoi Tock ?

> Initié en 2016 par l'équipe _Innovation_ chez [OUI.sncf](https://www.oui.sncf/) afin de motoriser l'analyse des 
commandes vocales sur ses [applications mobiles](https://www.oui.sncf/mobile), le framework fut ensuite utilisé pour 
créer son [bot Messenger](https://www.messenger.com/t/oui.sncf), avant d'être étendu à de nombreux canaux et d'accueillir 
d'autres bots pour de nouveaux cas d'usage.

A ses débuts, la plateforme donnait des résultats similaires à ceux obtenus avec différentes solutions du marché, tout 
en restant en maîtrise du code (embarquant des bibliothèques opensource issues du domaine universitaire), 
en évitant les effets _"boîte noire"_ (notamment pour débugger les modèles conversationnels) pour une réactivité accrue.

> Depuis, l'équipe derrière [OUIbot](https://www.oui.sncf/services/assistant) et d'autres équipes dédiées à des 
assistants conversationnels SNCF (pour les clients comme en interne) se sont créées et utilisent quotidiennement Tock en 
production, tout en enrichissant régulièrement la plateforme de nouvelles fonctionnalités et connecteurs.

Nous pensons qu'il y a un besoin pour des plateformes conversationnelles et IA ouvertes, permettant de nombreux
scénarios techniques et métier tout en restant en maîtrise du code, l'utilisateur étant propriétaire de ses modèles et de ses données.

L'ensemble est partagé avec la communauté opensource dans le but de fédérer et mutualiser l'effort des créateurs d'assistants. 
