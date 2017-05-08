package com.innso.apparkar.ui.viewModels;

import com.innso.apparkar.api.models.Parking;
import com.innso.apparkar.ui.interfaces.GenericItem;

public class ParkingViewModel implements GenericItem {

    public String name;
    public String address;
    public int cartCost;
    public int bikeCost;
    public int motorbikeCost;
    public int countLikes;
    public int countDislikes;
    public String costDescription;
    public int countComments;

    public boolean hasCost;

    public ParkingViewModel(Parking parking) {
        this.name = parking.getName();
        this.address = parking.getReferencePoint().getAddress();
        this.countLikes = parking.getCountLikes();
        this.countDislikes = parking.getCountDislikes();
        this.hasCost = parking.hasCost();
        if (hasCost) {
            this.costDescription = parking.getPrices().getDescription();
            this.cartCost = parking.getPrices().getCarCost();
            this.bikeCost = parking.getPrices().getBikeCost();
            this.motorbikeCost = parking.getPrices().getMotorbikeCost();
        }
    }

    @Override
    public Object getData() {
        return this;
    }

    @Override
    public int getType() {
        return 0;
    }
}
