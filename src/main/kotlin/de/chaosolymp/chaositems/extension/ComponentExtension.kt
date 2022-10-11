package de.chaosolymp.chaositems.extension

import com.google.gson.Gson
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer

fun Component.toMap(): Map<*, *> {
    val serializedJson = GsonComponentSerializer.gson().serialize(this)
    return Gson().fromJson(serializedJson, Map::class.java)
}

fun componentFromMap(map: Map<*, *>?): Component? {
    if(map == null) return null
    val serializedJson = Gson().toJson(map)
    return GsonComponentSerializer.gson().deserialize(serializedJson)
}