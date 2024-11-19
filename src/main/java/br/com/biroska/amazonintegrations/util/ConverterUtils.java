package br.com.biroska.amazonintegrations.util;

import br.com.biroska.amazonintegrations.integration.aws.sqs.impl.SqsMessageListenerImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConverterUtils {

    private static final Logger logger = LoggerFactory.getLogger(ConverterUtils.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
    }

    public static LocalDate stringToLocalDate(String dateString) {

        return LocalDate.parse(dateString, FORMATTER);
    }

    public static String dateToString(LocalDate date) {

        return date.format(FORMATTER);
    }

    public static String toJson(Object o){

        if (ObjectUtils.isEmpty(o)){
            return "";
        }

        try {
            return mapper.writeValueAsString( o );
        } catch (JsonProcessingException e) {
            logger.error("Ocorreu um erro ao converter o objeto: {} para Json", o);
            return o.toString();
        }
    }

}
