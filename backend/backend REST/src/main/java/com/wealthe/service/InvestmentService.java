package com.wealthe.service;
import com.wealthe.dto.request.InvestmentRequest; import com.wealthe.dto.response.InvestmentResponse; import com.wealthe.entity.Investment; import com.wealthe.exception.ResourceNotFoundException; import com.wealthe.repository.InvestmentRepository; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.math.RoundingMode; import java.util.List;
@Service public class InvestmentService{
 private final InvestmentRepository repo; private final UserService users; public InvestmentService(InvestmentRepository r,UserService u){repo=r;users=u;}
 public List<InvestmentResponse> all(Long userId){return repo.findByUserIdOrderByIdDesc(userId).stream().map(this::response).toList();} public InvestmentResponse one(Long id){return response(get(id));}
 public InvestmentResponse create(InvestmentRequest r){Investment i=new Investment();i.setUser(users.getById(r.userId()));i.setName(r.name());i.setType(r.type());i.setInvestedAmount(r.investedAmount());i.setCurrentValue(r.currentValue());return response(repo.save(i));}
 public InvestmentResponse update(Long id,InvestmentRequest r){Investment i=get(id);i.setName(r.name());i.setType(r.type());i.setInvestedAmount(r.investedAmount());i.setCurrentValue(r.currentValue());return response(repo.save(i));}
 public void delete(Long id){repo.delete(get(id));} public Investment get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Investment not found"));}
 private InvestmentResponse response(Investment i){BigDecimal gain=i.getCurrentValue().subtract(i.getInvestedAmount());BigDecimal pct=i.getInvestedAmount().signum()==0?BigDecimal.ZERO:gain.multiply(BigDecimal.valueOf(100)).divide(i.getInvestedAmount(),2,RoundingMode.HALF_UP);return new InvestmentResponse(i.getId(),i.getUser().getId(),i.getName(),i.getType(),i.getInvestedAmount(),i.getCurrentValue(),gain,pct);}
}
