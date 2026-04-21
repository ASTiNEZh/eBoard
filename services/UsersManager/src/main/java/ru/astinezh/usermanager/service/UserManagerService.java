package ru.astinezh.usermanager.service;

import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.ASTiNEZh.dto.UserDTO;
import ru.ASTiNEZh.dto.UserLoginResponseDTO;
import ru.ASTiNEZh.dto.UserRegisterDTO;
import ru.astinezh.usermanager.feign.UserCRUDFeignClient;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserManagerService {

    @Autowired
    private final Keycloak keycloak;

    @Autowired
    private final UserCRUDFeignClient userCRUDFeignClient;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    private final RestTemplate restTemplate = new RestTemplate();

    public String registerNewUser(UserRegisterDTO userAutoriseDTO) {

        StringBuilder usernameBuilder = new StringBuilder(userAutoriseDTO.getSurname()).append(" ")
                .append(userAutoriseDTO.getFirstName()).append(" ")
                .append(userAutoriseDTO.getLastName());

        UserRepresentation user = new UserRepresentation();
        user.setUsername(usernameBuilder.toString());
        user.setEmail(userAutoriseDTO.getEmail());
        user.setFirstName(userAutoriseDTO.getFirstName());
        user.setLastName(userAutoriseDTO.getSurname());
        user.setEnabled(true);
        user.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userAutoriseDTO.getPassword());
        credential.setTemporary(false);
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm("eBoard").users().create(user);

        if (response.getStatus() != 201) {
            throw new RuntimeException("User creation failed: " + response.getStatus());
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        List<ClientRepresentation> clients = keycloak.realm("eBoard").clients()
                .findByClientId(clientId);
        if (clients.isEmpty()) {
            throw new RuntimeException("Client 'backend' not found");
        }
        String clientUuid = clients.get(0).getId();

        RoleRepresentation role = keycloak.realm("eBoard").clients()
                .get(clientUuid).roles().get("role_user").toRepresentation();

        UserResource userResource = keycloak.realm("eBoard").users().get(userId);
        userResource.roles().clientLevel(clientUuid).add(Collections.singletonList(role));

        UserDTO userDTO = new ModelMapper().map(userAutoriseDTO, UserDTO.class);
        userDTO.setUuid(UUID.fromString(userId));
        userCRUDFeignClient.saveUserData(userDTO);
        return userId;
    }

    public UserLoginResponseDTO login(String username, String password) {
        String tokenUrl = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);
        body.add("grant_type", "password");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    tokenUrl,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responsStr = response.getBody();

                UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
                userLoginResponseDTO.setAccessToken((String) responsStr.get("access_token"));
                userLoginResponseDTO.setExpiresIn((Integer) responsStr.get("expires_in"));
                userLoginResponseDTO.setRefreshExpiresIn((Integer) responsStr.get("refresh_expires_in"));
                userLoginResponseDTO.setRefreshToken((String) responsStr.get("refresh_token"));
                userLoginResponseDTO.setTokenType((String) responsStr.get("token_type"));
                userLoginResponseDTO.setNotBeforePolicy((Integer) responsStr.get("not-before-policy"));
                userLoginResponseDTO.setSessionState((String) responsStr.get("session_state"));
                userLoginResponseDTO.scope((String) responsStr.get("scope"));

                return  userLoginResponseDTO;
            } else {
                throw new RuntimeException("Login failed with status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user: " + e.getMessage(), e);
        }
    }
}
