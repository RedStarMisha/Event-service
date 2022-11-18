package ru.practicum.explorewithme.server.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import ru.practicum.explorewithme.clients.stat.StatClient;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;
import ru.practicum.explorewithme.server.models.Event;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class StatsHandler {
    private final StatClient statClient;

    public Event statsHandle(Event event, String ip) {
        String uri = "/events/" + event.getId();
        saveStats(uri, ip);
        return getStats(event, new String[]{uri});
    }

    public Event statsHandle(Event event) {
        String uri = "/events/" + event.getId();
        return getStats(event, new String[]{uri});
    }

    public Event statsHandle(Event event, String uri, String ip) {
        saveStats(uri, ip);
        return getStats(event, new String[]{uri});
    }

    private Event getStats(Event event, String[] uris) {
        ResponseEntity<List<ViewStats>> response = statClient.getStats(event.getCreated(),
                LocalDateTime.now().plusMinutes(1L), uris, false);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Сервер сейчас не доступен");
        }

        if (response.getBody().size() > 0) {
            ViewStats stats = response.getBody().get(0);
            event.setViews(stats.getHits());
        }

        return event;
    }

    private void saveStats(String requestURI, String remoteAddr) {
        EndpointHit endpointHit = new EndpointHit("server", requestURI, remoteAddr, LocalDateTime.now());

        ResponseEntity<EndpointHit> response = statClient.addHit(endpointHit);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Сервер сейчас не доступен");
        }
    }
}
