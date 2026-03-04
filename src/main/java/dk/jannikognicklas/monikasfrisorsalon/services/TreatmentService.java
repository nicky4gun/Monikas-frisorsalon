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

    public HairTreatment findHairTreatmentById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Ugyldigt behandlings id");
        }

        HairTreatment treatment = treatmentRepository.findTreatmentById(id);

        if (treatment == null) {
            throw new IllegalArgumentException("Ingen behandling fundet");
        }

        return treatment;
    }
}
