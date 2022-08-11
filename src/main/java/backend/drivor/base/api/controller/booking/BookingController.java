package backend.drivor.base.api.controller.booking;

import backend.drivor.base.api.controller.BaseController;
import backend.drivor.base.domain.document.Account;
import backend.drivor.base.domain.repository.BookingHistoryRepository;
import backend.drivor.base.domain.request.AcceptBookingRequest;
import backend.drivor.base.domain.request.DriverArrivedRequest;
import backend.drivor.base.domain.request.NewBookingRequest;
import backend.drivor.base.domain.response.ApiResponse;
import backend.drivor.base.domain.response.BookingHistoryResponse;
import backend.drivor.base.domain.response.GeneralSubmitResponse;
import backend.drivor.base.service.distribution.BookingDistribution;
import backend.drivor.base.service.inf.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/booking")
public class BookingController extends BaseController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingDistribution bookingDistribution;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @PostMapping("/new")
    public ResponseEntity<?> newBookingRequest(@Valid @RequestBody NewBookingRequest request) {

        Account account = getLoggedAccount();

        BookingHistoryResponse data = bookingService.newBookingRequest(account, request);

        ApiResponse<BookingHistoryResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("New booking request successfully!");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptBookingRequest(@Valid @RequestBody AcceptBookingRequest request) {

        Account account = getLoggedAccount();

        GeneralSubmitResponse data = bookingService.acceptBookingRequest(account, request);

        ApiResponse<GeneralSubmitResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("Arrived booking request successfully!");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/arrived")
    public ResponseEntity<?> arrivedBookingRequest(@Valid @RequestBody DriverArrivedRequest request) {

        Account account = getLoggedAccount();
        GeneralSubmitResponse data = bookingService.arrivedBookingRequest(account, request);

        ApiResponse<GeneralSubmitResponse> response = new ApiResponse<>();
        response.setCode(HttpStatus.OK.name());
        response.setMessage("Arrived booking request successfully!");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

}
