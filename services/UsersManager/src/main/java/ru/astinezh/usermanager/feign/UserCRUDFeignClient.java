package ru.astinezh.usermanager.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.ASTiNEZh.dto.UserDTO;

@FeignClient(
        name = "${service.user-crud.name}",
        url = "${service.user-crud.url}",
        path = "${service.user-crud.path}"
)
public interface  UserCRUDFeignClient {

    @PostMapping("/users/new")
    void saveUserData(@RequestBody UserDTO userDTO);
}
