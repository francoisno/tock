## Images Docker

Si vous souhaitez ne pas développer un assistant en utilisation l'API BOT ( c'est à dire en développant un assistant "Tock"), 
ou si vous souhaitez mettre en plateforme de production, vous aurez besoin d'installer
Tock sur vos serveurs.

Des images docker sont mises à disposition pour faciliter le démarrage.

Ces images sont disponibles dans le [Hub Docker](https://hub.docker.com/r/tock/).

Le code source utilisé pour construire ces images, ainsi que les fichiers [docker-compose](https://docs.docker.com/compose/) 
utilisés pour démarrer l'ensemble de la boite à outils *Tock* sont disponibles dans le repository github [https://github.com/voyages-sncf-technologies/tock-docker](https://github.com/voyages-sncf-technologies/tock-docker).


## Commencer le développement d'un Assistant via API

Un exemple de configuration est disponible ici dans le projet tock-docker
 sous le nom de docker-compose-bot.yml. Veuillez consulter la [documentation correspondante](https://github.com/voyages-sncf-technologies/tock-docker).

## Un exemple d'Assistant Tock

Un bot d'exemple utilisant Tock est mis à disposition sur github : [https://github.com/voyages-sncf-technologies/tock-bot-open-data](https://github.com/voyages-sncf-technologies/tock-bot-open-data).
 
Il se base sur les [API Open Data de la SNCF](https://data.sncf.com/), et présente des fonctionnalités minimales permettant de démontrer l’usage de Tock. 

Il s'agit d'un bon point de départ, puisque il comporte également un modèle NLP très simple.
Bien entendu, comme le modèle n'est pas complet, la qualité du bot est faible, mais suffit cependant à démontrer le principe de l'outil.

Dans la suite de la documentation, nous nous référerons à cet exemple pour couvrir l'ensemble des fonctionnalités. 


### Stack docker

Une image docker est mis à disposition pour le lancer directement.
Les instructions pour la démarrer sont précisées dans le [projet github contenant les images docker](https://github.com/voyages-sncf-technologies/tock-docker#user-content-run-the-open-data-bot-example).