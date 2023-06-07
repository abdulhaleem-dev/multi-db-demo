package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("matters")
public class MatterApi {

  private final MatterRepository matterRepository;

  public MatterApi(MatterRepository matterRepository) {
    this.matterRepository = matterRepository;
  }

  @GetMapping
  public ResponseEntity<List<Matter>> addMatter(@RequestParam String description) {
    Matter m = new Matter();
    m.setDescription(description);
    matterRepository.save(m);
    return ResponseEntity.ok(matterRepository.findAll());
  }
}
