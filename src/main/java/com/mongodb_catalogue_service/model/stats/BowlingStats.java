package com.mongodb_catalogue_service.model.stats;


import com.mongodb_catalogue_service.model.stats.bowling_stats.ODIMatches;
import com.mongodb_catalogue_service.model.stats.bowling_stats.T20IMatches;
import com.mongodb_catalogue_service.model.stats.bowling_stats.TestMatches;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BowlingStats {

TestMatches testMatches;
ODIMatches odiMatches;
T20IMatches t20IMatches;

}
