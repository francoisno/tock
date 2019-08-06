# Le framework Tock en Kotlin

> TODO

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
