package by.bsuir.exchange.validator;

import by.bsuir.exchange.bean.OfferBean;

public class OfferValidator {
    public static boolean validate(OfferBean offerBean){
        return offerBean.getTransport() != null && !offerBean.getTransport().isEmpty();
    }
}
