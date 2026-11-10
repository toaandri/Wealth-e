package com.wealthe.service;
import com.wealthe.dto.request.GoalRequest; import com.wealthe.dto.response.GoalResponse; import com.wealthe.entity.Goal; import com.wealthe.enumeration.GoalStatus; import com.wealthe.exception.ResourceNotFoundException; import com.wealthe.repository.GoalRepository; import org.springframework.stereotype.Service;
import java.math.BigDecimal; import java.math.RoundingMode; import java.util.List;
@Service public class GoalService{
 private final GoalRepository repo; private final UserService users; public GoalService(GoalRepository r,UserService u){repo=r;users=u;}
 public List<GoalResponse> all(Long userId){return repo.findByUserIdOrderByDeadlineAsc(userId).stream().map(this::response).toList();}
 public GoalResponse one(Long id){return response(get(id));}
 public GoalResponse create(GoalRequest r){Goal g=new Goal();g.setUser(users.getById(r.userId()));g.setName(r.name());g.setTargetAmount(r.targetAmount());g.setCurrentAmount(r.currentAmount());g.setDeadline(r.deadline());g.setStatus(r.status()==null?GoalStatus.ACTIVE:r.status());return response(repo.save(g));}
 public GoalResponse update(Long id,GoalRequest r){Goal g=get(id);g.setName(r.name());g.setTargetAmount(r.targetAmount());g.setCurrentAmount(r.currentAmount());g.setDeadline(r.deadline());if(r.status()!=null)g.setStatus(r.status());return response(repo.save(g));}
 public void delete(Long id){repo.delete(get(id));} private Goal get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Goal not found"));}
 private GoalResponse response(Goal g){BigDecimal p=g.getTargetAmount().signum()==0?BigDecimal.ZERO:g.getCurrentAmount().multiply(BigDecimal.valueOf(100)).divide(g.getTargetAmount(),2,RoundingMode.HALF_UP);return new GoalResponse(g.getId(),g.getUser().getId(),g.getName(),g.getTargetAmount(),g.getCurrentAmount(),p,g.getDeadline(),g.getStatus());}
}
