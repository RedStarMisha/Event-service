package ru.practicum.explorewithme.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

public class ViewsParamDecoder {
    private String query;
    private Map<String, String> parameters = new HashMap<>();

    public ViewsParamDecoder(String query) {
        getParam(query);
    }

    private void getParam(String query) {
        String[] url = query.split("&");

        for (String param : url) {
            parameters.put(param.split("=")[0], param.split("=")[1]);
        }
    }

    public LocalDateTime getStart() throws UnsupportedEncodingException {
        String date = parameters.get("start");
        while (!dateCheck(date)) {
            date = decode(date);
        }
        return toLocalDateTime(date);
    }

    public LocalDateTime getEnd() throws UnsupportedEncodingException {
        String date = parameters.get("end");
        while (!dateCheck(date)) {
            date = decode(date);
        }
        return toLocalDateTime(date);
    }

    private String decode(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }

    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    private boolean dateCheck(String date) {
        try {
            LocalDateTime.parse(date, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }


}
