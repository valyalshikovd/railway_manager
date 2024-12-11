package com.example.railway_manager.service.railway.impl;


import com.example.railway_manager.dto.railways.CarriageDTO;
import com.example.railway_manager.model.Carriage;
import com.example.railway_manager.model.CarriageType;
import com.example.railway_manager.model.Locomotive;
import com.example.railway_manager.model.LocomotiveType;
import com.example.railway_manager.repository.railway.CarriageRepository;
import com.example.railway_manager.repository.railway.CarriageTypeRepository;
import com.example.railway_manager.repository.railway.LocomotiveRepository;
import com.example.railway_manager.repository.railway.LocomotiveTypeRepository;
import com.example.railway_manager.service.railway.TestDataGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TestDataGeneratorImpl implements TestDataGenerator {

    private final CarriageRepository carriageRepository;
    private final CarriageTypeRepository carriageTypeRepository;
    private final LocomotiveTypeRepository locomotiveTypeRepository;
    private final LocomotiveRepository locomotiveRepository;

    @Override
    public void generateTestData() {

        CarriageType car1 = new CarriageType();
        car1.setNameType("v1");
        car1.setCapacity(200L);
        CarriageType car2 = new CarriageType();
        car1.setNameType("v2");
        car1.setCapacity(300L);
        CarriageType car3 = new CarriageType();
        car1.setNameType("v3");
        car1.setCapacity(400L);
        carriageTypeRepository.save(car1);
        carriageTypeRepository.save(car2);
        carriageTypeRepository.save(car3);

        Random random = new Random();

        for (int i = 0; i < 334; i++) {

            Carriage carrEntity = new Carriage();
            carrEntity.setCarriage_state(random.nextLong(0,100));
            carrEntity.setCountOfEngineHours(random.nextLong(0,1000));
            carrEntity.setCarriageType(car1);
            carrEntity.setDateOfPurchase(LocalDate.now());
            carriageRepository.save(carrEntity);


            Carriage carrEntity2 = new Carriage();
            carrEntity2.setCarriage_state(random.nextLong(0, 1000));
            carrEntity2.setCountOfEngineHours(random.nextLong(0,1000));
            carrEntity2.setCarriageType(car2);
            carrEntity2.setDateOfPurchase(LocalDate.now());
            carriageRepository.save(carrEntity2);


            Carriage carrEntity3 = new Carriage();
            carrEntity3.setCarriage_state(random.nextLong(0,100));
            carrEntity3.setCountOfEngineHours(random.nextLong(0,1000));
            carrEntity3.setDateOfPurchase(LocalDate.now());
            carrEntity3.setCarriageType(car3);
            carriageRepository.save(carrEntity3);

        }


        LocomotiveType lt = new LocomotiveType();
        lt.setName("v1");
        lt.setPower(200L);
        locomotiveTypeRepository.save(lt);

        LocomotiveType lt1 = new LocomotiveType();
        lt1.setName("v1");
        lt1.setPower(300L);
        locomotiveTypeRepository.save(lt1);

        LocomotiveType lt2 = new LocomotiveType();
        lt2.setName("v1");
        lt2.setPower(400L);
        locomotiveTypeRepository.save(lt2);


        for (int i = 0; i < 334; i++) {

            Locomotive locomotiveEnt = new Locomotive();
            locomotiveEnt.setLocomotive_state(random.nextLong(0,100));
            locomotiveEnt.setCountOfEngineHours(random.nextLong(0,1000));
            locomotiveEnt.setDateOfPurchase(LocalDate.now());
            locomotiveEnt.setLocomotiveType(lt);
            locomotiveRepository.save(locomotiveEnt);

            locomotiveEnt = new Locomotive();
            locomotiveEnt.setLocomotive_state(random.nextLong(0,100));
            locomotiveEnt.setCountOfEngineHours(random.nextLong(0,1000));
            locomotiveEnt.setDateOfPurchase(LocalDate.now());
            locomotiveEnt.setLocomotiveType(lt1);
            locomotiveRepository.save(locomotiveEnt);

            locomotiveEnt = new Locomotive();
            locomotiveEnt.setLocomotive_state(random.nextLong(0,100));
            locomotiveEnt.setCountOfEngineHours(random.nextLong(0,1000));
            locomotiveEnt.setDateOfPurchase(LocalDate.now());
            locomotiveEnt.setLocomotiveType(lt2);
            locomotiveRepository.save(locomotiveEnt);



        }
    }
}
