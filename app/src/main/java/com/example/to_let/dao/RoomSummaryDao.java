package com.example.to_let.dao;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.to_let.entity.RoomSummaryEntity;

import java.util.List;


public interface RoomSummaryDao {
    @Insert
    void insert(RoomSummaryEntity roomSummary);

    @Query("SELECT * from room_summary")
    MutableLiveData<List<RoomSummaryEntity>> getAllRooms();
}
