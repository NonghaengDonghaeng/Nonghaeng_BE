package tour.nonghaeng.domain.trip.presentation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tour.nonghaeng.domain.trip.dto.MainResponseDto;
import tour.nonghaeng.domain.trip.dto.TripResponseDto;
import tour.nonghaeng.domain.trip.service.TripService;
import tour.nonghaeng.global.auth.auth.service.AuthService;

@RestController
@RequestMapping("/trips")
@RequiredArgsConstructor
@Slf4j
public class TripController {

    private final AuthService authService;
    private final TripService tripService;

    @GetMapping
    public ResponseEntity<TripResponseDto> showTripResponseDto() {

        return new ResponseEntity<>(tripService.getTripResponseDto(), HttpStatus.OK);
    }

    @GetMapping("/best")
    public ResponseEntity<MainResponseDto> showBestTripResponseDto() {

        return new ResponseEntity<>(tripService.getTripBestResponseDto(), HttpStatus.OK);
    }
}
