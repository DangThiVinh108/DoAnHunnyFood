package com.example.doanhunnyfood.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.doanhunnyfood.dao.AppDatabase;
import com.example.doanhunnyfood.dao.Order_detailDao;
import com.example.doanhunnyfood.entydi.Order_detail;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Order_detailRepository {
    private Order_detailDao orderDetailDao;
    private LiveData<List<Order_detail>> AllOrder_detail;

    ExecutorService executorService;

    public Order_detailRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.orderDetailDao = db.orderDetailDao();
        this.AllOrder_detail = orderDetailDao.findAll();
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public LiveData<List<Order_detail>> getAllOrder_detail() {
        return AllOrder_detail;
    }

    public void insertOrderDetails(List<Order_detail> orderDetails) {
        executorService.execute(() -> {
            for (Order_detail orderDetail : orderDetails) {
                orderDetailDao.insert(orderDetail);
            }
        });
    }
}
