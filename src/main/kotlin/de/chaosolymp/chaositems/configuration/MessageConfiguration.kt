package de.chaosolymp.chaositems.configuration

import de.chaosolymp.chaositems.extension.componentFromMap
import de.chaosolymp.chaositems.extension.toMap
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.MemorySection
import java.lang.RuntimeException
import java.util.logging.Logger

internal class MessageConfiguration {
    internal var notAPlayer: Component? = null
    internal var noPermission: Component? = null

    internal var commandExceptionOccurred: Component? = null

    internal var getItemsSuccess: Component? = null

    internal var reloadInvalidArgument: Component? = null
    internal var reloadInvalidArgumentCount: Component? = null
    internal var reloadConfigSuccess: Component? = null
    internal var reloadConfigIoException: Component? = null
    internal var reloadConfigSecurityException: Component? = null

    internal var reloadFullSuccess: Component? = null
    internal var reloadFullFailed: Component? = null

    internal fun toMap(): Map<String, Any?> {
        return emptyMap()
    }

    internal fun fillConfigurationSection(section: ConfigurationSection) {
        section["command.feedback.notAPlayer"] = notAPlayer?.toMap()
        section["command.feedback.noPermission"] = noPermission?.toMap()
        section["command.feedback.exceptionOccurred"] = commandExceptionOccurred?.toMap()

        section["command.get-items.success"] = getItemsSuccess?.toMap()

        section["command.reload.invalidArgument"] = reloadInvalidArgument?.toMap()
        section["command.reload.invalidArgumentCount"] = reloadInvalidArgumentCount?.toMap()
        section["command.reload.config.success"] = reloadConfigSuccess?.toMap()
        section["command.reload.config.error.io_exception"] = reloadConfigIoException?.toMap()
        section["command.reload.config.error.security_exception"] = reloadConfigSecurityException?.toMap()

        section["command.reload.full.success"] = reloadFullSuccess?.toMap()
        section["command.reload.full.error"] = reloadFullFailed?.toMap()
    }

    internal fun ensureValid(): MessageConfiguration {
        validateNull("command.feedback.notAPlayer", notAPlayer)
        validateNull("command.feedback.noPermission", noPermission)
        validateNull("command.feedback.exceptionOccurred", commandExceptionOccurred)
        validateNull("command.get-items.success", getItemsSuccess)
        validateNull("command.reload.invalidArgument", reloadInvalidArgument)
        validateNull("command.reload.invalidArgumentCount", reloadInvalidArgumentCount)
        validateNull("command.reload.config.success", reloadConfigSuccess)
        validateNull("command.reload.config.error.io_exception", reloadConfigIoException)
        validateNull("command.reload.config.error.security_exception", reloadConfigSecurityException)
        validateNull("command.reload.full.success", reloadFullSuccess)
        validateNull("command.reload.full.error", reloadFullFailed)

        return this
    }

    private fun validateNull(messagePath: String, component: Component?) {
        if(component == null) {
            throw Exception("Message $messagePath is null")
        }
    }

    companion object {
        internal fun fromConfigurationSection(configurationSection: ConfigurationSection?): MessageConfiguration? {
            if(configurationSection == null) {
                return null
            }
            return MessageConfiguration().apply {
                notAPlayer = componentFromMap(configurationSection.getConfigurationSection("command.feedback.notAPlayer")?.getValues(true))
                noPermission = componentFromMap(configurationSection.getConfigurationSection("command.feedback.noPermission")?.getValues(true))
                commandExceptionOccurred = componentFromMap(configurationSection.getConfigurationSection("command.feedback.exceptionOccurred")?.getValues(true))

                getItemsSuccess = componentFromMap(configurationSection.getConfigurationSection("command.get-items.success")?.getValues(true))

                reloadInvalidArgument = componentFromMap(configurationSection.getConfigurationSection("command.reload.invalidArgument")?.getValues(true))
                reloadInvalidArgumentCount = componentFromMap(configurationSection.getConfigurationSection("command.reload.invalidArgumentCount")?.getValues(true))
                reloadConfigSuccess = componentFromMap(configurationSection.getConfigurationSection("command.reload.config.success")?.getValues(true))
                reloadConfigIoException = componentFromMap(configurationSection.getConfigurationSection("command.reload.config.error.io_exception")?.getValues(true))
                reloadConfigSecurityException = componentFromMap(configurationSection.getConfigurationSection("command.reload.config.error.security_exception")?.getValues(true))

                reloadFullSuccess = componentFromMap(configurationSection.getConfigurationSection("command.reload.full.success")?.getValues(true))
                reloadFullFailed = componentFromMap(configurationSection.getConfigurationSection("command.reload.full.error")?.getValues(true))
            }
        }

        internal val defaultConfig: MessageConfiguration = MessageConfiguration().apply {
            val prefix = Component.text()
                .append(Component.text("[").color(TextColor.color(85, 85, 85)))
                .append(Component.text("!").decorate(TextDecoration.BOLD).color(TextColor.color(255, 255, 85)))
                .append(Component.text("]").color(TextColor.color(85, 85, 85)))
                .append(Component.text(" "))
                .append(Component.text("Chaos Items").decorate(TextDecoration.BOLD).color(TextColor.color(106, 176, 76)))
                .append(Component.text(" "))
                .append(Component.text("»").color(TextColor.color(85, 85, 85)))
                .append(Component.text().resetStyle())
                .asComponent()

            notAPlayer = Component
                .text()
                .append(prefix)
                .append(Component.text("You're not a player").color(TextColor.color(255, 85, 85)))
                .asComponent()

            noPermission = Component
                .text()
                .append(prefix)
                .append(Component.text("You have no permission to do this.").color(TextColor.color(255, 85, 85)))
                .asComponent()

            commandExceptionOccurred = Component
                .text()
                .append(prefix)
                .append(Component.text("Exception occurred while executing command").color(TextColor.color(255, 85, 85)))
                .asComponent()

            getItemsSuccess = Component
                .text()
                .append(prefix)
                .append(Component.text("The chaotic items are now in your inventory.").color(TextColor.color(85, 85, 255)))
                .asComponent()

            reloadInvalidArgument = Component
                .text()
                .append(prefix)
                .append(Component.text("Argument missing. Please select between `full` and `config`.").color(TextColor.color(255, 85, 85)))
                .asComponent()

            reloadInvalidArgumentCount = Component
                .text()
                .append(prefix)
                .append(Component.text("Invalid arguments.").color(TextColor.color(255, 85, 85)))
                .asComponent()

            reloadConfigIoException = Component
                .text()
                .append(prefix)
                .append(Component.text("IO failure occurred - See logs").color(TextColor.color(255, 85, 85)))
                .asComponent()

            reloadConfigSecurityException = Component
                .text()
                .append(prefix)
                .append(Component.text("Your Java runtime uses a (already deprecated) SecurityManager which blocks IO operations.").color(TextColor.color(255, 85, 85)))
                .asComponent()

            reloadConfigSuccess = Component
                .text()
                .append(prefix)
                .append(Component.text("Reload config successful.").color(TextColor.color(85, 85, 255)))
                .asComponent()

            reloadFullSuccess = Component
                .text()
                .append(prefix)
                .append(Component.text("Full reload successful.").color(TextColor.color(85, 85, 255)))
                .asComponent()

            reloadFullFailed = Component
                .text()
                .append(prefix)
                .append(Component.text("Full reload failed - View logs").color(TextColor.color(255, 85, 85)))
                .asComponent()

        }
    }

}