package com.gongsi.app.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {
    public OffsetDateTimeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public OffsetDateTime deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
            throws IOException {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String date = jsonParser.getText().replace("T", " ");
        if (date.length() == 16) {
            date += ":00";
        }

        final LocalDateTime localDateTime = LocalDateTime.from(formatter.parse(date));
        return OffsetDateTime.of(localDateTime, OffsetDateTime.now().getOffset());
    }
}
