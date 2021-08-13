import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    java
    id("net.minecrell.plugin-yml.bukkit") version "0.4.0"
}

version = "1.0.0"

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
    mavenCentral()
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.17.1-R0.1-SNAPSHOT")
}

configure<BukkitPluginDescription> {
    main = "dev.s7a.DynamicWorldBorder.Main"
    apiVersion = "1.17"
    author = "sya_ri"
    commands {
        register("dynamicworldborder") {
            aliases = listOf("dwb")
            permission = "dynamicworldborder.command"
        }
    }
}
