package com.cbt.testspringapr24;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
public class AppController
{
    //@Autowired
    //CredentialRepository credentialRepository;

    CredentialRepository credentialRepository;
    UserdetailRepository userdetailRepository;
    WalletRepository walletRepository;
    UsernamewalletlinkRepository usernamewalletlinkRepository;
    UsertypelinkRepository usertypelinkRepository;
    ProductofferRepository productofferRepository;
    ProductRepository productRepository;
    ProductofferstatusRepository productofferstatusRepository;
    OrderRepository orderRepository;
    OrderstatusRepository orderstatusRepository;
    OfferOrderViewService offerOrderViewService;
    String admin_username;
    private final PaymentwalletlinkRepository paymentwalletlinkRepository;
    private final PaymentRepository paymentRepository;
    private final NegomessageRepository negomessageRepository;

    AppController( CredentialRepository credentialRepository,
                   UserdetailRepository userdetailRepository,
                   WalletRepository walletRepository,
                   UsernamewalletlinkRepository usernamewalletlinkRepository,
                   UsertypelinkRepository usertypelinkRepository,
                   ProductofferRepository productofferRepository,
                   ProductRepository productRepository,
                   ProductofferstatusRepository productofferstatusRepository,
                   OrderRepository orderRepository,
                   OrderstatusRepository orderstatusRepository,
                   OfferOrderViewService offerOrderViewService,
                   String admin_username,
                   PaymentwalletlinkRepository paymentwalletlinkRepository,
                   PaymentRepository paymentRepository,
                   NegomessageRepository negomessageRepository)

    {
        this.credentialRepository = credentialRepository;
        this.userdetailRepository = userdetailRepository;
        this.walletRepository = walletRepository;
        this.usernamewalletlinkRepository = usernamewalletlinkRepository;
        this.usertypelinkRepository = usertypelinkRepository;
        this.productofferRepository = productofferRepository;
        this.productRepository = productRepository;
        this.productofferstatusRepository = productofferstatusRepository;
        this.orderRepository = orderRepository;
        this.orderstatusRepository = orderstatusRepository;
        this.offerOrderViewService = offerOrderViewService;
        this.admin_username = admin_username;
        this.paymentwalletlinkRepository = paymentwalletlinkRepository;
        this.paymentRepository = paymentRepository;
        this.negomessageRepository = negomessageRepository;
    }

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestBody Credential credential )
    {
        //Database Transaction
        credentialRepository.save(credential);
        return ResponseEntity.ok("New User Signup");
    }

