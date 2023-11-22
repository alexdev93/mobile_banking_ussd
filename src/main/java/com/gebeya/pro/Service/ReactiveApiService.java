package com.gebeya.pro.Service;

import com.gebeya.pro.Model.TopUp;
import com.gebeya.pro.Repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
@Service
public class ReactiveApiService {
    @Autowired
    WebClient webClientForTopUp;

    @Autowired
    private AccountRepository accountRepository;

    public TopUp topUpFetch(Long paramValue)
    {
        TopUp jsonResponse = webClientForTopUp.get()
                .uri("/topup.php/"+paramValue)
                .retrieve()
                .onStatus(status ->status.is4xxClientError(), response->{
                    //throw new ErrorHandler(HttpStatus.BAD_REQUEST,"bad request occurred");
                    throw new Error("adfasf");
                })
                .onStatus(status -> status.is5xxServerError(), response ->{
                    //  throw new ErrorHandler(HttpStatus.INTERNAL_SERVER_ERROR,"unknown server error occurred. please try again later");
                    throw new Error("adfasf");
                })
                .bodyToMono(TopUp.class)
                .block();

        return jsonResponse;
    }

//    public TopUpResponseDto topUp(TopUpRequestDto topup)
//    {
//        // if(!accountRepo.existsById(topup.getAccount_no()))
//        // throw new ErrorHandler(HttpStatus.NOT_FOUND,"user account number could not be found");
//        Account account = accountRepo.findById(topup.getAccount_no()).get();
//        // if(account.getBalance()<topup.getAmount())
//        // throw new ErrorHandler(HttpStatus.BAD_REQUEST, "Insufficient balance");
//        TopUpResponseDto returnedResponse = topUpFetch(topup.getAmount());
//        account.setBalance(account.getBalance()-topup.getAmount());
//        accountRepo.save(account);
//        return returnedResponse;
//
//
//    }
}
