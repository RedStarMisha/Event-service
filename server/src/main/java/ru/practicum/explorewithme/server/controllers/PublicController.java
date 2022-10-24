package ru.practicum.explorewithme.server.controllers;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.practicum.explorewithme.models.event.EventSort;

import java.util.Map;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
public class PublicController {



//    @GetMapping("/events")
//    public ResponseEntity<Object> getEvents(@RequestParam(name = "text", required = false) String text,
//                                           @RequestParam(name = "categories", required = false) int[] categories,
//                                           @RequestParam(name = "paid", required = false) Boolean paid,
//                                           @RequestParam(name = "rangeStart", required = false) String rangeStart,
//                                           @RequestParam(name = "rangeEnd", required = false) String rangeEnd,
//                                           @RequestParam(name = "available", defaultValue = "false") boolean available,
//                                           @RequestParam(name = "sort", defaultValue = "EVENT_DATE") EventSort sort,
//                                           @RequestParam(name = "from", defaultValue = "0") int from,
//                                           @RequestParam(name = "size", defaultValue = "10") int size) {
//
//
//        return client.getEvents(parameters);
//    }
//
//    @GetMapping("/events/{eventId}")
//    public ResponseEntity<Object> getEventById(@PathVariable(name = "eventId") int eventId) {
//        return client.getEventById(eventId);
//    }
//
//    @GetMapping("/compilation")
//    public ResponseEntity<Object> getCompilations(@RequestParam(name = "pinned", required = false) boolean pinned,
//                                                  @RequestParam(name = "from", defaultValue = "0") int from,
//                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
//        return client.getCompilations(pinned, from, size);
//    }
//
//    @GetMapping("/compilation/{compilationId}")
//    public ResponseEntity<Object> getCompilationById(@PathVariable(name = "compilationId") Long compilationId) {
//        return client.getCompilationById(compilationId);
//    }
//
//    @GetMapping("/categories")
//    public ResponseEntity<Object> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
//                                                @RequestParam(name = "size", defaultValue = "10") int size) {
//        return client.getCategories(from, size);
//    }
//    @GetMapping("/categories/{categoryId}")
//    public ResponseEntity<Object> getCategoryById(@PathVariable(name = "categoryId") long categoryId) {
//        return client.getCategoryById(categoryId);
//    }


}
