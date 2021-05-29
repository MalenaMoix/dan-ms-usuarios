package jms.dan.usuarios.repository;

import jms.dan.usuarios.dto.OrderDTO;
import jms.dan.usuarios.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class OrderRepository implements IOrderRepository {

    private static final String BASEURL = "http://localhost:8081/api-orders/";
    private static final String ORDERS_URL = BASEURL + "orders";

    @Override
    public List<OrderDTO> getOrdersByClientId(Integer id) {
        WebClient webClient = WebClient.create(ORDERS_URL + "?clientId=" + id);
        try {
            ResponseEntity<List<OrderDTO>> response = webClient.get()
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .toEntityList(OrderDTO.class)
                    .block();
            List<OrderDTO> orders;
            if (response != null && response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
                orders = new ArrayList<>(Objects.requireNonNull(response.getBody()));
            } else {
                throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                        "An error has occurred - Orders not found", HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
            return orders;
        } catch (WebClientException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
