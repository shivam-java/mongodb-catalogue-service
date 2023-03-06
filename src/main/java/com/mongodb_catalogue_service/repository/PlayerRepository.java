package com.mongodb_catalogue_service.repository;

import com.mongodb_catalogue_service.model.Player;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends MongoRepository<Player,String>
{

    @Aggregation
            (pipeline =
                    {
                            "{$match:{'profile.name':?#{#name}}}"
                    }
            )
    Player findByName(@Param("name") String name);
}
