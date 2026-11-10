package com.wealthe.service;
import com.wealthe.dto.response.NetWorthResponse; import com.wealthe.entity.Account; import com.wealthe.repository.*; import org.springframework.stereotype.Service;
import java.math.BigDecimal;
@Service public class NetWorthService{
 private final AccountRepository accounts; private final InvestmentRepository investments; private final AssetRepository assets; private final LiabilityRepository liabilities;
 public NetWorthService(AccountRepository a,InvestmentRepository i,AssetRepository as,LiabilityRepository l){accounts=a;investments=i;assets=as;liabilities=l;}
 public NetWorthResponse get(Long userId){
  BigDecimal accountAssets=accounts.findByUserIdOrderByIdDesc(userId).stream().map(Account::getBalance).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal investmentAssets=investments.findByUserIdOrderByIdDesc(userId).stream().map(x->x.getCurrentValue()).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal otherAssets=assets.findByUserIdOrderByIdDesc(userId).stream().map(x->x.getValue()).reduce(BigDecimal.ZERO,BigDecimal::add);
  BigDecimal total=accountAssets.add(investmentAssets).add(otherAssets);
  BigDecimal debt=liabilities.findByUserIdOrderByIdDesc(userId).stream().map(x->x.getRemainingBalance()).reduce(BigDecimal.ZERO,BigDecimal::add);
  return new NetWorthResponse(userId,accountAssets,investmentAssets,otherAssets,total,debt,total.subtract(debt));
 }
}
