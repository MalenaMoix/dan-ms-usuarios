package jms.dan.usuarios.repository;

import jms.dan.usuarios.dto.OrderDTO;

import java.util.List;

public interface IOrderRepository {

    List<OrderDTO> getOrdersByClientId(Integer Id);
}
