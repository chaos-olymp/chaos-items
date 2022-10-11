import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation

plugins {
    kotlin("jvm") version "1.7.10"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "de.chaosolymp"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://repo.papermc.io/repository/maven-public/")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val relocateShadowJar = tasks.register<ConfigureShadowRelocation>("relocateShadowJar") {
    target = tasks.shadowJar.get()
    prefix = "de.chaosolymp.chaositems.libs"
}

dependencies {
    shadow(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.19.2-R0.1-SNAPSHOT")

    testImplementation(kotlin("test"))
    testImplementation("com.github.seeseemelk:MockBukkit-v1.19:2.122.0")
    testImplementation("net.kyori:adventure-text-serializer-plain:4.11.0")
    testImplementation("io.kotest:kotest-runner-junit5:5.5.0")
    testImplementation("io.kotest:kotest-assertions-core:5.5.0")
    testImplementation("io.kotest:kotest-property:5.5.0")
}

bukkit {
    name = "ChaosItems"
    version = project.version.toString()
    main = "de.chaosolymp.chaositems.ItemsPlugin"
    apiVersion = "1.19"
    website = "https://chaosolymp.de/"
    authors = listOf("ChaosOlymp.DE Developers")

    commands {
        register("chaos-items") {
            description = "Main command for ChaosItems"
            aliases = listOf("chaositems", "citems")
            permission = "chaositems.command.chaositems"
            usage = "Use it or lose it!"
        }
    }

    permissions {
        register("chaositems.*") {
            children = listOf("chaositems.command.*")
        }
        register("chaositems.command.*") {
            children = listOf("chaositems.command.chaositems")
        }
        register("chaositems.command.chaositems") {
            children = listOf("chaositems.command.chaositems.reload", "chaositems.command.chaositems.getitems")
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("chaositems.command.chaositems.reload") {
            description = "Allows you to run /chaos-items reload"
            default = BukkitPluginDescription.Permission.Default.OP
        }
        register("chaositems.command.chaositems.getitems") {
            description = "Allows you to run /chaos-items get-items"
            default = BukkitPluginDescription.Permission.Default.OP
        }
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("chaos-items-shadow")
        manifest {
            attributes(mapOf("Plugin-Class" to "de.chaosolymp.chaositems.ItemsPlugin"))
        }
        dependsOn(relocateShadowJar)
    }
    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}