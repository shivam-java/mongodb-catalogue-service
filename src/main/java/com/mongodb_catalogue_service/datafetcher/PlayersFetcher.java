package com.mongodb_catalogue_service.datafetcher;

import com.mongodb_catalogue_service.error.DataNotAvaialableException;
import com.mongodb_catalogue_service.model.Player;
import com.mongodb_catalogue_service.repository.PlayerRepository;
import com.mongodb_catalogue_service.service.DefaultPlayerService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@DgsComponent
public class PlayersFetcher {

    @Autowired
    private DefaultPlayerService defaultPlayerService;

    @Autowired
    private PlayerRepository playerRepository;

    @DgsQuery
     public List<Player> players(@InputArgument String name) throws IOException {

        if(name!=null)
        {   List<Player> players=new ArrayList<>();
            Player byName = playerRepository.findByName(name);
            if (byName==null)
            {
                throw new DataNotAvaialableException("Data Not Available For Player "+name);
            }
            players.add(byName);
            return players;
        }
        List<Player> players = defaultPlayerService.getPlayers();
        System.out.println(players);
        return players;
    }
}
