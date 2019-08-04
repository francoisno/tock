# Programmer des parcours en Kotlin

Les interfaces _Tock Studio_ permettent de créer des bots et des parcours relativement simples, comme 
des _arbres de décision_ et des réponses à des questions courantes. Cela s'avère suffisant pour de nombreux cas 
d'usages conversationnels.

Toutefois, il est possible de construire des réponses et des parcours plus complexes :

* Se brancher à un compte utilisateur
 
* Aggréger les informations de référentiels métier

* Appeler les services du _SI (Système d'Information)_ dans une organisation
 
* Intégrer des API externes pour enrichir ses parcours de services tiers

* Effectuer des actions et des _transactions_ : création de tickets, paiements, etc.

* Implémenter des règles de gestion et comportements spécifiques

* Optimiser les enchaînements entre les intentions

 Pour construire des parcours complexes, _Tock_ propose plusieurs modes d'intégration destinés à 
 différents langages et frameworks de développement.
 
Dans ce guide, vous utiliserez le langage [Kotlin](https://kotlinlang.org/) et le mode 
  _WebSocket_ pour ajouter une intention à un bot initié dans _Tock Studio_.

Si vous le souhaitez, vous pouvez sauter cette étape et [déployer un plateforme avec Docker](start-platform.md) 
ou passer directement au [manuel utilisateur](../user-manual/toc.md) pour en savoir plus sur les possibilités de _Tock Studio_.
 
## Ce que vous allez créer

* Une _intention_ Tock développée avec le langage [Kotlin](https://kotlinlang.org/)

* Un programme se connectant au bot en _WebSocket_ pour l'enrichir de parcours programmés

## Pré-requis

* Environ 5 à 10 minutes

* Un bot Tock fonctionnel (par exemple suite au guide [premier bot Tock](start-studio.md))

* Un environnement de développement (ou _IDE_) supportant [Kotlin](https://kotlinlang.org/), par exemple 
[IntelliJ](https://www.jetbrains.com/idea/) avec des versions récentes du [JDK](https://jdk.java.net/) 
et de [Maven](https://maven.apache.org/)

> Si vous ne souhaitez pas utiliser d'_IDE_, ou Maven, pas de problème. Il est tout à fait possible de réaliser le même 
>exercice avec d'autres outils.
>
> Il est également possible d'utiliser d'autres manières de développer que le mode _WebSocket_ et d'autres 
>langages que Kotlin. Vous en apprendrez plus dans le [manuel utilisateur Tock](../user-manual/toc.md).

## Créer un programme Kotlin avec la dépendance Tock

Il existe de nombreuses manières de créer un projet en Kotlin.

Ajoutez au _classpath_ la bibliothèque `tock-bot-api-websocket` pour le mode _WebSocket_.

Si vous utilisez [Apache Maven](https://maven.apache.org/), voici un exemple de _POM_ (`pom.xml`) pour Kotlin avec 
la dépendance `tock-bot-api-websocket` incluse :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>test</groupId>
    <artifactId>tock-kotlin-websocket</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.build.sourceDirectory>${project.basedir}/src/main/kotlin</project.build.sourceDirectory>
        <project.build.testSourceDirectory>${project.basedir}/src/test/kotlin</project.build.testSourceDirectory>
        <lib.tock.version>19.3.2</lib.tock.version>
        <plugin.kotlin.version>1.3.41</plugin.kotlin.version>
        <plugin.source.version>3.1.0</plugin.source.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>fr.vsct.tock</groupId>
            <artifactId>tock-bot-api-websocket</artifactId>
            <version>${lib.tock.version}</version>
        </dependency>
    </dependencies>

    <build>
        <sourceDirectory>${project.build.sourceDirectory}</sourceDirectory>
        <testSourceDirectory>${project.build.testSourceDirectory}</testSourceDirectory>
        <plugins>
            <plugin>
                <groupId>org.jetbrains.kotlin</groupId>
                <artifactId>kotlin-maven-plugin</artifactId>
                <version>${plugin.kotlin.version}</version>
                <executions>
                    <execution>
                        <id>compile</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>compile</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>test-compile</id>
                        <phase>test-compile</phase>
                        <goals>
                            <goal>test-compile</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>${plugin.source.version}</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```

## Créer une fonction qui se connecte à Tock

Créer un fichier Kotlin (par exemple dans `src/main/kotlin/StartWebSocket.kt) et éditez-le avec le code suivant :

```kotlin
import fr.vsct.tock.bot.api.client.newBot
import fr.vsct.tock.bot.api.client.newStory
import fr.vsct.tock.bot.api.websocket.startWithDemo

fun main() {
    startWithDemo( // Integrate with the Tock demo platform by default
            newBot(
                    "PUT-YOUR-TOCK-APP-API-KEY-HERE", // Get your app API key from Bot Configurations in Tock Studio
                    newStory("bonjour") { // Answer for the 'bonjour' story
                        end("Coucou")
                    }
            ))
}
```
