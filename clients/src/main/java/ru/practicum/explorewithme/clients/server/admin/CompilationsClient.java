package ru.practicum.explorewithme.clients.server.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;


public class CompilationsClient extends BaseClient {

    public CompilationsClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> addCompilation(NewCompilationDto compilationDto) {
        return post("", compilationDto);
    }

    public ResponseEntity<Object> deleteCompilation(long compId) {
        return delete("/" + compId);
    }

    public ResponseEntity<Object> deleteEventFromCompilation(long compId, long eventId) {
        return delete("/" + compId + "/events/" + eventId);
    }

    public ResponseEntity<Object> addEventFromCompilation(long compId, long eventId) {
        return patch("/" + compId + "/events/" + eventId);
    }

    public ResponseEntity<Object> unpinCompilation(long compId) {
        return delete("/" + compId + "/pin");
    }

    public ResponseEntity<Object> pinCompilation(long compId) {
        return patch("/" + compId + "/pin");
    }
}
