package fr.vsct.tock.bot.mongo

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.litote.jackson.JacksonModuleServiceLoader

internal class Feature_Serializer : StdSerializer<Feature>(Feature::class.java),
        JacksonModuleServiceLoader {
    override fun module() = SimpleModule().addSerializer(Feature::class.java, this)

    override fun serialize(
        value: Feature,
        gen: JsonGenerator,
        serializers: SerializerProvider
    ) {
        gen.writeStartObject()
        gen.writeFieldName("_id")
        val __id_ = value._id
        gen.writeString(__id_)
        gen.writeFieldName("key")
        val _key_ = value.key
        gen.writeString(_key_)
        gen.writeFieldName("enabled")
        val _enabled_ = value.enabled
        gen.writeBoolean(_enabled_)
        gen.writeFieldName("botId")
        val _botId_ = value.botId
        gen.writeString(_botId_)
        gen.writeFieldName("namespace")
        val _namespace_ = value.namespace
        gen.writeString(_namespace_)
        gen.writeEndObject()
    }
}
