package controller;

import java.util.List;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;

/**
 * This class is the base controller for our AIMS project
 * @author nguyenlm
 */

// Lớp này có 2 trách nhiệm, vi phạm SRP, nên tách ra làm 2 lớp con để rõ ràng hơn
public class BaseController {
    
    /**
     * The method checks whether the Media in Cart, if it were in, we will return the CartMedia else return null
     * @param media
     * @return CartMedia or null
     */



    // Stamp coupling - lấy 1 vài thuộc tính trong Media
    public CartMedia checkMediaInCart(Media media){
        return Cart.getCart().checkMediaInCart(media);
    }

    /**
     * This method gets the list of items in cart
     * @return List[CartMedia]
     */
    public List getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}
