package az.edu.turing.controller;

import az.edu.turing.dao.FlightDao;
import az.edu.turing.dao.impl.FlightPostgresDao;
import az.edu.turing.service.FlightService;
import az.edu.turing.service.impl.FlightServiceİmpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class FlightServlet extends HttpServlet {
    private final FlightServiceHandler flightServiceHandler;

    public FlightServlet() {
        FlightDao flightDao = new FlightPostgresDao();
        FlightService flightService = new FlightServiceİmpl(flightDao);
        ObjectMapper objectMapper = new ObjectMapper();
        this.flightServiceHandler = new FlightServiceHandler(flightService, objectMapper);
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            flightServiceHandler.handleGetFLightByID(req, resp);

        } else {
            flightServiceHandler.handleGetAllFlight(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        flightServiceHandler.handleCreateFLight(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        flightServiceHandler.handleCancelFlight(req, resp);
    }
}
