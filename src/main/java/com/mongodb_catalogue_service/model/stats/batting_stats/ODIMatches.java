package com.mongodb_catalogue_service.model.stats.batting_stats;

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
    int runs;
    int hundreds;
    int fifties;
    int avg;
    int strikeRate;



}
