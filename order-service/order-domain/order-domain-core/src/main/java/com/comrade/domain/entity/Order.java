package com.comrade.domain.entity;

import com.comrade.domain.constants.DomainConstants;
import com.comrade.domain.exception.OrderDomainException;
import com.comrade.domain.valueobject.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Order extends AggregateRoot<OrderId> {

    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final StreetAddress deliveryAddress;
    private final Money price;
    private final List<OrderItem> items;
    private TrackingId trackingId;
    private OrderStatus orderStatus;
    private List<String> failureMessages;

    public void validateOrder(){
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    private void validateInitialOrder() {

        if (orderStatus !=null && getId() !=null){
            throw new OrderDomainException(DomainConstants.ORDER_DOMAIN_ERROR);
        }
    }

    private void validateTotalPrice() {

        if (price == null || !price.isGreaterThanZero()) {
            throw new OrderDomainException(DomainConstants.ORDER_TOTAL_PRICE_ERROR);
        }
    }

    private void validateItemsPrice() {

     Money orderItemTotal=items.stream().map(orderItem -> {
            validateItemPrice(orderItem);
            return orderItem.getSubTotal();
        }).reduce(Money.ZERO,Money::add);

        if (!price.equals(orderItemTotal)) {
                throw new OrderDomainException("Total price: "+price.getAmount()+" is not equals to Order item total:"+orderItemTotal.getAmount()+" !");
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: "+orderItem.getPrice().getAmount() +" is not valid for the product: "+orderItem.getProduct().getId().getValue());
        }
    }
    public void pay(){
        if (orderStatus !=OrderStatus.PENDING){
            throw new OrderDomainException(DomainConstants.ORDER_STATUS_PAY_ERROR);
        }
        orderStatus=OrderStatus.PAID;
    }

    public void approve(){
        if (orderStatus !=OrderStatus.PAID){
            throw new OrderDomainException(DomainConstants.ORDER_STATUS_APPROVE_ERROR);
        }
        orderStatus = OrderStatus.APPROVED;
    }

    public void initCancel(List<String> failureMessages){
        if(orderStatus != OrderStatus.PAID){
            throw new OrderDomainException(DomainConstants.ORDER_STATUS_CANCELLING_ERROR);
        }
        orderStatus=OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
    }

    public void cancel(List<String> failureMessages){
        if (!(orderStatus == OrderStatus.CANCELLED || orderStatus == OrderStatus.PENDING)){
            throw new OrderDomainException(DomainConstants.ORDER_STATUS_CANCEL_ERROR);
        }
        orderStatus=OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages !=null && failureMessages !=null){
            this.failureMessages.addAll(failureMessages.stream().filter(messages->!messages.isEmpty()).toList());
        }
        if (this.failureMessages==null){
            this.failureMessages=failureMessages;
        }
    }
    public void initializeOrder(){
        setId(new OrderId(UUID.randomUUID()));
        trackingId=new TrackingId(UUID.randomUUID());
        orderStatus=OrderStatus.PENDING;
        initializeOrderItems();
    }

    private void initializeOrderItems() {

        long itemId=1;
        for (OrderItem orderItem:items){
            orderItem.initializeOrderItem(super.getId(),new OrderItemId(itemId++));
        }
    }

    private Order(Builder builder) {
        super.setId(builder.orderId);
        customerId = builder.customerId;
        restaurantId = builder.restaurantId;
        deliveryAddress = builder.deliveryAddress;
        price = builder.price;
        items = builder.items;
        trackingId = builder.trackingId;
        orderStatus = builder.orderStatus;
        failureMessages = builder.failureMessages;
    }

    public static Builder builder() {
        return new Builder();
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public RestaurantId getRestaurantId() {
        return restaurantId;
    }

    public StreetAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public Money getPrice() {
        return price;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public TrackingId getTrackingId() {
        return trackingId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public static final class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private StreetAddress deliveryAddress;
        private Money price;
        private List<OrderItem> items;
        private TrackingId trackingId;
        private OrderStatus orderStatus;
        private List<String> failureMessages;

        private Builder() {
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder customerId(CustomerId val) {
            customerId = val;
            return this;
        }

        public Builder restaurantId(RestaurantId val) {
            restaurantId = val;
            return this;
        }

        public Builder deliveryAddress(StreetAddress val) {
            deliveryAddress = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder trackingId(TrackingId val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
