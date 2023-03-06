package com.mongodb_catalogue_service.model.stats;


import com.mongodb_catalogue_service.model.stats.batting_stats.ODIMatches;
import com.mongodb_catalogue_service.model.stats.batting_stats.T20IMatches;
import com.mongodb_catalogue_service.model.stats.batting_stats.TestMatches;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BattingStats {

TestMatches testMatches;
ODIMatches odiMatches;
T20IMatches t20IMatches;

}
