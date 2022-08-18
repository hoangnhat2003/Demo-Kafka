package backend.drivor.base.consumer.event;

import backend.drivor.base.domain.document.BookingHistory;

public interface BookingEvent {

    void arrivedBookingRequest(BookingHistory bookingHistory);

}
