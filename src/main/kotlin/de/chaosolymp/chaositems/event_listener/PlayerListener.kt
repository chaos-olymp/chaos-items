package de.chaosolymp.chaositems.event_listener

import de.chaosolymp.chaositems.ItemsPlugin
import de.chaosolymp.chaositems.configuration.PluginConfiguration
import de.chaosolymp.chaositems.configuration.trigger.InteractTriggerConfiguration
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot

internal class PlayerListener(private val plugin: ItemsPlugin): Listener {
    @EventHandler
    fun handleRightClick(event: PlayerInteractEvent) {
        val item = event.player.inventory.getItem(EquipmentSlot.HAND)

        if(item.type == Material.AIR) {
            return
        }

        plugin.config.items
            ?.filter { itemConfig -> itemConfig.triggers?.any { trigger ->
                if(trigger !is InteractTriggerConfiguration) {
                    return@any false
                }

                if(trigger.sneakState != null) {
                    if(event.player.isSneaking != trigger.sneakState) return@any false
                }
                if(trigger.sprintState != null) {
                    if(event.player.isSprinting != trigger.sprintState) return@any false
                }

                return@any true
            } == true }
            ?.forEach { itemConfig ->
            if(itemConfig.selector != null && itemConfig.action != null) {
                if(itemConfig.selector!!.doesMatch(item)) {
                    itemConfig.action!!.createAction().performAction(event.player)
                    if(item.amount == 1) {
                        item.type = Material.AIR
                    } else {
                        item.amount--
                    }
                    event.player.inventory.setItem(EquipmentSlot.HAND, item)
                    event.player.playSound(event.player.location, Sound.BLOCK_COMPARATOR_CLICK, SoundCategory.AMBIENT, 1.0f, 0.0f)
                    plugin.logger.fine("${event.player.name} (${event.player.uniqueId}) used chaos item")
                }
            }
        }
    }
}