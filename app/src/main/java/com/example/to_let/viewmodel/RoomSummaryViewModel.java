package com.example.to_let.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.to_let.entity.RoomSummaryEntity;
import com.example.to_let.repository.RoomSummaryDataFactory;
import com.example.to_let.repository.RoomSummaryRepository;
import com.parse.ParseObject;

public class RoomSummaryViewModel extends AndroidViewModel {
    //variable to hold a reference to repository
    private RoomSummaryRepository roomSummaryRepository;
    //LiveData variable to cache list of rooms
    private LiveData<PagedList<ParseObject>> mAllRooms;
    private LiveData<PagedList<ParseObject>> mAllCurrentUserRooms;

    RoomSummaryDataFactory roomSummaryDataSourceFactory;

    public RoomSummaryViewModel(@NonNull Application application) {
        super(application);
        roomSummaryRepository = new RoomSummaryRepository(application);
    }

    //completely hides the implementation from the UI
    public LiveData<PagedList<ParseObject>> getmAllRooms(){
        //use repository to get rooms
        mAllRooms = roomSummaryRepository.getAllRooms();
        return mAllRooms;
    }

    public LiveData<PagedList<ParseObject>> getmAllCurrentUserRooms(String currentUserID){
        mAllCurrentUserRooms = roomSummaryRepository.getCurrentlyLoggedinUserRooms(currentUserID);
        return mAllCurrentUserRooms;
    }

    //invalidate rooms datasource
    public void invalidateRoomSummaryDatasource(){
        //datasource.invalidate
        roomSummaryRepository.invalidateRoomSummaryDatasource();
    }

    //invalidate currentUserRooms datasource
    public void invalidateCurrentUserDatasource(String currentUserID){
        roomSummaryRepository.invalidateRoomSummaryDatasource(currentUserID);
    }


    public void insert(RoomSummaryEntity roomSummaryEntity){
        roomSummaryRepository.insert(roomSummaryEntity);
    }
}
