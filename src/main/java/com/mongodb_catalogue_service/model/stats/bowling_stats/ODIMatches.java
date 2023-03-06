package com.mongodb_catalogue_service.model.stats.bowling_stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ODIMatches {
    int matches;
    int innings;
    int wickets;
    double economy;
    int fiveWicketsHauls;
    double avg;



}
