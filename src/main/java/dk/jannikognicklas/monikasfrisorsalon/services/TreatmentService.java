package dk.jannikognicklas.monikasfrisorsalon.services;

import dk.jannikognicklas.monikasfrisorsalon.models.HairTreatment;
import dk.jannikognicklas.monikasfrisorsalon.repositories.TreatmentRepository;

import java.util.List;

public class TreatmentService {
    private final TreatmentRepository treatmentRepository;

    public TreatmentService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    public List<HairTreatment> findAllTreatments() {
        return treatmentRepository.findAllTreatments();
    }

    public HairTreatment findHairTreatmentById(int id) { return treatmentRepository.findTreatmentById(id);}
}
