package az.edu.turing.controller;

import az.edu.turing.dao.entity.BookingEntity;
import az.edu.turing.dto.BookingDto;
import az.edu.turing.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class BookingServiceHandler {
    private final BookingService bookingService;
    private final ObjectMapper objectMapper;

    public BookingServiceHandler(BookingService bookingService, ObjectMapper objectMapper) {
        this.bookingService = bookingService;
        this.objectMapper = objectMapper;
    }

    public void handleCreateBooking(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("Reading JSON from request ");
            BookingDto bookingDto = objectMapper.readValue(req.getReader(), BookingDto.class);
            System.out.println("Json input read succesfully" + bookingDto);
            bookingService.saveBooking(bookingDto);
            System.out.println("Booking saved with ID" + bookingDto.getId());

            if (bookingDto.getId() != 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Booking Succesfully");
            } else {
                System.out.println("Booking failed, ID is zero");
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Booking failed");
            }
        } catch (RuntimeException e) {
            System.out.println("Runtime Exception occured");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);

        } catch (Exception e) {
            System.out.println("Exception occured");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong ,pls tyr again");
        }

    }

    public void handleCancelBooking(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long bookingId = Long.parseLong(req.getParameter("id"));
            bookingService.cancelBooking(bookingId);
            System.out.println("Booking successfully canceled" + bookingId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Booking successfully canceled");
        } catch (RuntimeException e) {
            System.out.println("Runtime Exception occured");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception Occured");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    public void handleGetAllBookings(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("Fetching all bookings");
            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), bookingService.getAllBookings());
        } catch (RuntimeException e) {
            System.out.println("Runtime exception occured when all bookings fetching");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            System.out.println(" Exception occured");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong , pls try again");
        }
    }

    public void handleFindById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long bookingId = Long.parseLong(req.getParameter("id"));
            System.out.println("Fetchin booking by id" + bookingId);
            BookingEntity bookingEntity = bookingService.findById(bookingId);
            if (bookingEntity != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), bookingEntity);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }

        } catch (RuntimeException e) {
            System.out.println();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            System.out.println();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
