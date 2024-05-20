package pe.gob.munichupaca.apiservice.shared.infrastructure.jwt.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pe.gob.munichupaca.apiservice.auth.domain.models.entity.UserEntity;
import pe.gob.munichupaca.apiservice.shared.infrastructure.jwt.utils.JwtUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        UserEntity userEntity;
        String username;
        String password;

        try {
            userEntity = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);
            username = userEntity.getUsername();
            password = userEntity.getPassword();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        return getAuthenticationManager().authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String token = jwtUtil.generateAccessToken(user.getUsername());

        response.addHeader("Authorization", token);

        Map<String, Object> httpResponse = new HashMap<>();
        httpResponse.put("Token", token);
        httpResponse.put("Message", "Authentication successful");
        httpResponse.put("Username", user.getUsername());

        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
