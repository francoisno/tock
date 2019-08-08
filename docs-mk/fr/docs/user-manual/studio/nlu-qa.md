# Le menu _NLU QA_

Le menu _NLU QA_ permet d'évaluer et de suivre dans le temps la qualité/pertinence/performance des modèles conversationnels.
 
Dans cette page, le détail de chaque onglet est présenté. Voir aussi [Qualifier et suivre la performance des modèles](studio/model-qa.md)
pour une présentation plus guidée par l'usage.

## L'onglet _Stats_

Cet écran permet de suivre les trois principaux indicateurs sur le modèle conversationnel :

* **Pertinence** : les scores des algorithmes de détection sur les intentions (_Intent average probability_) 
et sur les entités (_Entity average probability_)

* **Trafic / erreurs** : le nombre de sollicitations du modèle (_Calls_) et le nombre d'erreurs (_Errors_)

* **Performance** : le temps de réponse du modèle (_Average call duration_)

## L'onglet _Intent distance_

> TODO

## L'onglet _Model Builds_

Cet écran présente des statistiques sur les dernières reconstructions du modèle.

## L'onglet _Tests Trend_

> TODO

## L'onglet _Intent Test Errors_

Tock permet d'évaluer la qualité du modèle en extrayant une partie du _corpus_ de phrases, en reconstruisant le modèle 
sans cet échantillon, puis en vérifiant si le modèle reconnait cet échantillon.

Cet écran présente les résultats des tests de détection d'intentions, avec 
le détails des phrases/expressions reconnues différemment du modèle réel.

> Il est intéressant d'analyser périodiquement ces écarts, certaines différences s'expliquant bien, étant même 
>parfois "assumées" (faux négatifs), d'autres pouvant réveler un problème dans le modèle.

Voir aussi [Qualifier et suivre la performance des modèles](studio/model-qa.md).

## L'onglet _Entity Test Errors_

A l'instar de _Intent Test Errors_ pour les entités, cet écran présente les résultats des tests de détection des entités.

> Il est intéressant d'analyser périodiquement ces écarts, certaines différences s'expliquant bien, étant même 
>parfois "assumées" (faux négatifs), d'autres pouvant réveler un problème dans le modèle.

Voir aussi [Qualifier et suivre la performance des modèles](studio/model-qa.md).

## Continuer...

Rendez-vous dans [Menu _Build_](build.md) pour la suite du manuel utilisateur. 

> Vous pouvez aussi passer directement au chapitre suivant : [Développement](dev.md). 
