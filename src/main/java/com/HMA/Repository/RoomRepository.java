package com.HMA.Repository;

import com.HMA.Entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {

}