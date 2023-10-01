package com.comrade.dataaccess.restaurant.mapper;

import com.comrade.dataaccess.restaurant.entity.RestaurantEntity;
import com.comrade.dataaccess.restaurant.exception.RestaurantDataAccessException;
import com.comrade.domain.entity.Product;
import com.comrade.domain.entity.Restaurant;
import com.comrade.domain.valueobject.Money;
import com.comrade.domain.valueobject.ProductId;
import com.comrade.domain.valueobject.RestaurantId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RestaurantDataAccessMapper {

    public List<UUID> restaurantToRestaurantProducts(Restaurant restaurant) {
        return restaurant.getProducts().stream()
                .map(product -> product.getId().getValue())
                .collect(Collectors.toList());
    }

    public Restaurant restaurantEntityToRestaurant(List<RestaurantEntity> restaurantEntities) {
        RestaurantEntity restaurantEntity = restaurantEntities.stream().findFirst().orElseThrow(() -> new RestaurantDataAccessException("Restaurant could not be found!"));

        List<Product> restaurantProducts = restaurantEntities.stream().map(entity -> new Product(new ProductId(entity.getProductId()), entity.getProductName(), new Money(entity.getProductPrice()))).toList();

        return Restaurant.builder()
                .restaurantId(new RestaurantId(restaurantEntity.getRestaurantId()))
                .products(restaurantProducts)
                .active(restaurantEntity.isRestaurantActive())
                .build();
    }
}
