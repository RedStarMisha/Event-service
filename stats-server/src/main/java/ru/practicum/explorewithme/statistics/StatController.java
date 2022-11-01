package ru.practicum.explorewithme.statistics;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.statistics.EndpointHit;
import ru.practicum.explorewithme.models.statistics.ViewStats;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class StatController {

    private final StatsService service;

    @PostMapping("/hit")
    public EndpointHit saveStats(@RequestBody EndpointHit hit) {
        log.info("endpoint {} добавлен", hit);
        return service.saveStats(hit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(name = "uris", required = false) String[] uris,
                                    @RequestParam(name = "unique", defaultValue = "false") boolean unique, HttpServletRequest request) throws UnsupportedEncodingException {
        return service.getStats(request.getQueryString(), uris, unique);
    }


}