//    @GetMapping("login")
//    public ResponseEntity<String> login()
//    {
//        return ResponseEntity.ok("ALL OK");
//    }

    @PostMapping("save/user/details")
    public ResponseEntity<Userdetail>  saveUserDetails(@RequestBody Userdetail userdetail,
                                                       @Autowired Wallet wallet,
                                                       @Autowired Usernamewalletlink usernamewalletlink
                                                       )

    {

        userdetailRepository.save(userdetail);
        wallet.setWalletid(String.valueOf(UUID.randomUUID()));
        wallet.setBalance(5000000);
        wallet.setState("valid");
        walletRepository.save(wallet);


        usernamewalletlink.setWalletid(wallet.getWalletid());
        usernamewalletlink.setUsername(userdetail.getUsername());
        usernamewalletlinkRepository.save(usernamewalletlink);

        return ResponseEntity.ok(userdetail);
    }

    @PostMapping("save/user/type/{username}/{usertype}")
    public ResponseEntity<Usertypelink>  saveUserType(@PathVariable String username,
                                                      @PathVariable String usertype,
                                                      Usertypelink usertypelink)
    {
        usertypelink.setLinkid(String.valueOf(UUID.randomUUID()));
        usertypelink.setUsername(username);
        usertypelink.setType(usertype);

        usertypelinkRepository.save(usertypelink);

        return ResponseEntity.ok(usertypelink);
    }

    @PostMapping("save/product/offer")
    public ResponseEntity<Productoffer> createOffer(@RequestBody Productoffer offer)
    {
        offer.setId(String.valueOf(UUID.randomUUID()));
        productofferRepository.save(offer);
        return ResponseEntity.ok(offer);
    }

    @GetMapping("get/all/products")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("get/user/details/{username}")
    public ResponseEntity<Userdetail> getUserDetails(@PathVariable String username)
    {
        return ResponseEntity.ok(userdetailRepository.findById(username).get());
    }

    @GetMapping("get/all/offers/{sellername}")
    public ResponseEntity<List<Productoffer>> getAllOffersBySellername(@PathVariable String sellername)
    {
        return ResponseEntity.ok(productofferRepository.findBySellername(sellername));
    }

    @GetMapping("get/all/offers/open") //get all offers from all sellers. This is a bad practice. We should have a separate endpoint for this. But for simplicity, I am leaving it as it is.  We can change it later.  We can also add a filter to this endpoint.  For example, we can add a filter to get offers for a particular seller.  We can also add a filter to get offers for a particular product.  We can also add a filter to get offers for a particular product and seller.  We can also add a filter to get offers for a particular product and seller and a particular state.  We can also add a filter to get offers for a particular product and seller and a particular state and a particular country.  We can also add a filter to get offers for a particular product and seller and a particular state and a particular country and a particular currency.  We can also add a filter to get offers for a particular product and seller and a particular state and a particular country and a particular currency and a particular price range.  We can also add a filter to get offers for a particular product and seller and a particular state and a particular country and a particular currency and a particular price range and
    public ResponseEntity<List<Productoffer>> getAllOpenOffers()
    {
        List<Productofferstatus> productofferstatuses = productofferstatusRepository.findByStatus("OPEN");
         List<Productoffer> productoffers = productofferstatuses.stream().map(status -> productofferRepository.findById(status.getOfferid()).get()).collect(Collectors.toList());productofferstatuses.stream().map(status -> productofferRepository.findById(status.getOfferid()).get()).collect(Collectors.toList());
        return ResponseEntity.ok(productoffers);
    }

    @PostMapping("save/order")
    public ResponseEntity<Order> createOrder(@RequestBody Order order)
    {
        order.setOrderid(String.valueOf(UUID.randomUUID()));
        orderRepository.save(order);
        return ResponseEntity.ok(order);
    }

    @GetMapping("get/orders/{sellername}")
    public ResponseEntity<List<OfferOrderView>> getOfferOrderViews(@PathVariable String sellername)
    {
        List<Productoffer> productoffers =  productofferRepository.findBySellername(sellername);productofferRepository.findBySellername(sellername);
        List<OfferOrderView> offerOrderViews = productoffers.stream().filter(productoffer -> offerOrderViewService.createOfferOrderView(productoffer.getId(),new OfferOrderView()).isPresent()).map(productoffer -> offerOrderViewService.createOfferOrderView(productoffer.getId(),new OfferOrderView()).get()).collect(Collectors.toList());
        return ResponseEntity.ok(offerOrderViews);
    }

    @PostMapping("accept/order/{orderid}")
    @Transactional
    public ResponseEntity<String> acceptOrder(@PathVariable String orderid,
                                              Payment payment,
                                              Paymentwalletlink paymentwalletlink)
    {
        //UPDATE OFFER STATUS TO PROCESSING
        Order order =  orderRepository.findById(orderid).get();
        Productoffer productoffer = productofferRepository.findById(order.getOfferid()).get();
        productofferstatusRepository.updateStatusByOfferid("PROCESSING",order.getOfferid());

        //UPDATE ORDER STATUS TO ACCEPTED/REJECTED
        orderstatusRepository.updateStatusByOrderid("ACCEPTED", orderid);

        Optional<OfferOrderView> offerOrderView = offerOrderViewService.createOfferOrderView(order.getOfferid(), new OfferOrderView());
        if(offerOrderView.isPresent())
        {
            List<Order> otherOrders =   offerOrderView.get().getOrders().stream().filter(orderEl -> !(orderEl.getOrderid().equals(orderid))).collect(Collectors.toList());
            if(!otherOrders.isEmpty())
            {
                otherOrders.stream().forEach(order1 -> orderstatusRepository.updateStatusByOrderid("REJECTED",order1.getOrderid()));
            }
        }

        //CREATE A PAYMENT WITH STATUS 'DUE'
        String buyername = order.getBuyername();
        String sellername = productoffer.getSellername();

        paymentwalletlink.setLinkid(String.valueOf(UUID.randomUUID()));
        paymentwalletlink.setPayerwallet(usernamewalletlinkRepository.findByUsername(buyername).getWalletid());
        paymentwalletlink.setPayeewallet(usernamewalletlinkRepository.findByUsername(sellername).getWalletid());
        paymentwalletlink.setEscrowwallet(usernamewalletlinkRepository.findByUsername(admin_username).getWalletid());
        paymentwalletlink.setAmount(order.getBid());
        paymentwalletlink.setPaymenttype("ORDER");

        payment.setId(String.valueOf(UUID.randomUUID()));
        payment.setOfferid(productoffer.getId());
        payment.setOrderid(order.getOrderid());
        payment.setStatus("DUE");
        payment.setPaymentwalletlink(paymentwalletlink.getLinkid());

        paymentwalletlink.setPaymentrefid(payment.getId());

        paymentwalletlinkRepository.save(paymentwalletlink);
        paymentRepository.save(payment);

        return ResponseEntity.ok("Order Accepeted");

    }

    @PostMapping("send/message")
    public ResponseEntity sendMessage(@RequestBody Negomessage negomessage)
    {
        negomessage.setId(String.valueOf(UUID.randomUUID()));
        negomessage.setTime(Instant.now());
        negomessageRepository.save(negomessage);
        return ResponseEntity.ok(negomessage);
    }


    @PostMapping("make/payment/{orderid}")
    public ResponseEntity<String> makePayment(@PathVariable String orderid)
    {
        if( paymentRepository.findByOrderid(orderid).isPresent())
        {
            //GET PAYMENT DETAILS (PAYMENT REF ID
            Payment payment = paymentRepository.findByOrderid(orderid).get();
            Paymentwalletlink paymentwalletlink = paymentwalletlinkRepository.findByPaymentrefid(payment.getId());

            //DEBIT/CREDIT OF RELEVANT WALLETS
            Wallet buyerwallet = walletRepository.findById(paymentwalletlink.getPayerwallet()).get();
            Wallet escrowwallet = walletRepository.findById(paymentwalletlink.getEscrowwallet()).get();

            buyerwallet.setBalance(buyerwallet.getBalance() - paymentwalletlink.getAmount());
            escrowwallet.setBalance(escrowwallet.getBalance() + paymentwalletlink.getAmount());

            //STATUS OF THE PAYMENT WILL CHANGE TO ESCROW
            paymentRepository.updateStatusById("ESCROW", payment.getId());

            return ResponseEntity.ok("Payment Made");
        }
        else
        {
            return ResponseEntity.badRequest().body("Payment not made");
        }


    }


}
