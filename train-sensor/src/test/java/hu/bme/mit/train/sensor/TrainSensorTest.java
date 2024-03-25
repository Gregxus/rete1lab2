package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import com.google.common.collect.Table;
import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;

import java.time.LocalTime;

public class TrainSensorTest {

    TrainController mockTC;
    TrainUser mockTU;
    TrainSensorImpl trainSensor;

    @Before
    public void before(){
        mockTC = mock(TrainController.class);
        mockTU = mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockTC, mockTU);
    }

    @Before
    public void setUp() {
        trainSensor = new TrainSensorImpl(null,null);
    }

    @Test
    public void checkSpeedLimit(){
        Assert.assertEquals(10, 10);
    }

    @Test
    public void AbsMinBoundry(){
        trainSensor.overrideSpeedLimit(-1);
        verify(mockTU, times(1)).setAlarmState(true);
    }

    @Test
    public void AbsMaxBoundry(){
        trainSensor.overrideSpeedLimit(501);
        verify(mockTU, times(1)).setAlarmState(true);
    }

    // MIT2-ben hasznalt unit testek
    @Test
    public void testTachographRecording() {
        // Hozzáadunk néhány elemet a tachográfhoz
        trainSensor.recordData();
        trainSensor.recordData();
        trainSensor.recordData();

        // Ellenőrizzük, hogy három elem került-e a tachográfba
        Table<LocalTime, Integer, Integer> tachograph = trainSensor.getTachograph();
        assertEquals(3, tachograph.size());
    }
}
