package tour.nonghaeng.global.converter.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import tour.nonghaeng.domain.reservation.dto.room.CreateRoomReservationDto;

import java.io.IOException;

@Component
@Slf4j
public class CreateRoomReservationDtoConverter extends MappingJackson2HttpMessageConverter {
    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        log.info("컨버터실행");
        if (clazz.equals(CreateRoomReservationDto.class)) {
            log.info("CreateRoomReservationDto클래스라서 이거 실행");
            ObjectMapper objectMapper = getObjectMapper();
            CreateRoomReservationDto requestDto = objectMapper.readValue(inputMessage.getBody(), CreateRoomReservationDto.class);
            requestDto.toSetLocalDateList();
            return requestDto;
        }
        return super.readInternal(clazz, inputMessage);
    }
}
