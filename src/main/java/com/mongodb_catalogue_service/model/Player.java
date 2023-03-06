package com.mongodb_catalogue_service.model;


import com.mongodb_catalogue_service.model.stats.Stats;
import lombok.*;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Document("playersModel")
public class Player {
  Profile profile;
  Stats stats;
  CarrerSummary carrerSummary;
}
