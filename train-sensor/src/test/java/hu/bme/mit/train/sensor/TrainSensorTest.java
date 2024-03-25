package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.google.common.collect.Table;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

import java.time.LocalTime;

public class TrainSensorTest {

    private TrainSensorImpl trainSensor;
    private TrainUser mockedTrainUser;
    private TrainController mockedTrainController;

    @Before
    public void setUp() {
        mockedTrainController = mock(TrainController.class);
        mockedTrainUser =  mock(TrainUser.class);
        trainSensor = new TrainSensorImpl(mockedTrainController,mockedTrainUser);
        doAnswer(answer -> when(mockedTrainUser.getAlarmState()).thenReturn((Boolean) answer.getArguments()[0])).when(mockedTrainUser).setAlarmState(anyBoolean());
        doAnswer(answer -> when(mockedTrainController.getReferenceSpeed()).thenReturn((Integer) answer.getArguments()[0])).when(mockedTrainController).setSpeedLimit(anyInt());
    }

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

    @Test
    public void testAlarm()
    {
        trainSensor.overrideSpeedLimit(550);
        assertEquals(true, mockedTrainUser.getAlarmState());
        trainSensor.overrideSpeedLimit(490);
        assertEquals(false, mockedTrainUser.getAlarmState());
    }
}
