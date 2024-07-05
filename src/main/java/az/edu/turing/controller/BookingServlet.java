package az.edu.turing.controller;

import az.edu.turing.dao.BookingDao;
import az.edu.turing.dao.impl.BookingPostgresDao;
import az.edu.turing.service.BookingService;
import az.edu.turing.service.impl.BookingServiceİmpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BookingServlet extends HttpServlet {
    private  final BookingServiceHandler bookingServiceHandler;

    public BookingServlet() {
        BookingDao bookingDao=new BookingPostgresDao();
        BookingService bookingService=new BookingServiceİmpl(bookingDao);
        ObjectMapper objectMapper= new ObjectMapper();
        this.bookingServiceHandler=new BookingServiceHandler(bookingService,objectMapper);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws IOException, ServletException {
        String id= req.getParameter("id");
        if (id!=null){
            bookingServiceHandler.handleFindById(req,resp);
        }
        else {
            bookingServiceHandler.handleGetAllBookings(req,resp);
        }

    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        bookingServiceHandler.handleCreateBooking(req,resp);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        bookingServiceHandler.handleCancelBooking(req,resp);
    }



}
