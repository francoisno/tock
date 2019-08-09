# Développer des bots

## Deux possibilités de développement

Pour aller plus loin dans le développement d'un assistant, il va nécessaire d'utiliser des scripts
ou du code. 

### Assistant Tock

Dans ce mode, vous accès à l'intégralité des fonctionnalités que met à disposition Tock
 pour développer un Bot. C'est de cette manière que sont développés aujourd'hui les bots publiés par
 les concepteurs de Tock. 
 
Cependant la phase de mise en place de la solution est assez complexe.
Il est en effet nécessaire :

- D'installer une stack docker sur son poste ou sur son serveur
- De permettre la connexion partagée à la base MongoDB entre les poste de dev et la stack Tock utilisée
- De maîtriser le langage Kotlin

### Assistant via API

Si vous souhaitez évaluer la solution Tock, il est conseillé d'utiliser les APIs (actuellement en phase béta) 
mises à disposition par Tock. Deux options s'offrent à vous.

> TODO

* _Tock Bot API_ :
    * Architectures
    * Le mode _WebSocket_
    * Le mode _WebHook_
    * Utiliser l'API

* _Tock Bot Framework_ :
    * Architecture
    * Le _bus_ Tock
    * Les types de _Message_
    * _StoryHandler_ & _StoryDef_
    * _NlpListener_
    * ...
