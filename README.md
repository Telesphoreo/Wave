# Wave [![Build Status](https://travis-ci.org/Telesphoreo/Wave.svg?branch=master)](https://travis-ci.org/Telesphoreo/Wave)

Wave is a reverse permission plugin for servers running TotalFreedomMod.

## How Wave works
In the configuration file, you have two sections: operators and superadmins. Add any permission node that you would like the player to not have. For example, if I were to add ```- essentials.antioch``` to the operator section, they would not be able to use /antioch. There is no inheritance, so you would have to add it to the superadmin section to block admins from using it. Wave already comes with a list of pre-defined permissions that you are free to use or remove.

## Developers
Developers can add Wave as a dependency with Jitpack. In your repository section, Jitpack:
```yaml
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
```

Now you can add Wave as a dependency:
```yaml
        <dependency>
            <groupId>com.github.Telesphoreo</groupId>
            <artifactId>Wave</artifactId>
            <version>1.3.0</version>
            <scope>provided</scope>
        </dependency>
```

You can hook into the PermissionsCheck using the following:
```java
        PermissionCheck PermissionCheck = new PermissionCheck();
```
You can now use any of the methods in the class.

## Permission Nodes
Command: /wave reload
<br>
Permission: wave.reload
<br>
Usage: Reloads the plugin and configuration file

## Updating
Wave has an automatic updater that runs on a server shutdown or reload. You can disable it in the configuration file.
