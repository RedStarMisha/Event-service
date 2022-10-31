package ru.practicum.explorewithme.publ;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.explorewithme.clients.server.PublicClient;
import ru.practicum.explorewithme.exceptions.UnknownEnumElementException;
import ru.practicum.explorewithme.models.event.EventSort;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static ru.practicum.explorewithme.validation.ValidUtil.dateValidation;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class PublicController {

    private final PublicClient client;

    @GetMapping("/events")
    public ResponseEntity<Object> getEvents(@RequestParam(name = "text", required = false) String text,
                                            @RequestParam(name = "categories", required = false) int[] categories,
                                            @RequestParam(name = "paid", required = false) Boolean paid,
                                            @RequestParam(name = "rangeStart", required = false) String rangeStart,
                                            @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
                                            @RequestParam(name = "available", required = false) Boolean available,
                                            @RequestParam(name = "sort", defaultValue = "EVENT_DATE") String sort,
                                            @RequestParam(name = "from", defaultValue = "0") int from,
                                            @RequestParam(name = "size", defaultValue = "10") int size) {

        EventSort sortType = EventSort.from(sort).orElseThrow(()-> new UnknownEnumElementException("Неизвестный тип сортировки"));

        dateValidation(rangeStart, rangeEnd);

        Map<String, Object> parameters = new HashMap<>();

        parameters.put(  "text", text);
        parameters.put( "categories", categories);
        parameters.put( "paid", paid);
        parameters.put( "rangeStart", rangeStart);
        parameters.put( "rangeEnd", rangeEnd);
        parameters.put("available", available);
        parameters.put("sort", sortType);
        parameters.put("from", from);
        parameters.put("size", size);

        return client.getEvents(parameters);
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<Object> getEventById(@PathVariable(name = "eventId") long eventId) {
        return client.getEventById(eventId);
    }

    @GetMapping("/compilations")
    public ResponseEntity<Object> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                  @RequestParam(name = "from", defaultValue = "0") int from,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        return client.getCompilations(pinned, from, size);
    }

    @GetMapping("/compilations/{compilationId}")
    public ResponseEntity<Object> getCompilationById(@PathVariable(name = "compilationId") Long compilationId) {
        return client.getCompilationById(compilationId);
    }

    @GetMapping("/categories")
    public ResponseEntity<Object> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                                @RequestParam(name = "size", defaultValue = "10") int size) {
        return client.getCategories(from, size);
    }
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Object> getCategoryById(@PathVariable(name = "categoryId") Long categoryId) {
        return client.getCategoryById(categoryId);
    }


}
