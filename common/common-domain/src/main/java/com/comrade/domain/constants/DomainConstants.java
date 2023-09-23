package com.comrade.domain.constants;

public class DomainConstants {

    private DomainConstants(){

    }
    public static final String ORDER_DOMAIN_ERROR="Order is not in correct state for initialization!";
    public static final String ORDER_TOTAL_PRICE_ERROR="Order is not in correct state for initialization!";

    public static final String ORDER_STATUS_PAY_ERROR ="Order is not in correct state for pay operation!";
    public static final String ORDER_STATUS_APPROVE_ERROR ="Order is not in correct state for approve operation!";
    public static final String ORDER_STATUS_CANCELLING_ERROR ="Order is not in correct state for init cancel operation!";

    public static final String ORDER_STATUS_CANCEL_ERROR = "Order is not in correct state for  cancel operation!";
}
