package com.mongodb_catalogue_service.model.stats;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Stats {
    BattingStats battingStats;
    BowlingStats bowlingStats;

}
