/*
 * serializer
 */
package com.ricoh360.thetaclient.transferred

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * floating point number to/from Long serializer
 */
object NumberAsLongSerializer : KSerializer<Long> {
    /**
     * serial descriptor
     */
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("NumberAsLongSerializer", PrimitiveKind.LONG)

    /**
     * serialize value with encoder
     * @param encoder encoder object
     * @param value value to encode
     */
    override fun serialize(encoder: Encoder, value: Long) {
        encoder.encodeLong(value)
    }

    /**
     * deserialize value with decoder and return decoded value
     * @param decoder decoder object
     * @return decoded value
     */
    override fun deserialize(decoder: Decoder): Long {
        val value = decoder.decodeDouble()
        return value.toLong()
    }
}

/**
 * floating point number to/from Long serializer
 */
object NumberAsIntSerializer : KSerializer<Int> {
    /**
     * serial descriptor
     */
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("NumberAsIntSerializer", PrimitiveKind.INT)

    /**
     * serialize value with encoder
     * @param encoder encoder object
     * @param value value to encode
     */
    override fun serialize(encoder: Encoder, value: Int) {
        encoder.encodeInt(value)
    }

    /**
     * deserialize value with decoder and return decoded value
     * @param decoder decoder object
     * @return decoded value
     */
    override fun deserialize(decoder: Decoder): Int {
        val value = decoder.decodeDouble()
        return value.toInt()
    }
}

/**
 * floating point number to/from List<Int> serializer
 */
@kotlinx.serialization.ExperimentalSerializationApi
object NumbersAsIntsSerializer : KSerializer<List<Int>> {
    /**
     * serial descriptor
     */
    override val descriptor: SerialDescriptor = listSerialDescriptor<Int>()

    /**
     * Double serializer to decode number
     */
    private val doubleSerializer = Double.serializer()

    /**
     * Long serializer to encode Int
     */
    private val intSerializer = Int.serializer()

    /**
     * serialize value with encoder
     * @param encoder encoder object
     * @param value value to encode
     */
    override fun serialize(encoder: Encoder, value: List<Int>) {
        val composite = encoder.beginCollection(descriptor, value.size)
        val iterator = value.iterator()
        for (index in value.indices) {
            composite.encodeSerializableElement(
                descriptor, index, intSerializer, iterator.next()
            )
        }
        composite.endStructure(descriptor)
    }

    /**
     * deserialize value with decoder and return decoded value
     * @param decoder decoder object
     * @return decoded value
     */
    override fun deserialize(decoder: Decoder): List<Int> {
        val result = mutableListOf<Int>()
        val compositeDecoder = decoder.beginStructure(descriptor)
        while (true) {
            val index = compositeDecoder.decodeElementIndex(descriptor)
            if (index == CompositeDecoder.DECODE_DONE) {
                break
            }
            result.add(decoder.decodeSerializableValue(doubleSerializer).toInt())
        }
        compositeDecoder.endStructure(descriptor)
        return result
    }
}

/**
 * floating point number to/from List<Long> serializer
 */
@kotlinx.serialization.ExperimentalSerializationApi
object NumbersAsLongsSerializer : KSerializer<List<Long>> {
    /**
     * serial descriptor
     */
    override val descriptor: SerialDescriptor = listSerialDescriptor<Long>()

    /**
     * Double serializer to decode number
     */
    private val doubleSerializer = Double.serializer()

    /**
     * Long serializer to encode long
     */
    private val longSerializer = Long.serializer()

    /**
     * serialize value with encoder
     * @param encoder encoder object
     * @param value value to encode
     */
    override fun serialize(encoder: Encoder, value: List<Long>) {
        val composite = encoder.beginCollection(descriptor, value.size)
        val iterator = value.iterator()
        for (index in value.indices) {
            composite.encodeSerializableElement(
                descriptor, index, longSerializer, iterator.next()
            )
        }
        composite.endStructure(descriptor)
    }

    /**
     * deserialize value with decoder and return decoded value
     * @param decoder decoder object
     * @return decoded value
     */
    override fun deserialize(decoder: Decoder): List<Long> {
        val result = mutableListOf<Long>()
        val compositeDecoder = decoder.beginStructure(descriptor)
        while (true) {
            val index = compositeDecoder.decodeElementIndex(descriptor)
            if (index == CompositeDecoder.DECODE_DONE) {
                break
            }
            result.add(decoder.decodeSerializableValue(doubleSerializer).toLong())
        }
        compositeDecoder.endStructure(descriptor)
        return result
    }
}
