package com.comrade.dataaccess.restaurant.repository;

import com.comrade.dataaccess.restaurant.entity.RestaurantEntity;
import com.comrade.dataaccess.restaurant.entity.RestaurantEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<RestaurantEntity, RestaurantEntityId> {
    Optional<List<RestaurantEntity>> findByRestaurantIdAndProductIdIn(UUID RestaurantId,List<UUID> productIds);
}
