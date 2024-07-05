package az.edu.turing.controller;

import az.edu.turing.dto.FlightDto;
import az.edu.turing.service.FlightService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class FlightServiceHandler {
    private final FlightService flightService;
    private final ObjectMapper objectMapper;

    public FlightServiceHandler(FlightService flightService, ObjectMapper objectMapper) {
        this.flightService = flightService;
        this.objectMapper = objectMapper;
    }

    public void handleGetFLightByID(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long flightId = Long.parseLong(req.getParameter("id"));
            FlightDto flightDto = flightService.getFlightById(flightId);
            System.out.println("Logger found" + flightDto);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(flightDto));
        } catch (NumberFormatException e) {
            handleException(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid flight format", e);

        } catch (RuntimeException e) {
            handleException(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), e);

        } catch (Exception e) {
            handleException(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong ! Pls Try again", e);

        }
    }

    public void handleGetAllFlight(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("Getting all flights");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(flightService.getAllFlights()));
        } catch (RuntimeException e) {
            handleException(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            handleException(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    public void handleCreateFLight(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            System.out.println("readinf JSON input from request.. ");
            FlightDto flightDto = objectMapper.readValue(req.getReader(), FlightDto.class);
            System.out.println("JSON succesfully " + flightDto);
            flightService.saveFlight(flightDto);
            System.out.println(" FLight saved with id" + flightDto.getId());
            if (flightDto.getId() != 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("FLight saved succesfully");
            } else {
                handleException(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "FLight saved failed Id is zero", null);
            }
        } catch (RuntimeException e) {
            handleException(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            handleException(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Something went wrong!, Try again", e);
        }
    }

    private void handleException(HttpServletResponse resp, int statusCode, String message, Exception e) {
        if (e != null) {
            System.out.println(message);
        } else {
            System.out.println(message);
        }
        resp.setStatus(statusCode, message);
    }

    public void handleCancelFlight(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            long flightId = Long.parseLong(req.getParameter("id"));
            flightService.cancelFlight(flightId);
            System.out.println("cancel flight with id" + flightId);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(objectMapper.writeValueAsString(flightId));
        } catch (NumberFormatException e) {
            handleException(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid flight format", e);
        } catch (RuntimeException e) {
            handleException(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage(), e);
        } catch (Exception e) {
            handleException(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
