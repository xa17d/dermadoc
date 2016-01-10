package at.tuwien.telemedizin.dermadoc.server.services;

import at.tuwien.telemedizin.dermadoc.entities.GeoLocation;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.entities.rest.UserList;
import at.tuwien.telemedizin.dermadoc.server.persistence.dao.hibernate.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by daniel on 10.01.2016.
 */
@Service
public class PhysicianService {

    @Autowired
    private UserRepository userRepository;

    private int nearestCount = 10;

    /**
     * List n nearest physician for a specific GeoLocation (n defined by constant in this class - default 10)
     * @param geoLocation Location to search at
     * @return Ordered list with nearest Physicians. Nearest first.
     */
    public List<Physician> listNearestPhysicians(GeoLocation geoLocation) {
        Iterable<Physician> physicians = userRepository.listPhysicians();

        // Calculate Distances to all Physicians

        ArrayList<PhysicianDistance> physicianDistances = new ArrayList<>();

        for (Physician p : physicians) {
            if (p.getLocation() != null) {
                PhysicianDistance d;

                if (geoLocation == null || p.getLocation() == null) {
                    d = new PhysicianDistance(p, Double.MAX_VALUE);
                }
                else {
                    d = new PhysicianDistance(p, GeoLocation.Distance(geoLocation, p.getLocation()));
                }

                physicianDistances.add(d);
            }
        }

        // Sort by distance

        Collections.sort(physicianDistances, new Comparator<PhysicianDistance>() {
            @Override
            public int compare(PhysicianDistance o1, PhysicianDistance o2) {
                if (o1.getDistance() < o2.getDistance()) return -1;
                if (o1.getDistance() > o2.getDistance()) return 1;
                return 0;
            }
        });

        // Generate result list with nearest physicians

        List<Physician> result = new ArrayList<Physician>();

        int resultSize = Math.min(nearestCount, physicianDistances.size());
        for (int i=0; i<resultSize; i++) {
            result.add(physicianDistances.get(i).getPhysician());
        }

        return result;
     }

    private class PhysicianDistance {
        public PhysicianDistance(Physician physician, double distance) {
            this.physician = physician;
            this.distance = distance;
        }

        private Physician physician;
        private double distance;

        public Physician getPhysician() { return physician; }
        public double getDistance() { return distance; }
    }
}
