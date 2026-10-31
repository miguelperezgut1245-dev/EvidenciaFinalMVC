package com.tecmilenio.evidencia.controller;

import com.google.gson.Gson;
import com.tecmilenio.evidencia.model.Directory;
import com.tecmilenio.evidencia.model.File;
import com.tecmilenio.evidencia.model.Notify;
import com.tecmilenio.evidencia.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

@WebServlet(name = "ApiController", urlPatterns = {"/api"})
public class ApiController extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String resource = request.getParameter("resource");
        String format = request.getParameter("format");

        // Validar formato por defecto si viene vacío (JSON)
        if (format == null || format.trim().isEmpty()) {
            format = "json";
        }

        // Configurar el tipo de respuesta (Content-Type)
        if (format.equalsIgnoreCase("xml")) {
            response.setContentType("application/xml;charset=UTF-8");
        } else {
            response.setContentType("application/json;charset=UTF-8");
        }

        PrintWriter out = response.getWriter();

        try {
            // Manejo de Excepciones: Parámetro obligatorio vacío
            if (resource == null || resource.trim().isEmpty()) {
                throw new IllegalArgumentException("El parametro 'resource' es obligatorio (user, file, directory, notify).");
            }

            // Procesar la solicitud según el recurso pedido
            switch (resource.toLowerCase()) {
                case "user":
                    User user = new User(1, "alexa_dev", "alexa@tecmilenio.edu.mx");
                    sendResponse(out, user, format, User.class);
                    break;

                case "file":
                    File file = new File(101, "evidencia_final.pdf", 2048576L);
                    sendResponse(out, file, format, File.class);
                    break;

                case "directory":
                    Directory directory = new Directory(202, "/var/www/html/evidencia");
                    sendResponse(out, directory, format, Directory.class);
                    break;

                case "notify":
                    Notify notify = new Notify(303, "Proyecto cargado exitosamente en Apache NetBeans", "SUCCESS");
                    sendResponse(out, notify, format, Notify.class);
                    break;

                default:
                    // Manejo de Excepciones: Recurso no encontrado
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    sendError(out, "Recurso '" + resource + "' no encontrado.", format);
                    break;
            }

        } catch (IllegalArgumentException e) {
            // Manejo de Excepciones: Petición incorrecta (Bad Request)
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendError(out, e.getMessage(), format);
        } catch (Exception e) {
            // Manejo de Excepciones: Errores internos generales
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            sendError(out, "Error interno en el servidor: " + e.getMessage(), format);
        } finally {
            out.close();
        }
    }

    // Método para convertir y enviar las respuestas a XML o JSON dinámicamente
    private void sendResponse(PrintWriter out, Object data, String format, Class<?> clazz) throws Exception {
        if (format.equalsIgnoreCase("xml")) {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(data, out);
        } else {
            out.print(gson.toJson(data));
        }
    }

    // Método para estructurar los mensajes de error en XML o JSON limpio
    private void sendError(PrintWriter out, String message, String format) {
        if (format.equalsIgnoreCase("xml")) {
            out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            out.println("<error>");
            out.println("    <message>" + message + "</message>");
            out.println("</error>");
        } else {
            out.print("{\"error\":\"" + message + "\"}");
        }
    }
}
