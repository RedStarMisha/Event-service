package ru.practicum.explorewithme.clients.admin;

import io.micrometer.core.lang.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.models.user.NewUserRequest;

import java.util.HashMap;
import java.util.Map;

public class UserClient extends BaseClient {

    public UserClient(RestTemplate rest) {
        super(rest);
    }

     public ResponseEntity<Object> getUsers(@Nullable long[] ids, int from, int size) {
         Map<String, Object> param = new HashMap<>(Map.of("from", from, "size", size));
         param.put("ids", ids);

        return get("?ids={ids}&from={from}&size={size}", param);
    }

    public ResponseEntity<Object> addUser(NewUserRequest newUserRequest) {
        return post("", newUserRequest);
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        return delete("/" + userId);
    }
}
