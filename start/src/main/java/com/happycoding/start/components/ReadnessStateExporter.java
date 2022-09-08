package com.happycoding.start.components;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ReadnessStateExporter {

    @EventListener
    public void onStateChange(AvailabilityChangeEvent<ReadinessState> event){
        if(event.getState() == ReadinessState.ACCEPTING_TRAFFIC){
            System.out.println("::::::accepting_traffic");
        } else if(event.getState() == ReadinessState.REFUSING_TRAFFIC){
            System.out.println("::::::refusing_traffic");
        }
    }

    @EventListener
    public void onLiveStateChange(AvailabilityChangeEvent<LivenessState> event){
        if(event.getState() == LivenessState.CORRECT){
            System.out.println("::::::correct");
        } else if(event.getState() == LivenessState.BROKEN){
            System.out.println("::::::broken");
        }
    }
}
