package tour.nonghaeng.domain.reservation.dto.payment;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class PgResponseDeserializer extends JsonDeserializer<PortOneResponseDto.PgResponse> {

    @Override
    public PortOneResponseDto.PgResponse deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        String pgResponseJson = p.readValueAs(String.class);
        return mapper.readValue(pgResponseJson, PortOneResponseDto.PgResponse.class);
    }
}
