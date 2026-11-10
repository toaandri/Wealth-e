package com.wealthe.service;
import com.wealthe.dto.request.AssetRequest; import com.wealthe.dto.response.AssetResponse; import com.wealthe.entity.Asset; import com.wealthe.exception.ResourceNotFoundException; import com.wealthe.repository.AssetRepository; import org.springframework.stereotype.Service; import java.util.List;
@Service public class AssetService{
 private final AssetRepository repo; private final UserService users; public AssetService(AssetRepository r,UserService u){repo=r;users=u;} public List<AssetResponse> all(Long userId){return repo.findByUserIdOrderByIdDesc(userId).stream().map(this::response).toList();} public AssetResponse one(Long id){return response(get(id));}
 public AssetResponse create(AssetRequest r){Asset a=new Asset();a.setUser(users.getById(r.userId()));a.setName(r.name());a.setType(r.type());a.setValue(r.value());return response(repo.save(a));}
 public AssetResponse update(Long id,AssetRequest r){Asset a=get(id);a.setName(r.name());a.setType(r.type());a.setValue(r.value());return response(repo.save(a));} public void delete(Long id){repo.delete(get(id));}
 public Asset get(Long id){return repo.findById(id).orElseThrow(()->new ResourceNotFoundException("Asset not found"));} private AssetResponse response(Asset a){return new AssetResponse(a.getId(),a.getUser().getId(),a.getName(),a.getType(),a.getValue());}
}
