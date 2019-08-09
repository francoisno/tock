# Architecture de Tock

Deux composants majeurs sont disponibles, le moteur NLP ( pour Natural Language Processing ou [TALN](https://fr.wikipedia.org/wiki/Traitement_automatique_du_langage_naturel) en français),
 et un framework conversationnel qui intègre les services NLP et différents connecteurs comme Messenger, Google Assistant ou Slack. 
 
La composante NLP est indépendante de la partie conversationnelle. 
Il est donc possible d'utiliser le NLP sans devoir maîtriser la complexité induite par la gestion des conversations.
Dans certain cas d'usage importants, comme l'[Internet des objets](https://fr.wikipedia.org/wiki/Internet_des_objets), 
l'utilisation d'un modèle NLP seule est pertinente.

![schéma Tock](img/tock.png "Les différentes composantes de Tock")
