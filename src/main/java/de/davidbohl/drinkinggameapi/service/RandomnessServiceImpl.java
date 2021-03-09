package de.davidbohl.drinkinggameapi.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomnessServiceImpl implements RandomnessService {

    private Random random;

    public RandomnessServiceImpl() {
        this.random = new Random();
    }

    @Override
    public int getRandomInt(int bound) {
        return random.nextInt(bound);
    }
}
