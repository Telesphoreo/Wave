# Ｗａｖｅ　援ケイ [![Build Status](https://travis-ci.org/Telesphoreo/Wave.svg?branch=master)](https://travis-ci.org/Telesphoreo/Wave)

### Note: Wave is now discontinued. Many plugins do not support the way Wave handled permissions. Wave is incompatible with many plugins and can break WorldEdit and WorldGuard. No future updates or support for Wave will be provided. Wave will continue to work with any plugins that may be compatible with it.

Wave is a reverse permissions plugin for servers running TotalFreedomMod. It automatically adds players to either an admin or OP group based on their respective rank. Pretty cool.

## How Wave works
In the configuration file, you have two sections: operators and superadmins. Add any permission node that you would like the player to not have. For example, if I were to add ```- essentials.antioch``` to the operator section, they would not be able to use /antioch. There is no inheritance, so you would have to add it to the superadmin section to block admins from using /antioch as well. Wave already comes with a list of pre-defined permissions that you are free to use or remove.

## TotalFreedomMod Bridge
Please note that the official TotalFreedomMod builds do not fully support Wave. If you would like full support with Wave, use a version of TotalFreedomMod from here (>2018.3.2): https://github.com/Telesphoreo/TotalFreedomMod/releases. If you choose not too, whenever an admin gets added or removed, you will have to use ```/reloadpermissions``` to update their permission groups. This does not affect the admin removal. Versions of TotalFreedomMod after 2019.2.1 do not support Wave.

## Developers
Developers can add Wave as a dependency with Maven. First add the Telesphoreo Maven Repository.
```xml
        <repository>
            <id>telesphoreo-repo</id>
            <url>https://telesphoreo.me/repo/maven/</url>
        </repository>
```

Now, you can add Wave as a dependency by pasting the following:
```xml
        <dependency>
            <groupId>me.telesphoreo</groupId>
            <artifactId>Wave</artifactId>
            <version>1.6.1</version>
            <scope>provided</scope>
        </dependency>
```

You can hook into the PermissionsCheck using the following:
```java
        PermissionCheck PermissionCheck = new PermissionCheck();
```
You can now use any of the methods in the class.

## Command Usages
Command: /wave reload
<br>
Permission: wave.reload
<br>
Usage: Reloads the plugin and configuration file
<hr>
Command: /reloadpermissions [playername | -a]
<br>
Aliases: /rp, /reloadperms
<br>
Permission: wave.reloadpermissions
<br>
Usage: Reload permissions for everyone or a player

## Updating
Wave has an automatic updater that runs on a server shutdown or reload.
