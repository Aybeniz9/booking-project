package az.edu.turing;

import az.edu.turing.controller.BookingServlet;
import az.edu.turing.controller.FlightServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main{
    public static void main(String[] args) throws Exception
    {
        Server server= new Server(8080);
        ServletContextHandler context=new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new BookingServlet()),"/bookings/*");
        context.addServlet(new ServletHolder (new FlightServlet()),"/flights/*");
        server.start();
        server.join();

    }
}