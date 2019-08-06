
Deux composants majeurs sont disponibles, le moteur NLP ( pour Natural Language Processing ou [TALN](https://fr.wikipedia.org/wiki/Traitement_automatique_du_langage_naturel) en français),
 et un framework conversationnel qui intègre les services NLP et différents connecteurs comme Messenger, Google Assistant ou Slack. 
 
La composante NLP est indépendante de la partie conversationnelle. 
Il est donc possible d'utiliser le NLP sans devoir maîtriser la complexité induite par la gestion des conversations.
Dans certain cas d'usage importants, comme l'[Internet des objets](https://fr.wikipedia.org/wiki/Internet_des_objets), 
l'utilisation d'un modèle NLP seule est pertinente.

![schéma Tock](img/tock.png "Les différentes composantes de Tock")

## Une plateforme pour construire des modèles d'analyse du language naturel 

### Interface d'administration

L'outil principal est constitué par une interface d'administration qui permet de qualifier des 
phrases afin de construire des modèles de traitement automatique du langage naturel ( [TALN](https://fr.wikipedia.org/wiki/Traitement_automatique_du_langage_naturel) ) :

![Interface d'admin NLP - qualification de phrase](img/tock-nlp-admin.png "Exemple de qualification de phrase")

### Suivi de qualité des modèles

Cette interface fournit également les outils pour faire évoluer les modèles et permet de monitorer leurs pertinences :

![Interface d'admin NLP - QA](img/tock-nlp-admin-qa.png "Exemple de monitoring de pertinence")

### [Stanford CoreNLP](https://stanfordnlp.github.io/CoreNLP/) ou [Apache OpenNLP](https://opennlp.apache.org/)

La construction automatique des modèles est basée sur une de ces solutions open-sources (au choix). 
Tock fournit un niveau d'indirection qui permet d'intégrer d'autres librairies NLP. 
L'intégration de [SparkNLP](http://nlp.johnsnowlabs.com) est d'ailleurs en cours d'étude.

### [Duckling](https://github.com/facebook/duckling) 

Un outil de parsing de dates et de types simples basé sur la librairie open-source [Duckling](https://github.com/facebook/duckling) 
est également intégré par défaut.

### API NLP

Les modèles peuvent être utilisés via l'[API](../api/) mis à disposition.

## Un framework conversationnel 

Ce framework est la deuxième brique qui permet de construire des assistants.

Elle utilise la brique de TALN de Tock via son [API](../api/).

### Gestion du contexte et de l'historique 
La gestion des contextes des dialogues et de l’historique des conversations est automatiquement disponible. 
Des notions avancées comme la fusion d'entités sont également mis à disposition.
(par exemple le fait de préciser la date "demain" puis plus tard "plutôt le soir" met automatiquement la valeur de la date à jour)

### Connecteurs tiers
Des connecteurs à Facebook Messenger, Google Assistant, WhatsApp, RocketChat, Twitter, Alexa, Teams et Slack sont disponibles. 
Il est possible d'en créer facilement d'autres, que ce soit pour se connecter à d'autres canaux ou pour répondre à des besoins spécifiques.

### Suivi des conversations
Enfin une interface d'administration est mise à disposition et permet de tester les bots et de suivre les conversations des utilisateurs. 
