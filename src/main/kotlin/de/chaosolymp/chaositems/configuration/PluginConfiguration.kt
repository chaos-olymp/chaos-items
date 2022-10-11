package de.chaosolymp.chaositems.configuration

import org.bukkit.configuration.file.YamlConfiguration
import java.time.Instant
import java.util.logging.Logger

internal class PluginConfiguration {
    internal var items: List<ItemConfiguration>? = null
    internal var messageConfiguration: MessageConfiguration? = null

    internal var initializedAt: Instant = Instant.now()

    internal fun toConfiguration(): YamlConfiguration {
        val config = YamlConfiguration()
        config.set("items", items?.map { itemConfig -> itemConfig.toMap() })

        val messageSection = config.getConfigurationSection("messages") ?: config.createSection("messages")
        messageConfiguration?.fillConfigurationSection(messageSection)

        return config
    }

    internal fun ensureValid(logger: Logger): PluginConfiguration {
        if(messageConfiguration == null) {
            throw Exception("No message configuration found")
        }
        messageConfiguration?.ensureValid()
        if(items == null) {
            throw Exception("No items section provided")
        }
        if(items!!.isEmpty()) {
            logger.warning("No items provided")
        }
        return this
    }

    companion object {
        internal fun fromConfiguration(configuration: YamlConfiguration): PluginConfiguration {
            val itemConfigMaps = configuration.getMapList("items")

            val items = buildList {
                itemConfigMaps.map { map ->
                    @Suppress("UNCHECKED_CAST")
                    ItemConfiguration.fromMap(map as Map<String, Any?>)
                }.forEach { config -> add(config) }
            }

            return PluginConfiguration().apply {
                this.items = items
                this.messageConfiguration = MessageConfiguration.fromConfigurationSection(configuration.getConfigurationSection("messages"))
            }
        }
    }
}