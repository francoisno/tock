# Les interfaces _Tock Studio_

> TODO

## Bandeau et généralités

> TODO

Le menu à gauche permet d'accéder aux différentes fonctionnalités.

Le menu **Configuration** permet de créer de nouveaux modèle et de paramétrer les options importantes.

Les menus **NLP** et **NLP QA** sont dédiés à la construction de modèles.

Les menus **Build**, **Test** et **Monitoring** sont eux utilisés dans le cadre de la construction de bots ou d'assistants.


## Section _Configuration_

### Vue _NLU Applications_

### Vue _NLU Applications_

## Section _NLU_

1. **Try it** : permet d'ajouter ou de tester l'analyse de nouvelles phrases
2. **Inbox** : l'ensemble des phrases non encore qualifiées
3. **Archive** : l'ensembles des phrases archivées, c'est à dire volontairement non encore reconnue par le modèle
4. **Search** : une interface de recherche avancée qui permet de rechercher les phrases enregistrées, qu'elles soient ou non qualifiées  
5. **Intents** : la liste des intentions du modèle
6. **Entities** : la liste des entités du modèle
7. **Logs** : La liste des requêtes interrogeant le modèle 

L'utilisateur est redirigé par défaut sur la catégorie *Inbox*.

[Construire le modèle](build-model.md)

![schéma Tock](img/inbox.png "Aucune phrase à qualifier")


La composante NLP est indépendante de la partie conversationnelle. 
Il est donc possible d'utiliser le NLP sans devoir maîtriser la complexité induite par la gestion des conversations.
Dans certain cas d'usage importants, comme l'[Internet des objets](https://fr.wikipedia.org/wiki/Internet_des_objets), 
l'utilisation d'un modèle NLP seule est pertinente.

![schéma Tock](img/tock.png "Les différentes composantes de Tock")

Une plateforme pour construire des modèles d'analyse du language naturel

L'outil principal est constitué par une interface d'administration qui permet de qualifier des 
phrases afin de construire des modèles de traitement automatique du langage naturel ( [TALN](https://fr.wikipedia.org/wiki/Traitement_automatique_du_langage_naturel) ) :

![Interface d'admin NLP - qualification de phrase](img/tock-nlp-admin.png "Exemple de qualification de phrase")

### Vue _Try it_

### Vue _Inbox_

### Vue _Unknown_

### Vue _Search_

### Vue _Intents_

### Vue _Entities_

### Vue _Logs_

## Section _NLU QA_

Cinq onglets permettent de contrôler la pertinence du modèle :

1. **Stats** : statistiques minimales qui permettent de suivre la qualité du modèle en production. Elles comprennent:
    * l'auto-évaluation du modèle sur sa pertinence en terme de reconnaissance d'intention et d'entités
    * le nombre d'appels et le nombre d'erreurs
    * le temps moyen d'exécution 
2. **Test Trend** : évolution de la pertinence des [tests partiels de modèle](#tests-partiels-de-modele) 
3. **Intent Errors** : la liste des erreurs d'intention (vraies ou fausses) trouvées lors des tests partiels de modèle
4. **Entity Errors** : la liste des erreurs d'entité (vraies ou fausses) trouvées lors des tests partiels de modèle
5. **Model Builds** : la liste des constructions des modèles avec notamment le type de modèle, le nombre de phrases et la durée de construction

[Evaluer/Suivre la pertinence/qualité du modèle](model-qa.md)

Suivi de qualité des modèles

Cette interface fournit également les outils pour faire évoluer les modèles et permet de monitorer leurs pertinences :

![Interface d'admin NLP - QA](img/tock-nlp-admin-qa.png "Exemple de monitoring de pertinence")

## Section _Build_

## Ajouter une première réponse - Le menu "Build"'

### Créer une Story via l'interface d'administration

Dans le menu "Build" il est possible de créer une réponse à une intention du modèle.

#### Réponse simple

Dans l'onglet "New Story", indiquez une phrase d'exemple, la réponse attendue,
 puis sur le bouton "Create Story".
 
Il est maintenant possible de tester (via le menu "Test") que le bot répond correctement
à la phrase d'exemple.

![Test_de_la_réponse dédiée](img/build-2.png "Test de la réponse dédiée")

#### Réponses Complexes

Il est possible d'indiquer plusieurs réponses et également des réponses "riches" appelées "Media Message".

Cela permet, quel que soit le canal d'afficher des images, des titres, des sous-titres et des boutons d'action.

#### Entités obligatoires

Il est possible, avant d'afficher la réponse principale, de vérifier si certaines entitées
sont renseignées, et si ce n'est pas le cas, d'afficher la question adéquate.

L'option correspondante est appellée "Mandatory Entities".

Par exemple, supposons que nous ayons besoin de connaître la destination de l'utilisateur.
Si il ne l'a pas déjà indiquée, Nous allons lui poser la question "Pour quelle destination ?"  

#### Actions

Les actions sont présentées comme des suggestions quand le canal le permet.
Il est possible de présenter une arborescence d'actions pour construire un arbre de décision.

### Bot Flow

Cet onglet permet de visualiser les différents arbres de décisions configurés,
et également de visualiser tout ce qui a été réellement effectué par les utilisateurs.


### Modification des réponses et internationalisation

Enfin il est possible de modifier chaque réponse du bot par type d'interface (chat/voix), par type de connecteur et par langue
via l'onglet **i18n**.

Il est aussi possible de rajouter des réponses alternatives ( à chaque fois une réponse de la liste sera choisie au hasard) via cette interface.

![Internationalisation](img/i18n.png "Internationalisation")

## Section _Test_

## Le menu Test

Via ce menu, vous pouvez commencer à tester le bot :

![Test du bot](img/test.png "Test du bot")

L'interface est minimale car il s'agit d'un mode de test. 

L'objectif reste de faire dialoguer vos utilisateurs avec le bot via des canaux comme Messenger, Google Assistant...
ou vos sites ou applications.

## La section _Monitoring_

## Le menu Monitoring

Il est ensuite possible de consulter la discussion que vous venez d'avoir avec le bot via l'onglet Monitoring

![Monitoring des conversations](img/monitoring.png "Monitoring des conversations")

Ici, le canal est indiqué comme étant celui de Messenger puisque il a été simulé une conversation Messenger.
