package dk.jannikognicklas.monikasfrisorsalon.repositories;

import dk.jannikognicklas.monikasfrisorsalon.infrastructure.DbConfig;
import dk.jannikognicklas.monikasfrisorsalon.models.Employee;
import dk.jannikognicklas.monikasfrisorsalon.models.HairTreatment;
import dk.jannikognicklas.monikasfrisorsalon.models.enums.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentRepository {
    private final DbConfig config;

    public TreatmentRepository(DbConfig config) {
        this.config = config;
    }

    public List<HairTreatment> findAllTreatments() {
        List<HairTreatment> treatments = new ArrayList<>();
        String sql = "SELECT * FROM hair_treatments";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                treatments.add(mapTreatment(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("An error occurred trying to get all treatments" + e);
        }

        return treatments;
    }
    public HairTreatment findTreatmentById(int treatmentId) {
        String sql = "SELECT * FROM hair_treatments WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, treatmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapTreatment(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting getting treatment with id: " + treatmentId, e);
        }

        return null;
    }

    private HairTreatment mapTreatment(ResultSet rs) throws SQLException {
        return new HairTreatment(
                rs.getInt("id"),
                Treatment.valueOf(rs.getString("hair_treatment")),
                rs.getInt("duration"),
                rs.getDouble("price")
        );
    }
}
