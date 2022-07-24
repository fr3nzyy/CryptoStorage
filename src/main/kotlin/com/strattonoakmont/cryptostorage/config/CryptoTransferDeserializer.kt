package com.strattonoakmont.cryptoreceiver.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer
import com.strattonoakmont.cryptostorage.service.CryptoTransfer
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.time.format.DateTimeFormatter
import kotlin.text.Charsets.UTF_8


class CryptoTransferDeserializer : Deserializer<CryptoTransfer> {

    private val objectMapper = ObjectMapper().registerModule(JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    private val log = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): CryptoTransfer? {
        log.info("Deserializing...")
        return objectMapper.readValue(
            String(
                data ?: throw SerializationException("Error when deserializing byte[] to Product"), UTF_8
            ), CryptoTransfer::class.java
        )
    }
}

@Configuration
@AutoConfigureBefore(JacksonAutoConfiguration::class)
class DateTimeAutoConfiguration {
    @Bean
    fun jacksonObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer() {
            fun customize(jacksonObjectMapperBuilder: Jackson2ObjectMapperBuilder): Unit {
                jacksonObjectMapperBuilder.deserializers(
                    LocalDateDeserializer(
                        DateTimeFormatter.ofPattern(DateTimeFormatter.ISO_OFFSET_DATE_TIME.toString())
                    )
                )
            }
        };
    }
}
@Configuration
class ContactAppConfig {
    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.simpleDateFormat(dateTimeFormat)
            builder.serializers(LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
            builder.serializers(LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
        }
    }

    companion object {
        private const val dateFormat = "yyyy-MM-dd"
        private const val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
    }
}

